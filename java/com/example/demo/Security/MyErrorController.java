package com.example.demo.Security;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Objects;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Principal principal, Model model){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(!Objects.isNull(principal)) {
            model.addAttribute("principal", principal.getName());
        }
        else{
            model.addAttribute("principal", null);
        }
        if(status!=null){
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()){
                return "404";
            }
            if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                return "500";
            }
        }
        return "error";
    }
}
