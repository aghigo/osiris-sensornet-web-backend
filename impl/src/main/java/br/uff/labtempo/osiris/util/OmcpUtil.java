package br.uff.labtempo.osiris.util;

import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.StatusCode;
import br.uff.labtempo.omcp.common.exceptions.*;
import org.springframework.http.HttpStatus;

public class OmcpUtil {

    public static HttpStatus toHttpStatus(StatusCode omcpStatus) {
        return HttpStatus.valueOf(omcpStatus.toCode());
    }

    public static StatusCode toOmcpStatus(HttpStatus httpStatus) {
        return StatusCode.valueOf(httpStatus.name());
    }

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
