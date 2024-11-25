package com.db.sitebd.controller;

import com.db.sitebd.model.Gasto;
import com.db.sitebd.service.GastoService;
import com.db.sitebd.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

@Controller
public class GastosController {

    @Autowired
    private GastoService gastoService;

    private static final Logger logger = LoggerFactory.getLogger(GastosController.class);

    private Usuario validarUsuario(HttpSession session) {
        return (Usuario) session.getAttribute("usuario");
    }

    private Long obterUsuarioId(HttpSession session) {
        Usuario usuario = validarUsuario(session);
        if (usuario == null) {
            return null;
        }
        return usuario.getCd_id();
    }

    @GetMapping("/gerenciador")
    public String listarGastos(Model model, HttpSession session) {
        Long usuarioId = obterUsuarioId(session);
        if (usuarioId == null) {
            return "redirect:/";
        }

        Integer usuarioIdInt = usuarioId.intValue();
        
        BigDecimal saldoTotal = Optional.ofNullable(gastoService.calcularTotalCarteira(usuarioIdInt)).orElse(BigDecimal.ZERO);
        BigDecimal totalEntradas = Optional.ofNullable(gastoService.calcularTotalEntrada(usuarioIdInt)).orElse(BigDecimal.ZERO);
        BigDecimal totalSaidas = Optional.ofNullable(gastoService.calcularTotalSaida(usuarioIdInt)).orElse(BigDecimal.ZERO);
        
        model.addAttribute("gastos", Optional.ofNullable(gastoService.listarTodos(usuarioIdInt)).orElse(Collections.emptyList()));
        model.addAttribute("saldoTotal", saldoTotal.floatValue());
        model.addAttribute("totalEntradas", totalEntradas.floatValue());
        model.addAttribute("totalSaidas", totalSaidas.floatValue());

        return "gerenciador";
    }

    @PostMapping("/gastos/adicionar")
    public String adicionarGasto(@RequestParam String description, 
                                 @RequestParam String value,
                                 @RequestParam String category,
                                 @RequestParam Boolean type,
                                 HttpSession session) {
        Long usuarioId = obterUsuarioId(session);
        if (usuarioId == null) {
            return "redirect:/";
        }

        try {
            if (value != null && !value.trim().isEmpty()) {
                value = value.replace(",", ".");
                BigDecimal valor = new BigDecimal(value);
                Gasto gasto = new Gasto(null, description, category, valor, type, usuarioId);
                gastoService.salvar(gasto);
            } else {
                logger.warn("Erro ao adicionar gasto: valor inválido");
                return "redirect:/gerenciador?erro=valor_invalido";
            }
        } catch (NumberFormatException e) {
            logger.error("Erro ao adicionar gasto: {}", e.getMessage());
            return "redirect:/gerenciador?erro=valor_invalido";
        }

        logger.info("Gasto adicionado com sucesso");
        return "redirect:/gerenciador";
    }

    @PostMapping("/gastos/editar/{id}")
    @ResponseBody
    public ResponseEntity<?> editarGasto(
            @PathVariable Integer id,
            @RequestBody Gasto gasto,
            HttpSession session) {

        Long usuarioId = obterUsuarioId(session);
        if (usuarioId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
        }
        gasto.setCd_id(id);
        gasto.setCd_user(usuarioId);

        try {
            gastoService.atualizar(gasto);
            return ResponseEntity.ok("Gasto atualizado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o gasto");
        }
    }

    @PostMapping("/gastos/deletar/{id}")
    public String deletarGasto(@PathVariable("id") Integer id, HttpSession session) {
        Long usuarioId = obterUsuarioId(session);
        if (usuarioId == null) {
            return "redirect:/";
        }

        boolean deletado = gastoService.deletar(id);
        if (deletado) {
            return "redirect:/gerenciador";
        } else {
            return "redirect:/gerenciador?erro=true";
        }
    }

    @GetMapping("/api/gastos/{id}")
    @ResponseBody
    public Gasto buscarGastoPorId(@PathVariable Integer id, HttpSession session) {
        Long usuarioId = obterUsuarioId(session);
        if (usuarioId == null) {
            throw new RuntimeException("Usuário não autenticado.");
        }

        Gasto gasto = gastoService.buscarPorId(id);
        if (gasto == null) {
            throw new RuntimeException("Gasto não encontrado.");
        }

        return gasto;
    }
}