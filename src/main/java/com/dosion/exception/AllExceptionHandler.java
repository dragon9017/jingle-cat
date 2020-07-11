package com.dosion.exception;

import com.dosion.constant.Constant;
import com.dosion.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 *
 * @author 陈登文
 */
@RestControllerAdvice
@Slf4j
public class AllExceptionHandler {

    @Value("${spring.profiles.active}")
    private String active;

    /**
     * 自定义异常统一处理
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<R> customException(CustomException exception) {
        log.error("自定义异常，错误码：{}，异常信息：{}", exception.getCode(), exception.getMessage());
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.valueOf(exception.getCode());
        return new ResponseEntity<R>(R.failed(exception.getMessage()), responseHeaders, httpStatus);
    }


    /**
     * 所有异常报错
     *
     * @param exception
     * @return String
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<R> allExceptionHandler(Exception exception) {
        log.error("系统异常，异常信息：{}", exception.getMessage());
        HttpHeaders responseHeaders = new HttpHeaders();

        R<Object> failed = null;
        //生产环境
        if (Constant.PROD.equals(active)) {
            failed = R.failed("程序异常，请联系工作人员");
        }
        //开发环境
        else if (Constant.DEV.equals(active)) {
            failed = R.failed(exception.getMessage());
        }
        return new ResponseEntity<R>(failed, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
