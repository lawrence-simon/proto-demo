package com.example;

import com.example.smnl.protos.AddressBook;
import com.example.smnl.protos.AddressBookOrBuilder;
import com.example.smnl.protos.Person;
import com.example.smnl.protos.PhoneNumber;

public class Main {

    public static void main(String[] args) {

        Person annie = Person.newBuilder()
                .setName("Annie")
                .setId(1001)
                .setEmail("annie@gmail.com")
                .addPhones(PhoneNumber.newBuilder()
                        .setNumber("01286472111")
                        .build())
                .build();

        Person barry = Person.newBuilder()
                .setName("Barry")
                .setId(2001)
                .setEmail("barry@gmail.com")
                .addPhones(PhoneNumber.newBuilder()
                        .setNumber("04676555444"))
                .build();

        AddressBook addressBook = AddressBook.newBuilder()
                .addPeople(annie)
                .addPeople(barry)
                .build();

        System.out.println(addressBook);
    }

}
