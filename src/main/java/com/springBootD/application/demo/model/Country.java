package com.springBootD.application.demo.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Country extends BaseEntity {
    /**
     * 名称
     */
    private String countryname;

    /**
     * 代码
     */
    private String countrycode;


}