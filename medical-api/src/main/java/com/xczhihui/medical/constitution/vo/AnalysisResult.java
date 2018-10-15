package com.xczhihui.medical.constitution.vo;

import com.xczhihui.medical.constitution.model.MedicalConstitutionProposal;
import com.xczhihui.medical.constitution.model.MedicalConstitutionRecipe;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AnalysisResult implements Serializable{

    private String constitution;
    private List<MedicalConstitutionProposal> medicalConstitutionProposals;
    private List<MedicalConstitutionRecipe> medicalConstitutionRecipes;
    private List<ConstitutionScore> constitutionScoreList;
}
