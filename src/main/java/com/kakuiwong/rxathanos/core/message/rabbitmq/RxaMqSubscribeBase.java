package com.kakuiwong.rxathanos.core.message.rabbitmq;

import com.kakuiwong.rxathanos.bean.RxaMessage;
import com.kakuiwong.rxathanos.contant.RxaContant;
import com.kakuiwong.rxathanos.core.message.RxaSubscribe;
import com.kakuiwong.rxathanos.util.RxaContext;
import com.kakuiwong.rxathanos.util.RxaLogUtil;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;

/**
 * @author gaoyang
 * @email 785175323@qq.com
 */
@RabbitListener(queues = RxaContant.RXA_BASE_QUEUE)
public class RxaMqSubscribeBase implements RxaSubscribe {

    @RabbitHandler
    public void Listen(String msg, Channel channel, Message message) throws IOException {
        onMessage(msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @Override
    public void onMessage(String message) {
        RxaMessage serialize = RxaMessage.serialize(message);
        RxaLogUtil.debug(() -> RxaLogUtil.logMessage(serialize));
        RxaContext.changeSub(serialize.getRxaId(), serialize.getSubId(), serialize.getStatusEnum());
        if (RxaContext.isFail(serialize.getRxaId()) || RxaContext.isReady(serialize.getRxaId())) {
            RxaContext.unParkThread(serialize.getRxaId());
        }
    }
}
