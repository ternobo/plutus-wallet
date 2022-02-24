package com.ternobo.wallet.user.http.filters;

import com.ternobo.wallet.client.services.ApplicationClientService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class SignupFilter implements Filter {

    private final ApplicationClientService applicationClientService;

    public SignupFilter(ApplicationClientService applicationClientService) {
        this.applicationClientService = applicationClientService;
    }


    // ClientToken.ClientSecret
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String authorizationToken = "";
        try {
            byte[] authorizations = Base64.decodeBase64(
                    Optional.ofNullable(req.getHeader("Authorization"))
                            .orElse("Bearer Tk9BUElUT0tFTi5UT0tFTg==")
                            .replace("Bearer", ""));
            authorizationToken = new String(authorizations);
        } catch (IllegalStateException e) {
            resp.sendError(401, "Invalid API Key");
        }


        String[] authTokenSplit = authorizationToken.split("\\.");
        if (authTokenSplit.length == 2) {
            String clientToken = authTokenSplit[0];
            String clientSecret = authTokenSplit[1];

            if (this.applicationClientService.validateApplicationClient(clientToken, clientSecret)) {
                chain.doFilter(request, response);
                return;
            }
        }
        resp.sendError(401, "Invalid API Key");
    }
}
