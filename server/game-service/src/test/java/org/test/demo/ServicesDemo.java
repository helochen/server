package org.test.demo;

import com.service.controller.RunnableFactory;
import com.service.controller.impl.BusinessController;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServicesDemo {


    @Test
    public void test() throws InterruptedException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:*-spring.xml");

        RunnableFactory runnableFactory = applicationContext.getBean(BusinessController.class);

        Thread thread = new Thread(runnableFactory.getRunnable("demo", "test"));
        thread.start();
        thread.join();

        System.out.println(runnableFactory.getGroup("demo"));
    }
}
