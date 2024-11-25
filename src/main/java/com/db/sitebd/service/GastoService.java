package com.db.sitebd.service;

import com.db.sitebd.model.Gasto;
import com.db.sitebd.repository.GastoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.util.List;

@Service
public class GastoService {

    private static final Logger logger = LoggerFactory.getLogger(GastoService.class);

    @Autowired
    private GastoDAO gastoDAO;

    public List<Gasto> listarTodos(Integer usuarioId) {
        return gastoDAO.listarTodos(usuarioId);
    }

    public Gasto buscarPorId(Integer id) {
        return gastoDAO.buscarPorId(id);
    }

    public boolean salvar(Gasto gasto) {
        boolean result = gastoDAO.salvar(gasto);
        if (result) {
            logger.info("Gasto salvo com sucesso: {}", gasto);
        } else {
            logger.warn("Falha ao salvar gasto: {}", gasto);
        }
        return result;
    }

    public boolean atualizar(Gasto gasto) {
        return gastoDAO.atualizar(gasto);
    }

    public boolean deletar(Integer id) {
        return gastoDAO.deletar(id);
    }

    public BigDecimal calcularTotalCarteira(Integer usuarioId) {
        return BigDecimal.valueOf(gastoDAO.calcularTotalCarteira(usuarioId));
    }

    public BigDecimal calcularTotalEntrada(Integer usuarioId) {
        return BigDecimal.valueOf(gastoDAO.calcularTotalEntrada(usuarioId));
    }

    public BigDecimal calcularTotalSaida(Integer usuarioId) {
        return BigDecimal.valueOf(gastoDAO.calcularTotalSaida(usuarioId));
    }
}