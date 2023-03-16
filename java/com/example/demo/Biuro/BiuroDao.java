package com.example.demo.Biuro;

import com.example.demo.Pracownik.Pracownik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class BiuroDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public BiuroDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }
    public List<Biuro> list(){
        String sql = "SELECT * FROM BIURA";
        List<Biuro> listbiuro = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Biuro.class));
        return listbiuro;
    }
    public void delete(int nrbiura){
        String sql = "DELETE FROM Biura WHERE nrbiura= ?";
        jdbcTemplate.update(sql,nrbiura);
    }

    public Biuro get(int nrbiura){
        String sql = "SELECT * FROM BIURA WHERE NRBIURA = ?";
        Object[] args = {nrbiura};
        Biuro biuro = jdbcTemplate.queryForObject(sql,args,BeanPropertyRowMapper.newInstance(Biuro.class));
        return biuro;
    }

    public void update(Biuro biuro){
        String sql = "UPDATE BIURA SET nazwa=:nazwa, nradresu=:nradresu WHERE nrbiura=:nrbiura";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(biuro);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
        template.update(sql,param);
    }
    public void save(Biuro biuro){
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withTableName("Biura").usingColumns("nazwa","nradresu");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(biuro);
        insertActor.execute(param);
    }
    public int getNr(Biuro biuro){
        String sql = "SELECT nrbiura FROM BIURA WHERE nazwa=?";
        Object args[] = {biuro.nazwa};
        int out = jdbcTemplate.queryForObject(sql,args,BeanPropertyRowMapper.newInstance(Integer.class));
        return out;
    }
}
