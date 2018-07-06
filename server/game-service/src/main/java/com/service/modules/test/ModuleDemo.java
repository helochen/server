package com.service.modules.test;


import com.annotation.EasyMapping;
import com.annotation.EasyModule;
import org.share.msg.IOResult;
import org.share.msg.Message;
import org.springframework.stereotype.Service;

@Service
@EasyModule(name = "moduleDemo", group = "test")
public class ModuleDemo {

    @EasyMapping(command = "demo")
    public IOResult mappingDemo(Message msg) {
        System.out.println("................running.........." + msg);
        return IOResult.Builder.WorldIOResult("stageDemo", "demo", "");
    }

    @EasyMapping(command = "demo2")
    public void mappingDemo2(Message msg) {
        System.out.println("..........return void type\t" + msg);
    }

    @EasyMapping(command = "demo3")
    public String mappingDemo3(Message msg) {
        System.out.println("..........return void type\t" + msg);
        return "123";
    }
}
