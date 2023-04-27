package com.example.kafka;

import com.example.serde.AddressBookSerde;
import com.example.smnl.protos.AddressBook;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

public class ProtoKafkaConsumer {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:19092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AddressBookSerde.AddressBookDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, AddressBook> consumer = new KafkaConsumer<String, AddressBook>(props);

        consumer.subscribe(Collections.singleton("proto-output"));

        int failures = 0;
        while (true) {
            ConsumerRecords<String, AddressBook> records = consumer.poll(1000);
            if (records.count() == 0) {
                if (failures > 10) { break; } else {failures++;}
            }
            records.forEach(System.out::println);
        }
        consumer.close();
    }
}
