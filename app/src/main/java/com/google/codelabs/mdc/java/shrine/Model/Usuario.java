package com.google.codelabs.mdc.java.shrine.Model;

import java.util.List;

public class Usuario {
    private int id;
    private String claveUsuario;
    private String username;

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public class UsuarioList {
        private List<List<Object>> usuarios;

        public List<List<Object>> getUsuarios() {
            return usuarios;
        }

        public void setUsuarios(List<List<Object>> usuarios) {
            this.usuarios = usuarios;
        }
    }
}
