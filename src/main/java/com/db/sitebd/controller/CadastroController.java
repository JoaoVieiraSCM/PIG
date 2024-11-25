package com.db.sitebd.controller;

import com.db.sitebd.model.Usuario;
import com.db.sitebd.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("usuario")
public class CadastroController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/cadastro")
    public String mostrarFormularioCadastro() {
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrarUsuario(@RequestParam String login, 
                                   @RequestParam String senha, 
                                   @RequestParam String confirm_senha, 
                                   HttpSession session,
                                   Model model) {
        if (!senha.equals(confirm_senha)) {
            model.addAttribute("error", "Senhas não coincidem");
            return "index";
        }

        if (usuarioService.buscarPorEmail(login) != null) {
            model.addAttribute("error", "Email já cadastrado");
            return "index";
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setDs_email(login);
        novoUsuario.setDs_hash_password(senha);

        try {
            usuarioService.salvarUsuario(novoUsuario, novoUsuario.getDs_hash_password());

            session.setAttribute("usuario", novoUsuario);

            model.addAttribute("success", "Usuário cadastrado com sucesso!");
            return "gerenciador";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao cadastrar usuário: " + e.getMessage());
            return "index";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String login, 
                        @RequestParam String senha, 
                        HttpSession session,
                        Model model) {
        Usuario usuario = usuarioService.buscarPorEmail(login);

        if (usuario != null && usuarioService.verificarSenha(senha, usuario.getDs_hash_password())) {
            session.setAttribute("usuario", usuario);
            return "redirect:/gerenciador";
        } else {
            model.addAttribute("error", "Email ou senha inválidos");
            return "index";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}