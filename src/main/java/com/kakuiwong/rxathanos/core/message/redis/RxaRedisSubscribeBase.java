package com.kakuiwong.rxathanos.core.message.redis;

import com.kakuiwong.rxathanos.bean.RxaMessage;
import com.kakuiwong.rxathanos.core.message.RxaSubscribe;
import com.kakuiwong.rxathanos.util.RxaContext;
import com.kakuiwong.rxathanos.util.RxaLogUtil;

/**
 * @author gaoyang
 * @email 785175323@qq.com
 */
public class RxaRedisSubscribeBase implements RxaSubscribe {

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
