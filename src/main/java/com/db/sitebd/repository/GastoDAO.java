package com.db.sitebd.repository;

import com.db.sitebd.model.Gasto;
import com.db.sitebd.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class GastoDAO {

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbc;

    @PostConstruct
    private void initialize() {
        jdbc = new JdbcTemplate(dataSource);
    }

    public List<Gasto> listarTodos(Integer usuarioId) {
        String sql = "SELECT * FROM gasto WHERE cd_user = ? ORDER BY dt_data DESC";
        List<Gasto> gastos = jdbc.query(sql, new Object[]{usuarioId}, (rs, rowNum) -> Tool.converterGasto(rs));
        return gastos;
    }

    public Gasto buscarPorId(Integer id) {
        String sql = "SELECT * FROM gasto WHERE cd_id = ?";
        Gasto gasto = jdbc.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> Tool.converterGasto(rs));
        return gasto;
    }

    public boolean salvar(Gasto gasto) {
        BigDecimal valorDecimal = gasto.getVl_valor();
        String sql = "INSERT INTO gasto (ds_descricao, ds_categoria, vl_valor, ic_tipo, cd_user) " +
                     "VALUES (?, ?, ?, ?, ?)";
        int rowsAffected = jdbc.update(sql, 
                                       gasto.getDs_descricao(),
                                       gasto.getDs_categoria(),
                                       valorDecimal,
                                       gasto.getIc_tipo(),
                                       gasto.getCd_user());
        return rowsAffected > 0;
    }

    public boolean atualizar(Gasto gasto) {
        BigDecimal valorDecimal = gasto.getVl_valor();
        String sql = "UPDATE gasto SET ds_descricao = ?, ds_categoria = ?, vl_valor = ?, ic_tipo = ?, cd_user = ? " +
                     "WHERE cd_id = ?";
        int rowsAffected = jdbc.update(sql,
                                       gasto.getDs_descricao(),
                                       gasto.getDs_categoria(),
                                       valorDecimal,
                                       gasto.getIc_tipo(),
                                       gasto.getCd_user(),
                                       gasto.getCd_id());
        return rowsAffected > 0;
    }

    public boolean deletar(Integer id) {
        String sql = "DELETE FROM gasto WHERE cd_id = ?";
        int rowsAffected = jdbc.update(sql, id);
        return rowsAffected > 0;
    }    

    public double calcularTotalEntrada(Integer usuarioId) {
        String sql = "SELECT COALESCE(SUM(vl_valor), 0.0) FROM gasto WHERE cd_user = ? AND ic_tipo = false";
        return jdbc.queryForObject(sql, new Object[]{usuarioId}, Double.class);
    }
    
    public double calcularTotalSaida(Integer usuarioId) {
        String sql = "SELECT COALESCE(SUM(vl_valor), 0.0) FROM gasto WHERE cd_user = ? AND ic_tipo = true";
        return jdbc.queryForObject(sql, new Object[]{usuarioId}, Double.class);
    }
    
    public double calcularTotalCarteira(Integer usuarioId) {
        double totalEntrada = calcularTotalEntrada(usuarioId);
        double totalSaida = calcularTotalSaida(usuarioId);
        return totalEntrada - totalSaida;
    }
}