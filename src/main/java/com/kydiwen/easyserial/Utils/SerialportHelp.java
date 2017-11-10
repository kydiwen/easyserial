package com.kydiwen.easyserial.Utils;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android_serialport_api.SerialPort;

/**
 * Created by 孙文权 on 2017/5/4.
 * 串口帮助类
 */

public class SerialportHelp {
    private static SerialportHelp help;
    private SerialPort mSerialPort;
    private static Context mContext;
    private InputStream mInputStream;
    private OutputStream mOutputStream;
    private ExecutorService sendSingleThread;
    private ExecutorService acceptSingleThread;
    private boolean isRun = true;
    private static String mport;
    private static int mbaudrate;

    //单例模式获取帮助类对象
    private SerialportHelp() {
        try {
            mSerialPort = new SerialPort(new File(mport), mbaudrate, 0);
            mInputStream = mSerialPort.getInputStream();
            mOutputStream = mSerialPort.getOutputStream();
            sendSingleThread = Executors.newSingleThreadExecutor();
            acceptSingleThread = Executors.newSingleThreadExecutor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取帮助类实例
    public static SerialportHelp open(Context context, String port, int baudrate) {
        if (help == null) {
            mContext = context;
            mport = port;
            mbaudrate = baudrate;
            help = new SerialportHelp();
        }
        return help;
    }
    //发送打印数据
    public void print(final String data) {
        sendSingleThread.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mOutputStream.write(data.getBytes("gbk"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //发送数据
    public void send(final String data) {
        sendSingleThread.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mOutputStream.write(StringOrHex.ToByteArray(data));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //接收数据
    public void read(final readCallback callback) {
        acceptSingleThread.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isRun) {
                        byte[] buffer = new byte[1024];
                        int size = mInputStream.read(buffer);
                        if (size > 0) {
                            callback.onDataReceived(StringOrHex.ToHexString(buffer, size));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //关闭串口
    public void close() {
        try {
            mInputStream.close();
            mInputStream = null;
            mOutputStream.close();
            mOutputStream = null;
            mSerialPort.close();
            mSerialPort = null;
            help = null;
            sendSingleThread.shutdown();
            sendSingleThread = null;
            acceptSingleThread.shutdown();
            acceptSingleThread = null;
            isRun = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //数据接收接口
    public interface readCallback {
        void onDataReceived(String data);
    }
}
