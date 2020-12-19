package com.hfaria.micronotesback.authentication.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JWTService {

    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        try {
            InputStream stream = getClass().getResourceAsStream("/micronotes.jks");
            keyStore = KeyStore.getInstance("JKS");
            keyStore.load(stream, "123456".toCharArray());
        } catch (NoSuchAlgorithmException | CertificateException | IOException | KeyStoreException e) {
            throw new RuntimeException("Couldn't load keystore.");
        }

    }

    public String getToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return Jwts.builder().setSubject(user.getUsername()).signWith(getPrivateKey()).compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey)keyStore.getKey("micronotes", "123456".toCharArray());
        } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Couldn't get private key.");
        }
    }

    public boolean validateToken(String token) {
        Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(token);
        return true;
    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("micronotes").getPublicKey();
        } catch (KeyStoreException e) {
            throw new RuntimeException("Couldn't get private key.");
        }
    }

    public String getSubjectFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}
