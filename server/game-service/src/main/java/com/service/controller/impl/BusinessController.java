package com.service.controller.impl;

import com.service.annotation.EasyMapping;
import com.service.annotation.EasyModule;
import com.service.controller.RunnableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class BusinessController implements BeanPostProcessor , RunnableFactory {

    private static final Logger logger = LoggerFactory.getLogger(BusinessController.class);
    /**
     * commandInfo的key是Command对应的RouteInfo
     * RouteInfo包含组的信息，根据组选择业务线程池
     * RouteInfo包含BeanClass信息，通过ApplicationContenxt获取Bean对象
     * RouteInfo通过获得Method对象，Method.invoke(bean)创建Runnable对象，添加到线程池中
     */
    private Map<String, RouteInfo> commandInfo = new HashMap<>();
    /**
     * 每个命令对于的组
     * */
    private Map<String, String> commandModule = new HashMap<>();

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        EasyModule bean_annotation = bean.getClass().getAnnotation(EasyModule.class);
        if (bean_annotation != null) {

            logger.info("添加映射模块：{} ， Bean类型：{}", bean_annotation.name(), bean.getClass());

            Method[] methods = bean.getClass().getMethods();
            for (Method method : methods) {
                EasyMapping m_annotation = method.getAnnotation(EasyMapping.class);
                if (m_annotation != null) {
                    RouteInfo routeInfo = new RouteInfo();
                    routeInfo.command = m_annotation.command();
                    routeInfo.clazz = bean.getClass();
                    routeInfo.method = method;
                    routeInfo.bean = bean;
                    routeInfo.module = bean_annotation.name();
                    routeInfo.deprecated = m_annotation.deprecated();
                    routeInfo.group = bean_annotation.group();
                    /*加入到Map中，下次使用这个对象来获取Runable创建线程*/
                    commandInfo.put(m_annotation.command(), routeInfo);
                    /*决定Runnalbe对象使用哪个线程池*/
                    commandModule.put(m_annotation.command(), bean_annotation.group());
                }
            }
        }

        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Runnable getRunnable(String command, Object data) {

        RouteInfo routeInfo = commandInfo.get(command);
        if (routeInfo != null) {
            if (routeInfo.deprecated) {
                logger.error("客户端获取放弃的映射command:{}, bean Clazz:{}", command ,routeInfo.clazz);
                return null;
            } else {
                Method method = routeInfo.method;
                Object bean = routeInfo.bean;
                return () ->{
                    try {
                        method.invoke(bean, data);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        logger.error("不好了，出现错误了BusinessController->getRunnalbe:{}", command);
                    }
                };
            }
        } else {
            logger.error("客户端获取未设置的映射command:{}", command);
            return null;
        }

    }

    @Override
    public String getGroup(String command) {
        return commandModule.get(command);
    }

    class RouteInfo {
        /*通过注解得到的*/
        String command;

        /*Bean的Class对象*/
        Class clazz;

        /*Bean注解的方法*/
        Method method;

        /*Bean对象本身*/
        Object bean;

        /*是否deprecated*/
        boolean deprecated;

        /*所在模块的名称*/
        String module;

        /*线程池组标识*/
        String group;
    }
}
