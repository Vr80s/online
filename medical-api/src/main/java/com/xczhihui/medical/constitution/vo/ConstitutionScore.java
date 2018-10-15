package com.xczhihui.medical.constitution.vo;

import com.xczhihui.medical.constitution.model.MedicalConstitution;
import lombok.Data;

import java.io.Serializable;

@Data
public class ConstitutionScore implements Serializable{

    private MedicalConstitution constitution;
    private Integer score;

    public ConstitutionScore(MedicalConstitution constitution, Integer score) {
        this.constitution = constitution;
        this.score = score;
    }
}
