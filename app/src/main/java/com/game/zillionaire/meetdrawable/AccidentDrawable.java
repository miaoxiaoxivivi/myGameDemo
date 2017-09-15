package com.game.zillionaire.meetdrawable;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.game.zillionaire.activity.ZActivity;
import com.game.zillionaire.card.MonsterCard;
import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.map.MyMeetableDrawable;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.view.GameView;

public class AccidentDrawable extends MyMeetableDrawable implements Serializable{
	
	private static final long serialVersionUID = 12012776437921062L;
	static String[] accidentName={"fortune01","fortune02","fortune03","fortune04","fortune05",
		"fortune06","fortune07","fortune08","fortune09","fortune10","fortune11","fortune12","fortune13"};//机遇图片ID
	static Bitmap[] accident=new Bitmap[accidentName.length];//加载机遇图片
	boolean isOnTouch=false;//是否触摸
	public static ShipAnimation ships;//飞船类
	public static Figure figures;//英雄 用来播放动画类调用
	int nowMoney=0;
	public AccidentDrawable(){}
	public AccidentDrawable(ZActivity at,String bmpSelf,String dbitmap,String bmpDialogBack,boolean meetable,int width,int height,int col,int row,
			int refCol,int refRow,int [][] noThrough,int [][] meetableMatrix,int da) {
		super(at,bmpSelf,dbitmap, col, row, width, height, refCol, refRow, noThrough, meetable,
				meetableMatrix, da,bmpDialogBack);
	}
	@Override
	public void drawDialog(Canvas canvas, Figure figure) {
		tempFigure=figure;
		figures=figure;
		for(int i=0;i<accident.length;i++)
		{
			accident[i]=PicManager.getPic(accidentName[i],tempFigure.father.activity.getResources());
		}
		if(figure.isDreaw)
		{
			canvas.drawBitmap(accident[accidentRandom], 250, 90, null);//随机画卡片
			isOnTouch=true;//允许触摸
		}
	}
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			if(isOnTouch)//允许触摸
			{
				try{
					Thread.sleep(500);
					recoverGame();//返回游戏
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				switch(accidentRandom)//随机数
				{
				case 0://当前英雄存款减少10%
					nowMoney=(int) (tempFigure.mhz.xMoney*0.1);
					tempFigure.mhz.xMoney-=nowMoney;
					tempFigure.mhz.zMoney-=nowMoney;
					break;
				case 1://随机获得三张卡片
					int countFlag=0;
					for(int i=0;i<tempFigure.CardNum.length;i++)
					{
						if(tempFigure.CardNum[i]==-1)
						{
							tempFigure.CardNum[i]=(int)Math.random()*21;
							countFlag++;
							if(countFlag==3)
								break;
						}		
					}
					break;
				case 2://当前英雄存款加200
					nowMoney=200;
					tempFigure.mhz.xMoney+=nowMoney;
					tempFigure.mhz.zMoney+=nowMoney;
					break;
				case 3://当前英雄存款减少1800
					nowMoney=1800;
					tempFigure.mhz.xMoney-=nowMoney;
					tempFigure.mhz.zMoney-=nowMoney;
					break;
				case 4://当前英雄存款加10000
					nowMoney=10000;
					tempFigure.mhz.xMoney+=nowMoney;
					tempFigure.mhz.zMoney+=nowMoney;
					break;
				case 5://当前英雄消失三天,不参与置骰子
					ships=new ShipAnimation(tempFigure);
					ships.startAnimation();//开始动画
					ShipAnimation.isShip=true;
					break;
				case 6://当前英雄存款加5000
					nowMoney=5000;
					tempFigure.mhz.xMoney+=nowMoney;
					tempFigure.mhz.zMoney+=nowMoney;
					break;
				case 7://当前英雄存款加600
					nowMoney=600;
					tempFigure.mhz.xMoney+=nowMoney;
					tempFigure.mhz.zMoney+=nowMoney;
					break;
				case 8://当前英雄存款减半
					nowMoney=(int)(tempFigure.mhz.xMoney/2);
					tempFigure.mhz.xMoney-=nowMoney;
					tempFigure.mhz.zMoney-=nowMoney;
					break;
				case 9://当前英雄存款减少5000
					nowMoney=5000;
					tempFigure.mhz.xMoney-=nowMoney;
					tempFigure.mhz.zMoney-=nowMoney;
					break;
				case 10://当前英雄存款减少1500
					nowMoney=1500;
					tempFigure.mhz.xMoney-=nowMoney;
					tempFigure.mhz.zMoney-=nowMoney;
					break;
				case 11://银行不允许存款、取款、贷款10天
					BankDrawable.isWork=false;
					BankDrawable.existDay=10;
					tempFigure.father.gvt.setChanging(true);//启动变换人物的线程
					break;
				case 12://当前英雄存款减少12000
					nowMoney=12000;
					tempFigure.mhz.xMoney-=nowMoney;
					tempFigure.mhz.zMoney-=nowMoney;
					break;
				}
			}
		}
		return true;
	}
	public void recoverGame()//恢复游戏状态
	{
		isOnTouch=false;
		tempFigure.father.setCurrentDrawable(null);//置空记录引用的变量
		tempFigure.father.setOnTouchListener(tempFigure.father);//返还监听器
		tempFigure.father.setStatus(0);//重新设置GameView为待命状态
		if(accidentRandom!=5)
		{
			tempFigure.father.gvt.setChanging(true);//启动变换人物的线程
		}
	}
	//画飞船
	public void drawShip(Canvas canvas,GameView gameView,Figure figure,Figure figure1,Figure figure2)
	{
		Figure nowFigure=null;
		if(gameView.isFigure==0)//如果当前是操作人物
		{
			nowFigure=figure;
		}else if(gameView.isFigure==1)//如果当前是系统人物1
		{
			nowFigure=figure1;
		}else if(gameView.isFigure==2)//如果当前是系统人物2
		{
			nowFigure=figure2;
		}
		if(ShipAnimation.isShip)//如果卡片上显示飞船图片
		{
			if(nowFigure!=null)
			{
				ships.drawSelf(canvas, nowFigure.topLeftCornerX-70,nowFigure.topLeftCornerY-140);//画飞船
			}
		}
		if(ShipAnimation.isDemon)//如果画恶魔动画
		{
			MonsterCard.ship.drawSelf(canvas, gameView.useCard.cd.x-30, gameView.useCard.cd.y-70);//画恶魔
		}
	}
}
