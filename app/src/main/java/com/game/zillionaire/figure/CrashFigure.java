package com.game.zillionaire.figure;

import java.util.HashMap;

import com.game.zillionaire.map.MyDrawable;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

@SuppressLint("UseSparseArrays")
public class CrashFigure //碰撞人物类
{
	//记录标志位，用于绘制图片
	public int refCol;
	public int refRow;	
	public int x,y;//在底层地图中的坐标位置
	public int i,j;//碰撞人物的数组索引
	public static int[][] idColRow=new int[31][31];//根据位置，记录神明id
	public static Bitmap[][] figureBitmap=new Bitmap[2][6];
	public static Bitmap[]  smallGod=new Bitmap[11];//存放碰撞人物人头
	public static int[][] lowland=new int[6][2];//存放底层坐标
	public int id;//用于标识碰撞人物的类型
	public static boolean isMeet=false;
	public static boolean isMeet0=false;
	public static boolean isMeet1=false;
	public MyDrawable myDrawable;//地图单元
	public CrashFigure(int refCol,int refRow,int i,int j,int x,int y,int id,MyDrawable myDrawable)
	{
		this.refCol=refCol;
		this.refRow=refRow;
		this.i=i;
		this.j=j;
		this.x = x;
		this.y = y;
		this.id=id;
		idColRow[x][y]=this.id;
		this.myDrawable=myDrawable;
	}
	public static void initBitmap(Resources r)
	{
		ConstantUtil.hm=new HashMap<Bitmap,Integer>();//初始化hm
		ConstantUtil.hm1=new HashMap<Integer,Bitmap>();//初始化hm1
		Bitmap tempBmp=PicManager.getPic("cx", r); //幸运人物大图
		figureBitmap[0]=new Bitmap[6];
		for(int i=0;i<6;i++)
	    {//初始化幸运人物图片
			figureBitmap[0][i]=Bitmap.createBitmap(tempBmp,0,60*i, 48,50);
	    }
		figureBitmap[1]=new Bitmap[6];
		for(int i=0;i<6;i++)
	    {//初始化倒霉人物图片
			figureBitmap[1][i]=Bitmap.createBitmap(tempBmp,0, 60*(i+6), 48,50);
	    }
		tempBmp=null;
		tempBmp=PicManager.getPic("smgod", r);
		for(int i=0;i<11;i++)
		{//初始化碰撞神明头像
			smallGod[i]=Bitmap.createBitmap(tempBmp,0,ConstantUtil.smgoddata[i][0],53,ConstantUtil.smgoddata[i][1]);
			ConstantUtil.hm1.put(i, smallGod[i]);//将图片放入HashMap hm1
		}
		tempBmp=null;
		for(int i=0;i<6;i++)
		{//将图片放入HashMap hm
			ConstantUtil.hm.put(figureBitmap[0][i], i);//0大财神、1大福神、2土地公、3小财神、4小天使、5小福神
			ConstantUtil.hm.put(figureBitmap[1][i], i+6);//6--小穷鬼 7--大穷鬼  8--小衰神 9-大衰神  10--大恶魔  11--疯狗
		}
	}
	//方法：绘制自己
	public void drawSelf(Canvas canvas,int screenRow,int screenCol,int offsetX,int offsetY)
	{
		int x= (screenCol-refCol)*ConstantUtil.TILE_SIZE_X;//求出自己所拥有的块数中左上角块的x坐标
		int y =screenRow*ConstantUtil.TILE_SIZE_Y+(refRow+1)*ConstantUtil.TILE_SIZE_Y-figureBitmap[i][j].getHeight();//求出自己所拥有的块数中左上角块的y坐标
		canvas.drawBitmap(figureBitmap[i][j], x-offsetX+5, y-offsetY-16, null);//根据自己的左上角的xy坐标画出自己
	}
}
