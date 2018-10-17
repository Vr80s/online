package com.xczhihui.medical.constitution.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.constitution.mapper.*;
import com.xczhihui.medical.constitution.model.*;
import com.xczhihui.medical.constitution.service.IConstitutionService;
import com.xczhihui.medical.constitution.vo.AnalysisResult;
import com.xczhihui.medical.constitution.vo.ConstitutionScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2018-10-15
 */
@Service
public class ConstitutionServiceImpl implements IConstitutionService {

    @Autowired
    private MedicalConstitutionQuestionBankMapper medicalQuestionBankMapper;
    @Autowired
    private MedicalConstitutionQuestionRecordMapper medicalQuestionRecordMapper;
    @Autowired
    private MedicalConstitutionQuestionRecordDetailsMapper medicalQuestionRecordDetailsMapper;
    @Autowired
    private MedicalConstitutionMapper medicalConstitutionMapper;
    @Autowired
    private MedicalConstitutionProposalMapper medicalConstitutionProposalMapper;
    @Autowired
    private MedicalConstitutionRecipeMapper medicalConstitutionRecipesMapper;

    public static Map<String,Integer> scoreMap = new HashMap<String,Integer>();

    static{
        scoreMap.put("A",0);
        scoreMap.put("B",1);
        scoreMap.put("C",2);
    }

    @Override
    public List<MedicalConstitutionQuestionBank> getQuestionBank() {
        return medicalQuestionBankMapper.selectAll();
    }

    @Override
    public AnalysisResult saveRecord(String userId, String birthday, Integer sex, List<MedicalConstitutionQuestionRecordDetails> medicalQuestionRecordDetailsList){
        AnalysisResult analysis = analysis(medicalQuestionRecordDetailsList,false);
        MedicalConstitutionQuestionRecord medicalQuestionRecord = new MedicalConstitutionQuestionRecord();
        medicalQuestionRecord.setUserId(userId);
        medicalQuestionRecord.setBirthday(birthday);
        medicalQuestionRecord.setSex(sex);
        medicalQuestionRecord.setCreateTime(new Date());
        medicalQuestionRecord.setResult(analysis.getConstitution());
        medicalQuestionRecordMapper.insert(medicalQuestionRecord);
        for (int i = 0; i < medicalQuestionRecordDetailsList.size(); i++) {
            MedicalConstitutionQuestionRecordDetails medicalQuestionRecordDetails = medicalQuestionRecordDetailsList.get(i);
            medicalQuestionRecordDetails.setRecordId(medicalQuestionRecord.getId());
            medicalQuestionRecordDetails.setCreateTime(new Date());
//            medicalQuestionRecordDetailsMapper.insert(medicalQuestionRecordDetails);
        }
        medicalQuestionRecordDetailsMapper.insertBatch(medicalQuestionRecordDetailsList);
        return analysis;
    }

    @Override
    public List<MedicalConstitution> getConstitution() {
        return medicalConstitutionMapper.getAll();
    }

    @Override
    public AnalysisResult getRecordById(Integer recordId) {
        List<MedicalConstitutionQuestionRecordDetails> medicalQuestionRecordDetailsList = medicalQuestionRecordDetailsMapper.selectByRecordId(recordId);
        return analysis(medicalQuestionRecordDetailsList,true);
    }

    @Override
    public Page<MedicalConstitutionQuestionRecord> getRecordListByPage(int current, int size) {
        Page<MedicalConstitutionQuestionRecord> page = new Page<>(current,size);
        List<MedicalConstitutionQuestionRecord> medicalQuestionRecordList = medicalQuestionRecordDetailsMapper.selectRecordListByPage(page);
        page.setRecords(medicalQuestionRecordList);
        return page;
    }

    private AnalysisResult analysis(List<MedicalConstitutionQuestionRecordDetails> medicalConstitutionQuestionRecordDetailsList, boolean more) {
        for (int i = 0; i < medicalConstitutionQuestionRecordDetailsList.size(); i++) {
            MedicalConstitutionQuestionRecordDetails medicalConstitutionQuestionRecordDetails = medicalConstitutionQuestionRecordDetailsList.get(i);
            medicalConstitutionQuestionRecordDetails.setScore(scoreMap.get(medicalConstitutionQuestionRecordDetails.getAnswer()));
        }

        List result = new ArrayList();
        List<MedicalConstitution> all = medicalConstitutionMapper.getAll();
        for (int i = 0; i < all.size(); i++) {
            Integer score = getAnalysisScore(all.get(i),medicalConstitutionQuestionRecordDetailsList);
            result.add(new ConstitutionScore(all.get(i),score));
        }
        List<ConstitutionScore> constitutionScoreList = sortAndFilter(result);
        AnalysisResult analysisResult = new AnalysisResult();
        if(more){
            analysisResult.setAllConstitutionScoreList(result);
            analysisResult.setMedicalConstitutionQuestionRecordDetailsList(medicalConstitutionQuestionRecordDetailsList);
        }
        analysisResult.setConstitutionScoreList(constitutionScoreList);
        if(constitutionScoreList.size() > 0){
            StringBuilder constitution = new StringBuilder();
            List<MedicalConstitutionProposal> medicalConstitutionProposals = new ArrayList<>();
            List<MedicalConstitutionRecipe> medicalConstitutionRecipes = new ArrayList<>();

            for (int i = 0; i < constitutionScoreList.size(); i++) {
                ConstitutionScore constitutionScore = constitutionScoreList.get(i);

                if(constitution.length() > 0){
                    constitution.append("、");
                }
                constitution.append(constitutionScore.getConstitution().getTag());

                MedicalConstitutionProposal medicalConstitutionProposal = medicalConstitutionProposalMapper.selectByConstitutionId(constitutionScore.getConstitution().getId());
                medicalConstitutionProposals.add(medicalConstitutionProposal);

                List<MedicalConstitutionRecipe> mcr = medicalConstitutionRecipesMapper.selectByConstitutionId(constitutionScore.getConstitution().getId());
                medicalConstitutionRecipes.addAll(mcr);
            }
            analysisResult.setConstitution(constitution.toString());
            analysisResult.setMedicalConstitutionProposals(medicalConstitutionProposals);
            analysisResult.setMedicalConstitutionRecipes(medicalConstitutionRecipes);
        }
        return analysisResult;
    }

    private List<ConstitutionScore> sortAndFilter(List<ConstitutionScore> list){
        for (int i = 0; i < list.size()-1; i++) {
            for (int j = 0; j < list.size()-i-1; j++) {
                if(list.get(j).getScore() < list.get(j+1).getScore()){
                    ConstitutionScore constitutionScore = list.get(j);
                    list.set(j,list.get(j + 1));
                    list.set(j + 1,constitutionScore);
                }
            }
        }
        List<ConstitutionScore> constitutionScoreList = new ArrayList<>();
        if(list.get(0).getScore() > 0){
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getScore() == list.get(0).getScore() && list.get(i).getScore() > 0){
                    constitutionScoreList.add(list.get(i));
                }
            }
            if(constitutionScoreList.size() == 1 && list.get(1).getScore() != 0 && list.get(1).getScore() != list.get(2).getScore()){
                constitutionScoreList.add(list.get(1));
            }
        }
        return constitutionScoreList;
    }

    private Integer getAnalysisScore(MedicalConstitution medicalQuestionBankConstitution, List<MedicalConstitutionQuestionRecordDetails> medicalQuestionRecordDetailsList) {
        Integer subject1Score = getAnalysisScoreBySubject(medicalQuestionBankConstitution.getSubject1(), medicalQuestionRecordDetailsList);
        Integer subject2Score = getAnalysisScoreBySubject(medicalQuestionBankConstitution.getSubject2(), medicalQuestionRecordDetailsList);
        Integer subject3Score = getAnalysisScoreBySubject(medicalQuestionBankConstitution.getSubject3(), medicalQuestionRecordDetailsList);
        Integer subject4Score = getAnalysisScoreBySubject(medicalQuestionBankConstitution.getSubject4(), medicalQuestionRecordDetailsList);
        Integer subject5Score = getAnalysisScoreBySubject(medicalQuestionBankConstitution.getSubject5(), medicalQuestionRecordDetailsList);
        return subject1Score + subject2Score + subject3Score + subject4Score + subject5Score;
    }

    private Integer getAnalysisScoreBySubject(String subject, List<MedicalConstitutionQuestionRecordDetails> medicalQuestionRecordDetailsList) {
        Integer score = null;
        if(!subject.equals("10-1") && !subject.equals("10-2")){
            for (MedicalConstitutionQuestionRecordDetails medicalQuestionRecordDetails : medicalQuestionRecordDetailsList) {
                if(subject.equals(medicalQuestionRecordDetails.getQuestionNo())){
                    score = medicalQuestionRecordDetails.getScore();
                }
            }
        } else {
            MedicalConstitutionQuestionRecordDetails mqrd = null;
            for (MedicalConstitutionQuestionRecordDetails medicalQuestionRecordDetails : medicalQuestionRecordDetailsList) {
                if("10".equals(medicalQuestionRecordDetails.getQuestionNo())){
                    mqrd = medicalQuestionRecordDetails;
                }
            }
            if(mqrd!=null && mqrd.getAnswer().equals(subject)){
                score = 2;
            }
        }
        return score == null ? 0 : score;
    }
}
