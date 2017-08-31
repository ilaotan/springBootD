package com.springBootD.application.demo.service;

import com.springBootD.application.demo.model.Country;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by tan on 2017/7/31.
 */
public interface ICountryService {

    List<Country> getAll(Country country);

    Country getById(Integer id);

    void deleteById(Integer id);

    void save(Country country);

    void test1();
    void test2();
    void test3();

}
