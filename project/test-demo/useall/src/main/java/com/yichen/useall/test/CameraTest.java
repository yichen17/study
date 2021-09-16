package com.yichen.useall.test;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import sun.nio.ch.DirectBuffer;

import javax.swing.*;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/9/16 14:39
 * @describe opencv 读取摄像头数据
 *   https://www.cnblogs.com/itqn/p/14531823.html
 */
public class CameraTest {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.setImageWidth(1280);
        grabber.setImageHeight(720);
        grabber.start();
        CanvasFrame canvas = new CanvasFrame("摄像头");
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setAlwaysOnTop(true);
        Frame frame=null;
//        FileChannel channel=new FileOutputStream("e:/data/data.txt").getChannel();
        while (canvas.isDisplayable()) {
            frame=grabber.grab();
            Buffer buffer=frame.image[0];
//            System.out.println(buffer.hasArray());
//            System.out.println(buffer.isReadOnly());
            int capacity=buffer.capacity();
            System.out.println("capacity  "+capacity);
            byte []data=new byte[capacity];
            ((ByteBuffer) buffer).get(data);
            System.out.println(">>>>>> "+ Arrays.toString(data));
            canvas.showImage(frame);
            TimeUnit.MILLISECONDS.sleep(40);
        }
        grabber.stop();
    }

}
