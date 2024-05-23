package com.pyr.permission.interfaces;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import java.io.File;

public class HutoolEncryptFileController {
    public static void main(String[] args) {
        String inputFilePath = "D:\\Nexus\\DocumentManagement\\Pdf\\20417_14\\86700001202310_LR_2024_04_18_16_01.pdf"; // 输入文件路径
        String encryptedFilePath = "D:\\Nexus\\DocumentManagement\\Pdf\\20417_14\\encrpt\\86700001202310_LR_2024_04_18_16_02.pdf"; // 加密后输出文件路径
        String decryptedFilePath = "D:\\Nexus\\DocumentManagement\\Pdf\\20417_14\\decrpt\\86700001202310_LR_2024_04_18_16_02.pdf"; // 解密后输出文件路径

        // 密钥（AES规定密钥长度为16/24/32字节）
        String key = "1234567890123456";

        // 创建AES加密对象
        SymmetricCrypto aes = new SymmetricCrypto("AES", key.getBytes());

        try {
            encryptFile(inputFilePath, aes, encryptedFilePath);
            decryptedFile(encryptedFilePath, aes, decryptedFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void encryptFile(String inputFilePath, SymmetricCrypto aes, String encryptedFilePath) {
        // 加密文件
        File inputFile = new File(inputFilePath);
        // 读取原始文件并加密
        byte[] inputBytes = FileUtil.readBytes(inputFile);
        byte[] encryptedBytes = aes.encrypt(inputBytes);
        // 将加密后的数据写入到输出文件
        FileUtil.writeBytes(encryptedBytes, encryptedFilePath);
//            aes.encryptFile(inputFilePath, encryptedFilePath);
        System.out.println("文件加密成功！加密后文件路径：" + encryptedFilePath);
    }

    private static void decryptedFile(String encryptedFilePath, SymmetricCrypto aes, String decryptedFilePath) {
        // 解密文件
        File encryptedFile = new File(encryptedFilePath);
        // 读取加密文件并解密
        byte[] encryptedBytes1 = FileUtil.readBytes(encryptedFile);
        byte[] decryptedBytes = aes.decrypt(encryptedBytes1);
        // 将解密后的数据写入到输出文件
        FileUtil.writeBytes(decryptedBytes, decryptedFilePath);
//            aes.decryptFile(encryptedFilePath, decryptedFilePath);
        System.out.println("文件解密成功！解密后文件路径：" + decryptedFilePath);
    }
}
