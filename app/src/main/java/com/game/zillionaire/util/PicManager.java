package com.game.zillionaire.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PicManager 
{
	static long min=0;//图片被加载时的最长时间
	static int count=0;
	static boolean isCirculate=true;
	static HashMap<String,MyBitmap> picList=new HashMap<String,MyBitmap>();
	public  static Bitmap getPic(String picName,Resources r)//获取图片
	{
		Bitmap result=null;
		isCirculate=true;
		while(isCirculate)
		{
			if(picList.get(picName)!=null)
			{
				result=picList.get(picName).bm;
				picList.get(picName).date=System.nanoTime();
				isCirculate=false;
			}
			else
			{
				Loading(picName,r);
			}
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public static  void Loading(String picName,Resources r)//加载图片
	{
		MyBitmap bitmap = new MyBitmap();
		if(picList.size()<100)//加载图片
		{
			try {
				String path="pic/"+picName+".png";
				InputStream in= r.getAssets().open(path);
				bitmap.bm=BitmapFactory.decodeStream(in);
				bitmap.date=System.nanoTime();
				picList.put(picName, bitmap);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else//删除图片
		{
			Iterator<Entry<String, MyBitmap>> iter = picList.entrySet().iterator();
			while (iter.hasNext())
			{
				Map.Entry entry = (Map.Entry) iter.next();
				if(count==0)
				{
					min=((MyBitmap)entry.getValue()).date;
					count++;
				}else
				{
					if(min>((MyBitmap)entry.getValue()).date)
					{
						min=((MyBitmap)entry.getValue()).date;
					}
				}
			}
			picList.remove(min);
			try {
				String path="pic/"+picName+".png";
				InputStream in= r.getAssets().open(path);
				bitmap.bm=BitmapFactory.decodeStream(in);//加载图片
				bitmap.date=System.nanoTime();
				picList.put(picName, bitmap);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
