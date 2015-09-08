NB: To use the encryption library please install  the *** Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files ***


### 1 Use AES to encrypt and decrypt a String

```java


String plainText = "Hello, World!";

Spy c = Spy.getInstance("AES");

String key = c.createKey(); // By default it creates a 256 bit key.. c.createKey("128"), c.createKey("192"), c.createKey("256") 

// Encrypt Message
String encryptedMsg = c.encrypt(plainText, key); 

// Decrypt Message
String decryptedMsg = c.decrypt(plainText, key); 

```
