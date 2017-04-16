package br.uff.labtempo.osiris.controller;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static br.uff.labtempo.osiris.util.AllowHeaderUtil.allows;

/**
 * Controller class to serve endpoints for the Login API
 * Login user (authenticate), Logout user (revoke authentication)
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@RequestMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LoginController {

    /**
     * Perform user Login
     * Authenticates user by a given Token
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(auth);
    }

    /**
     * Get a list of all available HTTP methods of the /login endpoint
     * @return list of HTTP methods
     */
    @RequestMapping(value = "/login", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsLogin() {
        return allows(HttpMethod.POST, HttpMethod.OPTIONS);
    }

    /**
     * Perform User logout. Revokes user authentication
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logout() {
        return null;
    }

    /**
     * Get a list of all available HTTP methods of the /logout endpoint
     * @return list of HTTP methods
     */
    @RequestMapping(value = "/logout", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsLogout() {
        return allows(HttpMethod.POST, HttpMethod.OPTIONS);
    }
}
