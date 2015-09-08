package com.teradactol.rnd.spy;

import static org.junit.Assert.*;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import com.teradactol.rnd.spy.Spy;

public class Spike {

	
	@Test	
	public void testAPI() throws Exception {
		
		String plainText = "Hello, World!";
		
		Spy spy = Spy.getInstance("AES");	
		String key = spy.createKey();		
		
		String encryptedMsg = spy.encrypt(plainText,key);	
		String decryptedMsg = spy.decrypt(encryptedMsg,key);				
	}
	

	public void testDecodeAndEncode() throws Exception {		
		String _key = "mq8ijZKhJ2EAZEhZDdChA2EuWIW7N1dQOdgYwCgVElw=";
		String _msg = "+ytqSsqoqO6Wo/IsWzwZxg==";
		
		Spy spy = Spy.getInstance("AES");
		
		String plainText = spy.decrypt(_msg, _key);
		System.out.println("Msg : " + plainText);
	}
	
}
