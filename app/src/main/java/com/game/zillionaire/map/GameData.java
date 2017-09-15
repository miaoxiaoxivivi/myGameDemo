package com.game.zillionaire.map;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.game.zillionaire.activity.ZActivity;

import android.content.res.Resources;
/*
 * 该类提供游戏中的数据           底层地图
 */
public class GameData{

	//各种MyDrawable对象在这里初始化
	public static Resources resources;//resources的引用
	
	static String grassBitmap;
	static String croadBitmap;
	static String croad1Bitmap;
	static String rroadBitmap;
	static String rroad1Bitmap;
	static String road1Bitmap;
	static String road2Bitmap;
	static String road3Bitmap;
	static String road4Bitmap;
	static String lt1Bitmap;
	static String lt2Bitmap;
	static String lt3Bitmap;
	static String lt4Bitmap;
	static String lt5Bitmap;
	static String yyBitmap;
	static String jyBitmap;
	static String[] bitmaps; 	
	public static MyDrawable [][] mapData;
	
	public static void initMapImage()//加载图片
	{
		grassBitmap ="grass";
		croadBitmap = "croad";
		croad1Bitmap = "croad1";
		rroadBitmap ="rroad";
		rroad1Bitmap = "rroad1";
		road1Bitmap = "road1";
		road2Bitmap ="road2";
		road3Bitmap = "road3";
		road4Bitmap = "road4";
		lt1Bitmap ="lt1";
		lt2Bitmap = "lt2";
		lt3Bitmap = "lt3";
		lt4Bitmap = "lt4";
		lt5Bitmap = "lt5";
		yyBitmap = "yy";
		jyBitmap = "jy";
		
		bitmaps=new String[]{
				grassBitmap,
				croadBitmap,
				croad1Bitmap,
				rroadBitmap,
				rroad1Bitmap,
				road1Bitmap,
				road2Bitmap,
				road3Bitmap,
				road4Bitmap,
				lt1Bitmap,
				lt2Bitmap,
				lt3Bitmap,
				lt4Bitmap,
				lt5Bitmap,
				yyBitmap,
				jyBitmap
		};
	}

	public static void initMapData(ZActivity at)
	{
		//二维数组为一个MyDrawable对象的引用
		mapData = new MyDrawable [31][31];
		try
		{ 
			InputStream in = resources.getAssets().open("maps.so");
			DataInputStream din = new DataInputStream(in);
			int totalBlocks = din.readInt();
			for(int i=0; i<totalBlocks; i++)
			{
				int outBitmapInxex = din.readByte();//图片的下标
				int kyf=din.readByte();//可遇否，0—不可遇
				int w = din.readByte();//图元的宽度
				int h = din.readByte();//图元的高度
				int col = din.readByte();//总列数
				int row = din.readByte();//总行数
				int pCol = din.readByte();//占位列
				int pRow = din.readByte();//占位行
				int bktgCount=din.readByte();//不可通过点的数量
				int[][] notIn=new int[bktgCount][2];
				int indext=-1;
				if(outBitmapInxex==0)
				{
					indext=0;
				}
				if(outBitmapInxex==1||outBitmapInxex==2||outBitmapInxex==3||outBitmapInxex==4)
				{
					indext=1;//正常公路
				}else if(outBitmapInxex==5||outBitmapInxex==6||outBitmapInxex==7||outBitmapInxex==8)
				{
					indext=2;//泥石路
				}
				for(int j=0; j<bktgCount; j++)//读入不可通过点
				{ 
					notIn[j][0] = din.readByte();
					notIn[j][1] = din.readByte();
				}
				mapData[row][col]=new MyDrawable(
						at,
						bitmaps[outBitmapInxex], 
						bitmaps[outBitmapInxex],
						((kyf==0)?false:true), //可遇否标志位
						w, 
						h, 
						col, 
						row, 
						pCol, 
						pRow, 
						notIn,
						indext,
						0
				);
			}
			din.close();//关闭流
			in.close();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}