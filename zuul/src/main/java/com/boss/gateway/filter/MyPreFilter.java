package com.boss.gateway.filter;

import com.boss.gateway.util.JwtUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MyPreFilter extends ZuulFilter {
    private static final Logger logger= LoggerFactory.getLogger(MyPreFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
//        logger.info(getClass().getTypeName()+"is working!");

        RequestContext context = RequestContext.getCurrentContext();
//        context.setSendZuulResponse(false);
//        context.setResponseStatusCode(401);
//        context.setResponseBody(HttpStatus.UNAUTHORIZED.getReasonPhrase());

//        String token = context.getRequest().getParameter("token");
//        if(StringUtils.isEmpty(token)){
//            context.getResponse().setHeader("Content-Type", "text/html;charset=UTF-8");
//            context.setSendZuulResponse(false);
//            context.setResponseStatusCode(401);
//            context.setResponseBody("用户未登录");
//        }
        String token = context.getRequest().getParameter("token");
        String uid = context.getRequest().getParameter("userId");
        if(token!=null && uid!=null){
            String userIdInToken = JwtUtils.verify(token);
            if(uid.equals(userIdInToken)){
                return null;
            }
        }
        context.getResponse().setHeader("Content-Type", "text/html;charset=UTF-8");
        context.setSendZuulResponse(false);
        context.setResponseStatusCode(401);
        context.setResponseBody("用户未登录");
        return null;
    }
}
