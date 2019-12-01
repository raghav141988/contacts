package com.example.contactsexternal.apigateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OktaToken {
	 private String token_type;
	 private float expires_in;
	 private String access_token;
	 private String scope;
	 private String id_token;
}
