package com.lu.car_internet.config;

import android.os.Environment;

import java.io.File;

public class QuestionConfig {

	// 供DBHelper使用
	public static final String DB_NAME = "question_bank.db";// 文件夹名
	public static final String TABLE_NAME = "question";// 表名
	public static final int DB_VERSION = 1;// 版本号

	//
	public static String FILE_PATH =Environment.getExternalStorageDirectory()+File.separator+"question_bank";

	// 把raw下的数据库写进SD卡时所需的路径
	public static final String DB_FILE_PATH =FILE_PATH+File.separator+"question_bank.db";

}
