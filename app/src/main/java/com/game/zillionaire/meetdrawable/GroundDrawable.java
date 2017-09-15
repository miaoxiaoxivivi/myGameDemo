package com.game.zillionaire.meetdrawable;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import com.game.zillionaire.activity.ZActivity;
import com.game.zillionaire.dialog.DialogEnum;
import com.game.zillionaire.figure.CrashFigure;
import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.map.MyMeetableDrawable;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.util.ScreenTransUtil;

public class GroundDrawable extends MyMeetableDrawable implements Serializable
{
	String dialogMessage[] = {//对话框的提示信息
			"1,500|茶晶",
			"1,000|蓝宝",
			"800|翡翠",
			"2,500|黑耀",
			"1,200|粉晶",
			"600|紫晶"
	};
	String dialogMessage1[] = {//对话框1的提示信息
			"公园。公共开放设施，不向使用者收取任何费用，也不能再升级。",
			"加油站。无论是否有交通工具，皆须依照行走步数强制购买汽油。",
			"研究所。不收取过路费，每升一级可多开发一种道具。",
			"购物中心。必须以轮盘决定消费金额。",
			"旅馆。必须以轮盘决定消费金额及休息天数。"
	};
	String dialogMessage2[] = {//对话框2的提示信息
			"机器人",
			"遥控骰子",
			"传送带",
			"工程车",
			"核子飞弹"
	};
	public static String[][] bitmaps=new String[3][6];
	public static String[] bitmap=new String[3];//各个大房子的初始图片
	static Bitmap bmpDialogBacks[]=new Bitmap[6];//背景图
	static Bitmap bmpDialogBacks1;
	
	int t=0;//背景图片的标志位
	public static Bitmap[] bitmap1=new Bitmap[5];
	public static String[][] bitmap2=new String[5][];//各个房子图片
	private boolean isJS=true;//第一次绘制是计算
	boolean isGet=false;//判断资金是否够
	//绘制选中图片时的坐标
	private int x=101;
	private int y=213;
	private int count=0;
	private int pp=0;//信息索引
	public static int godId=-1;//特殊人物的id
	public Bitmap dialogBack;
	
	public GroundDrawable(){}
	public GroundDrawable(ZActivity at,String bmpSelf,String dbitmap,String bmpDialogBack,boolean meetable,int width,int height,int col,int row,
			int refCol,int refRow,int [][] noThrough,int [][] meetableMatrix,int da) {
		super(at,bmpSelf,dbitmap, col, row, width, height, refCol, refRow, noThrough, meetable,
				meetableMatrix, da,bmpDialogBack);
	}
	@Override
	public void drawDialog(Canvas canvas, Figure figure) {
		tempFigure = figure;
		bmpDialogBacks[0]=PicManager.getPic("sgd",tempFigure.father.activity.getResources()); //一般背景图
		bmpDialogBacks[1]=PicManager.getPic("lou1",tempFigure.father.activity.getResources()); //背景图1
		bmpDialogBacks[2]=PicManager.getPic("lou2",tempFigure.father.activity.getResources()); //背景图2
		bmpDialogBacks[3]=PicManager.getPic("lou3",tempFigure.father.activity.getResources()); //背景图3
		bmpDialogBacks[4]=PicManager.getPic("lou4",tempFigure.father.activity.getResources()); //背景图4
		bmpDialogBacks[5]=PicManager.getPic("lou4",tempFigure.father.activity.getResources()); //背景图5
		bmpDialogBacks1=PicManager.getPic("droom",tempFigure.father.activity.getResources());
		
		bitmap1[0]=PicManager.getPic("droom0",tempFigure.father.activity.getResources());
		bitmap1[1]=PicManager.getPic("droom1",tempFigure.father.activity.getResources());
		bitmap1[2]=PicManager.getPic("droom2",tempFigure.father.activity.getResources());
		bitmap1[3]=PicManager.getPic("droom3",tempFigure.father.activity.getResources());
		bitmap1[4]=PicManager.getPic("droom4",tempFigure.father.activity.getResources());
		
		bitmaps[0][0]="sxm0";
		bitmaps[0][1]="sxm1";
		bitmaps[0][2]="sxm2";
		bitmaps[0][3]="sxm3";
		bitmaps[0][4]="sxm4";
		bitmaps[0][5]="sxm5";
		
		bitmaps[1][0]="qfr0";
		bitmaps[1][1]="qfr1";
		bitmaps[1][2]="qfr2";
		bitmaps[1][3]="qfr3";
		bitmaps[1][4]="qfr4";
		bitmaps[1][5]="qfr5";
		
		bitmaps[2][0]="atb0";
		bitmaps[2][1]="atb1";
		bitmaps[2][2]="atb2";
		bitmaps[2][3]="atb3";
		bitmaps[2][4]="atb4";
		bitmaps[2][5]="atb5";

	    bitmap[0]="sxmb"; //孙小美的房子大图1
	    bitmap[1]="qfrb"; //钱夫人的房子大图1
	    bitmap[2]="atbb"; //阿土伯的房子大图1
	    
	    bitmap2[0]=new String[1];
		bitmap2[0][0]="dpark";//公园
		bitmap2[1]=new String[1];
		bitmap2[1][0]="jiayouzhan";//加气站
		//研究院
		bitmap2[2]=new String[5];
		bitmap2[2][0]="yjy1";
		bitmap2[2][1]="yjy2";
		bitmap2[2][2]="yjy3";
		bitmap2[2][3]="yjy4";
		bitmap2[2][4]="yjy5";
		//购物中心
		bitmap2[3]=new String[5];
		bitmap2[3][0]="dshop1";
		bitmap2[3][1]="dshop2";
		bitmap2[3][2]="dshop3";
		bitmap2[3][3]="dshop4";
		bitmap2[3][4]="dshop5";
		//旅馆
		bitmap2[4]=new String[5];
		bitmap2[4][0]="hotel1";
		bitmap2[4][1]="hotel2";
		bitmap2[4][2]="hotel3";
		bitmap2[4][3]="hotel4";
		bitmap2[4][4]="hotel5";
		
		pp=setDialogMessage();
		if(kk==0)//绘制操作人物的购买土地对话框
		{
			if(flagg)//小土地对话框
			{
				drawDialog0(canvas);
			}else//大土地对话框
			{
				drawDialog1(canvas);
			}
		}else//系统人物购买土地
		{
			String showString=dialogMessage[pp];
			isGD(showString);
			if(isGet)//可以购买土地
			{
				isJS=false;
				addRoom();//加盖房子
				value+=tempFigure.mhz.result/2;//过路费增加
				if(tempFigure.father.aa!=null)//如果有神明
				{
					GodGround();
		    	}else//没有神明
		    	{
		    		tempFigure.mhz.cutMoney(0);//现金减少
		    	}
				recoverGame();
			}else
			{
				if(count>=8)
				{
					count=0;
					recoverGame();
				}
				//先画背景
				dialogBack=PicManager.getPic(bmpDialogBack, tempFigure.father.activity.getResources());
				canvas.drawBitmap(dialogBack, 200, 60, null);
				Paint paint=initPaint();
				paint.setTextSize(26);//设置文字大小
				canvas.drawText("资金不足，不能购买该土地",240,130,paint);
				if(k<0)//土地还原
				{
					this.ss=-1;
					this.kk=-1;
				}
				count++;
			}
		}
	}
	public Paint initPaint()//设置字体画笔
	{
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setFakeBoldText(true);//字体加粗
		return paint;
	}
	public void drawDialog0(Canvas canvas) 
	{
		String showString = null;//需要显示到对话框中的字符串
		showString=dialogMessage[pp];
		isGD(showString);
		if(isGet)//现金够时,才让购买土地
		{
			//先画背景
			canvas.drawBitmap(bmpDialogBacks[k+1], 200, 60, null);
			drawString0(canvas, showString);
			isJS=false;
		}else
		{
			//先画背景
			while(dialogBack==null)
			{
				dialogBack=PicManager.getPic(bmpDialogBack, tempFigure.father.activity.getResources());
			}
			canvas.drawBitmap(dialogBack, 200, 60, null);
			Paint paint=initPaint();
			paint.setTextSize(26);//设置文字大小
			canvas.drawText("资金不足，不能购买该土地",240,130,paint);
			if(k<0)//土地还原
			{
				this.ss=-1;
				this.kk=-1;
			}
			recoverGame();
		}
	}
	public void isGD(String showString)
	{
		if(isJS)
		{
			String[] message=showString.split("\\|");
			isGet=tempFigure.mhz.isCut(message[0]);
		}
	}
	//绘制给定的字符串到对话框1上
	public void drawString0(Canvas canvas,String string)
	{
		Paint paint=initPaint();
		paint.setTextSize(22);//设置文字大小
		String[] message=string.split("\\|");
		canvas.drawText(message[0],490,200,paint);
		canvas.drawText(message[1],530,280,paint);
	}
	public void drawDialog1(Canvas canvas) 
	{
		String showString = null;//需要显示到对话框中的字符串
		//先画背景
		canvas.drawBitmap(bmpDialogBacks1, 80, 60, null);
		canvas.drawBitmap(bitmap1[zb], x, y,null);
		showString=dialogMessage1[zb];
		drawString1(canvas, showString);
	}
	//绘制给定的字符串到对话框上
	public void drawString1(Canvas canvas,String string)
	{
		Paint paint=initPaint();
		paint.setTextSize(22);//设置文字大小
		canvas.drawText(string,138,170,paint);
	}
	
	public int setDialogMessage()//土地所在的地区标志
	{
		if(this.col>=7&&this.col<=13&&this.row>=5&&this.row<=8)//茶晶地区-1500-(7-13)(5-8)
		{
			pp=0;
		}else if(this.col>=15&&this.col<=23&&this.row>=5&&this.row<=10)//蓝宝地区-1000-(15-23)(5-10)
		{
			pp=1;
		}else if(this.col>=8&&this.col<=16&&this.row>=11&&this.row<=16)//翡翠地区-800-(8-16)(11-16)
		{
			pp=2;
		}else if(this.col>=6&&this.col<=13&&this.row>=18&&this.row<=24)//黑耀-2500-(6-13)(18-24)
		{
			pp=3;
		}else if(this.col>=17&&this.col<=23&&this.row>=17&&this.row<=23)//粉晶-1200-(17-23)(17-23)
		{
			pp=4;
		}else if(this.col==24&&this.row>=12&&this.row<=15)//紫晶-600-(24)(12-15)
		{
			pp=5;
		}
		return pp;
	}
	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		int x=ScreenTransUtil.xFromRealToNorm((int)event.getX());
		int y=ScreenTransUtil.yFromRealToNorm((int)event.getY());
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			if(flagg)
			{
				if(x>=ConstantUtil.GroundDrawable_NoBuyGround_Left_X&&x<=ConstantUtil.GroundDrawable_NoBuyGround_Right_X
						&&y>=ConstantUtil.GroundDrawable_NoBuyGround_Up_Y&&y<=ConstantUtil.GroundDrawable_NoBuyGround_Down_Y)//不购买土地
				{
					if(this.k<0)
					{
						//土地标志返原
						this.kk=-1;
						this.ss=-1;
					}
				    recoverGame();
			    }else if(x>=ConstantUtil.GroundDrawable_BuyGround_Left_X&&x<=ConstantUtil.GroundDrawable_BuyGround_Right_X
			    		&&y>=ConstantUtil.GroundDrawable_BuyGround_Up_Y&&y<=ConstantUtil.GroundDrawable_BuyGround_Down_Y)//购买土地
			    {
			    	addRoom();
			    	value+=tempFigure.mhz.result/2;//过路费增加
			    	if(CrashFigure.isMeet1)//头上戴有特殊人物
			    	{
			    		GodGround();
			    	}else
			    	{
			    		tempFigure.mhz.cutMoney(0);//现金减少
			    	}
			    	recoverGame();
			    }
			}else//建筑其他房子
			{
				if(x>=ConstantUtil.GroundDrawable_BuildHouse_Left_X&&x<=ConstantUtil.GroundDrawable_BuildHouse_Right_X
						&&y>=ConstantUtil.GroundDrawable_BuildHouse_Up_Y&&y<=ConstantUtil.GroundDrawable_BuildHouse_Down_Y)//建房子
				{
					k++;
					this.bpName=bitmap[kk];
					this.dbpName=bitmap2[zb][k];
					this.flagg=true;
					this.first=false;
					recoverGame();
				}else if(x>=ConstantUtil.GroundDrawable_NoBuildHouse_Left_X&&x<=ConstantUtil.GroundDrawable_NoBuildHouse_Right_X
						&&y>=ConstantUtil.GroundDrawable_NoBuildHouse_Up_Y&&y<=ConstantUtil.GroundDrawable_NoBuildHouse_Down_Y)//不建房子
				{
					//土地标志返原
					recoverGame();
				}else if(x>=ConstantUtil.GroundDrawable_BuildPark_Left_X&&x<=ConstantUtil.GroundDrawable_BuildPark_Right_X
						&&y>=ConstantUtil.GroundDrawable_BuildThings_Up_Y&&y<=ConstantUtil.GroundDrawable_BuildThings_Down_Y)//建筑公园
				{
					this.x=101;
					this.zb=0;
				}else if(x>=ConstantUtil.GroundDrawable_BuildJiaqizhan_Left_X&&x<=ConstantUtil.GroundDrawable_BuildJiaqizhan_Right_X
						&&y>=ConstantUtil.GroundDrawable_BuildThings_Up_Y&&y<=ConstantUtil.GroundDrawable_BuildThings_Down_Y)//建筑加气站
				{
					this.x=229;
					this.zb=1;
				}else if(x>=ConstantUtil.GroundDrawable_BuildKeyanshi_Left_X&&x<=ConstantUtil.GroundDrawable_BuildKeyanshi_Right_X
						&&y>=ConstantUtil.GroundDrawable_BuildThings_Up_Y&&y<=ConstantUtil.GroundDrawable_BuildThings_Down_Y)//建筑科研室
				{
					this.x=357;
					this.zb=2;
				}else if(x>=ConstantUtil.GroundDrawable_BuildShoppingCenter_Left_X&&x<=ConstantUtil.GroundDrawable_BuildShoppingCenter_Right_X
						&&y>=ConstantUtil.GroundDrawable_BuildThings_Up_Y&&y<=ConstantUtil.GroundDrawable_BuildThings_Down_Y)//建筑购物中心
				{
					this.x=484;
					this.zb=3;
				}else if(x>=ConstantUtil.GroundDrawable_BuildHotel_Left_X&&x<=ConstantUtil.GroundDrawable_BuildHotel_Right_X
						&&y>=ConstantUtil.GroundDrawable_BuildThings_Up_Y&&y<=ConstantUtil.GroundDrawable_BuildThings_Down_Y)//建筑旅馆
				{
					this.x=612;
					this.zb=4;
				}
			}
		}
		return true;
	}
	public void addRoom()//在土地上交换房屋图片
	{
		if(da!=1)//小土地
	    {
    		k++;
	    	this.bpName=bitmaps[kk][k];
	    }else//大土地
	    {
	    	if(!first)
	    	{
	    		if(k<4&&zb!=0&&zb!=1)
	    		{
	    			k++;
	    			this.bpName=bitmap[kk];
	    			this.dbpName=bitmap2[zb][k];
	    		}
	    	}else
	    	{
	    		this.bpName=bitmap[kk];
	    		this.dbpName=bitmap[kk];
	    		this.flagg=false;
	    		this.first=false;
	    	}
	    }
		if(k==0)
		{
			tempFigure.father.room[pp]++;//该地区房子数目加一
			if(da==1)
			{
				tempFigure.room[pp][0]++;//该地区房子数目加一
			}else
			{
				tempFigure.room[pp][1]++;
			}
			tempFigure.count++;//该人物的土地加一
		}
	}
	public void GodGround()//头上有特殊人物的方法
	{
		if(godId==1)//大福神
		{
			if(k>0&&k<5)//加盖一层
			{
				addRoom();
				value+=tempFigure.mhz.result/2;//过路费增加
				tempFigure.father.showDialog.initMessage(tempFigure.figureName,tempFigure.k,true,false,DialogEnum.MESSAGE_TWO,godId,(int)(3+2*Math.random()));
				tempFigure.father.isDialog=true;//绘制对话框
			}
		}else if(godId==4||godId==5)//4-小天使  5-小福神
		{
			tempFigure.mhz.cutMoney(0);//现金减少
			if(k>0&&k<5)//加盖一层
			{
				addRoom();
				value+=tempFigure.mhz.result/2;//过路费增加
				tempFigure.father.showDialog.initMessage(tempFigure.figureName,tempFigure.k,true,false,DialogEnum.MESSAGE_TWO,godId,(int)(3+2*Math.random()));
				tempFigure.father.isDialog=true;//绘制对话框
			}
		}else if(godId==8||godId==9)//8-小衰神  9-大衰神
		{
			tempFigure.mhz.cutMoney(0);//现金减少
			if(k>0)//盖房失败
			{
				k--;
				if(da!=1)//小土地
			    {
			    	this.bpName=bitmaps[kk][k];
			    }else//大土地
			    {
			    	this.dbpName=bitmap2[zb][k];
			    }
				value-=tempFigure.mhz.result/2;//过路费减少
				tempFigure.father.showDialog.initMessage(tempFigure.figureName,tempFigure.k,true,false,DialogEnum.MESSAGE_TWO,godId,(int)(3*Math.random()));
				tempFigure.father.isDialog=true;//绘制对话框
			}
		}else
		{
			tempFigure.mhz.cutMoney(0);//现金减少
		}
	}
	//方法：返还监听器，回复游戏状态
	public void recoverGame()
	{
		tempFigure.father.setCurrentDrawable(null);//置空记录引用的变量
		tempFigure.father.setOnTouchListener(tempFigure.father);//返还监听器
		tempFigure.father.setStatus(0);//重新设置GameView为待命状态
		tempFigure.father.gvt.setChanging(true);//启动变换人物的线程
		this.isJS=true;//计算现金标志返原
		this.isGet=false;//是否能购买土地标志返原
		godId=-1;
	}
}
