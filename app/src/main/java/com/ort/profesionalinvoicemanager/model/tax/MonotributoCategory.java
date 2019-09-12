package com.ort.profesionalinvoicemanager.model.tax;

import com.ort.profesionalinvoicemanager.model.base.PersistentObject;

import java.math.BigDecimal;

public class MonotributoCategory extends PersistentObject {
    private String category;
    private BigDecimal grossAmount;
    private Integer minimunEmployees;
    private Integer maxAffectedSup;
    private Integer maxElectricityConmsumtion;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public Integer getMinimunEmployees() {
        return minimunEmployees;
    }

    public void setMinimunEmployees(Integer minimunEmployees) {
        this.minimunEmployees = minimunEmployees;
    }

    public Integer getMaxAffectedSup() {
        return maxAffectedSup;
    }

    public void setMaxAffectedSup(Integer maxAffectedSup) {
        this.maxAffectedSup = maxAffectedSup;
    }

    public Integer getMaxElectricityConmsumtion() {
        return maxElectricityConmsumtion;
    }

    public void setMaxElectricityConmsumtion(Integer maxElectricityConmsumtion) {
        this.maxElectricityConmsumtion = maxElectricityConmsumtion;
    }
}
