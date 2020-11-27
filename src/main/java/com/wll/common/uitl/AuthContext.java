package com.wll.common.uitl;


import com.wll.common.bean.AuthEntity;

public class AuthContext {

    private static final ThreadLocal<AuthEntity>  authEntityThreadLocal = new ThreadLocal<>();

    public static void save(AuthEntity authEntity){
        authEntityThreadLocal.set(authEntity);
    }

    public static void remove(){
        authEntityThreadLocal.remove();
    }

    public static AuthEntity get(){
        return authEntityThreadLocal.get();
    }
}
