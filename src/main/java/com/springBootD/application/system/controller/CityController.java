/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.springBootD.application.system.controller;

import com.github.pagehelper.PageInfo;
import com.springBootD.application.system.model.City;
import com.springBootD.application.system.service.CityService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 *  测试 api文档
 *  访问 http://localhost:8080/debug/index.html
 *
 */
@RestController
@RequestMapping("/cities")
@Api(basePath = "/cities", value = "testapi", description = "用户相关", produces = "application/json")
public class CityController {

    @Autowired
    private CityService cityService;

    @ApiOperation(httpMethod = "GET", value = "获得所有城市", notes = "POST请求，根据model设置")
    @RequestMapping(value="",method = RequestMethod.GET)
    public PageInfo<City> getAll(City city) {
        city.setPage(2);
        List<City> countryList = cityService.getAll(city);
        return new PageInfo<City>(countryList);
    }

    @ApiOperation(httpMethod = "GET", value = "新增用户", notes = "POST请求，根据model设置")
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public City add() {
        return new City();
    }

    @ApiOperation(httpMethod = "get", value = "根据传来的id获取用户信息", notes = "POST请求，根据model设置")
    @RequestMapping(value = "/view/{id}",method = RequestMethod.GET)
    public City view(@PathVariable Integer id) {
        ModelAndView result = new ModelAndView();
        City city = cityService.getById(id);
        return city;
    }

    @ApiOperation(httpMethod = "get", value = "根据id删除", notes = "POST请求，根据model设置")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public ModelMap delete(@PathVariable Integer id) {
        ModelMap result = new ModelMap();
        cityService.deleteById(id);
        result.put("msg", "删除成功!");
        return result;
    }

    @ApiOperation(httpMethod = "POST", value = "保存城市", notes = "POST请求，根据model设置")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelMap save(City city) {
        ModelMap result = new ModelMap();
        String msg = city.getId() == null ? "新增成功!" : "更新成功!";
        cityService.save(city);
        result.put("city", city);
        result.put("msg", msg);
        return result;
    }
}
