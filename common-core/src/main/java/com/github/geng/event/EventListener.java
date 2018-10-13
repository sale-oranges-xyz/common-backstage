package com.github.geng.event;

import org.springframework.context.ApplicationListener;

/**
 * 抽象事件监听类，提供事件鉴别方法，判断是否执行
 * <pre>
 *     <b>注意:</b>
 *     spring bean 使用单例模式创建
 *     就算这里书写私有变量，也会在多线程下被共享，容易引起并发问题
 *     所以需要特别策略，不破坏线程的封闭性
 *     为什么不使用同步锁，因为使用同步锁容易引起阻塞并降低效率
 * </pre>
 * @author geng
 */
public abstract class EventListener implements ApplicationListener<EventPublish> {

    /**
     * 根据需要判断事件是否匹配<br>
     * 一般使用 instanceof 对 content 进行对象类型判断
     * @param event 自定义事件
     * @return true 匹配 | false 不匹配
     */
    protected abstract boolean match(EventPublish event);

    /**
     * 匹配成功后执行操作
     * @param content 事件内容
     */
    protected abstract void bingo(Object content);

    @Override
    public void onApplicationEvent(EventPublish event) {
        if (this.match(event)) {
            this.bingo(event.getContent());
        }
    }
}
