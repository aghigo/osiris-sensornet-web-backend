package br.uff.labtempo.osiris.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * Utilitary class to handle request Headers
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
public class AllowHeaderUtil {

    /**
     * Mounts a request with HttpMethods in the allow header
     * @see HttpHeaders
     * @see HttpMethod
     * @param methods
     * @return Http response containing httpMethods on the allow header
     */
    public static ResponseEntity<Void> allows(HttpMethod... methods) {
        HttpHeaders headers = new HttpHeaders();
        Set<HttpMethod> allow = new HashSet<>();
        for(HttpMethod method: methods){
            allow.add(method);
        }
        headers.setAllow(allow);

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }
}

