package com.exchange.manager.session;

import java.nio.channels.Channel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private final Map<Channel, session> channelsessionMap = new ConcurrentHashMap<>();




    class session{

        private Channel channel;

        private long primaryKey;

        private Map<String, Object> params = new HashMap<>();

    }
}
