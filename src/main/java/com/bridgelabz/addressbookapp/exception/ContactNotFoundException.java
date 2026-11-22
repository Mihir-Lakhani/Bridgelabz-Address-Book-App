package com.bridgelabz.addressbookapp.exception;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(long id) { super("Contact not found: " + id); }
}
