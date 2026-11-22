package com.bridgelabz.addressbookapp.service;

import com.bridgelabz.addressbookapp.dto.AddressBookDto;
import com.bridgelabz.addressbookapp.model.AddressBookEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class AddressBookService {
    private final List<AddressBookEntry> contacts = new ArrayList<>();
    private long nextId = 1;

    public synchronized List<AddressBookEntry> list() { return List.copyOf(contacts); }

    public synchronized AddressBookEntry get(long id) {
        return contacts.stream().filter(contact -> contact.getId() == id).findFirst()
                .orElseThrow(() -> new NoSuchElementException("Contact not found: " + id));
    }

    public synchronized AddressBookEntry create(AddressBookDto request) {
        AddressBookEntry contact = new AddressBookEntry(nextId++, request.getName(),
                request.getPhoneNumber(), request.getCity());
        contacts.add(contact);
        return contact;
    }

    public synchronized AddressBookEntry update(long id, AddressBookDto request) {
        AddressBookEntry contact = get(id);
        contact.setName(request.getName());
        contact.setPhoneNumber(request.getPhoneNumber());
        contact.setCity(request.getCity());
        return contact;
    }

    public synchronized void delete(long id) {
        contacts.remove(get(id));
    }
}
