package com.oak.service.impl;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class AESwithCBCPaddingCipher {
	private static final String TRANSFORMATION = "AES/CBC/PKCS5PADDING";
	private static final String ALGORITHM = "AES";
	private static final String KEY = "aesEncryptionKey";
	private static final String INIT_VECTOR = "encryptionIntVec";

	private AESwithCBCPaddingCipher() {
		throw new IllegalStateException("AESwithCBCPaddingCipher is a utility class");
	}

	public static String encrypt(String plainText) {
		try {
			IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes(UTF_8));
			SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes(UTF_8), ALGORITHM);

			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(plainText.getBytes());
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception ex) {
			return plainText;
		}
	}

	public static String decrypt(String encrypted) {
		try {
			IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes(UTF_8));
			SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes(UTF_8), ALGORITHM);

			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));

			return new String(original);
		} catch (Exception ex) {
			return encrypted;
		}
	}
	
	public static String encode(String rawPassword) {
		return new BCryptPasswordEncoder().encode(rawPassword);
	}

	public static void main(String[] args) {
		System.out.println("reader - " + encrypt("reader"));
		System.out.println("demouser - " + encrypt("demouser"));
		System.out.println("superuser - " + encrypt("superuser"));
		System.out.println("Admin - " + encrypt("Admin"));
		System.out.println("User - " + encrypt("User"));
		System.out.println("support3@oakitservices.com - " + encrypt("support3@oakitservices.com"));
		System.out.println("oakuser - " + encrypt("oakuser"));
		System.out.println("Oak - " + encrypt("Oak"));
		System.out.println("User - " + encrypt("User"));
		System.out.println("support@oakitservices.com - " + encrypt("support@oakitservices.com"));
		System.out.println("CONFIG_READ - " + encrypt("CONFIG_READ"));
		System.out.println("CONFIG_CREATE - " + encrypt("CONFIG_CREATE"));
		System.out.println("CONFIG_UPDATE - " + encrypt("CONFIG_UPDATE"));
		System.out.println("CONFIG_DELETE - " + encrypt("CONFIG_DELETE"));
		System.out.println("ASSET_READ - " + encrypt("ASSET_READ"));
		System.out.println("ASSET_CREATE - " + encrypt("ASSET_CREATE"));
		System.out.println("ASSET_UPDATE - " + encrypt("ASSET_UPDATE"));
		System.out.println("ASSET_DELETE - " + encrypt("ASSET_DELETE"));
		System.out.println("USER_READ - " + encrypt("USER_READ"));
		System.out.println("USER_CREATE - " + encrypt("USER_CREATE"));
		System.out.println("USER_UPDATE - " + encrypt("USER_UPDATE"));
		System.out.println("USER_DELETE - " + encrypt("USER_DELETE"));
		System.out.println("DATA_READ - " + encrypt("DATA_READ"));
		System.out.println("DATA_CREATE - " + encrypt("DATA_CREATE"));
		System.out.println("DATA_UPDATE - " + encrypt("DATA_UPDATE"));
		System.out.println("DATA_DELETE - " + encrypt("DATA_DELETE"));
		System.out.println("------------");
		System.out.println("CONFIG_ADMIN - " + encrypt("CONFIG_ADMIN"));
		System.out.println("ASSET_VIEW - " + encrypt("ASSET_VIEW"));
		System.out.println("ASSET_ADMIN - " + encrypt("ASSET_ADMIN"));
		System.out.println("USER_ADMIN - " + encrypt("USER_ADMIN"));
		System.out.println("DATA_VIEW - " + encrypt("DATA_VIEW"));
		System.out.println("DATA_ADMIN - " + encrypt("DATA_ADMIN"));
		System.out.println("CLIENT_ADMIN - " + encrypt("CLIENT_ADMIN"));
		System.out.println("SUPER_ADMIN - " + encrypt("SUPER_ADMIN"));
		System.out.println("OAK_ADMIN - " + encrypt("OAK_ADMIN"));
		System.out.println("@dM!n - " + encode("@dM!n"));
		System.out.println("*@kaDM1n - " + encode("*@kaDM1n"));
		System.out.println("reader - " + encode("reader"));
		System.out.println("demouser - " + encode("demouser"));
		System.out.println("larkton - " + encode("larkton"));
	}
}
