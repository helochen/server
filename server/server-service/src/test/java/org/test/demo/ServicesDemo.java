package org.test.demo;

import com.service.controller.impl.BusinessController;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServicesDemo {


    @Test
    public void test() throws InterruptedException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:*-spring.xml");

        /*RunnableFactory runnableFactory = applicationContext.getBean(BusinessController.class);*/

        /*Thread thread = new Thread(runnableFactory.getRunnable("demo", "test"));
        thread.start();
        thread.join();
        Thread thread2 = new Thread(runnableFactory.getRunnable("demo2", "test"));
        thread2.start();
        thread2.join();
        Thread thread3 = new Thread(runnableFactory.getRunnable("demo3", "test123"));
        thread3.start();
        thread3.join();*/

        //System.out.println(runnableFactory.getGroup("demo"));
    }
}
