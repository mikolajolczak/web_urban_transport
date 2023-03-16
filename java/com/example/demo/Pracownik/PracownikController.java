package com.example.demo.Pracownik;

import com.example.demo.Adres.Adres;
import com.example.demo.Adres.AdresDao;
import com.example.demo.Biuro.Biuro;
import com.example.demo.Biuro.BiuroDao;
import com.example.demo.ListaPrzystankow.ListaPrzystankow;
import com.example.demo.ListaPrzystankow.ListaPrzystankowDao;
import com.example.demo.Pojazdy.Pojazd;
import com.example.demo.Pojazdy.PojazdDao;
import com.example.demo.Przystanek.Przystanek;
import com.example.demo.Przystanek.PrzystanekDao;
import com.example.demo.Stanowisko.Stanowisko;
import com.example.demo.Stanowisko.StanowiskoDao;
import com.example.demo.Trasy.Trasa;
import com.example.demo.Trasy.TrasaDao;
import com.example.demo.Wynagrodzenie.Wynagrodzenie;
import com.example.demo.Wynagrodzenie.WynagrodzenieDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
public class PracownikController {
    @Autowired
    private PracownikDao pracownikDao;
    @Autowired
    private BiuroDao biuroDao;
    @Autowired
    private AdresDao adresDao;
    @Autowired
    private WynagrodzenieDao wynagrodzenieDao;
    @Autowired
    private StanowiskoDao stanowiskoDao;
    @Autowired
    private PojazdDao pojazdDao;
    @Autowired
    private PrzystanekDao przystanekDao;
    @Autowired
    private ListaPrzystankowDao listaPrzystankowDao;
    @Autowired
    private TrasaDao trasaDao;
    @RequestMapping("/pracownicy")
    public String pracownicy(Model model, Principal principal){
        List<Pracownik> pracownikList = pracownikDao.list();
        List<Biuro> biuroList = biuroDao.list();
        List<Adres> adresList = adresDao.list();
        List<Wynagrodzenie> wynagrodzenieList = wynagrodzenieDao.list();
        List<Stanowisko> stanowiskoList = stanowiskoDao.list();
        model.addAttribute("listPracownik",pracownikList);
        model.addAttribute("listBiuro",biuroList);
        model.addAttribute("listAdres",adresList);
        model.addAttribute("listWynagrodzenie",wynagrodzenieList);
        model.addAttribute("listStanowisko",stanowiskoList);
        model.addAttribute("principal",principal.getName());
        return "pracownicy";
    }
    @RequestMapping("/newpracownik")
    public String showNewForm(Model model,Principal principal){
        Pracownik pracownik = new Pracownik();
        Adres adres = new Adres();
        List<Biuro> biuroList = biuroDao.list();
        List<Wynagrodzenie> wynagrodzenieList = wynagrodzenieDao.list();
        List<Stanowisko> stanowiskoList = stanowiskoDao.list();
        model.addAttribute("pracownik",pracownik);
        model.addAttribute("listBiuro",biuroList);
        model.addAttribute("adres",adres);
        model.addAttribute("listWynagrodzenie",wynagrodzenieList);
        model.addAttribute("listStanowisko",stanowiskoList);
        model.addAttribute("principal",principal.getName());
        return "new_pracownik";
    }
    @RequestMapping(value = "/newpracownik/save", method = RequestMethod.POST)
    public String savePracownik(@ModelAttribute("pracownik") Pracownik pracownik, @ModelAttribute("adres") Adres adres){
        adresDao.save(adres);
        pracownik.setNradresu(adresDao.getNr(adres));
        pracownikDao.save(pracownik);
        return "redirect:/pracownicy";
    }
    @RequestMapping("/pracownicy/edit/{nrpracownika}")
    public String showEditForm(Model model,@PathVariable(name="nrpracownika") int nrpracownika, Principal principal){
        List<Biuro> biuroList = biuroDao.list();
        List<Wynagrodzenie> wynagrodzenieList = wynagrodzenieDao.list();
        List<Stanowisko> stanowiskoList = stanowiskoDao.list();
        Pracownik pracownik = pracownikDao.get(nrpracownika);
        Adres adres = adresDao.get(pracownik.getNradresu());
        model.addAttribute("pracownik",pracownik);
        model.addAttribute("listBiuro",biuroList);
        model.addAttribute("adres",adres);
        model.addAttribute("listWynagrodzenie",wynagrodzenieList);
        model.addAttribute("listStanowisko",stanowiskoList);
        model.addAttribute("nrpracownika",nrpracownika);
        model.addAttribute("principal",principal.getName());
        return "edit_pracownik";
    }
    @RequestMapping(value = "/pracownicy/update/{nrpracownika}", method = RequestMethod.POST)
    public String update(@ModelAttribute("pracownik") Pracownik pracownik, @ModelAttribute Adres adres,
                         @PathVariable(name="nrpracownika") int nrpracownika){
        pracownik.setNradresu(adresDao.getNr(adres));
        pracownik.setNrpracownika(nrpracownika);
        pracownikDao.update(pracownik);
        return "redirect:/pracownicy";
    }
    @RequestMapping("/pracownicy/delete/{nrpracownika}")
    public String deletePracownik(@PathVariable(name = "nrpracownika") int nrpracownika){
        pracownikDao.delete(nrpracownika);
        return "redirect:/pracownicy";
    }
    @RequestMapping("/")
    public String viewHomePage(Model model, Principal principal){
        if(!Objects.isNull(principal)) {
            model.addAttribute("principal", principal.getName());
        }
        else{
            model.addAttribute("principal", null);
        }
        return "index";
    }
    @RequestMapping("/biura")
    public String viewBiura(Model model,Principal principal){
        List<Biuro> biuroList= biuroDao.list();
        List<Adres> adresList = adresDao.list();
        model.addAttribute("biuroList",biuroList);
        model.addAttribute("adresList",adresList);
        model.addAttribute("principal",principal.getName());
        return "biura";
    }
    @RequestMapping("/biura/delete/{nrbiura}")
    public String deleteBiuro(@PathVariable(name = "nrbiura") int nrbiura){
        biuroDao.delete(nrbiura);
        return "redirect:/biura";
    }
    @RequestMapping("/biura/edit/{nrbiura}")
    public String editBiuro(@PathVariable(name = "nrbiura") int nrbiura,Model model,Principal principal){
        Biuro biuro = biuroDao.get(nrbiura);
        Adres adres = adresDao.get(biuro.getNradresu());
        model.addAttribute("biuro",biuro);
        model.addAttribute("adres",adres);
        model.addAttribute("principal",principal.getName());
        return "edit_biuro";
    }
    @RequestMapping("/biura/update/{nrbiura}")
    public String updateBiuro(@PathVariable(name = "nrbiura") int nrbiura, @ModelAttribute("adres") Adres adres,@ModelAttribute("biuro") Biuro biuro){
        biuro.setNradresu(adresDao.getNr(adres));
        biuro.setNrbiura(nrbiura);
        biuroDao.update(biuro);
        return "redirect:/biura";
    }
    @RequestMapping("/newbiuro")
    public String newBiuro(Model model,Principal principal){
        Biuro biuro = new Biuro();
        Adres adres = new Adres();
        model.addAttribute("biuro",biuro);
        model.addAttribute("adres",adres);
        model.addAttribute("principal",principal.getName());
        return "new_biuro";
    }
    @RequestMapping("/newbiuro/save")
    public String saveBiuro(@ModelAttribute("biuro") Biuro biuro, @ModelAttribute("adres") Adres adres){
        adresDao.save(adres);
        biuro.setNradresu(adresDao.getNr(adres));
        biuroDao.save(biuro);
        return "redirect:/biura";
    }
    @RequestMapping("/pojazdy")
    public String pojazdy(Model model,Principal principal){
        List<Pojazd> pojazdList = pojazdDao.list();
        List<Biuro> biuroList = biuroDao.list();
        model.addAttribute("listPojazd",pojazdList);
        model.addAttribute("listBiuro",biuroList);
        model.addAttribute("principal",principal.getName());
        return "pojazdy";
    }

    @RequestMapping("/newpojazd")
    public String newPojazd(Model model,Principal principal){
        List<Biuro> biuroList = biuroDao.list();
        Pojazd pojazd = new Pojazd();
        model.addAttribute("pojazd",pojazd);
        model.addAttribute("biuroList",biuroList);
        model.addAttribute("principal",principal.getName());
        return "new_pojazd";
    }

    @RequestMapping("/newpojazd/save")
    public String savePojazd(@ModelAttribute("pojazd") Pojazd pojazd){
        pojazdDao.save(pojazd);
        return "redirect:/pojazdy";
    }

    @RequestMapping("/pojazdy/edit/{nrpojazdu}")
    public String editPojazd(Model model, @PathVariable("nrpojazdu") int nrpojazdu, Principal principal){
        Pojazd pojazd = pojazdDao.get(nrpojazdu);
        List<Biuro> biuroList = biuroDao.list();
        model.addAttribute("biuroList",biuroList);
        model.addAttribute("pojazd",pojazd);
        model.addAttribute("principal",principal.getName());
        return "edit_pojazd";
    }
    @RequestMapping("/pojazdy/delete/{nrpojazdu}")
    public String deletePojazd(@PathVariable(name = "nrpojazdu") int nrpojazdu){
        pojazdDao.delete(nrpojazdu);
        return "redirect:/pojazdy";
    }
    @RequestMapping("/pojazdy/update/{nrpojazdu}")
    public String updatePojazd(@ModelAttribute("pojazd") Pojazd pojazd,@PathVariable("nrpojazdu") int nrpojazdu){
        pojazd.setNrpojazdu(nrpojazdu);
        pojazdDao.update(pojazd);
        return "redirect:/pojazdy";
    }
    @RequestMapping("/przystanki")
    public String listPrzystanki(Model model,Principal principal){
        List<Przystanek> przystanekList = przystanekDao.list();
        List<Adres> adresList = adresDao.list();
        model.addAttribute("przystanekList",przystanekList);
        model.addAttribute("adresList",adresList);
        model.addAttribute("principal",principal.getName());
        return "przystanki";
    }
    @RequestMapping("/newprzystanek")
    public String newPrzystanek(Model model,Principal principal){
        Przystanek przystanek = new Przystanek();
        Adres adres = new Adres();
        model.addAttribute("przystanek",przystanek);
        model.addAttribute("adres",adres);
        model.addAttribute("principal",principal.getName());
        return "new_przystanek";
    }
    @RequestMapping("/newprzystanek/save")
    public String savePrzystanek(@ModelAttribute("przystanek") Przystanek przystanek, @ModelAttribute("adres") Adres adres){
        przystanek.setNradresu(adresDao.getNr(adres));
        przystanekDao.save(przystanek);
        return "redirect:/przystanki";
    }
    @RequestMapping("/przystanki/delete/{nrprzystanku}")
    public String deletePrzystanek(@PathVariable("nrprzystanku") int nrprzystanku){
        przystanekDao.delete(nrprzystanku);
        return "redirect:/przystanki";
    }
    @RequestMapping("przystanki/edit/{nrprzystanku}")
    public String editPrzystanek(@PathVariable("nrprzystanku") int nrprzystanku,Model model,Principal principal){
        Przystanek przystanek = przystanekDao.get(nrprzystanku);
        Adres adres = adresDao.get(przystanek.getNradresu());
        model.addAttribute("przystanek",przystanek);
        model.addAttribute("adres",adres);
        model.addAttribute("principal",principal.getName());
        return "edit_przystanek";
    }
    @RequestMapping("przystanki/update/{nrprzystanku}")
    public String updatePrzystanek(@PathVariable("nrprzystanku") int nrprzystanku,@ModelAttribute("adres") Adres adres,@ModelAttribute("przystanek") Przystanek przystanek){
        przystanek.setNradresu(adresDao.getNr(adres));
        przystanek.setNrprzystanku(nrprzystanku);
        przystanekDao.update(przystanek);
        return "redirect:/przystanki";
    }
    @RequestMapping("lista")
    public String listRozklad(Model model,Principal principal){
        List<ListaPrzystankow> listaPrzystankowList = listaPrzystankowDao.list();
        List<Trasa> trasaList = trasaDao.list();
        List<Przystanek> przystanekList = przystanekDao.list();
        model.addAttribute("trasaList",trasaList);
        model.addAttribute("listaPrzystankowList",listaPrzystankowList);
        model.addAttribute("przystanekList",przystanekList);
        model.addAttribute("principal",principal.getName());
        return "lista";
    }
    @RequestMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            new SecurityContextLogoutHandler().logout(request,response,auth);
        }
        return "redirect:/";
    }
    @RequestMapping("/bilety")
    public String viewUser(Model model, Principal principal){
        model.addAttribute("principal",principal.getName());
        return "about";
    }
}
