package com.example.contactsbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequest {
	private String grant_type;
	private String scope;
	private String client_id;
	private String client_secret;
	private String code;
	private String state;

}
