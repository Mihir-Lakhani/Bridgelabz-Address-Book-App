package com.bridgelabz.addressbookapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AddressBookDto {
    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[A-Z][a-zA-Z]{2,}(?: [A-Za-z]+)*$", message = "Name must start with a capital letter and have at least 3 letters")
    private String name;
    private String phoneNumber;
    private String city;
}
