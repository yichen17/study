package com.yichen.nettydemo.zeroCopyDemo;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/4/2 16:56
 * @describe  测试领拷贝，使之不从内核态到用户态之间切换，一直在 内核态运行
 */
public class TransferTo {
    public static void testTransferTo() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("D:\\personal\\learn note\\project\\test-demo\\netty-demo\\src\\main\\resources\\data\\from.data", "rw");
        FileChannel fromChannel = fromFile.getChannel();
        RandomAccessFile toFile = new RandomAccessFile("D:\\personal\\learn note\\project\\test-demo\\netty-demo\\src\\main\\resources\\data\\to.data", "rw");
        FileChannel toChannel = toFile.getChannel();
        long position = 0;
        long count = fromChannel.size();
        fromChannel.transferTo(position, count, toChannel);
    }

    public static void main(String[] args)throws Exception {
        testTransferTo();
    }
}
