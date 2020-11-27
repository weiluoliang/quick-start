package com.wll.common.filter;

import com.wll.common.uitl.RequestUtils;
import com.wll.common.uitl.TraceIDUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class SimpleCORSFilter implements Filter {


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        ResponseWrapper response = new ResponseWrapper((HttpServletResponse) res);
        HttpServletRequest request = getWrapper((HttpServletRequest)req);
        //设置允许跨域访问的域，*表示支持所有的来源
        response.setHeader("Access-Control-Allow-Origin", "*");
        //设置允许跨域访问的方法
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers","Origin,X-Requested-With,Content-Type,Accept,Connection,User-Agent,Cookie,token,version");

        String token = request.getHeader("token");
        TraceIDUtils.addTraceId();
        //get params
        String header = request.getHeader("Content-Type");
        String servletPath = request.getServletPath();
        String method = request.getMethod();
        String data = RequestUtils.getReqData(request);
        // write log
        String version = request.getHeader("version");
        log.info("request path:{},method:{},data:{},token:{},content-type:{},version:{}",servletPath,method,data,token, header,version );
        //next filter
        chain.doFilter(request, response);
        //remove
        String resData = getResData(servletPath,response);
        log.info("response time:{}(Millis), path:{},data:{}",System.currentTimeMillis()-startTime,servletPath, resData );
        // 数据埋点处理
        TraceIDUtils.remove();
    }

    private String getResData(String path,ResponseWrapper response) {
        if("/base/getConfig".equals(path)){
            return "";
        }
        String contentType = response.getContentType();
        if(contentType != null && contentType.contains("multipart/form-data")){
            return "";
        }
        return new String(response.toByteArray());
    }

    private HttpServletRequest getWrapper(HttpServletRequest req) {
        String method = req.getMethod();
        if("POST".equalsIgnoreCase(method) ){
            String contentType = req.getContentType();
            if(contentType == null){
                return req;
            }
            if(contentType.contains("application/x-www-form-urlencoded")){
                return req;
            }
            if(contentType.contains("multipart/form-data")){
                return req;
            }
            try {
                return new RequestWrapper(req);
            } catch (IOException e) {
                log.error("wrapper error ",e );
                return req;
            }
        }
        return req;
    }
}
