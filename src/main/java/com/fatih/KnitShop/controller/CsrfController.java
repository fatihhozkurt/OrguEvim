package com.fatih.KnitShop.controller;

import com.fatih.KnitShop.controller.api.CsrfControllerApi;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfController implements CsrfControllerApi {
    @Override
    public CsrfToken csrf(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    }
}
