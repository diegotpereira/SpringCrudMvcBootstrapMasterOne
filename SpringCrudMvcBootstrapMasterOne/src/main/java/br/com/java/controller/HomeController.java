package br.com.java.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.java.service.IUsuarioService;

@Controller
public class HomeController {

	@Autowired
	private IUsuarioService service;

	@Autowired
	@RequestMapping(value = "/")
	public ModelAndView index(HttpServletRequest httpRequest) throws IOException {
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("usuarios", service.list());
		modelAndView.addObject("usuarioLogado", service.getAuthentication());
		return modelAndView;
	}

	@RequestMapping(value = "/acesso-negado")
	public String accessDenied(HttpServletRequest httpRequest) {
		return "acesso-negado";
	}
}
