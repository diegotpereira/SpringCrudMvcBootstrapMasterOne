package br.com.java.controller;

import java.security.Principal;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.java.model.Usuario;
import br.com.java.service.IUsuarioService;

@Controller
@RequestMapping("/usuario/**")
public class UsuarioController {
	
	@Autowired
	private IUsuarioService service;
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public Usuario add() {
		return new Usuario();
	}
	@RequestMapping(value = "/add/salvar", method = RequestMethod.POST)
	public String save(@Valid Usuario usuario, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "usuario/add";
		}
		service.salvar(usuario);
		model.addAttribute("message", messageSource.getMessage("message.usuario.salvar", null, Locale.getDefault()));
		return "redirect:/usuario/add";
	}
	@RequestMapping(value = "/checkusuarionome", method = RequestMethod.GET)
	public @ResponseBody String checkNomeUsuarioExiste(@RequestParam String username) {
		return String.valueOf(Objects.nonNull(service.findByUsername(username)));
	}
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") int id, Model model) {
		Usuario usuario = service.findById(id);
		model.addAttribute("usuario", usuario);
		return "usuario/add";
	}
	@Secured("FUNCAO_ADMIN")
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public String remove(Principal principal, HttpServletRequest httpRequest, @PathVariable("id") long id,
			Model model) {
		Usuario usuario = service.findById(id);
		Usuario usuarioLogado = service.findByUsername(principal.getName());
		service.remover(id);
		model.addAttribute("message", messageSource.getMessage("message.usuario.removed",
				new Object[] { usuario.getName() }, Locale.getDefault()));
		if (id == usuarioLogado.getId()) {
			return "redirect:/logout?logout";
		}
		return "redirect:/";
	}
}
