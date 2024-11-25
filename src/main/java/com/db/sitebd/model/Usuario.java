package com.db.sitebd.model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Usuario {

    private Long cd_id;
    private String ds_email;
    private String ds_hash_password;
    private String nm_foto;
    private String path_foto;

    public Usuario() {}

    public Usuario(Long cd_id, String ds_email, String ds_hash_password, String nm_foto, String path_foto) {
        this.cd_id = cd_id;
        setDs_email(ds_email);
        setDs_hash_password(ds_hash_password);
        this.nm_foto = nm_foto;
        setPath_foto(path_foto);
    }

    public Long getCd_id() {
        return cd_id;
    }

    public void setCd_id(Long cd_id) {
        this.cd_id = cd_id;
    }

    public String getDs_email() {
        return ds_email;
    }

    public void setDs_email(String ds_email) {
        if (ds_email != null && Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", ds_email)) {
            this.ds_email = ds_email;
        } else {
            throw new IllegalArgumentException("Email inválido");
        }
    }

    public String getDs_hash_password() {
        return ds_hash_password;
    }

    public void setDs_hash_password(String ds_hash_password) {
        if (ds_hash_password != null && ds_hash_password.length() >= 8 && Pattern.matches(".*[A-Z].*", ds_hash_password)
            && Pattern.matches(".*[0-9].*", ds_hash_password) && Pattern.matches(".*[!@#$%^&*(),.?\":{}|<>].*", ds_hash_password)) {
            this.ds_hash_password = ds_hash_password;
        } else {
            throw new IllegalArgumentException("A senha deve ter mais de 8 caracteres, incluindo maiúsculas, números e caracteres especiais");
        }
    }

    public String getNm_foto() {
        return nm_foto;
    }

    public void setNm_foto(String nm_foto) {
        this.nm_foto = nm_foto;
    }

    public String getPath_foto() {
        return path_foto;
    }

    public void setPath_foto(String path_foto) {
        this.path_foto = path_foto;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "cd_id=" + cd_id +
                ", ds_email='" + ds_email + '\'' +
                ", nm_foto='" + nm_foto + '\'' +
                ", path_foto='" + path_foto + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(cd_id, usuario.cd_id) &&
               Objects.equals(ds_email, usuario.ds_email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cd_id, ds_email);
    }
}