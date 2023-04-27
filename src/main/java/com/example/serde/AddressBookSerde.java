package com.example.serde;

import com.example.smnl.protos.AddressBook;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.commons.lang.SerializationException;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.types.DeserializationException;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class AddressBookSerde {

    //protected static Logger log = LoggerFactory.getLogger(AddressBookSerde.class);


    public AddressBookSerde() {
    }

    public static KafkaRecordSerializationSchema<AddressBook> serializer() {
        return new AddressBookSerializer();
    }

    public static KafkaRecordDeserializationSchema<AddressBook> deserializer() {
        return new AddressBookDeserializer();
    }

    public static class AddressBookSerializer implements KafkaRecordSerializationSchema<AddressBook>, Serializer<AddressBook> {

        public AddressBookSerializer() {
        }

        @Nullable
        @Override
        public ProducerRecord<byte[], byte[]> serialize(AddressBook addressBook, KafkaSinkContext kafkaSinkContext, Long timestamp) {
            try {
                return new ProducerRecord("proto-output", "foo".getBytes(StandardCharsets.UTF_8), serialize(null, addressBook)  );
            } catch (Exception e) {
                throw new SerializationException(e);
            }
        }

        @Override
        public byte[] serialize(String s, AddressBook addressBook) {
            return addressBook.toByteArray();
        }
    }

    public static class AddressBookDeserializer implements KafkaRecordDeserializationSchema<AddressBook>, Deserializer<AddressBook> {

        public AddressBookDeserializer() {
        }

        @Override
        public TypeInformation<AddressBook> getProducedType() {
            return Types.GENERIC(AddressBook.class);
        }

        @Override
        public void deserialize(ConsumerRecord<byte[], byte[]> record, Collector<AddressBook> collector) throws IOException {
            try {
                collector.collect(AddressBook.parseFrom(record.value()));
            } catch (Exception e) {
                if (record != null) {
                    //log that the record cannot be deserialized
                } else {
                    //log that the message is null!
                }
                throw new DeserializationException(e);
            }
        }

        @Override
        public AddressBook deserialize(String s, byte[] bytes) {
            try {
                return AddressBook.parseFrom(bytes);
            } catch (InvalidProtocolBufferException e) {
                throw new DeserializationException(e);
            }
        }
    }
}
