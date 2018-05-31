package com.service.modules.test;


import com.service.annotation.EasyMapping;
import com.service.annotation.EasyModule;
import org.springframework.stereotype.Service;

@Service
@EasyModule(name = "moduleDemo",group = "test")
public class ModuleDemo {

    @EasyMapping(command = "demo")
    public void mappingDemo(Object data) {
        System.out.println("................running.........." + data);
    }
}
