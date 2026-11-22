package com.bridgelabz.addressbookapp.model;

public class AddressBookEntry {
    private long id;
    private String name;
    private String phoneNumber;
    private String city;

    public AddressBookEntry(long id, String name, String phoneNumber, String city) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.city = city;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getCity() { return city; }
    public void setName(String name) { this.name = name; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setCity(String city) { this.city = city; }
}
