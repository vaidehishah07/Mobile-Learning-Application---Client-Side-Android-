package com.paril.mlaclientapp.util;

import android.content.Context;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.Calendar;
import java.util.Enumeration;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.security.auth.x500.X500Principal;

public class KeyManager
{

    public static class Keys
    {
        private PrivateKey privKey;
        private PublicKey pubKey;

        public Keys(PrivateKey privKey, PublicKey pubKey) {
            this.privKey = privKey;
            this.pubKey = pubKey;
        }

        public PrivateKey getPrivateKey() {
            return privKey;
        }
        public PublicKey getPublicKey() {
            System.out.println(pubKey);
            return pubKey;
        }
    }

    public KeyManager(int keylength) throws NoSuchAlgorithmException, NoSuchProviderException, IOException, CertificateException {

        try
        {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    public byte[] newGrpKeyGenerate()
    {
        byte[] key = new byte[1024];
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(128);
            key = keygen.generateKey().getEncoded();
        }catch (Exception e){}
        return key;
    }


    public static Keys getKeysFromKeyStore(String alias, Context ctx)
    {
        try {

            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);

            if(keyStore.containsAlias(alias))
            {
                KeyStore.Entry entry = keyStore.getEntry(alias, null);
                PrivateKey privateKey = ((KeyStore.PrivateKeyEntry) entry).getPrivateKey();
                PublicKey publicKey = keyStore.getCertificate(alias).getPublicKey();
                Enumeration<String> e = keyStore.aliases();
                return new Keys(privateKey, publicKey);
            }
            else
                {


                KeyPair kp= create_New_Keys(alias,ctx);
                PrivateKey privateKey=kp.getPrivate();
                PublicKey publicKey = kp.getPublic();
                return new Keys(privateKey, publicKey);
            }

        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | UnrecoverableEntryException | IOException e) {
            throw new RuntimeException("Unable to retrieve keys");
        }
    }


    static KeyPair create_New_Keys(String alias, Context ctx) throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
        try{

            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            end.add(Calendar.YEAR, 30);
            KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(ctx)
                    .setAlias(alias)
                    .setSubject(new X500Principal("CN=" + alias))
                    .setSerialNumber(BigInteger.TEN)
                    .setStartDate(start.getTime())
                    .setEndDate(end.getTime())
                    .build();

            KeyPairGenerator kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore");
            kpg.initialize(spec);

            KeyPair keyPair = kpg.generateKeyPair();
            return keyPair;

        }
        catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidAlgorithmParameterException exception) {
            throw new RuntimeException("Unable to generate KeyPair");
        }
    }


    public static byte[] decrypt_with_PrivateKey(byte[] encrypted, PrivateKey privatekey) throws Exception
    {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privatekey);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    public static byte[] encrypt_with_PubKey(byte[] grpkey, PublicKey pubkey) throws Exception
    {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, pubkey);
        byte[] encrypted = cipher.doFinal(grpkey);
        return encrypted;
    }

    public String postEncryptionusingSessionKey(String post, SecretKey sessionKey) throws Exception
    {

        byte[] postbytes = post.getBytes();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, sessionKey);
        String encryptedData = Base64.encodeToString(cipher.doFinal(postbytes), Base64.DEFAULT);
        return encryptedData;
    }

    public String postDecryptionusingSessionKey(String encrpytedPost, SecretKey sessionKey) throws Exception
    {
        byte[] encryptStr = Base64.decode(encrpytedPost, Base64.DEFAULT);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, sessionKey);
        byte[] decryptedData = cipher.doFinal(encryptStr);
        String decryptedStr = new String(decryptedData);
        return decryptedStr;
    }

    public SecretKey newSessionKeyGenerate() throws Exception
    {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey sessionKey = keyGenerator.generateKey();
        return sessionKey;
    }

    public String bTos(byte[] grpKeyByte)
    {
        String grpKey = Base64.encodeToString(grpKeyByte, Base64.DEFAULT);
        return grpKey;
    }

    public byte[] sTob(String str)
    {
        byte[] byt = Base64.decode(str, Base64.DEFAULT);
        return byt;
    }


public byte[] sign(String post, PrivateKey prkkey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

    Signature signature = Signature.getInstance("SHA256withRSA");
    signature.initSign(prkkey);
    signature.update(post.getBytes());
    return signature.sign();
}

    public boolean verifySign(byte[] data, byte[] signature, PublicKey pubkey) throws Exception
    {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(pubkey);
        sig.update(data);
        if (sig.verify(signature))
            return true;
        else return false;
    }

}
