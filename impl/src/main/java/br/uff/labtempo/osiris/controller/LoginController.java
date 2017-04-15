package br.uff.labtempo.osiris.controller;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static br.uff.labtempo.osiris.util.AllowHeaderUtil.allows;

/**
 * Controller class to serve endpoints for the Login API
 * Login user (authenticate), Logout user (revoke authentication)
 * Created by andre on 15/04/17.
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@RequestMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LoginController {
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login() {
        return null;
    }

    @RequestMapping(value = "/login", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsLogin() {
        return allows(HttpMethod.POST, HttpMethod.OPTIONS);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logout() {
        return null;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsLogout() {
        return allows(HttpMethod.POST, HttpMethod.OPTIONS);
    }
}
