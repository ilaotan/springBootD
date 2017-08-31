package com.springBootD.application.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.springBootD.application.demo.component.AsyncTask;
import com.springBootD.application.demo.dao.CountryMapper;
import com.springBootD.application.demo.model.Country;
import com.springBootD.application.demo.service.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class CountryService implements ICountryService{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CountryMapper countryMapper;
    @Autowired
    private AsyncTask asyncTask;

    public List<Country> getAll(Country country) {
        if (country.getPage() != null && country.getRows() != null) {
            PageHelper.startPage(country.getPage(), country.getRows());
        }
        Example example = new Example(Country.class);
        Example.Criteria criteria = example.createCriteria();
        if (country.getCountryname() != null && country.getCountryname().length() > 0) {
            criteria.andLike("countryname", "%" + country.getCountryname() + "%");
        }
        if (country.getCountrycode() != null && country.getCountrycode().length() > 0) {
            criteria.andLike("countrycode", "%" + country.getCountrycode() + "%");
        }
        return countryMapper.selectByExample(example);
    }

    public Country getById(Integer id) {
        return countryMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        asyncTask.test1();
        asyncTask.test2();
        countryMapper.deleteByPrimaryKey(id);
    }

    public void save(Country country) {
        if (country.getId() != null) {
            countryMapper.updateByPrimaryKey(country);
        } else {
            countryMapper.insert(country);
        }
    }

    public static Random random =new Random();

    @Async("myTaskAsyncPool")
    public void test1(){
        try {
            Thread.sleep(random.nextInt(10000));
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.debug("test1");
    }
    @Async("myTaskAsyncPool")
    public void test2(){
        try {
            Thread.sleep(random.nextInt(10000));
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.debug("test2");
        logger.debug("333333333333");
    }
    @Async("myTaskAsyncPool")
    public void test3(){
        try {
            Thread.sleep(random.nextInt(10000));
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.debug("test3");
    }
}
