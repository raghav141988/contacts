package com.example.contactsexternal.apigateway.controller;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.contactsexternal.apigateway.model.LoginInfo;
import com.example.contactsexternal.apigateway.model.OktaToken;
import com.example.contactsexternal.apigateway.model.SessionInfo;

import lombok.extern.slf4j.Slf4j;;

@RestController
@Slf4j
@RequestMapping(value = "/token")
public class OktaTokenController {
	@Value("${okta.auth.service.url}")
	private String oktaAuthUrl;
	@Value("${okta.auth.code.service.url}")
	private String oktaAuthCodeUrl;
	@Value("${okta.oauth2.client-id}")
	private String clientId;
	@Value("${okta.auth.scopes}")
	private String scopes;
	@Value("${okta.auth.login.redirect}")
	private String redirectUri;
	@Value("${okta.auth.access.token.url}")
	private String tokenUrl;
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/value", consumes = APPLICATION_FORM_URLENCODED_VALUE)
	@CrossOrigin(origins = "*")
	public String controllerMethod(@RequestParam MultiValueMap<String, String> map) {
		// String data="path Params"+ code +" :: STATE:: "+state;
		// log.info("path Params"+ code +" :: STATE:: "+state);

		return map.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	@CrossOrigin(origins = "*")
	public ResponseEntity<OktaToken> login(@RequestBody LoginInfo loginInfo)
			throws NoSuchAlgorithmException, IOException {
		//LOGIN WITH USER CREDS TO GET TEH SESSION TOKEN
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<LoginInfo> request = new HttpEntity<>(loginInfo);
		SessionInfo sessionInfo = restTemplate.postForObject(this.oktaAuthUrl, request, SessionInfo.class);
		String sessionCode = sessionInfo.getSessiontoken();

		//GET THE CODE WITH PKCE
		// 1: GENERATE RANDOM STRING OF CODE VERIFIER OF LENGTH 45 (min 43)
		int length = 45;
	    boolean useLetters = true;
	    boolean useNumbers = false;
	    String verifier = RandomStringUtils.random(length, useLetters, useNumbers);
	    String state = RandomStringUtils.random(length, useLetters, useNumbers);
	 
	    // 2: GENERATE SHA-256 HASH OF THE VERIFIER AS A CODE CHALLENGE

		byte[] bytes = verifier.getBytes("US-ASCII");
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(bytes, 0, bytes.length);
		byte[] digest = md.digest();
		// Use Apache "Commons Codec" dependency. Import the Base64 class
		// import org.apache.commons.codec.binary.Base64;
		String challenge = Base64.encodeBase64URLSafeString(digest);

		// 3: CALL OKTA OAUTH END POINT TO GENERATE A CODE
		String codeUrl = this.oktaAuthCodeUrl+"?client_id="+this.clientId+"&scope="+this.scopes+"&redirect_uri="+redirectUri+"&response_type=code&response_mode=form_post&state="+state+"&code_challenge_method=S256&code_challenge="
				+ challenge + "&sessionToken=" + sessionCode + "&prompt=none";
		
        // 4. CONNECT TO THE URL AND EXTRACT AUTH CODE
		log.info(codeUrl);
		Document doc;
		String code = "";
		ResponseEntity<OktaToken> tokenInfo=null;
		try {
			doc = Jsoup.connect(codeUrl).get();
			Element buddynameInput = doc.select("input[name=code]").first();
			if(buddynameInput!=null) {
			code = buddynameInput.attr("value");
			
			
			//5. GENERATE ACCESS TOKEN FROM THE CODE USING CODE VERIFIER GENERATED PREVIOUSLY
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			
			MultiValueMap<String, String> tokenMap=new LinkedMultiValueMap<>() ;
			
			tokenMap.add("grant_type", "authorization_code");
			
			tokenMap.add("scope", this.scopes);
			
			tokenMap.add("client_id", this.clientId);
			
			tokenMap.add("code_verifier", verifier);
			
			tokenMap.add("redirect_uri", this.redirectUri);
			
			tokenMap.add("code", code);
			
			
			RestTemplate tokenTemplate = new RestTemplate();

			HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(tokenMap, headers);
			
			 tokenInfo = tokenTemplate.postForEntity(this.tokenUrl, tokenRequest, OktaToken.class);
			}else {
				throw new RuntimeException("Cannot get code from the user session");
			}
			

		} catch (IOException e) {
			log.error(e.getMessage());
			throw e;
		}

		return tokenInfo;

	}
}
