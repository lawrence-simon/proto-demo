package com.example.data;

import com.example.smnl.protos.AddressBook;
import com.example.smnl.protos.Person;
import com.example.smnl.protos.PhoneNumber;

import java.util.Arrays;
import java.util.List;

public class AddressBookDB {

    public static final List<AddressBook> DB = Arrays.asList(
            AddressBook.newBuilder()
                    .addPeople(
                            Person.newBuilder()
                                    .setName("Annie")
                                    .setId(1001)
                                    .setEmail("annie@gmail.com")
                                    .addPhones(PhoneNumber.newBuilder()
                                            .setNumber("01286472111")
                                            .build())
                                    .build())
                    .build(),

            AddressBook.newBuilder()
                    .addPeople(
                            Person.newBuilder()
                                    .setName("Barry")
                                    .setId(2001)
                                    .setEmail("barry@gmail.com")
                                    .addPhones(PhoneNumber.newBuilder()
                                            .setNumber("04676555444")
                                            .build())
                                    .build())
                    .build()
    );
}
