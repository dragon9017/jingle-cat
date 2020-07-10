package com.dosion.exception;

import com.dosion.constant.Constant;
import com.dosion.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
     * 所有异常报错
     *
     * @param exception
     * @return String
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public R allExceptionHandler(Exception exception) {
        exception.printStackTrace();
        log.error("程序异常:", exception);
        //生产环境
        if (Constant.PROD.equals(active)) {
            return R.failed("程序异常，请联系工作人员");
        }
        //开发环境
        else if (Constant.DEV.equals(active)) {
            return R.failed("程序异常:" + exception.getMessage());
        }
        return R.failed("程序异常:" + exception.getMessage());
    }
}
