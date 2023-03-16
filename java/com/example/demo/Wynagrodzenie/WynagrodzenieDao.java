package com.example.demo.Wynagrodzenie;

import com.example.demo.Pracownik.Pracownik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WynagrodzenieDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public WynagrodzenieDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }
    public List<Wynagrodzenie> list(){
        String sql = "SELECT * FROM WYNAGRODZENIA";
        List<Wynagrodzenie> wynagrodzenieList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Wynagrodzenie.class));
        return wynagrodzenieList;
    }
}
