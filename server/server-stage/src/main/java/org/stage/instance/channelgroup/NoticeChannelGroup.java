package org.stage.instance.channelgroup;

import io.netty.channel.Channel;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutor;
import org.stage.instance.observer.Observer;


/**
 * Class:  NoticeChannelGroup
 * Author: word
 * Date:   2018/11/28 1:23
 */
public class NoticeChannelGroup extends DefaultChannelGroup  {

    private Observer observer;

    public NoticeChannelGroup(EventExecutor executor, Observer observer) {
        super(executor);
        this.observer = observer;
    }

    // 观察者模式

    @Override
    public boolean remove(Object o) {
        //TODO 获取到这个channel的角色信息，然后将角色信息离开场景信息进行推送
        observer.update(o);
        return super.remove(o);
    }

    @Override
    public boolean add(Channel channel) {
        //TODO 缓存获取角色信息，推送步骤
        observer.update(channel);
        return super.add(channel);
    }

}
