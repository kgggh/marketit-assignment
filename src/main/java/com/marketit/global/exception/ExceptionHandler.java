package com.marketit.global.exception;

import com.marketit.global.ResponseVO;
import com.marketit.service.ItemException;
import com.marketit.service.OrderNotExistException;
import com.marketit.service.OrderStatusChangeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<Object> exceptionHandler(final HttpMessageNotReadableException e) {
        log.error(String.format("[HttpMessageNotReadableException] msg=%s", e.getMessage()));
        return ResponseEntity.badRequest()
                .body(ResponseVO.fail(ErrorCode.BAD_REQUEST, e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({OrderStatusChangeException.class})
    public ResponseEntity<ResponseVO> exceptionHandler(final OrderStatusChangeException e) {
        log.error(String.format("[OrderException] msg=%s", e.getMessage()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseVO.fail(ErrorCode.ORDER_STATUS_CANNOT_BE_CHANGED, e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({OrderNotExistException.class})
    public ResponseEntity<ResponseVO> exceptionHandler(final OrderNotExistException e) {
        log.error(String.format("[OrderException] msg= %s", e.getMessage()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseVO.fail(ErrorCode.ORDER_STATUS_CANNOT_BE_CHANGED, e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ItemException.class})
    public ResponseEntity<ResponseVO> exceptionHandler(final ItemException e) {
        log.error(String.format("[ItemException] msg=%s", e.getMessage()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseVO.fail(ErrorCode.ITEM_NOT_EXIST, e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseVO> exceptionHandler(final Exception e) {
        log.error(String.format("[Exception] msg=%s", e.getMessage()));
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseVO.fail(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()));
    }
}
