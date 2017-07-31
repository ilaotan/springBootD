package com.springBootD.test.service;

/**
 * Created by tan on 2016/9/16.
 */
public class ModelAll {

    private String modelA;

    private String modelC;

    public String getModelA() {
        return modelA;
    }

    public void setModelA(String modelA) {
        this.modelA = modelA;
    }

    public String getModelC() {
        return modelC;
    }

    public void setModelC(String modelC) {
        this.modelC = modelC;
    }

    @Override
    public String toString() {
        return "ModelAll{" +
                "modelA='" + modelA + '\'' +
                ", modelC='" + modelC + '\'' +
                '}';
    }
}
