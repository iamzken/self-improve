package com.gupaoedu.exception;

import com.dangdang.ddframe.job.executor.handler.JobExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: qingshan
 * @Date: 2018/11/13 14:19
 * @Description: 咕泡学院，只为更好的你
 */
@Slf4j
public class CustomJobExceptionHandler implements JobExceptionHandler {
    @Override
    public void handleException(String jobName, Throwable cause) {
        log.error(String.format("Job '%s' exception occur in job processing", jobName), cause);
        //这里自定义异常处理逻辑
    }
}