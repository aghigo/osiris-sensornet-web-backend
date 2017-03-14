package br.uff.labtempo.osiris.util;


import br.uff.labtempo.omcp.common.StatusCode;
import org.springframework.http.HttpStatus;

public class OmcpUtil {

    public static HttpStatus toHttpStatus(StatusCode omcpStatus) {
        switch(omcpStatus){
            case OK:
                return HttpStatus.OK;
            case CREATED:
                return HttpStatus.CREATED;
            case BAD_REQUEST:
                return HttpStatus.BAD_REQUEST;
            case FORBIDDEN:
                return HttpStatus.FORBIDDEN;
            case INTERNAL_SERVER_ERROR:
                return HttpStatus.INTERNAL_SERVER_ERROR;
            case METHOD_NOT_ALLOWED:
                return HttpStatus.METHOD_NOT_ALLOWED;
            case NOT_FOUND:
                return HttpStatus.NOT_FOUND;
            case NOT_IMPLEMENTED:
                return HttpStatus.NOT_IMPLEMENTED;
            case REQUEST_TIMEOUT:
                return HttpStatus.REQUEST_TIMEOUT;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
