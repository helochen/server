package org.stage.service.modules;

import org.share.annotation.EasyMapping;
import org.share.annotation.EasyModule;
import org.share.command.FlagType;
import org.share.command.MsgType;
import org.share.command.StageMsgType;
import org.share.msg.Message;
import org.springframework.stereotype.Service;
import org.stage.service.constants.StageModuleConstants;

/**
 * class StageManagerController
 * function:
 *
 * @Author chens
 * @Date 2018/10/25 21:43
 */
@Service
@EasyModule(name = StageModuleConstants.STAGE_MODULE)
public class StageModule {


	@EasyMapping(command = StageMsgType.LOGIN_ENTER_STAGE , check =  FlagType.LOGIN_SUCCESS | FlagType.ACTIVE_INIT)
	public void enterStage(Message msg){
		System.out.println("收到了场景的消息");
	}
}
