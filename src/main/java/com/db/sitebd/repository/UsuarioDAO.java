package com.db.sitebd.repository;

import com.db.sitebd.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.db.sitebd.util.Tool;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

@Repository
public class UsuarioDAO {

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbc;

    @PostConstruct
    private void initialize() {
        jdbc = new JdbcTemplate(dataSource);
    }

    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO usuario (ds_email, ds_hash_password, nm_foto, path_foto) VALUES (?, ?, ?, ?);";
        jdbc.update(sql, usuario.getDs_email(), usuario.getDs_hash_password(), usuario.getNm_foto(), usuario.getPath_foto());
    }

    public List<Map<String, Object>> obterTodosUsuarios() {
        String sql = "SELECT * FROM usuario;";
        return jdbc.queryForList(sql);
    }

    public Usuario obterUsuario(Long id) {
        String sql = "SELECT * FROM usuario WHERE cd_id = ?";
        Map<String, Object> row = jdbc.queryForMap(sql, id);
        return Tool.converterUsuario(row);
    }

    public Map<String, Object> buscarPorId(Integer id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        return jdbc.queryForMap(sql, id);
    }

    public void atualizarUsuario(Long id, Usuario usuario) {
        String sql = "UPDATE usuario SET ds_email = ?, ds_hash_password = ?, nm_foto = ?, path_foto = ? WHERE cd_id = ?";
        jdbc.update(sql, usuario.getDs_email(), usuario.getDs_hash_password(), usuario.getNm_foto(), usuario.getPath_foto(), id);
    }

    public void deletarUsuario(Long id) {
        String sql = "DELETE FROM usuario WHERE cd_id = ?";
        jdbc.update(sql, id);
    }

    public List<Map<String, Object>> buscarPorEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE ds_email = ?";
        return jdbc.queryForList(sql, email);
    }
}