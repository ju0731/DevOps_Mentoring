package com.dineshonjava.accountservice;

import java.util.UUID;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class PostZuulFilter extends ZuulFilter {
	String POST_TYPE="POST";
	int SEND_RESPONSE_FILTER_ORDER = 3;
	
    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return SEND_RESPONSE_FILTER_ORDER - 1 ;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();

        context.getResponse().addHeader("Trace-ID", UUID.randomUUID().toString());

        return null;
    }

}