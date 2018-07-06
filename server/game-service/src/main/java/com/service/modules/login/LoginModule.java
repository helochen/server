package com.service.modules.login;

import com.annotation.EasyMapping;
import com.annotation.EasyModule;
import org.share.msg.IOResult;
import org.share.msg.Message;
import org.springframework.stereotype.Service;

@Service
@EasyModule(name = "loginModule" , group = "LOGIN")
public class LoginModule {


    @EasyMapping(command = "userLogin")
    public IOResult userLogin(Message msg) {
        return IOResult.Builder.WorldIOResult("" ,"" ,"");
    }

}
