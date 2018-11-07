package com.modules.login;

import com.constants.Module;
import com.google.protobuf.InvalidProtocolBufferException;
import org.share.annotation.EasyMapping;
import org.share.annotation.EasyModule;
import org.share.command.FlagType;
import org.share.command.MsgType;
import org.share.command.StageMsgType;
import org.share.manager.IBusinessManager;
import org.share.manager.impl.ChannelManager;
import org.share.msg.IOResult;
import org.share.msg.InnerMessage;
import org.share.msg.Message;
import org.share.tunnel.PublicMsgSender;
import org.share.tunnel.proto.login.Login;
import org.springframework.stereotype.Service;

@Service
@EasyModule(name = Module.LOGIN_MODULE, group = "LOGIN")
public class LoginModule {


	@EasyMapping(command = MsgType.USER_LOGIN, check = FlagType.ACTIVE_INIT | FlagType.LOGIN_SUCCESS)
	public IOResult userLogin(Message msg) throws InvalidProtocolBufferException {

		IBusinessManager businessManager = ChannelManager.getInstance();


		byte[] data = (byte[]) msg.getSource();

		Login.roleLoginRequest loginRequest = Login.roleLoginRequest.parseFrom(data);

		if ("chen".equals(loginRequest.getUserName()) && "master".equals(loginRequest.getPassWord())) {
			System.out.println("===============================" + loginRequest.getUserName() + "\t" + loginRequest.getPassWord());
			/**
			 * 假设登陆成功，返回了信息
			 * */
			String roleId = loginRequest.getUserName();

			businessManager.updateRoleIdInfo(msg.getSessionId(), roleId);

			Message tmp = new InnerMessage(msg.getSessionId(), StageMsgType.LOGIN_ENTER_STAGE, null);

			PublicMsgSender.SendInnerMsg(tmp);

			return IOResult.Builder.SelfIOResult(MsgType.USER_LOGIN, msg.getSessionId(), null, "login".getBytes());

		} else {
			return IOResult.Builder.ShutDownSessionIOResult(msg.getSessionId());
		}
	}

	@EasyMapping(command = MsgType.USER_LOIGNOUT)
	public IOResult userLoginOut(Message msg) throws InvalidProtocolBufferException {

		IBusinessManager businessManager = ChannelManager.getInstance();

		byte[] data = (byte[]) msg.getSource();

		Login.roleLoginOutRequest loginOutRequest = Login.roleLoginOutRequest.parseFrom(data);

		String roleId = loginOutRequest.getUserName();


		if (businessManager.checkLoginStatus(roleId)) {
			System.out.println("==========直接发送消息======================");
			PublicMsgSender.Send2Msg(IOResult.Builder.SelfIOResult(MsgType.USER_LOIGNOUT, msg.getSessionId(), null, "loginout".getBytes()));
		}

		return IOResult.Builder.ShutDownSessionIOResult(msg.getSessionId());
	}
}
