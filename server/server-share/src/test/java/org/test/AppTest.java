package org.test;

import org.share.msg.Message;

public class AppTest {

    public static void main(String[] args) {
        Message msg = Message.MessageFactory.getMessageObj(new Object(), null , null);
        System.out.println(msg.toString());
    }
}
