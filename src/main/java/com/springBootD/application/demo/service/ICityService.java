package com.springBootD.application.demo.service;

import com.springBootD.application.demo.model.City;

import java.util.List;

/**
 * Created by tan on 2017/7/31.
 */
public interface ICityService {

    List<City> getAll(City city);

    City getById(Integer id);

    void deleteById(Integer id);

    void save(City country);

}
