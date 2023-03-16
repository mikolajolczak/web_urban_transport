package com.example.demo.Trasy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrasaDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public TrasaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Trasa> list(){
        String sql = "SELECT * FROM TRASY";
        List<Trasa> trasaList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Trasa.class));
        return trasaList;
    }
}
