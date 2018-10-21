package com.pos.security.model;

import lombok.*;

import java.io.Serializable;

@Data
public class POSAuthenticationRequest implements Serializable {

    private static final long serialVersionUID = -32123224343121L;

    private String username;
    private String password;
}
