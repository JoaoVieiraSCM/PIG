package com.db.sitebd.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Gasto {

    private Integer cd_id;
    private String ds_descricao;
    private String ds_categoria;
    private BigDecimal vl_valor;
    private Boolean ic_tipo;
    private LocalDate dt_data;
    private Long cd_user;

    public Gasto() {
    }

    public Gasto(Integer cd_id, String ds_descricao, String ds_categoria, BigDecimal vl_valor, Boolean ic_tipo, Long cd_user) {
        this.cd_id = cd_id;
        this.ds_descricao = ds_descricao;
        this.ds_categoria = ds_categoria;
        this.vl_valor = vl_valor;
        this.ic_tipo = ic_tipo;
        this.cd_user = cd_user;
    }

    public Gasto(Integer cd_id, String ds_descricao, String ds_categoria, BigDecimal vl_valor, Boolean ic_tipo, LocalDate dt_data, Long cd_user) {
        this.cd_id = cd_id;
        this.ds_descricao = ds_descricao;
        this.ds_categoria = ds_categoria;
        this.vl_valor = vl_valor;
        this.ic_tipo = ic_tipo;
        this.dt_data = dt_data;
        this.cd_user = cd_user;
    }

    public Integer getCd_id() {
        return cd_id;
    }

    public void setCd_id(Integer cd_id) {
        this.cd_id = cd_id;
    }

    public String getDs_descricao() {
        return ds_descricao;
    }

    public void setDs_descricao(String ds_descricao) {
        this.ds_descricao = ds_descricao;
    }

    public String getDs_categoria() {
        return ds_categoria;
    }

    public void setDs_categoria(String ds_categoria) {
        this.ds_categoria = ds_categoria;
    }

    public BigDecimal getVl_valor() {
        return vl_valor;
    }

    public void setVl_valor(BigDecimal vl_valor) {
        this.vl_valor = vl_valor;
    }

    public Boolean getIc_tipo() {
        return ic_tipo;
    }

    public void setIc_tipo(Boolean ic_tipo) {
        this.ic_tipo = ic_tipo;
    }

    public LocalDate getDt_data() {
        return dt_data;
    }

    public void setDt_data(LocalDate dt_data) {
        this.dt_data = dt_data;
    }

    public Long getCd_user() {
        return cd_user;
    }

    public void setCd_user(Long cd_user) {
        this.cd_user = cd_user;
    }
}