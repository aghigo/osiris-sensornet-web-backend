package br.uff.labtempo.osiris.util;

import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.StatusCode;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.osiris.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.net.URI;

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
                throw new NotFoundException(response.getErrorMessage());
            case NOT_IMPLEMENTED:
                throw new NotImplementedException(response.getErrorMessage());
            case REQUEST_TIMEOUT:
                throw new RequestTimeoutException(response.getErrorMessage());
            case INTERNAL_SERVER_ERROR:
                throw new InternalServerErrorException(response.getErrorMessage());
            case BAD_REQUEST:
                throw new BadRequestException(response.getErrorMessage());
            case FORBIDDEN:
                throw new ForbiddenException(response.getErrorMessage());
            case METHOD_NOT_ALLOWED:
                throw new MethodNotAllowedException(response.getErrorMessage());
            case OK:
                break;
            case CREATED:
                break;
            default:
                throw new InternalServerErrorException(response.getErrorMessage());
        }
    }

    /**
     * Gets the id of an URI of created resource
     * e.g. /virtualsensornet/link/2
     * returns 2
     * @param uri
     * @return id
     */
    public static long getIdFromUri(URI uri) {
        return Long.parseLong(uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1));
    }

    public static ErrorResponse formatErrorResponse(AbstractRequestException exception) {
        return ErrorResponse.builder()
                .statusCode(exception.getStatusCode())
                .message(exception.getMessage())
                .build();
    }

    public static ErrorResponse formatErrorResponse(Exception exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .build();
    }
}
