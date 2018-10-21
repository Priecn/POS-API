package com.pos.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class POSAuthenticationResponse implements Serializable {

    public static final long serialVersionUID = 3434342321233L;

    private String token;
}
