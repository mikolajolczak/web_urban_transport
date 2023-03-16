package com.example.demo.Pracownik;

import com.example.demo.Adres.Adres;
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
public class PracownikDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PracownikDao(JdbcTemplate jdbcTemplate){
        super();
        this.jdbcTemplate=jdbcTemplate;
    }

    public List<Pracownik> list(){
        String sql="SELECT * FROM PRACOWNICY";
        List<Pracownik> listpracownik = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Pracownik.class));
        return listpracownik;
    }

    public void save(Pracownik pracownik){
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        pracownik.setDatazatrudnienia(LocalDate.now());
        insertActor.withTableName("Pracownicy").usingColumns("imie","nazwisko","plec","nrbiura","nradresu","nrkonta","nrtelefonu","pesel","datazatrudnienia","nrstanowiska","nrwynagrodzenia");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(pracownik);
        insertActor.execute(param);
    }

    public Pracownik get(int nrpracownika){
        String sql = "SELECT * FROM PRACOWNICY WHERE Nrpracownika = ?";
        Object[] args = {nrpracownika};
        Pracownik pracownik = jdbcTemplate.queryForObject(sql,args,BeanPropertyRowMapper.newInstance(Pracownik.class));
        return pracownik;
    }

    public void update(Pracownik pracownik){
        String sql = "UPDATE PRACOWNICY SET imie=:imie, nazwisko=:nazwisko, plec=:plec, nrkonta=:nrkonta, nrtelefonu=:nrtelefonu, pesel=:pesel, nrbiura=:nrbiura, nrstanowiska=:nrstanowiska, nrwynagrodzenia=:nrwynagrodzenia, nradresu=:nradresu WHERE Nrpracownika=:nrpracownika";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(pracownik);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
        template.update(sql,param);
    }

    public void delete(int nrpracownika){
        String sql = "DELETE FROM PRACOWNICY WHERE Nrpracownika= ?";
        jdbcTemplate.update(sql,nrpracownika);
    }

}
