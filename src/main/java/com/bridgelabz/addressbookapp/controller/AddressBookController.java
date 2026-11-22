package com.bridgelabz.addressbookapp.controller;

import com.bridgelabz.addressbookapp.dto.AddressBookDto;
import com.bridgelabz.addressbookapp.model.AddressBookEntry;
import com.bridgelabz.addressbookapp.service.AddressBookService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addressbookservice")
public class AddressBookController {
    private final AddressBookService service;

    public AddressBookController(AddressBookService service) { this.service = service; }

    @GetMapping({"", "/"})
    public List<AddressBookEntry> list() { return service.list(); }

    @GetMapping("/get/{id}")
    public AddressBookEntry get(@PathVariable long id) { return service.get(id); }

    @PostMapping("/create")
    public ResponseEntity<AddressBookEntry> create(@RequestBody AddressBookDto request) {
        return ResponseEntity.status(201).body(service.create(request));
    }

    @PutMapping("/update/{id}")
    public AddressBookEntry update(@PathVariable long id, @RequestBody AddressBookDto request) {
        return service.update(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
