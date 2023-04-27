package com.example.kafka;

import com.example.data.AddressBookDB;
import com.example.serde.AddressBookSerde;
import com.example.smnl.protos.AddressBook;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProtoKafkaProducer {

    public static void main(String[] args) {

        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:19092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AddressBookSerde.AddressBookSerializer.class);

        final KafkaProducer<String, AddressBook> producer = new KafkaProducer<String, AddressBook>(props);

        for (AddressBook addressBook : AddressBookDB.DB) {
            producer.send(new ProducerRecord<>("proto-input", addressBook));
        }

        producer.close();
    }

}
