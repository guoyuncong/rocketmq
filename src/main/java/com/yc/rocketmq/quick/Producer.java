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
        // <1.1> 创建 DefaultMQProducer 对象
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        // <1.2> 设置 RocketMQ Namesrv 地址
        producer.setNamesrvAddr("101.35.111.41:9876");
        producer.setSendMsgTimeout(60000);

        /*
         * Specify name server addresses.
         * <p/>
         *
         * Alternatively, you may specify name server addresses via exporting environmental variable: NAMESRV_ADDR
         * <pre>
         * {@code
         * producer.setNamesrvAddr("name-server1-ip:9876;name-server2-ip:9876");
         * }
         * </pre>
         */

        /*
         * Launch the instance.
         */
        // <1.3> 启动 producer 生产者
        producer.start();

        for (int i = 0; i < 1000; i++) {
            try {

                /*
                 * Create a message instance, specifying topic, tag and message body.
                 */
                // <2.1> 创建 Message 消息
                Message msg = new Message("TopicTest" /* Topic */,
                        "TagA" /* Tag */,
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );

                /*
                 * Call send message to deliver message to one of brokers.
                 */
                // <2.2> 同步发送消息
                SendResult sendResult = producer.send(msg);

                // <2.3> 打印发送结果
                System.out.printf("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }

        /*
         * Shut down once the producer instance is not longer in use.
         */
        // <3> 关闭 producer 生产者
        producer.shutdown();
    }

}
