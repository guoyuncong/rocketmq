package com.yc.rocketmq.quick;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author: gyc
 * @date: 2022/12/15 16:14
 * @description:
 */
public class Producer {
    public static void main(String[] args) throws MQClientException, InterruptedException {
        /*
         * Instantiate with a producer group name.
         */
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        /*
         * Specify name server addresses.
         */
        producer.setNamesrvAddr("101.35.111.41:9876");
        producer.start();
        for (int i = 0; i < 1000; i++) {
            try {
                /*
                 * Create a message instance, specifying topic, tag and message body.
                 */
                Message msg = new Message("TopicTest" /* Topic */,
                        "TagA" /* Tag */,
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );
                /*
                 * Call send message to deliver message to one of brokers.
                 */
                SendResult sendResult = producer.send(msg);
                System.out.printf("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }
        /*
         * Shut down once the producer instance is not longer in use.
         */
        producer.shutdown();
    }
}