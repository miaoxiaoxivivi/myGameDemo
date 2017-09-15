package com.game.zillionaire.meetdrawable;

import static com.game.zillionaire.util.ConstantUtil.DIALOG_WORD_SIZE;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.view.GameView;

public class BankLiXi implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1484871196215414048L;
	GameView father;
	public static Bitmap[] lx=new Bitmap[3];
	int  iscount=-1;//计数器
	int status=0;//图片索引
	int strIndex=0;//字符串数组索引
	Figure tempFigure=null;//记录冠军
	public int count=0;//计数---------每次进入本界面count加1
	static Bitmap[] headBitmaps=new Bitmap[3];//3个人物的头像图
	public String[] dialogMessage=
	{
		"各位客户辛苦了！又到了每月银行结算的日子。",
		"大富翁银行将根据您的存款，加发10%的利息。",
		"本月冠军是......",
		"孙小美","金贝贝","阿土伯",
		"其他人还要更努力喔！"
	};
	public BankLiXi(GameView father)
	{
		this.father=father;
		this.father.figure.mhz.lixi=(int) (this.father.figure.mhz.cMoney*0.1);
		this.father.figure1.mhz.lixi=(int) (this.father.figure1.mhz.cMoney*0.1);
		this.father.figure2.mhz.lixi=(int) (this.father.figure2.mhz.cMoney*0.1);
		
		dialogMessage[3]=this.father.figure.figureName;
		dialogMessage[4]=this.father.figure1.figureName;
		dialogMessage[5]=this.father.figure2.figureName;
	}
	//绘制背景以及字符串
	public void drawDialog(Canvas canvas)
	{
		lx[0]=PicManager.getPic("lx0",father.activity.getResources());//加载图片
		lx[1]=PicManager.getPic("lx1",father.activity.getResources());
		lx[2]=PicManager.getPic("lx2",father.activity.getResources());
		
		headBitmaps[0]=PicManager.getPic(this.father.figure.headBitmapName, this.father.activity.getResources()); //加载人物头像
		headBitmaps[1]=PicManager.getPic(this.father.figure1.headBitmapName, this.father.activity.getResources()); //加载人物头像
		headBitmaps[2]=PicManager.getPic(this.father.figure2.headBitmapName, this.father.activity.getResources()); //加载人物头像
		if(count==0)
		{//第一次绘制
			iscount++;//计数，折算成睡眠时间=iscount*100
			if(iscount<60)
			{
				father.isDraw=true;
				father.isYuanBao=false;
				father.isMenu=false;
				
				canvas.drawBitmap(lx[status], 0, 0, null);			
				drawString0(canvas,dialogMessage[strIndex],status);
				if((iscount==20||iscount==50)&&status<2)
				{
					status++;
					if(strIndex==2)
					{
						if(this.father.figure.mhz.cMoney>this.father.figure1.mhz.cMoney
						   &&this.father.figure.mhz.cMoney>this.father.figure2.mhz.cMoney)
						{
							strIndex=2;
							tempFigure=this.father.figure;
						}
						else if(this.father.figure1.mhz.cMoney>this.father.figure2.mhz.cMoney
						   &&this.father.figure1.mhz.cMoney>this.father.figure2.mhz.cMoney)
						{
							strIndex=3;
							tempFigure=this.father.figure1;
						}
						else if( this.father.figure2.mhz.cMoney>this.father.figure.mhz.cMoney
								   &&this.father.figure2.mhz.cMoney>this.father.figure.mhz.cMoney)
						{
							strIndex=4;
							tempFigure=this.father.figure2;
						}
						else
						{
							tempFigure=this.father.figure;
						}
						tempFigure.mhz.cMoney+=tempFigure.mhz.lixi;
						tempFigure.mhz.zMoney+=tempFigure.mhz.lixi;
					}
					strIndex++;
				}			
				if(status==0&&strIndex<2&&iscount==10){
					strIndex++;			
				}
				if(status==0)
				{
					Paint paint=initPaint();
					canvas.drawText(this.father.figure.mhz.cMoney+"", 268, 220, paint);//绘制存款
					canvas.drawText(this.father.figure.mhz.lixi+"", 268, 277, paint);//绘制利息
					
					canvas.drawText(this.father.figure1.mhz.cMoney+"", 625, 220, paint);//绘制存款
					canvas.drawText(this.father.figure1.mhz.lixi+"", 625, 277, paint);//绘制利息
					
					canvas.drawText(this.father.figure2.mhz.cMoney+"", 268, 372, paint);//绘制存款
					canvas.drawText(this.father.figure2.mhz.lixi+"", 268, 425, paint);//绘制利息
				}
				else if(status==2)
				{
					Paint paint=initPaint();
					canvas.drawText(tempFigure.mhz.xMoney+"", 330, 215, paint);//绘制现金
					canvas.drawText(tempFigure.mhz.cMoney+"", 330, 245, paint);//绘制存款
					canvas.drawText(tempFigure.mhz.zMoney+"", 330, 280, paint);//绘制总资产
					if(tempFigure.Bitmapindext==0)
					{//绘制获奖人物头像
						canvas.drawBitmap(headBitmaps[0], 550,156, paint);//获奖人物头像
						canvas.drawBitmap(headBitmaps[1], 233,365, paint);//无获奖人物1头像
						canvas.drawBitmap(headBitmaps[2], 343,365, paint);//无获奖人物2头像
					}
					else if(tempFigure.Bitmapindext==1)
					{//绘制获奖人物头像
						canvas.drawBitmap(headBitmaps[1], 550,156, paint);//人物头像
						canvas.drawBitmap(headBitmaps[0], 233,365, paint);//无获奖人物1头像
						canvas.drawBitmap(headBitmaps[2], 343,365, paint);//无获奖人物2头像
					}
					else if(tempFigure.Bitmapindext==2)
					{//绘制获奖人物头像
						canvas.drawBitmap(headBitmaps[2], 550,156, paint);//人物头像
						canvas.drawBitmap(headBitmaps[0], 233,365, paint);//无获奖人物1头像
						canvas.drawBitmap(headBitmaps[1], 343,365, paint);//无获奖人物2头像
					}
				}
			}
			else
			{
				count++;
				recoverGame();//恢复游戏
			}
		}	
		else if(this.father.date!=28)
		{
			count=0;
		}
	}
	public Paint initPaint()
	{
		Paint paint=new Paint();
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setTextSize(19);//设置文字大小
		paint.setFakeBoldText(true);//字体加粗
		return paint;
	}
	//方法：绘制字符串
	public void drawString0(Canvas canvas,String string,int id)
	{
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setTextSize(19);//设置文字大小
		paint.setFakeBoldText(true);//字体加粗
		int num=17;
		int lines = string.length()/num+(string.length()%num==0?0:1);//求出需要画几行文字
		for(int i=0;i<lines;i++)
		{
			String str="";
			if(i == lines-1)
			{//如果是最后一行那个不太整的汉字
				str = string.substring(i*10);
			}
			else
			{
				str = string.substring(i*10, (i+1)*10);
			}
			if(id==0)
			{
				canvas.drawText(str,300,100+DIALOG_WORD_SIZE*i,paint);
			}
			else
			{
				canvas.drawText(str,235,60+DIALOG_WORD_SIZE*i,paint);
			}			
		}
	}
	
	//方法：返还监听器，回复游戏状态
	public void recoverGame()
	{
		this.father.setOnTouchListener(this.father);//返还监听器
		father.setCurrentDrawable(null);//置空记录引用的变量
		this.father.setStatus(0);//重新设置GameView为待命状态
		
		iscount=-1;//计数器
		status=0;//图片索引
		strIndex=0;//字符串数组索引恢复
		tempFigure=null;
		
		father.isDraw=false;
		father.isYuanBao=true;
		father.isMenu=true;
	}
}
