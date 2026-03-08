package com.java;

public class IntAndByteConvert {

    public static void main(String[] args) {
        // int value = 127;
        // byte b = (byte) value; // 正确：127
        // byte c = (byte) 128;   // 错误：-128（溢出）
        // System.out.println("b is " + b);
        // System.out.println("c is " + c);

        int value = 156;
        byte b = (byte) (value & 0xFF); // 正确：-1（但值正确）
        System.out.println("b is " + b);

        // 1111 1111

        // byte[] buffer = new byte[4];
        // // 假设从文件中读取了 4 个字节
        // buffer[0] = 0x12;
        // buffer[1] = 0x34;
        // buffer[2] = 0x56;
        // buffer[3] = 0x78;
        //
        // // 将 4 个字节转换为 int
        // int value = (buffer[0] & 0xFF) << 24 |
        //         (buffer[1] & 0xFF) << 16 |
        //         (buffer[2] & 0xFF) << 8  |
        //         (buffer[3] & 0xFF);
        // System.out.println(Integer.toHexString(value)); // 输出: 12345678
    }

    // public byte[] toBytes(int number){
    //     byte[] bytes = new byte[4];
    //     bytes[3] = (byte)number;
    //     bytes[2] = (byte) ((number >> 8) & 0xFF);
    //     bytes[1] = (byte) ((number >> 16) & 0xFF);
    //     bytes[0] = (byte) ((number >> 24) & 0xFF);
    //     return bytes;
    // }
    //
    // public byte[] toBytes(int number){
    //     byte[] bytes = new byte[4];
    //     bytes[3] = (byte)number;
    //     bytes[2] = (byte) (number >> 8);
    //     bytes[1] = (byte) (number >> 16);
    //     bytes[0] = (byte) (number >> 24);
    //     return bytes;
    // }

    // int转byte[]（高位在前）
    byte[] intToBytes(int value) {
        byte[] bytes = new byte[4]; // 修复：指定数组大小为4（int占4字节）
        bytes[0] = (byte) ((value >> 24) & 0xFF); // 修复：使用数组索引赋值
        bytes[1] = (byte) ((value >> 16) & 0xFF);
        bytes[2] = (byte) ((value >> 8) & 0xFF);
        bytes[3] = (byte) (value & 0xFF);
        return bytes;
    }

    // byte[]转int
    int bytesToInt(byte[] bytes) {
        // 修复：1. 访问数组元素 2. 添加0xFF掩码防止符号扩展 3. 正确移位
        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) |
                (bytes[3] & 0xFF);
    }


}
