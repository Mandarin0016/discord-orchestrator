package orchestrator.api;

import com.fasterxml.jackson.databind.JsonMappingException;
import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import orchestrator.user.exception.UserDomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    public static final UUID UNAUTHORIZED_UUID = UUID.fromString("2ba57386-9c30-11ee-8c90-0242ac120002");
    public static final UUID INTERNAL_SERVER_ERROR_UUID = UUID.fromString("7059c87e-9c30-11ee-8c90-0242ac120002");
    public static final UUID BAD_REQUEST_ERROR_UUID = UUID.fromString("1b38fe4a-9c31-11ee-8c90-0242ac120002");

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> accessDenied() {

        return getErrorResponse(UNAUTHORIZED_UUID, UNAUTHORIZED, null);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> internalServerError(Throwable throwable) {

        return getErrorResponse(INTERNAL_SERVER_ERROR_UUID, INTERNAL_SERVER_ERROR, throwable.getMessage());
    }

    @ExceptionHandler({
            HttpMediaTypeException.class,
            ServletRequestBindingException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class,
            IllegalArgumentException.class,
            JsonMappingException.class,
            MethodArgumentTypeMismatchException.class,
            BindException.class
    })
    public ResponseEntity<ErrorResponse> badRequest() {

        return getErrorResponse(BAD_REQUEST_ERROR_UUID, BAD_REQUEST, null);
    }

    @ExceptionHandler(UserDomainException.class)
    public ResponseEntity<ErrorResponse> badRequest(UserDomainException domainException) {

        return getErrorResponse(INTERNAL_SERVER_ERROR_UUID, INTERNAL_SERVER_ERROR, domainException.getMessage());
    }

    private static ResponseEntity<ErrorResponse> getErrorResponse(UUID responseId, HttpStatus httpStatus, String data) {

        final ErrorResponse error = ErrorResponse.builder()
                .errorCode(responseId)
                .time(OffsetDateTime.now())
                .data(data)
                .build();

        return ResponseEntity
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }
}
