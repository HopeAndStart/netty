package com.zsl.bio;

import java.nio.ByteBuffer;

/**
 * 缓冲Buffer说明
 *
 * @author zsl
 * @version 1.0.0
 * @date: 2020/5/17 21:49
 **/
public class BufferDescription {
    
    public static void main (String[] args) {
        ByteBuffer  buffer = ByteBuffer.allocate(5);
        for (byte b = 0;b < buffer.capacity();b++){
            buffer.put(b);
        }
        buffer.flip();
        while (buffer.hasRemaining()){
            byte b = buffer.get();
            System.out.println(b);
        }
    }
    
}
