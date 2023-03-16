package com.example.demo.Przystanek;

import com.example.demo.Biuro.Biuro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class PrzystanekDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public PrzystanekDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }
    public List<Przystanek> list(){
        String sql = "SELECT * FROM PRZYSTANKI";
        List<Przystanek> przystanekList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Przystanek.class));
        return przystanekList;
    }
    public void save(Przystanek przystanek){
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withTableName("Przystanki").usingColumns("nazwa","nradresu");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(przystanek);
        insertActor.execute(param);
    }
    public void delete(int nrpzystanku){
        String sql = "DELETE FROM Przystanki WHERE nrprzystanku= ?";
        jdbcTemplate.update(sql,nrpzystanku);
    }
    public Przystanek get(int nrpzystanku){
        String sql = "SELECT * FROM PRZYSTANKI WHERE NRPRZYSTANKU = ?";
        Object[] args = {nrpzystanku};
        Przystanek przystanek = jdbcTemplate.queryForObject(sql,args,BeanPropertyRowMapper.newInstance(Przystanek.class));
        return przystanek;
    }
    public void update(Przystanek przystanek){
        String sql = "UPDATE PRZYSTANKI SET nazwa=:nazwa, nradresu=:nradresu WHERE nrprzystanku=:nrprzystanku";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(przystanek);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
        template.update(sql,param);
    }
}
