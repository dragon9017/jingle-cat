package com.dosion.annotation.log;

import com.dosion.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 参数打印
 *
 * @author 陈登文
 */
@Aspect
@Component
@Slf4j
public class WebLogAop {
    private long serialNumber;

    @Pointcut("execution(* com.dosion.model.*.controller..*(..))")
    public void webLog() {
    }

    /**
     * 方法执行前
     *
     * @param point
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint point) throws Throwable {
        ServletRequestAttributes attribute = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attribute.getRequest();
        //订单号
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        serialNumber = idWorker.nextId();
        log.info("流水号：[" + serialNumber + "]=====================>接收请求 开始<=====================");
        log.info("流水号：[" + serialNumber + "]请求URL：" + request.getRequestURL().toString());
        log.info("流水号：[" + serialNumber + "]请求类型：" + request.getMethod());
        Enumeration<String> enu = request.getParameterNames();
        log.info("流水号：[" + serialNumber + "]=====================>打印请求参数 开始<=====================");
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            log.info("流水号：[" + serialNumber + "]name：" + name + ",value：" + request.getParameter(name));
        }
        log.info("流水号：[" + serialNumber + "]=====================>打印请求参数 结束<=====================");
        log.info("流水号：[" + serialNumber + "]=====================>接收请求 结束<=====================");
    }

    /**
     * 方法执行结束
     *
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void aa(Object ret) throws Throwable {
        log.info("流水号：[" + serialNumber + "]=====================>请求处理 结束 流水号：" + serialNumber + "<=====================");
        log.info("流水号：[" + serialNumber + "]返回数据：" + ret);
    }

}
