package com.example.contactsexternal.apigateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Contact {

private String id;
private String name;
private String email;
private String phone;
private Address address;

}
