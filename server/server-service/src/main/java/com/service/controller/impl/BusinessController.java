package com.service.controller.impl;

import com.annotation.EasyMapping;
import com.annotation.EasyModule;
import com.service.controller.IBusiness;
import org.share.msg.IOResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * 业务线程池发射在这里保存提供给game-exchange使用的
 * 创建Bean的时候通过注解保持Bean的对象和方法属性
 *
 * */
@Component
public class BusinessController implements BeanPostProcessor, IBusiness {
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
     */
    private Map<String, String> commandModule = new HashMap<>();


    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        EasyModule bean_annotation = bean.getClass().getAnnotation(EasyModule.class);
        if (bean_annotation != null) {

            logger.info("添加映射模块：{} ， Bean类型：{}", bean_annotation.name(), bean.getClass());

            Method[] methods = bean.getClass().getMethods();
            for (Method method : methods) {
                EasyMapping m_annotation = method.getAnnotation(EasyMapping.class);
                if (m_annotation != null) {
                    RouteInfo routeInfo = new RouteInfo(m_annotation.check());
                    routeInfo.command = m_annotation.command();
                    routeInfo.clazz = bean.getClass();
                    routeInfo.method = method;
                    routeInfo.type = method.getReturnType();
                    if (routeInfo.type != IOResult.class && routeInfo.type != void.class) {
                        logger.error("做了一个约定，业务层返回的数据一定是Message对象或者void,实际上:{},类:{}，方法:{}",
                                routeInfo.type, bean.getClass(), method.getName());
                    }
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


    @Override
    public Method getMethod(String command) {
        RouteInfo routeInfo = commandInfo.get(command);
        return routeInfo == null || routeInfo.isDeprecated() ? null : routeInfo.getMethod();
    }

    @Override
    public Object getBean(String command) {
        RouteInfo routeInfo = commandInfo.get(command);
        return routeInfo == null || routeInfo.isDeprecated() ? null : routeInfo.getBean();
    }

    @Override
    public String getGroup(String command) {
        return commandModule.get(command);
    }

    @Override
    public int checkflag(String command, byte flag) {
        RouteInfo routeInfo = commandInfo.get(command);
        if (routeInfo != null) {
            return routeInfo.check(flag);
        } else {
            return -1;
        }
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public class RouteInfo {
        /*通过注解得到的*/
        String command;

        /*Bean的Class对象名称*/
        Class clazz;

        /*Bean注解的方法*/
        Method method;

        /*Bean对象本身*/
        Object bean;

        /*是否deprecated*/
        boolean deprecated;

        /*检查flag位*/
        final byte check;

        /*所在模块的名称*/
        String module;

        /*线程池组标识*/
        String group;

        /*Type函数返回的类型*/
        Type type;

        public String getCommand() {
            return command;
        }

        public Class getClazz() {
            return clazz;
        }

        public Method getMethod() {
            return method;
        }

        public Object getBean() {
            return bean;
        }

        public boolean isDeprecated() {
            return deprecated;
        }

        public String getModule() {
            return module;
        }

        public String getGroup() {
            return group;
        }

        public Type getType() {
            return type;
        }

        public RouteInfo(byte flag) {
            this.check = flag;
        }

        public int check(final byte flag) {
            return check & flag;
        }
    }
}
