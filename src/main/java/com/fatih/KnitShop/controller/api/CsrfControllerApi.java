package com.fatih.KnitShop.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.fatih.KnitShop.consts.UrlConst.CSRF;

@RequestMapping(CSRF)
public interface CsrfControllerApi {

    @GetMapping
    CsrfToken csrf(HttpServletRequest request);
}
