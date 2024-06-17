package edu.hit.service.impl;

import edu.hit.pay.PayApplicationTests;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class PayServiceImplTest extends PayApplicationTests {
    @Autowired
    AmqpTemplate amqpTemplate;
    @Test
    public void send(){
        amqpTemplate.convertAndSend("payNotify","hello");
    }

}