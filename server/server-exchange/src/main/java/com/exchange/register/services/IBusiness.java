package com.exchange.register.services;

import java.lang.reflect.Method;

public interface IBusiness {

    Method getMethod(String command);

    Object getBean(String command);

    String getGroup(String command);

    int checkflag(String command, byte flag);
}
