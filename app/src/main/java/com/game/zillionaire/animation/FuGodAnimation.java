package com.game.zillionaire.animation;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.game.zillionaire.figure.CrashFigure;
import com.game.zillionaire.meetdrawable.MessageEnum;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.view.GameView;

public class FuGodAnimation extends MeetableAnimation implements Serializable
{//大福神
	private static final long serialVersionUID = -4001727174185949135L;//大福神
	public static String godPicName[][]={{"luckyc0","luckyc1","luckyc2","luckyc3","luckyc4"},
		   {"luckyf1","luckyf2","luckyf3","luckyf4","luckyf5"}};//小福神
	public int k=0;//帧的索引
	BigFuGodAnimotionThread bfgt;//动画播放线程
	GameView father;
	public MessageEnum me;//表示英雄类型
	int tempLength=0;//长度
	public static Bitmap[] bitmapsBig=new Bitmap[godPicName[0].length];
	public static Bitmap[] bitmapsSmall=new Bitmap[godPicName[1].length];
	
	public FuGodAnimation(){}
	public FuGodAnimation(GameView gv)
	{
		super(gv);
		this.father=gv;
		if(bfgt==null)
		{
			bfgt=new BigFuGodAnimotionThread();
		}
		
	}

	@Override
	public void setClass(MessageEnum me) {
		this.me=me;//设置碰撞到的神明的类型
	}
	@Override
	public void drawGod(Canvas canvas) {
		for(int i=0;i<bitmapsBig.length;i++)
		{//大福神图片
			bitmapsBig[i]=PicManager.getPic(godPicName[0][i],father.activity.getResources());
		}
		for(int i=0;i<bitmapsSmall.length;i++)
		{//小福神图片
			bitmapsSmall[i]=PicManager.getPic(godPicName[1][i],father.activity.getResources());
		}
		if(isAngel)
		{
			if(bitmapnum==1)
			{//大福神
				canvas.drawBitmap(bitmapsBig[k],ConstantUtil.SCREEN_HEIGHT/2-150, ConstantUtil.SCREEN_WIDTH/2-100, null);
			}
			else if(bitmapnum==5)
			{//小福神
				canvas.drawBitmap(bitmapsSmall[k],ConstantUtil.SCREEN_HEIGHT/2-170, ConstantUtil.SCREEN_WIDTH/2-100, null);
			}
		}
	}
	@Override
	public void nextFrame() {//幻阵		
		if(bitmapnum==1)
		{
			tempLength=godPicName[0].length;
		}
		else if(bitmapnum==5)
		{
			tempLength=godPicName[1].length;
		}
		k++;
		if(k==(tempLength-1))
		{
			try{
				Thread.sleep(2500);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(k>=tempLength)
		{
			bfgt.isGameOn=false;//停止换帧
			bfgt.flag=false;//停止执行run方法
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
			bfgt=null;//线程引用置空
		}
	}
	
	@Override
	public void startAnimation() {//开始动画
		bfgt.isGameOn=true;//标志位设为true
		if(!bfgt.isAlive())//如果线程没有开启 则开启线程
		{
			bfgt.start();
		}
	}
	public void recoverGame()//恢复游戏状态
	{
		k=0;
		isAngel=false;
		bitmapnum=-1;
		halfOrNot=-1;//恢复
		gv.isFigureMove=false;//不播放特殊人物动画
		if(gv.currentDrawable==null)
		{
			gv.setOnTouchListener(gv);//返还监听器
			gv.setStatus(0);//重新设置GameView为待命状态
			gv.gvt.setChanging(true);//启动变换人物的线程
		}
	}
	class BigFuGodAnimotionThread extends Thread
	{
		boolean flag;//线程的run方法是否执行的标志位
		boolean isGameOn;//是否进行换帧的标志位
		public BigFuGodAnimotionThread()
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
