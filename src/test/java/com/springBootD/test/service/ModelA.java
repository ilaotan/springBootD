package com.springBootD.test.service;

/**
 * Created by tan on 2016/9/16.
 */
public class ModelA {

    private String modelA;

    private String selfValA;

    public ModelA(String modelA,String selfVal) {
        this.modelA = modelA;
        this.selfValA = selfVal;
    }

    public String getModelA() {
        return modelA;
    }

    public void setModelA(String modelA) {
        this.modelA = modelA;
    }

    public String getSelfValA() {
        return selfValA;
    }

    public void setSelfValA(String selfValA) {
        this.selfValA = selfValA;
    }

    @Override
    public String toString() {
        return "ModelA{" +
                "modelA='" + modelA + '\'' +
                ", selfValA='" + selfValA + '\'' +
                '}';
    }
}
