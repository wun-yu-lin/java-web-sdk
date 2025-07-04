package sdk.mssearch.javasdk.exception;

import org.slf4j.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sdk.mssearch.javasdk.logger.SdkLoggerFactory;


@ControllerAdvice
public class ControllerExceptionAdvice {

    private static final Logger logger = SdkLoggerFactory.getLogger(ControllerExceptionAdvice.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> prepareResponseForException(Exception exception) throws Exception {
        logger.error(exception.getMessage(),  exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
