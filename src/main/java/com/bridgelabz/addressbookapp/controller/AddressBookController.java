package com.bridgelabz.addressbookapp.controller;

import com.bridgelabz.addressbookapp.dto.AddressBookDto;
import com.bridgelabz.addressbookapp.model.AddressBookEntry;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addressbookservice")
public class AddressBookController {
    @GetMapping({"", "/"})
    public List<AddressBookEntry> list() { return List.of(); }

    @GetMapping("/get/{id}")
    public AddressBookEntry get(@PathVariable long id) {
        return new AddressBookEntry(id, "Example", "0000000000", "Example City");
    }

    @PostMapping("/create")
    public ResponseEntity<AddressBookEntry> create(@RequestBody AddressBookDto request) {
        return ResponseEntity.status(201).body(new AddressBookEntry(1, request.getName(),
                request.getPhoneNumber(), request.getCity()));
    }

    @PutMapping("/update/{id}")
    public AddressBookEntry update(@PathVariable long id, @RequestBody AddressBookDto request) {
        return new AddressBookEntry(id, request.getName(), request.getPhoneNumber(), request.getCity());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        return ResponseEntity.noContent().build();
    }
}
