package com.game.zillionaire.dialog;

import com.game.zillionaire.map.GameData2;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import static com.game.zillionaire.dialog.DialogConstant.*;
public class ShowDialog {
	GameView gv;//游戏界面引用
	public boolean isChange;//判断是否启动人物变换
	public int first=-1,second=-1,three=-1;//信息数组的索引值
	public DialogEnum message;//信息对话框的类型 
	public int day=0;//天数
	public int value=0;//钱数
	public String name;//人物姓名
	public int nameNum;//人物姓名索引
	public String guPiaoShu=null;//股票数
	public String showString=null;//对话框信息
	private int count=0;//绘制的次数
	public boolean dialogType;//对话框类型
	public Bitmap cardBitmap;//卡片图片
	public boolean meet=false;//是否带有特殊人物
	public boolean isHotel=false;//是否是旅馆
	
	public ShowDialog(GameView gv)
	{
		this.gv=gv;
	}
	public void initMessage(String name,int nameNum,boolean dialogType,boolean isChange,DialogEnum message,int first,int second)
	{
		this.name=name;//人物姓名赋值
		this.nameNum=nameNum;
		this.dialogType=dialogType;
		this.isChange=isChange;//给isChange赋值
		this.message=message;//给信息对话框类型赋值
		this.first=first;//信息索引赋值
		this.second=second;//信息索引赋值
		this.count=0;//绘制次数赋值
	} 
	public void drawDialog(Canvas canvas)
	{
		switch(message)
		{
			case MESSAGE_ONE://大部分信息
				if(count<10&&first!=-1)//第一句话
				{
					showString=dialogMessage[nameNum][first];//获取对话框信息
					change();//替换信息中的一些内容
					if(count==9&&meet)//带有特殊人物
					{
						first=three;
						count=0;
						message=DialogEnum.MESSAGE_TWO;
					}
				}else if(count<20&&second!=-1)//第二句话
				{
					showString=dialogMessage[nameNum][second];//获取对话框信息
					change();//替换信息中的一些内容
				}else
				{
					if(isHotel)//入住旅店信息
					{
						gv.figure0.isDreaw=false;//人物消失
						gv.figure0.isWhere=2;//住入旅馆
					}
					recoverGame();
				}
			break;
			case MESSAGE_TWO://特殊人物信息
				if(count<10&&first!=-1)//第一句话
				{
					showString=MeetPWord[first];//获取对话框信息
				}else if(count<20&&second!=-1)//第二句话
				{
					message=DialogEnum.MESSAGE_ONE;
					count--;//自减1
				}else
				{
					recoverGame();//方法：返还监听器，回复游戏状态
				}
			break;
		}
		if(dialogType)//绘制一般对话框
		{
			Bitmap bmpDialogBack=PicManager.getPic(GameData2.bmpDialogBack, gv.activity.getResources());
			canvas.drawBitmap(bmpDialogBack, 280,120, null);
		}else
		{
			Bitmap bmpDialogBack=PicManager.getPic(GameData2.bmpDialogBacks[0], gv.activity.getResources());
			canvas.drawBitmap(bmpDialogBack, 280,120, null);
			canvas.drawBitmap(cardBitmap, 560,128, null);
			showString="  ";
		}
		if(showString!=null&&showString.length()>0)//绘制文字
		{
			Paint paint = new Paint();
			paint.setARGB(255, 42, 48, 103);//设置字体颜色
			paint.setAntiAlias(true);//抗锯齿
			paint.setFakeBoldText(true);//字体加粗
			paint.setTextSize(28);//设置文字大小
			int lines = showString.length()/18+(showString.length()%18==0?0:1);//求出需要画几行文字
			for(int i=0;i<lines;i++)
			{
				String str="";
				if(i == lines-1)//如果是最后一行那个不太整的汉字
				{
					str = showString.substring(i*18);
				}else
				{
					str = showString.substring(i*18, (i+1)*18);
				}
				canvas.drawText(str, 320,200+30*i, paint);
			}
			count++;
		}
	}
	public void change()//替换信息中的xx、x、yy
	{
		showString = showString.replaceFirst("xx",name);
		if(day!=0)
		{
			showString = showString.replaceFirst("x",day+"");
		}
		if(value!=0)
		{
			showString = showString.replaceFirst("yy",value+"");
		}
		if(guPiaoShu!=null)
		{
			showString = showString.replaceFirst("y",guPiaoShu);
		}
	}
	//方法：返还监听器，回复游戏状态
	public void recoverGame()
	{
		//各个数据还原
		gv.isDialog=false;//不再绘制对话框
		first=-1;
		second=-1;
		three=-1;//信息数组的索引值
		guPiaoShu=null;//股票数还原
		count=0;//绘制次数返原
		meet=false;
		value=0;
		day=0;
		isHotel=false;//是否是旅馆
		if(gv.currentDrawable==null)
		{
			gv.setOnTouchListener(gv);//返还监听器
			gv.setStatus(0);//重新设置GameView为待命状态
			if(isChange)//不启动人物变换线程
			{
				if(gv.isFigure==0)
				{
					gv.isDraw=false;//绘制GO图标
					gv.isMenu=true;//绘制小菜单
				}else
				{
					gv.gvu.isGoDice(gv.figure0);//系统人物掷骰子
				}
			}else
			{
				gv.gvt.setChanging(true);//启动变换人物的线程
			}
		}
	}
}
