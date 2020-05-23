package com.novelasgame.novelas.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpringException  implements ErrorController  {
 
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
	    
	    return "404";
	}
 
    @Override
    public String getErrorPath() {
        return "/error";
    }
}