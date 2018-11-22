package com.gupaoedu.kafka.chapter2;

import kafka.utils.ShutdownableThread;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Consumer2 extends ShutdownableThread{
    // High level consumer
    // Low level consumer
    private final KafkaConsumer<Integer,String> consumer;

    public Consumer2() {
        super("KafkaConsumerTest",false);
        Properties properties=new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,KafkaProperties.KAFKAF_BROKER_LIST);
        //GroupId 消息所属的分组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"DemoGroup1");
        //是否自动提交消息:offset
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
        //自动提交的间隔时间
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");
        //设置使用最开始的offset偏移量为当前group.id的最早消息
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        //设置心跳时间
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,"30000");
        //对key和value设置反序列化对象
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");

        this.consumer=new KafkaConsumer<Integer, String>(properties);
      /*  TopicPartition p0=new TopicPartition(KafkaProperties.TOPIC,0);
        this.consumer.assign(Arrays.asList(p0));*/
    }

    public void doWork() {
       consumer.subscribe(Collections.singletonList(KafkaProperties.TOPIC));
        ConsumerRecords<Integer,String> records=consumer.poll(1000);
        for(ConsumerRecord record:records){
            System.out.println("["+record.partition()+"]receiver message:" +
                    "["+record.key()+"->"+record.value()+"],offset:"+record.offset()+"");
        }
    }

    public static void main(String[] args) {
        Consumer2 consumer=new Consumer2();
        consumer.start();



    }
}
