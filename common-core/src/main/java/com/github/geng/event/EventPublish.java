package com.github.geng.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

/**
 * <pre>
 * 构建事件，同时可以调用发布,依赖spring 容器
 * 事件接收者需要实现 ApplicationListener 接口
 * 同时，事件接收者需要注入spring 容器
 * 参考 https://blog.csdn.net/tuzongxun/article/details/53637159
 * </pre>
 * @author geng
 */
public class EventPublish extends ApplicationEvent {

    //这里使用注解无法获取ApplicationContext，暂不研究
    //所以曲线救国，直接传入ApplicationContext
//    @Autowired
    private ApplicationContext context;

    /**
     * 发布数据内容
     */
    private Object content;

    /**
     * @param context spring 容器上下文对象
     * @param source 创建事件的对象，一般传入this
     * @param content 发送事件内容
     */
    public EventPublish(ApplicationContext context, Object source, Object content) {
        super(source);
        this.context = context;
        this.content = content;
    }

    /**
     * 发布事件
     */
    public void publish() {
        context.publishEvent(new EventPublish(context, this, content));
    }

    /**
     * 获取事件内容
     * @return 事件内容
     */
    public Object getContent() {
        return content;
    }
}
