package com.pyr.permission.common.custom;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CustomClassLoader extends ClassLoader {
    private final String classPath;

    public CustomClassLoader(String classPath) {
        this.classPath = classPath;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        // 如果类名以 "java." 开头，仍然使用父类加载器加载，避免加载核心类时出现问题
        if (name.startsWith("java.")) {
            return super.loadClass(name);
        }
        // 尝试加载自定义类
        Class<?> clazz = findClass(name);
        if (clazz != null) {
            return clazz;
        }
        // 自己加载不了，再调用父类loadClass，保持双亲委托模式
        return super.loadClass(name);
    }

    // 获取class文件二进制字节数组
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] result;
        String path = classPath + File.separatorChar + name.replace('.', File.separatorChar) + ".class";
        try (FileInputStream fis = new FileInputStream(path)) {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            result = buffer;
        } catch (IOException e) {
            throw new ClassNotFoundException("Could not load class " + name, e);
        }
        return defineClass(name, result, 0, result.length);
    }

    // 自定义类加载逻辑，例如从文件、网络等加载类字节码

    public static void main(String[] args) throws Exception {
        CustomClassLoader customClassLoader = new CustomClassLoader("/Users/panyurou/learn/permission/permission/target/classes");
        Class<?> myStringClass = customClassLoader.loadClass("com.pyr.permission.common.custom.MyString");
        Object myStringInstance = myStringClass.newInstance();
        System.out.println(myStringInstance);
    }
}

