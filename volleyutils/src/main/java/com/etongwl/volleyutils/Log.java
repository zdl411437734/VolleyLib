package com.etongwl.volleyutils;


/**
 * 日志输出
 *
 * @author Administrator
 */

public class Log {

    /**
     * 日志总开关 当只需要部分日志时可在对应的日志开关中打开
     */
    public static boolean LOG_ON_OFF = true;

    private static String TAG = "OE";

    // 常量 用于写日志开关
    private static boolean isWriteLog_File = LOG_ON_OFF;
    // 常量 用户客户端程序日志开关
    private static boolean isWriteLog = LOG_ON_OFF;
    // 用于开关测试 日志 （从oncreate到onresume用时）
    private static boolean isWriteSystemLog = LOG_ON_OFF;

    private static void getTraceInfo(String msg) {
        android.util.Log.e(TAG, msg);
    }

    /**
     * print log
     */
    public static void log() {
        if (isWriteLog) {
            StringBuffer sb = new StringBuffer();
            StackTraceElement[] stacks = new Throwable().getStackTrace();
            sb.append("class: ").append(stacks[1].getClassName())
                    .append("; method: ").append(stacks[1].getMethodName())
                    .append("; number: ").append(stacks[1].getLineNumber());
            getTraceInfo(sb.toString());
        }
    }

    /**
     * print log
     *
     * @param msg
     */
    public static void log(String msg) {
        if (isWriteLog) {
            StringBuffer sb = new StringBuffer();
            StackTraceElement[] stacks = new Throwable().getStackTrace();
            sb.append("class: ").append(stacks[1].getClassName())
                    .append("; method: ").append(stacks[1].getMethodName())
                    .append("; number: ").append(stacks[1].getLineNumber());
            getTraceInfo(sb.toString() + " | " + msg);
        }
    }

    /**
     * print log
     *
     * @param TAG
     * @param msg
     */
    public static void log(String TAG, String msg) {
        if (isWriteLog) {
            StringBuffer sb = new StringBuffer();
            StackTraceElement[] stacks = new Throwable().getStackTrace();
            sb.append("class: ").append(stacks[1].getClassName())
                    .append("; method: ").append(stacks[1].getMethodName())
                    .append("; number: ").append(stacks[1].getLineNumber());
            android.util.Log.d(TAG, sb.toString() + " | " + msg);
        }
    }

    /**
     * @param msg 日志内容
     * @throws
     * @Methods: writeSystemLog
     * @Description: 性能测试日志
     */
    public static void writeSystemLog(String msg) {
        if (isWriteSystemLog) {
            System.out.println(TAG + msg);
        }
    }
}
