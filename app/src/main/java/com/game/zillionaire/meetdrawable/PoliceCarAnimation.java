package com.game.zillionaire.meetdrawable;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.game.zillionaire.dialog.DialogEnum;
import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.util.PicManager;

import static com.game.zillionaire.util.ConstantUtil.*;

public class PoliceCarAnimation implements Serializable
{
	private static final long serialVersionUID = 7616381866056590558L;
	static Bitmap[] police=new Bitmap[2];//加载警车图片
	static Bitmap[] ambulance=new Bitmap[2];//加载救护车图片
	static Bitmap[] temp=new Bitmap[2];
	public static boolean isPolice = false;//警车是否显示
	public static boolean isAmbulance=false;//是否播放救护车动画
	
	PoliceThread pt;//创建警车走的线程
	int currentFrame=0;//当前帧数为第一帧
	int nowX=0;//自定义变量  用来自加
	int nowXX=0;//自定义变量  用来自加
	int judge=0;//判断值
	int nowFigure=0;//记录当前英雄的int值
	Figure tempFigure=null;
	public PoliceCarAnimation(Figure figure){
		pt=new PoliceThread();
		nowFigure=figure.father.isFigure;//确定当前英雄值
		tempFigure=figure;//赋值给英雄
	}
	public void drawSelf(Canvas canvas,int x,int y,int figureX)//画警车走的图片
	{
		Bitmap tmp=PicManager.getPic("ambulance",tempFigure.father.activity.getResources());//加载救护车大图
		for(int i=0;i<2;i++)//加载救护车图片
		{
			ambulance[i]=Bitmap.createBitmap(tmp, 128*i, 0, 128, 128);
		}
		tmp=PicManager.getPic("policecar",tempFigure.father.activity.getResources());//加载警车大图
		for(int i=0;i<2;i++)//加载警车的图片
		{
			police[i]=Bitmap.createBitmap(tmp, 128*i, 0, 128, 128);
		}
		tmp=null;
		x=x+(50*nowX);//每次都变换X坐标
		nowX++;//变量自加
		if(isAmbulance)//如果点击了救护车图标
		{
			temp[currentFrame]=ambulance[currentFrame];
		}
		if(isPolice)//如果点击了警车图标
		{
			temp[currentFrame]=police[currentFrame];
		}
		if(x<=900)//如果小于屏幕宽度
		{
			if(Math.abs(x-figureX)<100)//如果救护车到达英雄位置
			{
				canvas.drawBitmap(temp[currentFrame], figureX-30, y, null);
				tempFigure.isDreaw=false;
			}else//如果救护车不在英雄位置
			{
				canvas.drawBitmap(temp[currentFrame], x,y, null);//变换走
			}
		}else//如果救护车已经超出屏幕宽度
		{
			judge=1;//判断值等于1 切换地图到医院位置
			x=x-(50*nowX)-100;//还原x值(缓冲一次)
			x=x+(50*nowXX);//变换x坐标
			nowXX++;//变量自加
			if(x<=figureX){//如果救护车x坐标小于医院的x坐标
				if(Math.abs(x-figureX)<100)//如果救护车到达医院位置
				{
					canvas.drawBitmap(temp[currentFrame], figureX-30, y, null);
					tempFigure.isDreaw=false;
				}else//如果救护车不在医院位置
				{
					canvas.drawBitmap(temp[currentFrame], x,y, null);//变换走
				}
			}else//如果救护车x坐标大于医院x坐标
			{
				judge=2;//判断值等于2 停止救护车动画 返回游戏
			}
		}
	}
	public void startAnimation()//开始动画
	{
		pt.isGameOn=true;//标志位设为true
		if(!pt.isAlive())//如果线程没有开启 则开启线程
		{
			pt.start();
		}
	}
	public void nextFrame()//变换到下一帧
	{
		currentFrame++;//帧数增加
		if(currentFrame>1)//一共两张图  0和1变换走
		{
			currentFrame=0;
		}
		if(judge==1)//如果判断值等于1
		{
			if(isAmbulance)
			{
				tempFigure.father.isFigure=10;//切换地图到医院
				tempFigure.startRow=8;
				tempFigure.startCol=0;
				tempFigure.offsetX=0;
				tempFigure.offsetY=0;
				tempFigure.row=12;
				tempFigure.col=6;
				tempFigure.x = 6*TILE_SIZE_X+TILE_SIZE_X/2+1;
				tempFigure.y = 12*TILE_SIZE_Y+TILE_SIZE_Y/2+1;
				tempFigure.direction=2;
				tempFigure.isWhere=0;
				//绘制对话框
				tempFigure.father.showDialog.meet=false;//没有带特殊人物
				tempFigure.father.showDialog.initMessage(tempFigure.figureName,tempFigure.k,true,false,DialogEnum.MESSAGE_ONE,5,-1);
				tempFigure.father.isDialog=true;//绘制对话框
			}else if(isPolice)
			{
				tempFigure.father.isFigure=11;//切换地图到监狱
				tempFigure.startRow=8;
				tempFigure.startCol=15;
				tempFigure.offsetX=0;
				tempFigure.offsetY=0;
				tempFigure.row=11;
				tempFigure.col=20;
				tempFigure.x = 20*TILE_SIZE_X+TILE_SIZE_X/2+1;
				tempFigure.y = 11*TILE_SIZE_Y+TILE_SIZE_Y/2+1;
				tempFigure.direction=2;
				tempFigure.isWhere=1;
				//绘制对话框
				tempFigure.father.showDialog.meet=false;//没有带特殊人物
				tempFigure.father.showDialog.initMessage(tempFigure.figureName,tempFigure.k,true,false,DialogEnum.MESSAGE_ONE,8,-1);
				tempFigure.father.isDialog=true;//绘制对话框
			}
		}
		if(judge==2)//如果判断值等于2
		{
			tempFigure.father.isFigure=nowFigure;//切换到当前英雄
			tempFigure.father.gvt.setChanging(true);//启动人物变换线程
			MagicHouseDrawable.police=null;
			pt.isGameOn=false;//停止换帧
			pt.flag=false;//停止执行run方法
			isPolice=false;//警车动画停止
			isAmbulance=false;//救护车动画停止
			pt=null;//线程引用置空
		}
	}
	class PoliceThread extends Thread{
		boolean flag;//线程的run方法是否执行的标志位
		boolean isGameOn;//是否进行换帧的标志位
		public PoliceThread(){
			super.setName("==Hero.HeroThread");
			flag = true;
		}
		public void run(){
			while(flag){
				while(isGameOn){
					try{
						nextFrame();//进行换帧操作
					}
					catch(Exception e){
						e.printStackTrace();
					}
					try{
						Thread.sleep(180);//换图片间隔0.18秒
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}
}
