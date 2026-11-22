package com.bridgelabz.addressbookapp.service;

import com.bridgelabz.addressbookapp.dto.AddressBookDto;
import com.bridgelabz.addressbookapp.model.AddressBookEntry;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AddressBookService {
    public List<AddressBookEntry> list() { return List.of(); }
    public AddressBookEntry get(long id) {
        return new AddressBookEntry(id, "Example", "0000000000", "Example City");
    }
    public AddressBookEntry create(AddressBookDto request) {
        return new AddressBookEntry(1, request.getName(), request.getPhoneNumber(), request.getCity());
    }
    public AddressBookEntry update(long id, AddressBookDto request) {
        return new AddressBookEntry(id, request.getName(), request.getPhoneNumber(), request.getCity());
    }
    public void delete(long id) { }
}
