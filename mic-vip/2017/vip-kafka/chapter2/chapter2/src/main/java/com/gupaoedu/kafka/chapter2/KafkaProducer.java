package com.gupaoedu.kafka.chapter2;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.IOException;
import java.util.Properties;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class KafkaProducer{


    private final org.apache.kafka.clients.producer.KafkaProducer<Integer,String> producer;

    public KafkaProducer() {
        Properties props=new Properties();
        props.put("bootstrap.servers",KafkaProperties.KAFKAF_BROKER_LIST);
        props.put("key.serializer","org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("partitioner.class","com.gupaoedu.kafka.chapter2.MyPartition");
        props.put("client.id","producerDemo");
        this.producer = new org.apache.kafka.clients.producer.KafkaProducer<Integer, String>(props);
    }

    public void sendMsg(){
        producer.send(new ProducerRecord<Integer, String>(KafkaProperties.TOPIC, 1, "message"), new Callback() {
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                System.out.println("message send to:["+recordMetadata.partition()+"],offset:["+recordMetadata.offset()+"]");
            }
        });
    }

    public static void main(String[] args) throws IOException {
        KafkaProducer producer=new KafkaProducer();
        producer.sendMsg();
        System.in.read();
    }
}
