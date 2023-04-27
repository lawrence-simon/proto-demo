package com.example.flink.topology;

import com.example.flink.sink.AddressBookSink;
import com.example.flink.source.AddressBookSource;
import com.example.serde.AddressBookSerde;
import com.example.smnl.protos.AddressBook;
import com.twitter.chill.protobuf.ProtobufSerializer;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;

public class AddressBookTopology {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().registerTypeWithKryoSerializer(AddressBook.class, ProtobufSerializer.class);

        KafkaSource<AddressBook> kafkaAddressBooks = AddressBookSource.get();
        DataStreamSource<AddressBook> addressBookSource = env.fromSource(kafkaAddressBooks, WatermarkStrategy.noWatermarks(), "Address Book Source");

        addressBookSource.addSink(new PrintSinkFunction<>());
        addressBookSource.sinkTo(AddressBookSink.get());

        env.execute();
    }
}
