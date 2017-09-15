package com.game.zillionaire.meetdrawable;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.map.MyDrawable;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;

public class ShipAnimation implements Serializable{
	private static final long serialVersionUID = -3039069621912981819L;
	static Bitmap[][] bmpAnimationPic=new Bitmap[3][6];//加载18张飞船图片的数组
	static Bitmap[] bmpAnimation=new Bitmap[18];//加载18张飞船图片的数组
	static Bitmap[] demonPic=new Bitmap[14];//加载14张恶魔图片的数组
	public static boolean isShip = false;//飞船是否显示
	public static boolean isDemon=false;//恶魔动画是否播放
	ShipThread sh;//换帧线程
	int currentFrame=0;//现在的帧数
	int frameCount1=0;//飞船动画总帧数
	int frameCount2=0;//恶魔动画总帧数
	public MyDrawable drawable;
	Figure figure;
	public ShipAnimation(Figure figure){
		this.figure=figure;
		frameCount1=bmpAnimation.length;//飞船共18张
		frameCount2=demonPic.length;//恶魔共14张
		sh=new ShipThread();
	}
	public void drawSelf(Canvas canvas,int x,int y)//显示飞船
	{
		Bitmap temp=PicManager.getPic("ship",figure.father.activity.getResources());//加载飞船大图
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<6;j++)
			{
				bmpAnimationPic[i][j]=Bitmap.createBitmap(temp, 180*j, 253*i, 180, 253);//将整图拆成二维数组
			}
		}
		int k=0;//定义变量 用来自加
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<6;j++)
			{
				bmpAnimation[k]=bmpAnimationPic[i][j];//将二维数组赋值给一维数组
				k++;
			}
		}
		temp=PicManager.getPic("emcard",figure.father.activity.getResources());//加载恶魔整图
		for(int i=0;i<demonPic.length;i++)
		{
			demonPic[i]=Bitmap.createBitmap(temp, ConstantUtil.Width0[i], 0, 135, 160);//将整图拆成一维数组
		}
		temp=null;
		
		if(isShip)//如果显示飞船
		{
			if(currentFrame>=13)//画到最后一张动画时
			{
				x=x-120*(currentFrame-13);//x变化位置
				y=y-10*(currentFrame-13);//y变化位置
				canvas.drawBitmap(bmpAnimation[currentFrame], x, y, null);//相当于平移效果
			}else 
			{
				canvas.drawBitmap(bmpAnimation[currentFrame], x, y, null);//画飞船动画
				if(currentFrame==7)
				{
					figure.isDreaw=false;
					figure.day=3;
					figure.isWhere=4;
				}
			}
		}
		if(isDemon)//如果显示恶魔
		{
			canvas.drawBitmap(demonPic[currentFrame], x, y, null);//画恶魔动画
		}
	}
	public void startAnimation()//开始动画
	{
		sh.isGameOn=true;//标志位设为true
		if(!sh.isAlive())//如果线程没有开启 则开启线程
		{
			sh.start();
		}
	}
	public void nextFrame()//帧数增加
	{
		currentFrame++;
		if(isShip)//如果显示飞船
		{
			if(currentFrame>=frameCount1)//如果当前帧数大于总帧数
			{
				AccidentDrawable.figures.father.gvt.setChanging(true);//启动变换人物线程
				sh.flag=false;//标志位设为false
				sh.isGameOn=false;//换帧标志位设为false
				isShip=false;//画飞船的标志位设为false
				sh=null;//引用置空
			}
		}else if(isDemon)//如果显示恶魔
		{
			if(currentFrame>=frameCount2)//如果当前帧数大于总帧数
			{
				drawable.k=0;
				drawable.bpName=GroundDrawable.bitmaps[drawable.kk][drawable.k];
				sh.flag=false;//标志位设为false
				sh.isGameOn=false;//换帧标志位设为false
				isDemon=false;//画飞船的标志位设为false
				sh=null;//引用置空
			}
		}
	}
	class ShipThread extends Thread{
		boolean flag;//线程的run方法是否执行的标志位
		boolean isGameOn;//是否进行换帧的标志位
		public ShipThread(){
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
					}
					try{
						if(isShip)//飞船显示的间隔时间
						{
							Thread.sleep(180);//换图片间隔0.18秒
						}
						if(isDemon)//恶魔显示的间隔时间
						{
							Thread.sleep(300);//换图片间隔0.3秒
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}
}


