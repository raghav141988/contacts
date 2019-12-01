package com.example.contactsexternal.apigateway.model;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionInfo {
	/* Copyright 2019 freecodeformat.com *
	
	/* Time: 2019-11-29 21:17:50 @author freecodeformat.com @website http://www.freecodeformat.com/json2javabean.php */
	
	    @JsonProperty("expiresAt")
	    private Date expiresat;
	    private String status;
	    @JsonProperty("sessionToken")
	    private String sessiontoken;
	   
	   

}
