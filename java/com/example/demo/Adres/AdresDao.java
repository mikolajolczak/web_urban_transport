package com.example.demo.Adres;

import com.example.demo.Pracownik.Pracownik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class AdresDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public AdresDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }
    public void save(Adres adres){
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        if(Objects.isNull(adres.getNrlokalu()) && Objects.isNull(adres.getKodpocztowy())) {
            insertActor.withTableName("Adresy").usingColumns("miasto", "ulica", "nrbudynku");
        }else
            if(Objects.isNull(adres.getNrlokalu())){
            insertActor.withTableName("Adresy").usingColumns("miasto", "ulica", "nrbudynku","kodpocztowy");
        }
            else if(Objects.isNull(adres.getKodpocztowy())){
                insertActor.withTableName("Adresy").usingColumns("miasto", "ulica", "nrbudynku","nrlokalu");
            }else{
                insertActor.withTableName("Adresy").usingColumns("miasto", "ulica", "nrbudynku","kodpocztowy","nrlokalu");
            }
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(adres);
        insertActor.execute(param);
    }
    public int savePrzystanekAdres(Adres adres){
        String sql = "SELECT * FROM ADRESY WHERE miasto = ? AND ulica = ? AND nrbudynku = ?";
        Object[] args = {adres.getMiasto(),adres.getUlica(),adres.getNrbudynku()};
        List<Adres> adresList = jdbcTemplate.query(sql,args, BeanPropertyRowMapper.newInstance(Adres.class));
        if(adresList.size()!=0){
            return adresList.get(0).getNradresu();
        }
        else {
            save(adres);
            return getNr(adres);
        }
    }
    public int getNr(Adres adres){
        String sql;
        Object[] args;
        if(adres.getNrlokalu().equals("") && adres.getKodpocztowy().equals("")) {
            sql = "SELECT * FROM Adresy WHERE miasto = ? AND ulica = ? AND nrbudynku = ?";
            args = new Object[]{adres.getMiasto(), adres.getUlica(), adres.getNrbudynku()};
        }else
        if(adres.getNrlokalu().equals("")){
            sql = "SELECT * FROM Adresy WHERE miasto = ? AND ulica = ? AND nrbudynku = ? AND kodpocztowy = ?";
            args = new Object[]{adres.getMiasto(), adres.getUlica(), adres.getNrbudynku(), adres.getKodpocztowy()};
        }
        else if(adres.getKodpocztowy().equals("")){
            sql = "SELECT * FROM Adresy WHERE miasto = ? AND ulica = ? AND nrbudynku = ? AND nrlokalu = ?";
            args = new Object[]{adres.getMiasto(),adres.getUlica(),adres.getNrbudynku(),adres.getNrlokalu()};
        }
        else{
            sql = "SELECT * FROM Adresy WHERE miasto = ? AND ulica = ? AND nrbudynku = ? AND nrlokalu = ? AND kodpocztowy = ?";
            args = new Object[]{adres.getMiasto(),adres.getUlica(),adres.getNrbudynku(),adres.getNrlokalu(),adres.getKodpocztowy()};
        }
        List<Adres> adresList = jdbcTemplate.query(sql,args, BeanPropertyRowMapper.newInstance(Adres.class));
        if(adresList.size()!=0){
            return adresList.get(0).getNradresu();
        }
        else {
            save(adres);
            return getNr(adres);
        }
    }
    public List<Adres> list(){
        String sql="SELECT * FROM ADRESY";
        List<Adres> listpracownik = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Adres.class));
        return listpracownik;
    }
    public Adres get(int nradresu){
        String sql = "SELECT * FROM Adresy WHERE Nradresu = ?";
        Object[] args = {nradresu};
        Adres adres = jdbcTemplate.queryForObject(sql,args,BeanPropertyRowMapper.newInstance(Adres.class));
        return adres;
    }

}
