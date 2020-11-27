package com.wll.common.bean;


import lombok.Data;

@Data
public class AuthEntity {

    private String token;

    private String refreshToken;

}
