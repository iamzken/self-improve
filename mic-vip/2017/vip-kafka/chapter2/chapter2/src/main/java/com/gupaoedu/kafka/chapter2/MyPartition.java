package com.gupaoedu.kafka.chapter2;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class MyPartition implements Partitioner{

    public int partition(String topic, Object key, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        List<PartitionInfo> partitionerList=cluster.partitionsForTopic(topic);
        int numPart=partitionerList.size(); //获得所有的分区
        int hashCode=key.hashCode(); //获得key的 hashcode
        return 1;
    }

    public void close() {

    }

    public void configure(Map<String, ?> map) {

    }
}
