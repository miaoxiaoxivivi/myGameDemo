package com.game.zillionaire.util;

import static com.game.zillionaire.util.ConstantUtil.DIALOG_WORD_SIZE;
import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateUtil 
{
	//获得某日期后的n天的日期
	public static Date getDateAfter(Date d,int day)
	{
	   Calendar now =Calendar.getInstance();
	   now.setTime(d);
	   now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
	   return now.getTime();
    }
	
	//根据日期计算出当期是星期几，并将整合好的年月日、星期返回-----------------结果在GameView中绘制
	public static String getWeekAndYMD(Date date)
	{  	
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	     String s1 = sdf.format(date);            //这里得到：1999/03/26 这个格式的日期
	     sdf = new SimpleDateFormat("EEEE");  
	     String week = sdf.format(date);   //根据日期取得星期几
         String result=s1+" "+week;           //指定字符串的格式，日期+“ ”+星期
         return result;  
    }  
	
	//根据年份月份日期和星期获得日期，并将其转化为int返回
	public static int getDate(String str)
	{
		String strs=str.substring(8, 10);
		int result=Integer.parseInt(strs);
		return result;
	}
	//绘制日期
	public static void drawString(Canvas canvas,String string)
	{
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setTextSize(20);//设置文字大小
		paint.setFakeBoldText(true);//字体加粗
		int lines = string.length()/22+(string.length()%22==0?0:1);//求出需要画几行文字
		for(int i=0;i<lines;i++)
		{
			String str="";
			if(i == lines-1)//如果是最后一行那个不太整的汉字
			{
				str = string.substring(i*22);
			}
			else
			{
				str = string.substring(i*22, (i+1)*22);
			}
			canvas.drawText(str,60,20+DIALOG_WORD_SIZE*i,paint);
			canvas.drawText("物价水平：1",90,20+DIALOG_WORD_SIZE,paint);
		}
	}
}
