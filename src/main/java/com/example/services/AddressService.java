package com.example.services;


import com.example.domain.Address;

public interface AddressService {
    Iterable<Address> listAllAddresses();

    Address getAddressById(Integer id);

    Address saveAddress(Address address);
}
