package com.jeff.shareapp.util;

import android.util.Log;

/**
 *  LOG相关
 * @author ly
 * @version v1.0 2013-5-1 09:30
 * 
 */
public class LogUtil {
	private LogUtil() {
	}


	/**
	 * 打印Log信息
	 * 
	 * @param message
	 */
	public static void Log(String message) {
			Log.d("---yirendai---", "---" + System.currentTimeMillis() + "---" + message);
	}

	/**
	 * 打印Log信息
	 * 
	 * @param tag
	 * @param message
	 */
	public static void Log(String tag, String message) {
			Log.d(tag, "---" + System.currentTimeMillis() + "---" + message);
	}

	/**
	 * 打印错误Log信息
	 * 
	 * @param tag
	 * @param message
	 */
	public static void LogError(String tag, String message) {
			Log.e(tag, "---" + System.currentTimeMillis() + "---" + message);
	}

	/**
	 * 打印低级别Log信息
	 * 
	 * @param tag
	 * @param message
	 */
	public static void LogVerbose(String tag, String message) {
			Log.v(tag, message);
	}
}
