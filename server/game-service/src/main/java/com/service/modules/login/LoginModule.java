package com.service.modules.login;

import com.annotation.EasyMapping;
import com.annotation.EasyModule;
import com.constants.Module;
import org.share.command.FlagType;
import org.share.command.MsgType;
import org.share.manager.IBusinessManager;
import org.share.manager.impl.ChannelManager;
import org.share.msg.IOResult;
import org.share.msg.Message;
import org.springframework.stereotype.Service;

@Service
@EasyModule(name =  Module.LOGIN_MODULE, group = "LOGIN")
public class LoginModule {


    @EasyMapping(command = MsgType.USER_LOGIN, check = FlagType.ACTIVE_INIT)
    public IOResult userLogin(Message msg) {

        IBusinessManager businessManager = ChannelManager.getInstance();

        /**
         * 假设登陆成功，返回了信息
         * */
        String roleId = "testLogin";

        businessManager.updateRoleIdInfo(msg.getSessionId(), roleId);

        return IOResult.Builder.WorldIOResult(MsgType.USER_LOGIN, "login".getBytes());
    }

}
