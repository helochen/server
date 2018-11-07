package org.share.msg;

import org.share.command.FlagType;

/**
 * class InnerMessage
 * function:
 *
 * @Author chens
 * @Date 2018/10/28 23:58
 */
public final class InnerMessage extends Message {

	public InnerMessage(String sessionId , String command, Object source){
		super(sessionId, FlagType.INNER_MSG, command, source);
	}
}
