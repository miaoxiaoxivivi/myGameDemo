package com.game.zillionaire.animation;

import java.io.Serializable;

import com.game.zillionaire.figure.CrashFigure;
import com.game.zillionaire.meetdrawable.MessageEnum;
import com.game.zillionaire.meetdrawable.PoliceCarAnimation;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class DogGodAnimation extends MeetableAnimation implements Serializable{
//恶狗
	private static final long serialVersionUID = 5340968457570204423L;
	public static String godPicName[]={"dog1","dog2","dog3","dog4"};
	public int k=0;//帧的索引
	GameView father;
	public MessageEnum me;//表示英雄类型
	int tempLength=0;//长度
	public static Bitmap[] bitmaps=new Bitmap[godPicName.length];
	DogGogAnimotionThread dgat;
	int x;
	int y;

	public DogGodAnimation(){}
	public DogGodAnimation(GameView gv)
	{
		super(gv);
		this.father=gv;
		if(dgat==null)
		{
			dgat=new DogGogAnimotionThread();
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
			canvas.drawBitmap(bitmaps[k],this.x, this.y, null);
		}
	}
	@Override
	public void setXAndY(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	@Override
	public  void setClass(MessageEnum me)
	{
		this.me=me;
	}
	@Override
	public void nextFrame() {
		k++;
		if(k>=godPicName.length)
		{
			dgat.isGameOn=false;//停止换帧
			dgat.flag=false;//停止执行run方法
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
			gv.aa.police=new PoliceCarAnimation(gv.figure0);//创建对象
			gv.aa.police.startAnimation();//启动救护车动画
			PoliceCarAnimation.isAmbulance=true;//设置标志位为true				
			gv.figure0.day=3;//住进医院3天
			gv.figure0.father.showDialog.day=3;//显示对话框3天
			
			recoverGame();
			dgat=null;//线程引用置空
		}
	}

	@Override
	public void startAnimation() {
		dgat.isGameOn=true;//标志位设为true
		if(!dgat.isAlive())//如果线程没有开启 则开启线程
		{
			dgat.start();
		}
	}
	public void recoverGame()//恢复游戏状态
	{
		k=0;
		isAngel=false;
		bitmapnum=-1;
		halfOrNot=-1;//恢复
		gv.isFigureMove=false;//不播放特殊人物动画
		gv.setCurrentDrawable(null);
		gv.setOnTouchListener(gv);//返还监听器
		gv.setStatus(0);//重新设置GameView为待命状态
	}
	class DogGogAnimotionThread extends Thread
	{
		boolean flag;//线程的run方法是否执行的标志位
		boolean isGameOn;//是否进行换帧的标志位
		public DogGogAnimotionThread()
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
