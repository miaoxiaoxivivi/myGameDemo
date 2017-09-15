package com.game.zillionaire.animation;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.game.zillionaire.figure.CrashFigure;
import com.game.zillionaire.meetdrawable.MessageEnum;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.view.GameView;

public class BigEmGodAnimation extends MeetableAnimation implements Serializable{
//大恶魔
	private static final long serialVersionUID = -5873862228163260720L;
	public static String godPicName[]={"bade3","bade4","bade5"};
	BigEmGodAnimotionThread begat;
	public int k=0;//帧的索引
	GameView father;
	public MessageEnum me;//表示英雄类型
	int tempLength=0;//长度
	public static Bitmap[] bitmaps=new Bitmap[godPicName.length];
	public BigEmGodAnimation(){}
	public BigEmGodAnimation(GameView gv)
	{
		super(gv);
		this.father=gv;
		if(begat==null)
		{
			begat=new BigEmGodAnimotionThread();
		}		
	}
	@Override
	public void drawGod(Canvas canvas) {
		for(int i=0;i<godPicName.length;i++)
		{//初始化图片资源
			bitmaps[i]=PicManager.getPic(godPicName[i],father.activity.getResources());
		}
		if(isAngel)
		{
			canvas.drawBitmap(bitmaps[k],ConstantUtil.SCREEN_HEIGHT/2-150, ConstantUtil.SCREEN_WIDTH/2-100, null);
		}
	}
	@Override
	public void nextFrame() {
		k++;
		if(k==(godPicName.length-1))
		{
			try{
				Thread.sleep(1800);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(k>=godPicName.length)
		{
			begat.isGameOn=false;//停止换帧
			begat.flag=false;//停止执行run方法
			isAngel=false;//停止播放帧
			switch(me)
			{//返回英雄类型：玩家操控还是系统控制
				case Artificial_Control:
					CrashFigure.isMeet1=true;
					break;
				case System_Control_1:
					CrashFigure.isMeet0=true;
					break;
				case System_Control_2:
					CrashFigure.isMeet=true;
					break;
			}			
			recoverGame();
			begat=null;//线程引用置空
		}
	}
	@Override
	public  void setClass(MessageEnum me)
	{
		this.me=me;
	}
	@Override
	public void startAnimation() {
		begat.isGameOn=true;//标志位设为true
		if(!begat.isAlive())//如果线程没有开启 则开启线程
		{
			begat.start();
		}
	}
	public void recoverGame()//恢复游戏状态
	{
		k=0;
		isAngel=false;
		bitmapnum=-1;
		halfOrNot=-1;//恢复初始值
		gv.isFigureMove=false;//不播放特殊人物动画
		if(gv.currentDrawable==null)
		{
			gv.setOnTouchListener(gv);//返还监听器
			gv.setStatus(0);//重新设置GameView为待命状态
			gv.gvt.setChanging(true);//启动变换人物的线程
		}
	}
	class BigEmGodAnimotionThread extends Thread
	{
		boolean flag;//线程的run方法是否执行的标志位
		boolean isGameOn;//是否进行换帧的标志位
		public BigEmGodAnimotionThread()
		{
			flag = true;
		}	
		public void run(){
			while(flag){
				while(isGameOn){
					try{						
						nextFrame();									
					}
					catch(Exception e){
						e.printStackTrace();
					}
					try{
						Thread.sleep(400);//换图片间隔0.4秒
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}
}
