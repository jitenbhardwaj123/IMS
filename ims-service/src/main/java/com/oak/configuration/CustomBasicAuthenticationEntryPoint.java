package com.oak.configuration;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import com.oak.common.exception.handler.ApiMessageGenerator;
import com.oak.common.exception.message.ApiMessage;

public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
	private final ApiMessageGenerator messageGenerator;
	
    public CustomBasicAuthenticationEntryPoint(Logger logger) {
		messageGenerator = new ApiMessageGenerator(logger);
	}

	@Override
    public void commence(final HttpServletRequest request, 
            final HttpServletResponse response, 
            final AuthenticationException authException) throws IOException, ServletException {
        //Authentication failed, send error response.
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
         
        PrintWriter writer = response.getWriter();
        ApiMessage apiMessage = messageGenerator.generate(authException);
		writer.println(new ToStringBuilder(apiMessage, ToStringStyle.JSON_STYLE).append("type", apiMessage.getType()).append("code", apiMessage.getCode())
				.append("label", apiMessage.getLabel()).append("messages", apiMessage.getMessages()).toString());
    }
     
    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("Oak IT");
        super.afterPropertiesSet();
    }
}
