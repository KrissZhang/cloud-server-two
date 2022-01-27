package com.self.cloudserver.exception;

import com.self.cloudserver.dto.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomizedResponseEntityExceptionHandler.class);

    /**
     * 参数校验异常
     */
    @ExceptionHandler(BindException.class)
    public final ResultEntity<Object> validatedBindException(BindException e) {
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return ResultEntity.addError("401", message);
    }

    /**
     * json参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResultEntity<Object> validatedBindException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResultEntity.addError("401", message);
    }

}
