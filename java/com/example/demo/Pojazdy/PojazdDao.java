package com.example.demo.Pojazdy;

import com.example.demo.Pracownik.Pracownik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public class PojazdDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public PojazdDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }
    public List<Pojazd> list(){
        String sql = "SELECT nrpojazdu,marka,rokprodukcji,model,nrrejestracyjny,datazakupu,dataubezpieczenia,nrbiura from pojazdy";
        List<Pojazd> pojazdList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Pojazd.class));
        return pojazdList;
    }
    public void update(Pojazd pojazd){
        String sql = "UPDATE POJAZDY SET marka=:marka, model=:model, rokprodukcji=:rokprodukcji, nrrejestracyjny=:nrrejestracyjny, datazakupu=:datazakupu, dataubezpieczenia=:dataubezpieczenia, nrbiura=:nrbiura WHERE nrpojazdu=:nrpojazdu";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(pojazd);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
        template.update(sql,param);
    }
    public Pojazd get(int nrpojazdu){
        String sql = "SELECT * FROM POJAZDY WHERE nrpojazdu = ?";
        Object[] args = {nrpojazdu};
        Pojazd pojazd = jdbcTemplate.queryForObject(sql,args,BeanPropertyRowMapper.newInstance(Pojazd.class));
        return pojazd;
    }
    public void save(Pojazd pojazd){
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withTableName("Pojazdy").usingColumns("marka","model","rokprodukcji","nrrejestracyjny","datazakupu","dataubezpieczenia","nrbiura");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(pojazd);
        insertActor.execute(param);
    }
    public void delete(int nrpojazdu){
        String sql = "DELETE FROM POJAZDY WHERE nrpojazdu= ?";
        jdbcTemplate.update(sql,nrpojazdu);
    }
}
