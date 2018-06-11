package com.service.modules.test;


import com.service.annotation.EasyMapping;
import com.service.annotation.EasyModule;
import org.share.msg.Message;
import org.springframework.stereotype.Service;

@Service
@EasyModule(name = "moduleDemo", group = "test")
public class ModuleDemo {

    @EasyMapping(command = "demo")
    public Message mappingDemo(Object data) {
        System.out.println("................running.........." + data);
        return Message.MessageFactory.getMessageObj("stageDemo", "demo", "");
    }

    @EasyMapping(command = "demo2")
    public void mappingDemo2(Object data) {
        System.out.println("..........return void type\t" + data);
    }

    @EasyMapping(command = "demo3")
    public String mappingDemo3(Object data) {
        System.out.println("..........return void type\t" + data);
        return "123";
    }
}
