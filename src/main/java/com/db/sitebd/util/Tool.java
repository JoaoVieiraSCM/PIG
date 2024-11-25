package com.db.sitebd.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import com.db.sitebd.model.*;

public class Tool {
    
    public static Usuario converterUsuario(Map<String, Object> row) {
        if (row == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        
        usuario.setCd_id(getLong(row, "cd_id"));
        usuario.setDs_email(getString(row, "ds_email"));
        usuario.setDs_hash_password(getString(row, "ds_hash_password"));
        usuario.setNm_foto(getString(row, "nm_foto"));
        usuario.setPath_foto(getString(row, "path_foto"));
        
        return usuario;
    }

    public static Gasto converterGasto(ResultSet rs) throws SQLException {
        Gasto gasto = new Gasto();
        
        gasto.setCd_id(rs.getInt("cd_id"));
        gasto.setDs_descricao(rs.getString("ds_descricao"));
        gasto.setDs_categoria(rs.getString("ds_categoria"));
        gasto.setVl_valor(rs.getBigDecimal("vl_valor"));
        gasto.setIc_tipo(rs.getBoolean("ic_tipo"));
        gasto.setDt_data(rs.getDate("dt_data").toLocalDate());
        gasto.setCd_user(rs.getLong("cd_user"));
        
        return gasto;
    }

    private static Long getLong(Map<String, Object> row, String key) {
        Object value = row.get(key);
        if (value == null) {
            return null;
        }
        
        if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof Integer) {
            return ((Integer) value).longValue();
        }
        
        return null;
    }

    private static String getString(Map<String, Object> row, String key) {
        Object value = row.get(key);
        if (value == null) {
            return null;
        }
        
        if (value instanceof String) {
            return (String) value;
        }
        
        return value.toString();
    }
}
