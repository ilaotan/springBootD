package com.springBootD.test.service;

/**
 * Created by tan on 2016/9/16.
 */
public class ModelC {

    private String modelC;

    private String selfValC;

    public ModelC(String modelC,String selfValC) {
        this.selfValC = selfValC;
        this.modelC = modelC;
    }

    public String getModelC() {
        return modelC;
    }

    public void setModelC(String modelC) {
        this.modelC = modelC;
    }

    public String getSelfValC() {
        return selfValC;
    }

    public void setSelfValC(String selfValC) {
        this.selfValC = selfValC;
    }

    @Override
    public String toString() {
        return "ModelC{" +
                "modelC='" + modelC + '\'' +
                ", selfValC='" + selfValC + '\'' +
                '}';
    }
}
