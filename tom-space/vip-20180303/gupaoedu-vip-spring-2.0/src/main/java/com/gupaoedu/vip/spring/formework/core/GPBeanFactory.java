package com.gupaoedu.vip.spring.formework.core;

/**
 * Created by Tom on 2018/4/21.
 */
public interface GPBeanFactory {

    /**
     * 根据beanName从IOC容器之中获得一个实例Bean
     * @param beanName
     * @return
     */
    Object getBean(String beanName);

}
