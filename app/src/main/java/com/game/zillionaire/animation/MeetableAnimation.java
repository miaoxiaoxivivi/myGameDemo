package com.game.zillionaire.animation;

import java.io.Serializable;
import android.graphics.Canvas;

import com.game.zillionaire.meetdrawable.MessageEnum;
import com.game.zillionaire.meetdrawable.PoliceCarAnimation;
import com.game.zillionaire.view.GameView;

public abstract class MeetableAnimation implements Serializable
{//动画
	private static final long serialVersionUID = -18249185294210194L;
	GameView gv;
	MessageEnum me;
	int x;//神明坐标
	int y;
	public boolean isAngel=false;//是否播放帧动画
	public int bitmapnum=-1;//表示神明类别
	public int halfOrNot=-1;//在财神类中代表的意义：0表示付全部的金额，1表示付一半的金额，
	//2表示多付一半的金额，3表示多付一倍的金额,4表示不交过路费
	public PoliceCarAnimation police=null;
	
	public MeetableAnimation(){}
	public MeetableAnimation(GameView gv)
	{
		this.gv=gv;
	}

	public  void setClass(MessageEnum me)
	{
		this.me=me;
	}
	public void setXAndY(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	public void setBitmapnum(int id)//记录神明编号
	{
		bitmapnum=id;
	}
	
	public abstract void drawGod(Canvas canvas);//绘制方法
	
	public abstract void nextFrame();//换帧
	
	public abstract void startAnimation();//开始动画
	
}
