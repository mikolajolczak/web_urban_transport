package com.example.demo.ListaPrzystankow;

import com.example.demo.Pojazdy.Pojazd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
public class ListaPrzystankowDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public ListaPrzystankowDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }
    public List<ListaPrzystankow> list(){
        String sql = "SELECT * FROM Listaprzystankow";
        List<ListaPrzystankow> listaPrzystankowList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(ListaPrzystankow.class));
        return listaPrzystankowList;
    }
}
