package com.teradactol.rnd.spy;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.encoders.Hex;

public class Spy {
	
	private static Spy instance;
	private String algorithm, 
	   				delim = ":";
	
	private Spy(String algorithm) {
		super();
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		this.algorithm = algorithm;
	}

	public static Spy getInstance(String algorithm){
		if(null == instance){
			instance = new Spy(algorithm);
		}		
		return instance;
	}
	
	public String createKey() throws NoSuchAlgorithmException, NoSuchProviderException {
		
		KeyGenerator keygen = KeyGenerator.getInstance("AES", "BC");
		keygen.init(256);	
		Key key = keygen.generateKey();
		
		return Base64.encodeBase64String(key.getEncoded());
	}

	public String encrypt(String plainText, String _key) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		
		byte[] keybytes = Base64.decodeBase64(_key);
		Key key = new SecretKeySpec(keybytes, "AES");		

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding","BC");
		cipher.init(Cipher.ENCRYPT_MODE,key);
		
		String iv = Base64.encodeBase64String(cipher.getIV());
		
		byte[] encMsg = cipher.doFinal(plainText.getBytes());
		return iv+delim+Base64.encodeBase64String(encMsg);
	}

	public String decrypt(String _encMsg, String _key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		
		String[] _encMsgTokens = _encMsg.split(delim);
				
		byte[] keybytes = Base64.decodeBase64(_key);
		byte[] iv = Base64.decodeBase64(_encMsgTokens[0]);
		byte[] encMsg = Base64.decodeBase64(_encMsgTokens[1]);
		
		Key key = new SecretKeySpec(keybytes, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding","BC");
		
		cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(iv));
		
		byte[] plainMsg = cipher.doFinal(encMsg);
		return new String(plainMsg);
	}

	
	
}
