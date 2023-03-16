package com.example.demo.Stanowisko;

import com.example.demo.Wynagrodzenie.Wynagrodzenie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StanowiskoDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public StanowiskoDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }
    public List<Stanowisko> list(){
        String sql = "SELECT * FROM STANOWISKA";
        List<Stanowisko> stanowiskoList= jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Stanowisko.class));
        return  stanowiskoList;
    }
}
