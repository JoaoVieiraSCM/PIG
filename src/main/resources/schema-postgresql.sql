CREATE TABLE IF NOT EXISTS usuario (
    cd_id SERIAL PRIMARY KEY,
    ds_email VARCHAR(255) NOT NULL UNIQUE,
    ds_hash_password VARCHAR(255) NOT NULL,
    nm_foto VARCHAR(100),
    path_foto TEXT
);

CREATE TABLE IF NOT EXISTS gasto (
    cd_id SERIAL PRIMARY KEY,
    ds_descricao VARCHAR(255),
    ds_categoria VARCHAR(255),
    vl_valor NUMERIC(10,2) NOT NULL,
    ic_tipo BOOLEAN NOT NULL,
    dt_data TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cd_user INT NOT NULL,
    CONSTRAINT fk_gastos_usuario FOREIGN KEY (cd_user) REFERENCES usuario (cd_id) ON DELETE CASCADE
);