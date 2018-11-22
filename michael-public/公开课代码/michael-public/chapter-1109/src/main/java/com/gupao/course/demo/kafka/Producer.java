package com.gupao.course.demo.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Producer extends Thread{

    private KafkaProducer<Integer,String> producer;

    private String topic;

    public Producer(String topic){
        Properties props=new Properties();
        props.put("bootstrap.servers",KafkaProperties.KAFKA_SERVER_URL);
        props.put("client.id", "DemoProducer");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    public void run(){
        int messageNo=1;
        while(true){
            String messageStr="Message_"+messageNo;
            producer.send(new ProducerRecord<Integer, String>(topic, messageNo, messageStr), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    System.out.println("message send to partition -｛"+recordMetadata.partition()+"｝" +
                            "--offset:"+recordMetadata.offset()+"");
                }
            });
            ++messageNo;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Producer producer=new Producer(KafkaProperties.TOPIC);
        producer.start();
    }
}
