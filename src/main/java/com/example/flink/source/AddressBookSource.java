package com.example.flink.source;

import com.example.serde.AddressBookSerde;
import com.example.smnl.protos.AddressBook;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.KafkaSourceBuilder;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Properties;

public class AddressBookSource {

    private AddressBookSource() {
    }

    public static KafkaSource<AddressBook> get() {
        final Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:19092");
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "proto");

        final KafkaSourceBuilder<AddressBook> sourceBuilder = KafkaSource.<AddressBook>builder()
                .setTopics("proto-input")
                .setDeserializer(AddressBookSerde.deserializer())
                .setProperties(props)
                .setStartingOffsets(OffsetsInitializer.earliest());

        return sourceBuilder.build();
    }

}
