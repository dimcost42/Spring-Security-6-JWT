package com.ons.securitylayerJwt.restcontroller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ons.securitylayerJwt.security.CustomerUserDetailsService;
import com.ons.securitylayerJwt.security.JwtUtilities;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/token")
public class TokenRestController {

    @Autowired
    JwtUtilities jwtUtilities;
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;


    @GetMapping("/validate")
    public ResponseEntity<?> getToken(HttpServletRequest httpServletRequest) {
        String token = jwtUtilities.getToken(httpServletRequest);
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", jwtUtilities.extractUsername(token));
        map.put("isValidToken", jwtUtilities.validateToken(token));
        map.put("isTokenExpired", jwtUtilities.isTokenExpired(token));
        map.put("roles", jwtUtilities.extractAllClaims(token).get("role").toString()
                .replace("[", "").replace("]", ""));
        map.put("expirationDate", jwtUtilities.extractExpiration(token).toString());
        return ResponseEntity.ok(map);
    }
}
