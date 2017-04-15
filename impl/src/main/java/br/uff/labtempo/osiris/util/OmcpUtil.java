package br.uff.labtempo.osiris.util;

import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.StatusCode;
import br.uff.labtempo.omcp.common.exceptions.*;
import org.springframework.http.HttpStatus;

/**
 * Utilitary class to handle common OMCP protocol operations
 * @see Response
 * @see br.uff.labtempo.omcp.client.OmcpClient
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
public class OmcpUtil {

    /**
     * Converts OMCP Status Code to HTTP Status Code
     * @see StatusCode
     * @see HttpStatus
     * @param omcpStatus
     * @return HttpStatus
     */
    public static HttpStatus toHttpStatus(StatusCode omcpStatus) {
        return HttpStatus.valueOf(omcpStatus.toCode());
    }

    /**
     * Converts HTTP Status Code to OMCP Status Code
     * @see HttpStatus
     * @see StatusCode
     * @param httpStatus
     * @return StatusCode
     */
    public static StatusCode toOmcpStatus(HttpStatus httpStatus) {
        return StatusCode.valueOf(httpStatus.name());
    }

    /**
     * Handle OMCP Response StatusCode
     * @see Response
     * @see StatusCode
     * @param response
     * @throws AbstractRequestException
     */
    public static void handleOmcpResponse(Response response) throws AbstractRequestException {
        switch(response.getStatusCode()) {
            case NOT_FOUND:
                throw new NotFoundException();
            case NOT_IMPLEMENTED:
                throw new NotImplementedException();
            case REQUEST_TIMEOUT:
                throw new RequestTimeoutException();
            case INTERNAL_SERVER_ERROR:
                throw new InternalServerErrorException();
            case BAD_REQUEST:
                throw new BadRequestException();
            case FORBIDDEN:
                throw new ForbiddenException();
            case METHOD_NOT_ALLOWED:
                throw new MethodNotAllowedException();
        }
    }
}
