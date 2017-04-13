package com.alexkoveckiy.common.protocol;

import com.alexkoveckiy.common.datamapper.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 12.04.17.
 */

@Component
public class MessageFactory {
    
    @Autowired
    private DataMapper dataMapper;
    
    public <T extends RequestData> Request<T> getRequestWithConcreteData(Request<?> msg, Class<T> clazz) {
        Request<T> request = new Request<>();
        request.setHeader(msg.getHeader());
        request.setRoutingData(msg.getRoutingData());
        request.setData(dataMapper.convert(msg.getData(), clazz));
        return request;
    }
}
