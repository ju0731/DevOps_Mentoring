package com.dineshonjava.accountservice;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpStatus;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class PreZuulFilter extends ZuulFilter {
	String PRE_TYPE="PRE";
	int PRE_DECORATION_FILTER_ORDER = 2;
	
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        if (!request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")) {
            context.setSendZuulResponse(false);

            context.setResponseStatusCode(HttpStatus.SC_FORBIDDEN);
        }

        return null;
    }
}