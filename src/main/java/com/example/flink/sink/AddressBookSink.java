package com.example.flink.sink;

import com.example.serde.AddressBookSerde;
import com.example.smnl.protos.AddressBook;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

public class AddressBookSink {

    public AddressBookSink() {
    }

    public static KafkaSink<AddressBook> get() {
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:19092");

        return KafkaSink.<AddressBook>builder()
                .setBootstrapServers("localhost:19092")
                .setRecordSerializer(AddressBookSerde.serializer())
                .setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
                .build();
    }
}
