package com.wll.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.wll.common.bean.AuthEntity;
import com.wll.common.bean.ResultEnum;
import com.wll.common.bean.RetData;
import com.wll.common.handler.AuthHandler;
import com.wll.common.uitl.AuthContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private AuthHandler authHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = getToken(request);
        if(StringUtils.isBlank(token)){
            log.info("token cannot be null . ");
            response.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(JSON.toJSONString(RetData.result(ResultEnum.INCORRECT_PARAMETERS.getCode(),"token cannot be null . ")));
            return false;
        }
        AuthEntity authEntity = authHandler.auth(token);
        if(authEntity == null ){
            response.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(JSON.toJSONString(RetData.result(ResultEnum.TOKEN_EXPIRED)));
            return false;
        }
        AuthContext.save(authEntity);
        return true;
    }


    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if(token == null){
            token = request.getParameter("token");
        }
        return token;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthContext.remove();
    }

}
