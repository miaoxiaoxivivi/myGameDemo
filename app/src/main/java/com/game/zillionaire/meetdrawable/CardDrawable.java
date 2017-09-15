package com.game.zillionaire.meetdrawable;

import java.io.Serializable;

import com.game.zillionaire.activity.ZActivity;
import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.map.MyMeetableDrawable;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.util.ScreenTransUtil;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class CardDrawable extends MyMeetableDrawable implements Serializable
{
	private static final long serialVersionUID = 3286545290822355086L;
	static int[] figureCardNum=new int[20];//存储人物购买的卡片
	static int figureHaveCardFlag=0;//画已购买卡片的标志位
	static int[] cardsNumArray=new int[12];//声明存储随机数的数组 
	static int picNum=0;//商店出售的卡片个数
	int cardgoNum=0;//向前或后查看图片的步数
	static boolean CardSelectedFlag=true;//在售卡片选中框框的标志位  true--买卡片   false--卖卡片
	static boolean isDraw=false;
	static boolean cardSaleOut=false;
	static boolean FigureBuyCardFlag=false;//系统人物购买卡片
	static boolean FigureBuyCardFlagOther=false;//系统人物购买卡片
	static int figureDianshu=0;//人物点数
	static int figureBuyCardNum=0;//系统人物买卡片的个数
	static Bitmap allcard;//所有卡片图
	static Bitmap[] cards=new Bitmap[21];//各个卡片
	static Bitmap bmpDialogBacks;//背景图
	static Bitmap arrowsGo;//前进箭头
	static Bitmap arrowsBack;//返回箭头
	static Bitmap kkuang;//选中框框
	static Bitmap cardshopsale;//卖卡片的商店
	static Bitmap cardshop;//买卡片的商店
	static Bitmap sysBackground;//系统人物买卡片提示框的背景
	static Bitmap person1;//系统人物1
	static Bitmap person2;//系统人物2
	static int drawBitmapNumTemp=0;
	int count=0;
	
	//构造器
	public CardDrawable(){}
	public CardDrawable(ZActivity at,String bmpSelf,String dbitmap,String bmpDialogBack,boolean meetable,int width,int height,int col,int row,
			int refCol,int refRow,int [][] noThrough,int [][] meetableMatrix,int da) {
		super(at,bmpSelf,dbitmap, col, row, width, height, refCol, refRow, noThrough, meetable,
				meetableMatrix, da,bmpDialogBack);
	}

	//系统人物买卡片方法
	public static void SysFigureBuyCards(){
		//只调用一次
		if(!FigureBuyCardFlag){
			//声明卡片编号字符串
			String suitCardNum="";
			//卡片点数
			int tempcardprice=0;
			//获得符合条件的卡片编号字符串
			for(int i=0;i<picNum;i++){
				if(ConstantUtil.CardsPriceInt[cardsNumArray[i]]<=figureDianshu){
					suitCardNum+=cardsNumArray[i]+"<#>";
				}
			}
			//存在符合条件的卡片
			if(suitCardNum.length()>0){
				suitCardNum=suitCardNum.substring(0,suitCardNum.length()-3);
				//获得卡片编号数组
				String[] cardsNumArrayArr=suitCardNum.split("<#>");
				//初始化系统人物购买卡片数量为0
				figureBuyCardNum=0;
				//遍历符合条件的卡片
				for(int j=0;j<cardsNumArrayArr.length;j++)
				{
					//获得每张卡片的点数
					tempcardprice=ConstantUtil.CardsPriceInt[Integer.parseInt(cardsNumArrayArr[j])];
					//购买该张卡片后剩余点数>80,卡片栏未满，则购买
					if((figureDianshu-tempcardprice>80)&&figureHaveCardFlag<20)
					{
						//将买到的卡片存入数组
						figureCardNum[figureHaveCardFlag]=Integer.parseInt(cardsNumArrayArr[j]);
						//已购总卡片个数+1
						figureHaveCardFlag++;
						//人物本次购买卡片数量+1
						figureBuyCardNum++;
						//人物点数减少
						figureDianshu-=ConstantUtil.CardsPriceInt[Integer.parseInt(cardsNumArrayArr[j])];
					}
					else
					{
						break;
					}
				}
				if(figureBuyCardNum>=1){
					drawBitmapNumTemp=(int)(Math.random()*figureHaveCardFlag);
					isDraw=true;
				}
			}
			//该方法只调用1次
			FigureBuyCardFlag=true;
		}
	}
	
	//初始化商店在售卡片和人物已购卡片
	public void initBuyCardNeed(Figure figure)
	{
		//产生随机数
		if(!FigureBuyCardFlagOther)
		{
			//随机产生12张图片数  
            for(int i=0;i<12;i++)
            {
            	cardsNumArray[i]=(int)(Math.random()*21);
            }
	        //随机产生商店在售卡片个数(N>=8&&N<=12)
	        int picNumtemp=0;
	        while(picNumtemp<8)
	        {
	        	picNumtemp=(int)(Math.random()*13);
	        }
	        picNum=picNumtemp;
	        //只随机产生数一次
	        //获得人物已购的卡片数量
	        int flag=0;
        	for(int i=0;i<20;i++)
        	{
				if(figure.CardNum[i]==-1)
				{
					flag=1;
				}
				if(flag==1)
					figureCardNum[i]=-1;
				else
				{
					figureCardNum[i]=figure.CardNum[i];
					//获得该人物已购卡片数量
					figureHaveCardFlag++;
				}
        	}
        	//获得人物的点数
			figureDianshu=figure.mhz.gameDian;
			FigureBuyCardFlagOther=true;
		}
	}
	
	@Override//绘制对话框的方法
	public void drawDialog(final Canvas canvas,Figure figure)
	{
		
		//实例化对象
		this.tempFigure=figure;
		//初始化图片
		bmpDialogBacks=PicManager.getPic("cardshopbuy",tempFigure.father.activity.getResources());//初始化图片
		cardshopsale=PicManager.getPic("cardshopsale",tempFigure.father.activity.getResources());
		cardshop=PicManager.getPic("cardshopbuy",tempFigure.father.activity.getResources());
		allcard=PicManager.getPic("usecard",tempFigure.father.activity.getResources());
		arrowsGo=PicManager.getPic("cardgo",tempFigure.father.activity.getResources());
		arrowsBack=PicManager.getPic("cardback",tempFigure.father.activity.getResources());
		kkuang=PicManager.getPic("card00",tempFigure.father.activity.getResources());
		sysBackground=PicManager.getPic("dialog_back",tempFigure.father.activity.getResources());
		person1=PicManager.getPic("figure1",tempFigure.father.activity.getResources());
		person2=PicManager.getPic("figure2",tempFigure.father.activity.getResources());
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<7;j++)
			{
				cards[i*7+j]=Bitmap.createBitmap(allcard,75*j,91*i,75,91);
			}
		}
		//需要显示到对话框中的字符串
		String showString="";
		//初始化商店在售卡片和人物已购卡片
		initBuyCardNeed(figure);
		//系统人物购买卡片
		if(tempFigure.father.isFigure!=0)
		{
			recoverGame();//返回游戏
		}
		//操纵人物购买卡片界面
		else if(tempFigure.father.isFigure==0)
		{
			/**
			 * 画商店背景，购买/卖出
			 */
	        //选中已购买卡片时画卖卡片背景
			if(figureHaveCardFlag>=1&&!CardSelectedFlag){
				canvas.drawBitmap(cardshopsale, 60,0 ,null);
			}
			//画买卡片背景
			if(CardSelectedFlag){
				canvas.drawBitmap(bmpDialogBacks, 60,0 ,null);
			}
			//无买无卖
			if(!CardSelectedFlag&&figureHaveCardFlag==0)
			{
				canvas.drawBitmap(cardshop, 60,0 ,null);
			}
				
			/**
			 * 显示随机个数卡片
			 */
			int i=0;
			for(int j=0;j<3;j++){
				if(i>picNum-1){
					break;
			    }else{
					for(int k=0;k<4;k++){
						if(i>picNum-1){
							break;
						}else{
							canvas.drawBitmap(cards[cardsNumArray[i]], 210+80*k,80+90*j ,null);
							i++;
						}
					}
				}
			}
			/**
			 * 判断人物是否有卡片,如果有则画卡片,只画最新的5张图片
			 */
			if(figureHaveCardFlag>0){
				int k=0,j=0;
				int tempfigureHaveCardFlag=figureHaveCardFlag;
				if(figureHaveCardFlag>5){
					k=figureHaveCardFlag-5+cardgoNum;
					tempfigureHaveCardFlag=k+5;
				}
				for(;k<tempfigureHaveCardFlag;k++){
					canvas.drawBitmap(cards[figureCardNum[k]], 110+70*j,350,null);
					j++;
				}	
			}
			/**
			 * 显示卡片介绍，选中框
			 */
			//赋值为选中个数
			i=ConstantUtil.CARDSELECTED_NUM;
			if(i>=0&&figureHaveCardFlag>=0)
			{
				//画在售卡片
				if(CardSelectedFlag){
					if(cardsNumArray[i]!=-1)
					{
						drawTitleString(canvas,ConstantUtil.CardsName[cardsNumArray[i]]);
						showString=ConstantUtil.CardsFunctionIntro[i];
						canvas.drawBitmap(cards[cardsNumArray[i]], 540,80,null);
						drawString(canvas,ConstantUtil.CardsPrice[cardsNumArray[i]],620,140);
					}
				}else{//画已购卡片
					int tempLocation=0;
					if(figureHaveCardFlag>5){
						tempLocation=figureCardNum[figureHaveCardFlag+cardgoNum-i-1];
						drawTitleString(canvas,ConstantUtil.CardsName[tempLocation]);
						showString=ConstantUtil.CardsFunctionIntro[tempLocation];
						canvas.drawBitmap(cards[tempLocation], 540,80,null);
						drawString(canvas,ConstantUtil.CardsPrice[tempLocation],620,140);
					}else{
						tempLocation=figureCardNum[4-i];
						drawTitleString(canvas,ConstantUtil.CardsName[tempLocation]);
						showString=ConstantUtil.CardsFunctionIntro[tempLocation];
						canvas.drawBitmap(cards[tempLocation], 540,80,null);
						drawString(canvas,ConstantUtil.CardsPrice[tempLocation],620,140);
					}
					tempLocation=0;
				}
				//显示介绍文字
				drawString(canvas,showString,550,220);
				//显示点数
				showString=figureDianshu+"";
				drawString(canvas,showString,585,50);
				//画选中的框框
				int tempR=0,tempL=0;
				if(CardSelectedFlag){
					tempR=i/4;//行
					tempL=i%4;//列
					canvas.drawBitmap(kkuang, 206+80*tempL,77+90*tempR,null);
				}else{
					tempL=4-i;//列
					canvas.drawBitmap(kkuang, 106+70*tempL,347,null);
				}
			}
			/**
			 * 判断人物卡片是否>5,大于则画前进或返回键
			 */
			if(figureHaveCardFlag>5){
				if(figureHaveCardFlag+cardgoNum>5){
					canvas.drawBitmap(arrowsBack,80,370,null);
				}	
				if(cardgoNum<0){
					canvas.drawBitmap(arrowsGo,475,370,null);
				}
			}			
		}
	}
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		//屏幕自适应
		int x = ScreenTransUtil.xFromRealToNorm((int)event.getX());
		int y = ScreenTransUtil.yFromRealToNorm((int)event.getY());
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			//第一列卡片
			if(x>ConstantUtil.CardDrawable_CardSop_Col1_Left_X&&x<ConstantUtil.CardDrawable_CardSop_Col1_Right_X&&picNum>=1){
				if(y>ConstantUtil.CardDrawable_CardSop_Row1_Up_Y&&y<ConstantUtil.CardDrawable_CardSop_Row1_Down_Y){//0
					ConstantUtil.CARDSELECTED_NUM=0;
					CardSelectedFlag=true;
				}else if(y>ConstantUtil.CardDrawable_CardSop_Row2_Up_Y&&y<ConstantUtil.CardDrawable_CardSop_Row2_Down_Y&&picNum>=5){//4
					ConstantUtil.CARDSELECTED_NUM=4;
					CardSelectedFlag=true;
				}else if(y>ConstantUtil.CardDrawable_CardSop_Row3_Up_Y&&y<ConstantUtil.CardDrawable_CardSop_Row3_Down_Y&&picNum>=9){//8
					ConstantUtil.CARDSELECTED_NUM=8;
					CardSelectedFlag=true;
				}
			}
			//第二列卡片
			else if(x>ConstantUtil.CardDrawable_CardSop_Col2_Left_X&&x<ConstantUtil.CardDrawable_CardSop_Col2_Right_X){
				if(y>ConstantUtil.CardDrawable_CardSop_Row1_Up_Y&&y<ConstantUtil.CardDrawable_CardSop_Row1_Down_Y&&picNum>=2){//1
					ConstantUtil.CARDSELECTED_NUM=1;
					CardSelectedFlag=true;
				}else if(y>ConstantUtil.CardDrawable_CardSop_Row2_Up_Y&&y<ConstantUtil.CardDrawable_CardSop_Row2_Down_Y&&picNum>=6){//5
					ConstantUtil.CARDSELECTED_NUM=5;
					CardSelectedFlag=true;
				}else if(y>ConstantUtil.CardDrawable_CardSop_Row3_Up_Y&&y<ConstantUtil.CardDrawable_CardSop_Row3_Down_Y&&picNum>=10){//9
					ConstantUtil.CARDSELECTED_NUM=9;
					CardSelectedFlag=true;
				}
			}
			//第三列卡片
			else if(x>ConstantUtil.CardDrawable_CardSop_Col3_Left_X&&x<ConstantUtil.CardDrawable_CardSop_Col3_Right_X){
				if(y>ConstantUtil.CardDrawable_CardSop_Row1_Up_Y&&y<ConstantUtil.CardDrawable_CardSop_Row1_Down_Y&&picNum>=3){//2
					ConstantUtil.CARDSELECTED_NUM=2;
					CardSelectedFlag=true;
				}else if(y>ConstantUtil.CardDrawable_CardSop_Row2_Up_Y&&y<ConstantUtil.CardDrawable_CardSop_Row2_Down_Y&&picNum>=7){//6
					ConstantUtil.CARDSELECTED_NUM=6;
					CardSelectedFlag=true;
				}else if(y>ConstantUtil.CardDrawable_CardSop_Row3_Up_Y&&y<ConstantUtil.CardDrawable_CardSop_Row3_Down_Y&&picNum>=11){//10
					ConstantUtil.CARDSELECTED_NUM=10;
					CardSelectedFlag=true;
				}
			}
			//第四列卡片
			else if(x>ConstantUtil.CardDrawable_CardSop_Col4_Left_X&&x<ConstantUtil.CardDrawable_CardSop_Col4_Right_X&&picNum>=4)
			{
				if(y>ConstantUtil.CardDrawable_CardSop_Row1_Up_Y&&y<ConstantUtil.CardDrawable_CardSop_Row1_Down_Y){//3
					ConstantUtil.CARDSELECTED_NUM=3;
					CardSelectedFlag=true;
				}else if(y>ConstantUtil.CardDrawable_CardSop_Row2_Up_Y&&y<ConstantUtil.CardDrawable_CardSop_Row2_Down_Y&&picNum>=8){//7
					ConstantUtil.CARDSELECTED_NUM=7;
					CardSelectedFlag=true;
				}else if(y>ConstantUtil.CardDrawable_CardSop_Row3_Up_Y&&y<ConstantUtil.CardDrawable_CardSop_Row3_Down_Y&&picNum>=12){//11
					ConstantUtil.CARDSELECTED_NUM=11;
					CardSelectedFlag=true;
				}
			}
			//已购卡片
		    if(y>ConstantUtil.CardDrawable_HaveCard_Up_Y&&y<ConstantUtil.CardDrawable_HaveCard_Down_Y){
				if(x>ConstantUtil.CardDrawable_HaveCardShow1_Left_X&&x<ConstantUtil.CardDrawable_HaveCardShow1_Right_X&&figureHaveCardFlag>=1){
					ConstantUtil.CARDSELECTED_NUM=4;
					CardSelectedFlag=false;
				}else if(x>ConstantUtil.CardDrawable_HaveCardShow2_Left_X&&x<ConstantUtil.CardDrawable_HaveCardShow2_Right_X&&figureHaveCardFlag>=2){
					ConstantUtil.CARDSELECTED_NUM=3;
					CardSelectedFlag=false;
				}else if(x>ConstantUtil.CardDrawable_HaveCardShow3_Left_X&&x<ConstantUtil.CardDrawable_HaveCardShow3_Right_X&&figureHaveCardFlag>=3){
					ConstantUtil.CARDSELECTED_NUM=2;
					CardSelectedFlag=false;
				}else if(x>ConstantUtil.CardDrawable_HaveCardShow4_Left_X&&x<ConstantUtil.CardDrawable_HaveCardShow4_Right_X&&figureHaveCardFlag>=4){
					ConstantUtil.CARDSELECTED_NUM=1;
					CardSelectedFlag=false;
				}else if(x>ConstantUtil.CardDrawable_HaveCardShow5_Left_X&&x<ConstantUtil.CardDrawable_HaveCardShow5_Right_X&&figureHaveCardFlag>=5){
					ConstantUtil.CARDSELECTED_NUM=0;
					CardSelectedFlag=false;
				}
			}
			//确定购买按钮
			if(x>ConstantUtil.CardDrawable_BuyCardButton_left_X&&x<ConstantUtil.CardDrawable_BuyCardButton_Right_X
					&&y>ConstantUtil.CardDrawable_BuyCardButton_Up_Y&&y<ConstantUtil.CardDrawable_BuyCardButton_Down_Y&CardSelectedFlag&&figureHaveCardFlag<20)
			{
				//商店卡片为空
				if(picNum==0)
				{
					//出toast提示框
					ConstantUtil.CARDSELECTED_NUM=-1;
					CardSelectedFlag=false;
				}
				else
				{
					//判断点数是否足够买到卡片
					if(ConstantUtil.CardsPriceInt[cardsNumArray[ConstantUtil.CARDSELECTED_NUM]]<figureDianshu)
					{
						CardSelectedFlag=true;
						//人物持有卡片数+1
						figureHaveCardFlag++;
						//将购买的卡片图片编号存入数组
						for(int k=figureHaveCardFlag-1;k<figureHaveCardFlag;k++){
							figureCardNum[k]=cardsNumArray[ConstantUtil.CARDSELECTED_NUM];
						}
						//取消已购买的卡片
						int temp=0;
						for(int k=0;k<picNum-1;k++){
							if(k==ConstantUtil.CARDSELECTED_NUM){
								temp=2;
							}	
							if(temp==2){
								cardsNumArray[k]=cardsNumArray[k+1];
							}else{
								cardsNumArray[k]=cardsNumArray[k];
							}
						}
						//商店卡片数量-1
						picNum-=1;
						//更新人物点数
						figureDianshu-=ConstantUtil.CardsPriceInt[cardsNumArray[ConstantUtil.CARDSELECTED_NUM]];
					}
					else{
						//出toast提示点数不足
					}
					//回复卡片商店的第一张卡片为选中状态
					if(picNum==0)
					{
						ConstantUtil.CARDSELECTED_NUM=-1;
						CardSelectedFlag=false;
					}else{
						ConstantUtil.CARDSELECTED_NUM=0;
					}	
				}
			}
			//已购卡片数量>20，提示无法购买，先卖出
			if(x>ConstantUtil.CardDrawable_BuyCardButton_left_X&&x<ConstantUtil.CardDrawable_BuyCardButton_Right_X
					&&y>ConstantUtil.CardDrawable_BuyCardButton_Up_Y&&y<ConstantUtil.CardDrawable_BuyCardButton_Down_Y&&figureHaveCardFlag==20){
				//toast提示
			}
			//查看卡片按钮
			if(figureHaveCardFlag>5)
			{//已购买卡片>5，画箭头
				if(cardgoNum<0)
				{
					if(x>ConstantUtil.CardDrawable_CheckCardGo_left_X&&x<ConstantUtil.CardDrawable_CheckCardGo_Right_X
							&&y>ConstantUtil.CardDrawable_CheckCard_Up_Y&&y<ConstantUtil.CardDrawable_CheckCard_Down_Y){//cardgo
						cardgoNum+=1;
						CardSelectedFlag=true;
					}
				}
				if(x>ConstantUtil.CardDrawable_CheckCardBack_left_X&&x<ConstantUtil.CardDrawable_CheckCardBack_Right_X
						&&y>ConstantUtil.CardDrawable_CheckCard_Up_Y&&y<ConstantUtil.CardDrawable_CheckCard_Down_Y&&figureHaveCardFlag+cardgoNum>5)
				{//cardback
					cardgoNum-=1;
					CardSelectedFlag=true;
				}
			}
			//sale
			if(x>ConstantUtil.CardDrawable_SaleCardButton_Left_X&&x<ConstantUtil.CardDrawable_SaleCardButton_Right_X
					&&y>ConstantUtil.CardDrawable_SaleCardButton_Up_Y&&y<ConstantUtil.CardDrawable_SaleCardButton_Down_Y&&figureHaveCardFlag>=1&&!CardSelectedFlag){
				int saleFlag=0;
				if(figureHaveCardFlag<=5)
				{
					saleFlag=4-ConstantUtil.CARDSELECTED_NUM;
				}else
				{
					saleFlag=figureHaveCardFlag+cardgoNum-ConstantUtil.CARDSELECTED_NUM-1;
				}
		    	//卖出卡片，点数增加
		    	figureDianshu+=ConstantUtil.CardsPriceInt[figureCardNum[saleFlag]];
		    	//取消已卖出的卡片
		    	int temp=0;
		    	for(int i=0;i<figureHaveCardFlag-1;i++){
		    		if(i==saleFlag){
		    			temp=2;
		    		}
	    			if(temp==2){
	    				figureCardNum[i]=figureCardNum[i+1];
	    			}else{
	    				figureCardNum[i]=figureCardNum[i];
	    			}
		    	}
		    	figureCardNum[figureHaveCardFlag-1]=-1;
		    	figureHaveCardFlag-=1;
		    	if(picNum==0)
		    	{
		    		CardSelectedFlag=false;
		    		ConstantUtil.CARDSELECTED_NUM=4;
		    	}else
		    	{
		    		CardSelectedFlag=true;
		    		ConstantUtil.CARDSELECTED_NUM=0;
		    	}	
			}
			//卡片栏为空无法卖出
			if(x>ConstantUtil.CardDrawable_SaleCardButton_Left_X&&x<ConstantUtil.CardDrawable_SaleCardButton_Right_X
					&&y>ConstantUtil.CardDrawable_SaleCardButton_Up_Y&&y<ConstantUtil.CardDrawable_SaleCardButton_Down_Y&&figureHaveCardFlag<1)
			{
			}
			//EXIT
			if(x>ConstantUtil.CardDrawable_ExitCardShop_Left_X&&x<ConstantUtil.CardDrawable_ExitCardShop_Right_X
					&&y>ConstantUtil.CardDrawable_ExitCardShop_Up_Y&&y<ConstantUtil.CardDrawable_ExitCardShop_Down_Y){
				recoverGame();
			}
		}
		return true;
	}
	//方法：返还监听器，回复游戏状态
	public void recoverGame(){
		//更新人物卡片存储数组
		int flag=0;
		for(int i=0;i<20;i++)
		{
			if(i==figureHaveCardFlag)
				flag=1;
			if(flag==1)
				tempFigure.CardNum[i]=-1;
			else
				tempFigure.CardNum[i]=figureCardNum[i];
		}
		//更新人物点数
		tempFigure.mhz.gameDian=figureDianshu;
		ConstantUtil.CARDSELECTED_NUM=0;
		//恢复默认
		figureHaveCardFlag=0;
		picNum=0;
		cardgoNum=0;
		CardSelectedFlag=true;
		isDraw=false;
		cardSaleOut=false;
		FigureBuyCardFlag=false;
		FigureBuyCardFlagOther=false;
		figureDianshu=0;
		figureBuyCardNum=0;
		for(int i=0;i<20;i++){//临时存储已购卡片的数组置零
			figureCardNum[i]=-1;
		}
		for(int i=0;i<12;i++)
		{
			cardsNumArray[i]=-1;
		}
		tempFigure.father.setCurrentDrawable(null);//置空记录引用的变量
		tempFigure.father.setOnTouchListener(tempFigure.father);//返还监听器
		tempFigure.father.setStatus(0);//重新设置GameView为待命状态
		tempFigure.father.gvt.setChanging(true);//骰子转起来
	}
}
