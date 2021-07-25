package pl.sda.customers.dto;

import lombok.Value;

@Value
public class AddressDto {
    String street;
    String city;
    String zipCode;
    String country;
}
