package com.google.codelabs.mdc.java.shrine.Model;

import java.util.List;

public class RptaGetUsuarios {
    private int code;
    private List<Usuario> data;
    private String message;

    public int getCode() {
        return code;
    }

    public List<Usuario> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
