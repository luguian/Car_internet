package com.lu.car_internet.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.lu.car_internet.activity.RouteActivity;
import com.lu.car_internet.config.FileTraversal;
import com.lu.car_internet.config.ImgCallBack;

import net.lemonsoft.lemonhello.LemonHello;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

public class Utils {
    public static final String DAY_NIGHT_MODE = "daynightmode";
    public static final String DEVIATION = "deviationrecalculation";
    public static final String JAM = "jamrecalculation";
    public static final String TRAFFIC = "trafficbroadcast";
    public static final String CAMERA = "camerabroadcast";
    public static final String SCREEN = "screenon";
    public static final String THEME = "theme";
    public static final String ISEMULATOR = "isemulator";


    public static final String ACTIVITYINDEX = "activityindex";

    public static final int SIMPLEHUDNAVIE = 0;
    public static final int EMULATORNAVI = 1;
    public static final int SIMPLEGPSNAVI = 2;
    public static final int SIMPLEROUTENAVI = 3;


    public static final boolean DAY_MODE = false;
    public static final boolean NIGHT_MODE = true;
    public static final boolean YES_MODE = true;
    public static final boolean NO_MODE = false;
    public static final boolean OPEN_MODE = true;
    public static final boolean CLOSE_MODE = false;
	Context context;

	public Utils(Context context) {
		this.context=context;
	}

	// 转向另一个页面
	public static void gotoActivity(Activity poFrom, Class<?> poTo,
			boolean pbFinish, Map<String, String> pmExtra) {
		Intent loIntent = new Intent(poFrom, poTo);
		if (pmExtra != null && !pmExtra.isEmpty()) {
			Iterator<String> loKeyIt = pmExtra.keySet().iterator();
			while (loKeyIt.hasNext()) {
				String lsKey = loKeyIt.next();
				loIntent.putExtra(lsKey, pmExtra.get(lsKey));
			}
		}
		if (pbFinish)
			poFrom.finish();
		poFrom.startActivity(loIntent);
	}

	// 字符串是否为空（全是不可见字符的字符串认为是空）
	public static boolean isStrEmpty(Editable poStr) {
		String lsStr = poStr.toString();
		return isStrEmpty(lsStr);
	}

	// 字符串是否为空（全是不可见字符的字符串认为是空）
	public static boolean isStrEmpty(String psStr) {
		return psStr == null || psStr.trim().length() == 0;
	}
	
	/**
	 * 关闭软键盘
	 * 
	 * @param
	 * @param
	 */
	public static void closeKeybord(EditText mEditText, Context mContext)
	{
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}
	public static void showDialogTip(int code,final Context context){
		if(!Utils.isNetworkAvailable(context)||code==4||code==6||code==5){
			LemonHello.getInformationHello("手机没连接网络,部分功能不能实现", "请在设置连接WIFI或者连接移动网络")
					.addAction(new LemonHelloAction("取消", new LemonHelloActionDelegate() {
						@Override
						public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
							helloView.hide();

						}
					}))
					.addAction(new LemonHelloAction("设置", Color.RED, new LemonHelloActionDelegate() {
						@Override
						public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
							helloView.hide();
							Intent intent =  new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
							context.startActivity(intent);
						}
					}))
					.show(context);
		}else{
			LemonHello.getInformationHello("权限不足", "请在设置-权限管理【开启】-读取位置信息")
//					.addAction(new LemonHelloAction("取消", new LemonHelloActionDelegate() {
//						@Override
//						public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
//							helloView.hide();
//
//						}
//					}))
					.addAction(new LemonHelloAction("好的", Color.RED, new LemonHelloActionDelegate() {
						@Override
						public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
							helloView.hide();
							Intent intent =  new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
							context.startActivity(intent);
						}
					}))
					.show(context);
		}

	}
	//判断网络连接是否可用
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
		} else {
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
			if (networkInfo != null&& networkInfo.length>0 ) {
				for (int i = 0; i < networkInfo.length; i++) {
					if (networkInfo[i].isConnected() && networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static Bitmap getbitmap(String imageUri) {

		// 显示网络上的图片
		Bitmap bitmap = null;
		try {
			URL myFileUrl = new URL(imageUri);
			HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();


		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	/**
	 * 获取全部图片地址
	 * @return
	 */
	public ArrayList<String> listAlldir(){
		Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		Uri uri = intent.getData();
		ArrayList<String> list = new ArrayList<String>();
		String[] proj ={MediaStore.Images.Media.DATA};
		Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);//managedQuery(uri, proj, null, null, null);
		while(cursor.moveToNext()){
			String path =cursor.getString(0);
			list.add(new File(path).getAbsolutePath());
		}
		return list;
	}

	public List<FileTraversal> LocalImgFileList(){
		List<FileTraversal> data=new ArrayList<FileTraversal>();
		String filename="";
		List<String> allimglist=listAlldir();
		List<String> retulist=new ArrayList<String>();
		if (allimglist!=null) {
			Set set = new TreeSet();
			String []str;
			for (int i = 0; i < allimglist.size(); i++) {
				retulist.add(getfileinfo(allimglist.get(i)));
			}
			for (int i = 0; i < retulist.size(); i++) {
				set.add(retulist.get(i));
			}
			str= (String[]) set.toArray(new String[0]);
			for (int i = 0; i < str.length; i++) {
				filename=str[i];
				FileTraversal ftl= new FileTraversal();
				ftl.filename=filename;
				data.add(ftl);
			}

			for (int i = 0; i < data.size(); i++) {
				for (int j = 0; j < allimglist.size(); j++) {
					if (data.get(i).filename.equals(getfileinfo(allimglist.get(j)))) {
						data.get(i).filecontent.add(allimglist.get(j));
					}
				}
			}
		}
		return data;
	}

	//显示原生图片尺寸大小
	public Bitmap getPathBitmap(Uri imageFilePath,int dw,int dh)throws FileNotFoundException {
		//获取屏幕的宽和高
		/**
		 * 为了计算缩放的比例，我们需要获取整个图片的尺寸，而不是图片
		 * BitmapFactory.Options类中有一个布尔型变量inJustDecodeBounds，将其设置为true
		 * 这样，我们获取到的就是图片的尺寸，而不用加载图片了。
		 * 当我们设置这个值的时候，我们接着就可以从BitmapFactory.Options的outWidth和outHeight中获取到值
		 */
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inJustDecodeBounds = true;
		//由于使用了MediaStore存储，这里根据URI获取输入流的形式
		Bitmap pic = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageFilePath),
				null, op);

		int wRatio = (int) Math.ceil(op.outWidth / (float) dw); //计算宽度比例
		int hRatio = (int) Math.ceil(op.outHeight / (float) dh); //计算高度比例

		/**
		 * 接下来，我们就需要判断是否需要缩放以及到底对宽还是高进行缩放。
		 * 如果高和宽不是全都超出了屏幕，那么无需缩放。
		 * 如果高和宽都超出了屏幕大小，则如何选择缩放呢》
		 * 这需要判断wRatio和hRatio的大小
		 * 大的一个将被缩放，因为缩放大的时，小的应该自动进行同比率缩放。
		 * 缩放使用的还是inSampleSize变量
		 */
		if (wRatio > 1 && hRatio > 1) {
			if (wRatio > hRatio) {
				op.inSampleSize = wRatio;
			} else {
				op.inSampleSize = hRatio;
			}
		}
		op.inJustDecodeBounds = false; //注意这里，一定要设置为false，因为上面我们将其设置为true来获取图片尺寸了
		pic = BitmapFactory.decodeStream(context.getContentResolver()
				.openInputStream(imageFilePath), null, op);

		return pic;
	}

	public String getfileinfo(String data){
		String filename[]= data.split("/");
		if (filename!=null) {
			return filename[filename.length-2];
		}
		return null;
	}

	public void imgExcute(ImageView imageView, ImgCallBack icb, String... params){
		LoadBitAsynk loadBitAsynk=new LoadBitAsynk(imageView,icb);
		loadBitAsynk.execute(params);
	}

	public class LoadBitAsynk extends AsyncTask<String, Integer, Bitmap> {

		ImageView imageView;
		ImgCallBack icb;

		LoadBitAsynk(ImageView imageView,ImgCallBack icb){
			this.imageView=imageView;
			this.icb=icb;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap=null;
			try {
				if (params!=null) {
					for (int i = 0; i < params.length; i++) {
						bitmap=getPathBitmap(Uri.fromFile(new File(params[i])), 200, 200);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if (result!=null) {
//				imageView.setImageBitmap(result);
				icb.resultImgCall(imageView, result);
			}
		}


	}




}
