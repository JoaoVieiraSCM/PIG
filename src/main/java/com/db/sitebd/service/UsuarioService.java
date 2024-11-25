package com.db.sitebd.service;

import com.db.sitebd.model.Usuario;
import com.db.sitebd.repository.UsuarioDAO;
import com.db.sitebd.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    public Usuario buscarPorEmail(String email) {
        List<Map<String, Object>> result = usuarioDAO.buscarPorEmail(email);
        if (result.isEmpty()) {
            return null;
        }
        return Tool.converterUsuario(result.get(0));
    }

    public boolean verificarSenha(String senha, String senhaArmazenada) {
        return senha.equals(senhaArmazenada);
    }

    public Usuario salvarUsuario(Usuario usuario, String senha) {
        Usuario usuarioExistente = buscarPorEmail(usuario.getDs_email());
        if (usuarioExistente != null) {
            throw new IllegalArgumentException("Usuário já existe com este e-mail");
        }

        usuario.setDs_hash_password(senha);

        usuarioDAO.inserir(usuario);

        return usuario;
    }

    public Usuario buscarPorId(Integer id) {
        Map<String, Object> result = usuarioDAO.buscarPorId(id);
        if (result == null || result.isEmpty()) {
            return null;
        }
        return Tool.converterUsuario(result);
    }
}
