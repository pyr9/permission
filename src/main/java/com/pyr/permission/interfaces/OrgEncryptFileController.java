package com.pyr.permission.interfaces;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;

public class OrgEncryptFileController {

    private static final String AES_ALGORITHM = "AES";

    public static void encryptFile(String inputFile, String outputFile, String key) throws Exception {
        Key secretKey = new SecretKeySpec(key.getBytes(), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        try (InputStream inputStream = new FileInputStream(inputFile);
             OutputStream outputStream = new CipherOutputStream(new FileOutputStream(outputFile), cipher)) {
            byte[] buffer = new byte[8192];
            int count;
            while ((count = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, count);
            }
        }
    }

    public static void decryptFile(String inputFile, String outputFile, String key) throws Exception {
        Key secretKey = new SecretKeySpec(key.getBytes(), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        try (InputStream inputStream = new CipherInputStream(new FileInputStream(inputFile), cipher);
             OutputStream outputStream = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[8192];
            int count;
            while ((count = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, count);
            }
        }
    }

    public static void main(String[] args) {
        String inputFile = "D:\\Nexus\\DocumentManagement\\Pdf\\20417_14\\86700001202310_LR_2024_04_18_16_01.pdf"; // 输入文件路径
        String encryptedFile = "D:\\Nexus\\DocumentManagement\\Pdf\\20417_14\\encrpt\\86700001202310_LR_2024_04_18_16_01.pdf"; // 加密后输出文件路径
        String decryptedFile = "D:\\Nexus\\DocumentManagement\\Pdf\\20417_14\\decrpt\\86700001202310_LR_2024_04_18_16_01.pdf"; // 解密后输出文件路径

        String key = "YourSecretKey" + "123"; // 加密密钥

        try {
            encryptFile(inputFile, encryptedFile, key);
            System.out.println("File encrypted successfully.");

            decryptFile(encryptedFile, decryptedFile, key);
            System.out.println("File decrypted successfully.");

        } catch (Exception e) {
            System.err.println("Error encrypting file: " + e.getMessage());
        }
    }
}
