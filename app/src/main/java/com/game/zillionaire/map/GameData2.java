package com.game.zillionaire.map;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.game.zillionaire.activity.ZActivity;
import com.game.zillionaire.meetdrawable.AccidentDrawable;
import com.game.zillionaire.meetdrawable.BankDrawable;
import com.game.zillionaire.meetdrawable.CaiPiaoDrawable;
import com.game.zillionaire.meetdrawable.CardDrawable;
import com.game.zillionaire.meetdrawable.GroundDrawable;
import com.game.zillionaire.meetdrawable.MagicHouseDrawable;
import com.game.zillionaire.meetdrawable.NewsDrawable;
import com.game.zillionaire.meetdrawable.NumberDrawable;

import android.content.res.Resources;

/*
 * 该类提供游戏中的数据           上层地图
 */

public class GameData2 {

	//各种MyDrawable对象在这里初始化
	public static Resources resources;
	
	static String huaBitmap;
	static String hua1Bitmap;
	static String hua2Bitmap;
	public static String jf1Bitmap;
	public static String jfBitmap;
	static String treeBitmap;
	static String tree1Bitmap;
	static String tree2Bitmap;
	static String grass2Bitmap;
	static String grass1Bitmap;
	static String bankBitmap;
	static String cardBitmap;
	static String dian3Bitmap;
	static String dian5Bitmap;
	static String dian8Bitmap;
	static String cshopBitmap;
	static String mfBitmap;
	static String newsBitmap;
	static String roomBitmap;
	static String room1Bitmap;
	static String saveBitmap;
	static String shopBitmap;
	static String touBitmap;
	static String whBitmap;
	static String xwBitmap;
	static String yyBitmap;
	static String yjBitmap;
	static String room3Bitmap;
	static String yiyuanBitmap;
	static String room2Bitmap;
	static String parkBitmap;
	static String jianyuBitmap;
	static String ctBitmap;
	static String ct1Bitmap;
	public static String bmpDialogBack;
	public static String[] bmpDialogBacks=new String[2];
	static String[] bitmaps;
	
	public static int flag;//获得点数的标志位
	public static MyMeetableDrawable[][] mapData;
	public static void initBitmap(){
		huaBitmap="hua";
		hua1Bitmap="hua1";
		hua2Bitmap="hua2";
		jf1Bitmap="jf1";
		jfBitmap="jf";
		treeBitmap = "tree";
		tree1Bitmap = "tree1";
		tree2Bitmap ="tree2";
		grass2Bitmap = "grass2";
		grass1Bitmap = "grass1";
		bankBitmap = "bank";
		cardBitmap = "card";
		dian3Bitmap = "dian3";
		dian5Bitmap = "dian5";
		dian8Bitmap = "dian8";
		cshopBitmap ="kp";
		mfBitmap = "mof";
		newsBitmap = "news";
		roomBitmap ="room";
		room1Bitmap = "room1";
		saveBitmap = "save";
		shopBitmap = "shop";
		touBitmap = "tou";
		whBitmap = "wh";
		xwBitmap = "xw";
		yyBitmap = "yy";
		yjBitmap = "yj";
		room3Bitmap = "room3";
		yiyuanBitmap = "yiyuan";
		room2Bitmap ="room2";
		parkBitmap = "park";
		jianyuBitmap = "jianyu";
		ctBitmap ="ct";
		ct1Bitmap = "ct1";
		bmpDialogBack="dialog_back";
		bmpDialogBacks[0]="dialog_back1";
		bmpDialogBacks[1]="dialog_back2";
		
		bitmaps = new String[]
        {
				huaBitmap,
				hua1Bitmap,
				hua2Bitmap,
				jf1Bitmap,
				jfBitmap,
				treeBitmap,
				tree1Bitmap,
				tree2Bitmap,
				grass2Bitmap ,
				grass1Bitmap ,
				bankBitmap,
				cardBitmap,
				dian3Bitmap,
				dian5Bitmap,
				dian8Bitmap,
				cshopBitmap,
				mfBitmap,
				newsBitmap,
				roomBitmap,
			    room1Bitmap,
			    saveBitmap,
			    shopBitmap,
			    touBitmap,
			    whBitmap,
			    xwBitmap,
			    yyBitmap,
			    yjBitmap,
			    room3Bitmap,
				yiyuanBitmap,
				room2Bitmap,
				parkBitmap,
				jianyuBitmap,
				ctBitmap,
				ct1Bitmap
        };
	}
	public static void initMapData(ZActivity at)
	{	 
		//二维数组为一个MyDrawable对象的引用
		mapData = new MyMeetableDrawable [31][31];
		try 
		{
			InputStream in = resources.getAssets().open("mapsu.so");
			DataInputStream din = new DataInputStream(in);
			int totalBlocks = din.readInt();
			for(int i=0; i<totalBlocks; i++)
			{
				int outBitmapInxex = din.readByte();
				int kyf=din.readByte();//可遇否，1—可遇
				int w = din.readByte();//图元的宽度
				int h = din.readByte();//图元的高度
				int col = din.readByte();//总列数
				int row = din.readByte();//总行数
				int pCol = din.readByte();//占位列
				int pRow = din.readByte();//占位行
				int bktgCount=din.readByte();//不可通过点的数量
				int[][] notIn=new int[bktgCount][2];
				for(int j=0; j<bktgCount; j++)
				{
					notIn[j][0] = din.readByte();
					notIn[j][1] = din.readByte();
				}
				int keYuCount = din.readByte();//可遇点的数量
				int[][] keYu=new int[keYuCount][2];
				for(int j=0; j<keYuCount; j++)
				{
					keYu[j][0] = din.readByte();
					keYu[j][1] = din.readByte();
				}
				int da=0;
				if(outBitmapInxex>=0)
				{	
					if(outBitmapInxex==3||outBitmapInxex==4)
					{
						if(outBitmapInxex==4)
						{
							da=1;//代表大的土地
						}else
						{
							da=2;
						}
						mapData[row][col]=new GroundDrawable
						(		
								at,
								bitmaps[outBitmapInxex],
								bitmaps[outBitmapInxex],
								bmpDialogBack,
								((kyf==0)?false:true), 
								w, 
								h, 
								col, 
								row, 
								pCol, 
								pRow, 
								notIn,
								keYu,
								da
						);
					}
					else if(outBitmapInxex==10)//银行
					{
						da=3;//代表路上的物体
						mapData[row][col]=new BankDrawable
						(		
								at,
								bitmaps[outBitmapInxex],
								bitmaps[outBitmapInxex],
								bmpDialogBack,
								((kyf==0)?false:true), 
								w, 
								h, 
								col, 
								row, 
								pCol, 
								pRow, 
								notIn,
								keYu,
								da
						);
					}else if(outBitmapInxex==11)//卡片
					{
						da=3;//代表路上的物体
						mapData[row][col]=new CardDrawable
						(		
								at,
								bitmaps[outBitmapInxex],
								bitmaps[outBitmapInxex],
								bmpDialogBack,
								((kyf==0)?false:true), 
								w, 
								h, 
								col, 
								row, 
								pCol, 
								pRow, 
								notIn,
								keYu,
								da
						);		
					}else if(outBitmapInxex==12||outBitmapInxex==13||outBitmapInxex==14)//数字
					{
						da=3;//代表路上的物体
						if(outBitmapInxex==12)
						{
							flag=12;
						}else if(outBitmapInxex==13)
						{
							flag=13;
						}else
						{
							flag=14;
						}
						mapData[row][col]=new NumberDrawable
						(
								at,
								bitmaps[outBitmapInxex],
								bitmaps[outBitmapInxex],
								bmpDialogBack,
								((kyf==0)?false:true), 
								w, 
								h, 
								col, 
								row, 
								pCol, 
								pRow, 
								notIn,
								keYu,
								da
						);		
					}
					else if(outBitmapInxex==16)//魔法屋
					{
						da=3;//代表路上的物体
						mapData[row][col]=new MagicHouseDrawable
						(
								at,
								bitmaps[outBitmapInxex],
								bitmaps[outBitmapInxex],
								bmpDialogBack,
								((kyf==0)?false:true), 
								w, 
								h, 
								col, 
								row, 
								pCol, 
								pRow, 
								notIn,
								keYu,
								da
						);		
					}else if(outBitmapInxex==23)//机遇
					{
						da=3;//代表路上的物体
						mapData[row][col]=new AccidentDrawable
						(
								at,
								bitmaps[outBitmapInxex],
								bitmaps[outBitmapInxex],
								bmpDialogBack,
								((kyf==0)?false:true), 
								w, 
								h, 
								col, 
								row, 
								pCol, 
								pRow, 
								notIn,
								keYu,
								da
						);		
					}else if(outBitmapInxex==17)//新闻
					{
						da=3;//代表路上的物体
						mapData[row][col]=new NewsDrawable
						(
								at,
								bitmaps[outBitmapInxex], 
								bitmaps[outBitmapInxex],
								bmpDialogBack,
								((kyf==0)?false:true), //可遇否标志位
								w, 
								h, 
								col, 
								row, 
								pCol, 
								pRow, 
								notIn,
								keYu,
								da
						);
					}else if(outBitmapInxex==26)//彩票
					{
						da=3;//代表路上的物体
						mapData[row][col]=new CaiPiaoDrawable
						(		
								at,
								bitmaps[outBitmapInxex],
								bitmaps[outBitmapInxex],
								bmpDialogBack,
								((kyf==0)?false:true), 
								w, 
								h, 
								col, 
								row, 
								pCol, 
								pRow, 
								notIn,
								keYu,
								da
						);		
					}else
					{
						da=3;
						mapData[row][col]=new CardDrawable
						(
								at,
								bitmaps[outBitmapInxex],
								bitmaps[outBitmapInxex],
								bmpDialogBack,
								((kyf==0)?false:true), 
								w, 
								h,
								col, 
								row,
								pCol, 
								pRow, 
								notIn,
								keYu,
								da
						);
					}
				}
			}
			din.close();
			in.close();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
