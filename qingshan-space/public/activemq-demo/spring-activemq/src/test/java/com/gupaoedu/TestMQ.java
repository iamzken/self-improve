package com.gupaoedu;


import com.gupaoedu.sender.QueueProducerService;
import com.gupaoedu.sender.TopicProducerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import javax.jms.Destination;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
@WebAppConfiguration
public class TestMQ {
    @Resource
    private QueueProducerService queueSender;
    @Resource
    private TopicProducerService topicSender;
    @Resource(name = "queueDestination")
    private Destination queueDestination;
    @Resource(name = "topicDestination")
    private Destination topicDestination;

    @Test
    public void send() {
        for (int i = 0; i < 2; i++) {
            queueSender.sendMessage(queueDestination, "queue msg-" + i);
        }

        for (int i = 0; i < 1; i++) {
            topicSender.sendMessage(topicDestination, "topic msg-" + i);
        }
    }
}