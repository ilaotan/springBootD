package com.springBootD.application.demo.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Country extends BaseEntity {
    /**
     * 名称
     */
    @NotNull
    @Size(min=3,max = 16,message = "33333333333333333333333333333333333")
//    @Size(min=3,max = 16,message = "{country.size}")
    private String countryname;

    /**
     * 代码
     */
    private String countrycode;


}