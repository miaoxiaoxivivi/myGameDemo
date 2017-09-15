package com.game.zillionaire.view;

import java.util.Calendar;
import java.util.Date;

import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.DateUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.util.ScreenTransUtil;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class CheckFigureInfomation implements View.OnTouchListener {
	GameView gameView;//定义GameView对象
	static String[] checkFigureInfoName={"lookinfo01","lookinfo04","lookinfo05","lookinfo06"};
	static Bitmap[] checkFigureInfo=new Bitmap[checkFigureInfoName.length];//加载查看英雄信息的图片
	static Bitmap[] selectFigureInfo=new Bitmap[6];//加载选择英雄信息的图片
	static Bitmap[] selectLandInfo=new Bitmap[6];//加载选择英雄土地信息的图片
	boolean isDrawSelectFigure=false;//是否画选中英雄图片	
	boolean isDrawSelectInfo=false;//是否画选中英雄信息图片
	boolean isDrawSelectLand=false;//是否画选中英雄土地信息图片
	boolean isLandMove=false;//查看土地信息是否允许滑动
	boolean isStockMove=false;//查看股票信息是否允许滑动
	int selectFigure=-1;//选择英雄的索引
	int selectInfo=-1;//选择英雄信息的索引
	int selectLand=-1;//选择英雄土地信息的索引
	Date date=null;//日期
	Calendar calendar=null;
	int landInfoY=275;
	int stockInfo=225;
	int downx=0;
	int downy=0;
	int landCount=0;
	public Bitmap[] figureHead=new Bitmap[3];
	
	public CheckFigureInfomation(GameView gameView)
	{
		this.gameView=gameView;
		calendar=Calendar.getInstance();//获取当前时间
	}
	public void onDraw(Canvas canvas)//绘制方法
	{
		for(int i=0;i<checkFigureInfo.length;i++)
		{
			checkFigureInfo[i]=PicManager.getPic(checkFigureInfoName[i],gameView.activity.getResources());
		}
		Bitmap temp=PicManager.getPic("lookinfo02",gameView.activity.getResources());//加载英雄信息整图
		for(int i=0;i<6;i++)
		{
			selectFigureInfo[i]=Bitmap.createBitmap(temp,120*i,0,120,80);
		}
		temp=PicManager.getPic("lookinfo03",gameView.activity.getResources());//加载英雄土地信息整图
		for(int i=0;i<6;i++)
		{
			selectLandInfo[i]=Bitmap.createBitmap(temp,150*i,0,150,68);
		}
		temp=null;
		figureHead[0]=PicManager.getPic(gameView.figure1.headBitmapName, gameView.activity.getResources());
		figureHead[1]=PicManager.getPic(gameView.figure.headBitmapName, gameView.activity.getResources());
		figureHead[2]=PicManager.getPic(gameView.figure2.headBitmapName, gameView.activity.getResources());
		canvas.drawBitmap(checkFigureInfo[0], 0, 5, null);//画背景框
		if(!isDrawSelectFigure)//如果没有选择英雄
		{
			canvas.drawBitmap(checkFigureInfo[3], 200, 20, null);//默认选中第一个
			
			canvas.drawBitmap(figureHead[0], 215, 20, null);
			canvas.drawBitmap(figureHead[1], 375, 20, null);
			canvas.drawBitmap(figureHead[2], 545, 20, null);
			getMoneyDrawString(gameView.figure1,canvas);
			if(isDrawSelectInfo)//如果选择查看英雄信息
			{
				ifDrawSelectInfo(canvas,gameView.figure1);
			}
		}else//如果已经选择了英雄
		{
			switch(selectFigure)//根据选择英雄索引值
			{
			case 0://选择了英雄1
				canvas.drawBitmap(checkFigureInfo[3], 200, 20, null);//画选中框
				canvas.drawBitmap(figureHead[0], 215, 20, null);
				canvas.drawBitmap(figureHead[1], 375, 20, null);
				canvas.drawBitmap(figureHead[2], 545, 20, null);
				ifDrawSelectInfo(canvas,gameView.figure1);
				break;
			case 1://选择了英雄2
				canvas.drawBitmap(figureHead[0], 215, 20, null);
				canvas.drawBitmap(checkFigureInfo[3], 360, 20, null);//画选中框
				canvas.drawBitmap(figureHead[1], 375, 20, null);
				canvas.drawBitmap(figureHead[2], 545, 20, null);
				ifDrawSelectInfo(canvas,gameView.figure);
				break;
			case 2://选择了英雄3
				canvas.drawBitmap(figureHead[0], 215, 20, null);
				canvas.drawBitmap(figureHead[1], 375, 20, null);
				canvas.drawBitmap(checkFigureInfo[3], 530, 20, null);//画选中框
				canvas.drawBitmap(figureHead[2], 545, 20, null);
				ifDrawSelectInfo(canvas,gameView.figure2);
				break;
			}
		}
	}
	public void ifDrawSelectInfo(Canvas canvas,Figure figure)//如果点击查看英雄信息的绘制方法
	{
		switch(selectInfo)
		{
		case -1:
			getMoneyDrawString(figure,canvas);//默认资金界面
			break;
		case 0://资金
			canvas.drawBitmap(selectFigureInfo[0], 88, 148, null);
			canvas.drawBitmap(selectFigureInfo[4], 95, 230, null);
			canvas.drawBitmap(selectFigureInfo[5], 95, 312, null);
			getMoneyDrawString(figure,canvas);
			break;
		case 1://土地
			canvas.drawBitmap(selectFigureInfo[3], 95, 148, null);
			canvas.drawBitmap(selectFigureInfo[1], 92, 230, null);
			canvas.drawBitmap(selectFigureInfo[5], 95, 312, null);
			canvas.drawBitmap(checkFigureInfo[2], 207, 135, null);
			isLandMove=false;
			getLandDrawString(figure,canvas);
			break;
		case 2://股票
			canvas.drawBitmap(selectFigureInfo[3], 95, 148, null);
			canvas.drawBitmap(selectFigureInfo[4], 95, 230, null);
			canvas.drawBitmap(selectFigureInfo[2], 94, 312, null);
			canvas.drawBitmap(checkFigureInfo[1], 207, 135, null);
			isStockMove=false;
			getStockDrawString(figure,canvas);
			break;
		}
		if(selectInfo==1&&isDrawSelectLand)//如果查看英雄土地信息 并对土地信息进行了选择
		{
			switch(selectLand)
			{
			case 0://全部
				canvas.drawBitmap(selectLandInfo[0], 340, 133, null);
				canvas.drawBitmap(selectLandInfo[4], 473, 132, null);
				canvas.drawBitmap(selectLandInfo[5], 607, 132, null);
				break;
			case 1://住宅
				canvas.drawBitmap(selectLandInfo[3], 340, 133, null);
				canvas.drawBitmap(selectLandInfo[1], 473, 132, null);
				canvas.drawBitmap(selectLandInfo[5], 607, 132, null);
				break;
			case 2://商业
				canvas.drawBitmap(selectLandInfo[3], 340, 133, null);
				canvas.drawBitmap(selectLandInfo[4], 475, 132, null);
				canvas.drawBitmap(selectLandInfo[2], 605, 132, null);
				break;
			}
		}
	}
	public void getMoneyDrawString(Figure figure,Canvas canvas)//获得选中英雄的资金信息
	{
		String[] result=new String[11];
		int count=0;
		if(figure!=null)
		{
			result[0]=figure.mhz.xMoney+"";//现金
			drawString(canvas,result[0],8,315,175);
			result[1]=figure.mhz.gameDian+"";//点数
			drawString(canvas,result[1],4,520,175);
			result[2]=figure.mhz.cMoney+"";//存款
			drawString(canvas,result[2],8,315,235);
			for(int index:figure.CardNum)
			{
				if(index!=-1)
				{
					count++;
				}
			}
			result[3]=count+"";//卡片数
			drawString(canvas,result[3],4,520,235);
			result[4]="0";//贷款
			drawString(canvas,result[4],8,315,295);
			result[5]="--";//还款天数
			drawString(canvas,result[5],4,560,295);
			result[6]="0";//股票
			drawString(canvas,result[6],8,315,355);
			result[7]=figure.count+"";//土地
			drawString(canvas,result[7],4,520,355);
			result[8]=figure.mhz.zMoney+"";//总资产
			drawString(canvas,result[8],8,325,415);
			result[9]="0";//商业设施
			drawString(canvas,result[9],4,560,415);
			
			date=DateUtil.getDateAfter(calendar.getTime(),gameView.count);//日期
			String str;
			result[10]=DateUtil.getWeekAndYMD(date);//获取指定形式的字符串
			str=result[10].substring(0, 7);//年、月
			drawString(canvas,str,7,620,320);
			str=result[10].substring(8, 10);//日
			drawString(canvas,str,7,650,360);
			str=result[10].substring(11, 14);//星期
			drawString(canvas,str,6,630,400);
		}
	}
	public void getLandDrawString(Figure figure,Canvas canvas)//获得选中英雄的土地信息
	{
		String landAddress="";
		String landAttribute="";
		String landPrice="";
		int count=0;
		if(figure!=null)//如果当前英雄不为空
		{
			for(int i=0;i<6;i++)
			{
				for(int j=0;j<2;j++)
				{
					if(figure.room[i][j]>=0)//有土地
					{
						if(j==0)//大土地
						{
							landAttribute="商业";
						}
						if(j==1)//小土地
						{
							landAttribute="住宅";
						}
						switch(i)//地区
						{
						case 0:
							landAddress="茶晶";//地区名称
							landPrice=1500+"";//购房价钱
							break;
						case 1:
							landAddress="蓝宝";
							landPrice=1000+"";
							break;
						case 2:
							landAddress="翡翠";
							landPrice=800+"";
							break;
						case 3:
							landAddress="黑耀";
							landPrice=2500+"";
							break;
						case 4:
							landAddress="粉晶";
							landPrice=1200+"";
							break;
						case 5:
							landAddress="紫晶";
							landPrice=600+"";
							break;
						}
						int y=landInfoY+count*50;//y坐标
						if(selectLand==-1||selectLand==1)
						{
							if(landAttribute.equals("住宅"))//只画住宅
							{
								if(y>=275&&y<=450)
								{
									drawString(canvas,landAddress,4,257,y);
									drawString(canvas,landAttribute,4,390,y);
									drawString(canvas,landPrice,4,520,y);
									drawString(canvas,(Integer.parseInt(landPrice)/2)+"",4,660,y);
								}
								count++;
							}
						}else if(selectLand==0)
						{
							if(y>=275&&y<=450)//画全部
							{
								drawString(canvas,landAddress,4,257,y);
								drawString(canvas,landAttribute,4,390,y);
								drawString(canvas,landPrice,4,520,y);
								drawString(canvas,(Integer.parseInt(landPrice)/2)+"",4,660,y);
							}
							count++;
						}else if(selectLand==2)
						{
							if(landAttribute.equals("商业"))//画商业
							{
								if(y>=275&&y<=450)
								{
									drawString(canvas,landAddress,4,257,y);
									drawString(canvas,landAttribute,4,390,y);
									drawString(canvas,landPrice,4,520,y);
									drawString(canvas,(Integer.parseInt(landPrice)/2)+"",4,660,y);
								}
								count++;
							}
						}
						if(count>4)//如果大于4行
						{
							isLandMove=true;//允许滑动
						}
					}
				}
			}
		}
		landCount=count;
	}
	public void getStockDrawString(Figure figure,Canvas canvas)//获得选中英雄的股票信息
	{
		int count=0;
		for(int i=0;i<figure.mpsName.size();i++)
		{
			if(!figure.mpsName.get(i).equals("0"))
			{
				int y=stockInfo+count*50;
				if(y>=225&&y<=425)
				{
					drawString(canvas,figure.mpsName.get(i),6,260,y);
					drawString(canvas,figure.mps.get(i)+"",6,465,y);
					drawString(canvas,figure.mpsCost.get(i)+"",8,625,y);
				}
				count++;
			}
			if(count>5)
			{
				isStockMove=true;
			}
		}
	}
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		int k=0;
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			int x=ScreenTransUtil.xFromRealToNorm((int)event.getX());//得到点击的X坐标
			int y=ScreenTransUtil.yFromRealToNorm((int)event.getY());//得到点击的Y坐标
			if(x>=ConstantUtil.CheckFigureInfomation_Close_Left_X&&x<=ConstantUtil.CheckFigureInfomation_Close_Right_X
					&&y>=ConstantUtil.CheckFigureInfomation_Close_Up_Y&&y<=ConstantUtil.CheckFigureInfomation_Close_Down_Y)//点击关闭图标
			{
				isDrawSelectFigure=false;
				isDrawSelectInfo=false;
				gameView.status=0;
				gameView.isDraw=false;//绘制GO图标
				gameView.isMenu=true;
				this.gameView.setOnTouchListener(this.gameView);//返还监听器
			}else if(x>=ConstantUtil.CheckFigureInfomation_ChooseFigure1_Left_X&&x<=ConstantUtil.CheckFigureInfomation_ChooseFigure1_Right_X
					&&y>=ConstantUtil.CheckFigureInfomation_ChooseFigure_Up_Y&&y<=ConstantUtil.CheckFigureInfomation_ChooseFigure_Down_Y)//选择英雄1 
			{
				isDrawSelectLand=false;
				isDrawSelectFigure=true;
				selectFigure=0;
			}else if(x>=ConstantUtil.CheckFigureInfomation_ChooseFigure2_Left_X&&x<=ConstantUtil.CheckFigureInfomation_ChooseFigure2_Right_X
					&&y>=ConstantUtil.CheckFigureInfomation_ChooseFigure_Up_Y&&y<=ConstantUtil.CheckFigureInfomation_ChooseFigure_Down_Y)//选择英雄2
			{
				isDrawSelectFigure=true;
				selectFigure=1;
			}else if(x>=ConstantUtil.CheckFigureInfomation_ChooseFigure3_Left_X&&x<=ConstantUtil.CheckFigureInfomation_ChooseFigure3_Right_X
					&&y>=ConstantUtil.CheckFigureInfomation_ChooseFigure_Up_Y&&y<=ConstantUtil.CheckFigureInfomation_ChooseFigure_Down_Y)//选择英雄3
			{
				isDrawSelectFigure=true;
				selectFigure=2;
			}else if(x>=ConstantUtil.CheckFigureInfomation_ChooseAndCheck_Left_X&&x<=ConstantUtil.CheckFigureInfomation_ChooseAndCheck_Right_X
					&&y>=ConstantUtil.CheckFigureInfomation_ChooseAndCheckMoney_Up_Y&&y<=ConstantUtil.CheckFigureInfomation_ChooseAndCheckMoney_Down_Y)//选择查看资金信息
			{
				isDrawSelectLand=false;
				isDrawSelectInfo=true;
				selectInfo=0;
			}else if(x>=ConstantUtil.CheckFigureInfomation_ChooseAndCheck_Left_X&&x<=ConstantUtil.CheckFigureInfomation_ChooseAndCheck_Right_X
					&&y>=ConstantUtil.CheckFigureInfomation_ChooseAndCheckGround_Up_Y&&y<=ConstantUtil.CheckFigureInfomation_ChooseAndCheckGround_Down_Y)//选择查看土地信息
			{
				isDrawSelectInfo=true;
				selectInfo=1;
			}else if(x>=ConstantUtil.CheckFigureInfomation_ChooseAndCheck_Left_X&&x<=ConstantUtil.CheckFigureInfomation_ChooseAndCheck_Right_X
					&&y>=ConstantUtil.CheckFigureInfomation_ChooseAndCheckStock_Up_Y&&y<=ConstantUtil.CheckFigureInfomation_ChooseAndCheckStock_Down_Y)//选择查看股票信息
			{
				isDrawSelectInfo=true;
				selectInfo=2;
			}
			if(selectInfo==1&&x>=ConstantUtil.CheckFigureInfomation_AllGroundInfo_Left_X&&x<=ConstantUtil.CheckFigureInfomation_AllGroundInfo_Right_X
					&&y>=ConstantUtil.CheckFigureInfomation_ChooseAndCheckAllInfo_Up_Y&&y<=ConstantUtil.CheckFigureInfomation_ChooseAndCheckAllInfo_Down_Y)//选择查看全部土地信息
			{
				isDrawSelectLand=true;
				selectLand=0;
				landInfoY=275;
			}else if(selectInfo==1&&x>=ConstantUtil.CheckFigureInfomation_HouseGroundInfo_Left_X&&x<=ConstantUtil.CheckFigureInfomation_HouseGroundInfo_Right_X
					&&y>=ConstantUtil.CheckFigureInfomation_ChooseAndCheckAllInfo_Up_Y&&y<=ConstantUtil.CheckFigureInfomation_ChooseAndCheckAllInfo_Down_Y)//选择查看住宅土地信息
			{
				isDrawSelectLand=true;
				selectLand=1;
				landInfoY=275;
			}else if(selectInfo==1&&x>=ConstantUtil.CheckFigureInfomation_TradeGroundInfo_Left_X&&x<=ConstantUtil.CheckFigureInfomation_TradeGroundInfo_Right_X
					&&y>=ConstantUtil.CheckFigureInfomation_ChooseAndCheckAllInfo_Up_Y&&y<=ConstantUtil.CheckFigureInfomation_ChooseAndCheckAllInfo_Down_Y)//选择查看商业土地信息
			{
				isDrawSelectLand=true;
				selectLand=2;
				landInfoY=275;
			}
			downx=x;
			downy=y;
		}else if(event.getAction()==MotionEvent.ACTION_MOVE)//滑动
		{
			int tempx=ScreenTransUtil.xFromRealToNorm((int)event.getX());
			int tempy=ScreenTransUtil.yFromRealToNorm((int)event.getY());
			if(isLandMove&&tempx>=ConstantUtil.CheckFigureInfomation_CheckGroundInfo_Left_X&&tempx<=ConstantUtil.CheckFigureInfomation_CheckGroundInfo_Right_X
					&&tempy>=ConstantUtil.CheckFigureInfomation_CheckGroundInfo_Up_Y&&tempy<=ConstantUtil.CheckFigureInfomation_CheckGroundInfo_Down_Y)//查看土地信息允许滑动
			{
				int prey=tempy-downy;
				if(landCount>6)//如果大于6张
				{
					k=landCount-6;
				}
				if((landInfoY+prey)>=(175-50*k)&&(landInfoY+prey)<=475)//在指定范围内
				{
					if(prey>=0)
					{
						landInfoY+=10;//变换y坐标
					}else
					{
						landInfoY-=10;
					}
				}
			}
			else if(isStockMove&&tempx>=ConstantUtil.CheckFigureInfomation_CheckStockInfo_Left_X&&tempx<=ConstantUtil.CheckFigureInfomation_CheckStockInfo_Right_X
					&&tempy>=ConstantUtil.CheckFigureInfomation_CheckStockInfo_Up_Y&&tempy<=ConstantUtil.CheckFigureInfomation_CheckStockInfo_Down_Y)//查看股票信息允许滑动
			{
				int prey=tempy-downy;
				if((stockInfo+prey)>=125&&(stockInfo+prey)<=400)
				{
					if(prey>=0)
					{
						stockInfo+=10;//变换y坐标
					}else
					{
						stockInfo-=10;
					}
				}
			}
		}
		return true;
	}
	public void drawString(Canvas canvas,String string,int instance,int x,int y)//画文字
	{
		Paint paint = new Paint();
		paint.setARGB(255, 0,0,0);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setTextSize(26);//设置文字大小
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		int lines = string.length()/instance+(string.length()%instance==0?0:1);//求出需要画几行文字
		for(int i=0;i<lines;i++){
			String str="";
			if(i == lines-1){//如果是最后一行那个不太整的汉字
				str = string.substring(i*instance);
			}
			else{
				str = string.substring(i*instance, (i+1)*instance);
			}
			canvas.drawText(str, x, y+24*i, paint);//将文字画到背景框中
		}
	}
}
