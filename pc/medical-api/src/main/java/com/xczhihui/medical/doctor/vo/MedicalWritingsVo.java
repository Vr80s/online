package com.xczhihui.medical.doctor.vo;

import com.xczhihui.medical.field.vo.MedicalFieldVo;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public class MedicalWritingsVo implements Serializable{

    /**
     * 著作表
     */
	private String id;
	private String articleId;
	private String author;
	private String title;
	private String buyLink;
	private String imgPath;
	private String content;
	private List<MedicalDoctorVo> medicalDoctors;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBuyLink() {
		return buyLink;
	}

	public void setBuyLink(String buyLink) {
		this.buyLink = buyLink;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<MedicalDoctorVo> getMedicalDoctors() {
		return medicalDoctors;
	}

	public void setMedicalDoctors(List<MedicalDoctorVo> medicalDoctors) {
		this.medicalDoctors = medicalDoctors;
	}
}
