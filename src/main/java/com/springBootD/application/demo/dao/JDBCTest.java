package com.springBootD.application.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

//@Repository
public class JDBCTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean test(){

        jdbcTemplate.query("", new Object[]{""}, new RowCallbackHandler() {

            @Override
            public void processRow(final ResultSet resultSet) throws SQLException {

                resultSet.getInt("aaa");

            }
        });

        return true;
    }

}
