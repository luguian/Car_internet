package com.lu.car_internet.database;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import com.lu.car_internet.R;
import com.lu.car_internet.config.QuestionConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataBaseToSD {

	private Context mContext;

	public DataBaseToSD(Context context) {
		this.mContext = context;
	}

	// 判断sd卡是否挂载
	public static boolean isMounted() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			return true;
		} else {
			return false;
		}
	}

	// 把raw下的数据库写进SD卡
	public void WriteToSD() {
		// 若SD卡已挂载
		if (isMounted()) {
			// 建立SD卡路径供数据库文件存储
			File file = new File(QuestionConfig.FILE_PATH);
			// 如果该路径没有被创建过,则创建该路径
			if (!file.exists()) {
				file.mkdirs();
                if(file.mkdirs()){
					Log.d("system.out","bt");
				}
			}
			File file_db = new File(QuestionConfig.DB_FILE_PATH);

			//如果不存在对应路径的数据库文件,则写入
			if (!file_db.exists()) {

				InputStream is = this.mContext.getResources().openRawResource(
						R.raw.question_bank);
				try {
					FileOutputStream fos = new FileOutputStream(
							QuestionConfig.DB_FILE_PATH);

					byte[] data = new byte[2048];
					int length = 0;
					while ((length = is.read(data)) != -1) {
						fos.write(data, 0, length);
					}
					is.close();
					fos.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					Log.d("system.out","rt");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block

					e.printStackTrace();
				}
			}

		} else {
			Toast.makeText(mContext, "您的SD卡没有挂载", Toast.LENGTH_LONG).show();
		}
	}
}
