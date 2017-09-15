package com.game.zillionaire.meetdrawable;

import java.io.Serializable;

import com.game.zillionaire.activity.ZActivity;
import com.game.zillionaire.card.TrapCard;
import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.map.Layer;
import com.game.zillionaire.map.MyDrawable;
import com.game.zillionaire.map.MyMeetableDrawable;
import com.game.zillionaire.thread.FigureGoThread;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.util.Room;
import com.game.zillionaire.util.ScreenTransUtil;
import com.game.zillionaire.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class MagicHouseDrawable extends MyMeetableDrawable implements Serializable{
	String[] dialogMessage={"欢迎来到魔法屋~","我选出符合条件的人，你来决定他们的命运~","","",""};//显示字符串
	String[] randomMessage={"土地最多的人~","存款最多的人~","现金最多的人~","走路的人~","卡片最多的人~","女人~"};//条件
	String[] conditionMessage={"坐牢五天~","住进医院三天~","建一层房子~","拆一层房子~","原地停留一步~","掉个头~","变卖所有卡片~",
			"拍卖所在地的房屋~","将所有现金存进银行~","获得一张卡片~"};
	static String[] magicPicName={"magic01","magic01","magic01","magic02","magic03",
		"magic04","magic05","magic06","magic07","magic08","magic09","magic10","magic11","magic12","magic13"};
	static Bitmap[] bm=new Bitmap[magicPicName.length];
	static String[] touPicName={"tou0","tou2","tou1"};//加载人物头像
	static Bitmap[] touPic=new Bitmap[touPicName.length];//加载人物头像
	int status=0;//变量 画图
	boolean isOnTouch=false;//是否可以触摸
	boolean isDrawResult=false;//是否显示选择结果
	boolean isDraw=true;//是否开始画图
	int drawIndex=-1;//画第几张图
	int nowMoney=0;//需要处理的钱
	public static PoliceCarAnimation police=null;//警车类对象
	
	static Figure resultFigure=null;
	public static int[] figureResult=new int[3];
	public static int figureLandValue=0;
	//英雄1：孙小美 英雄2:金贝贝 英雄3：阿土伯
	
	public MagicHouseDrawable(){}
	public MagicHouseDrawable(ZActivity at,String bmpSelf,String dbitmap,String bmpDialogBack,boolean meetable,int width,int height,int col,int row,
			int refCol,int refRow,int [][] noThrough,int [][] meetableMatrix,int da) {
		super(at,bmpSelf,dbitmap, col, row, width, height, refCol, refRow, noThrough, meetable,
				meetableMatrix, da,bmpDialogBack);
	}
	@Override 
	public void drawDialog(Canvas canvas,Figure figure) {
		tempFigure=figure;
		for(int i=0;i<bm.length;i++){
			bm[i]=PicManager.getPic(magicPicName[i],tempFigure.father.activity.getResources());
		}
		for(int i=0;i<touPic.length;i++){	//加载人物头像
			touPic[i]=PicManager.getPic(touPicName[i],tempFigure.father.activity.getResources());
		}
		String showString=null;//当前需要显示的字符串
		dialogMessage[2]=randomMessage[figure0MagicRandom];
		figureResult=selectFigure(figure0MagicRandom);
		if(figure.father.isFigure==0)//如果当前是操作人物
		{
			if(status>0&&status<4){//显示第一张到第四张图片中间间隔一秒
				try{
					Thread.sleep(1000);//间隔有一秒
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			if(status == 3){//第三张的时候 等待用户点击选择
				canvas.drawBitmap(bm[status], 210, 13,null);
				for(int index:figureResult)//画人物头像
				{
					if(index!=-1)
					{
						canvas.drawBitmap(touPic[index], 380, 180, null);
					}
				}
				isOnTouch=true;//添加点击监听方法
				return;
			}
			if(status >=0 && status < 4)//画第一张到第三张图片
			{
				showString=dialogMessage[status];
				canvas.drawBitmap(bm[status], 200, 10, null);
				drawString(canvas,showString);
				status++;//变量自加
			}
			if(isDrawResult)//点击选择任意一项
			{
				canvas.drawBitmap(bm[4], 210, 13,null);//画背景框
				canvas.drawBitmap(bm[drawIndex], 210,13, null);//选择的图片亮起来
			}
		}else//如果当前是系统人物
		{
			showString=dialogMessage[2]+" "+conditionMessage[figure1MagicRandom];
			drawIndex=figure1MagicRandom+5;
			magicFunction(drawIndex);//根据索引值实现选择的功能
			recoverGame();//返回游戏
		}
	}
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		int x=ScreenTransUtil.xFromRealToNorm((int)event.getX());//得到点击的X坐标
		int y=ScreenTransUtil.yFromRealToNorm((int)event.getY());//得到点击的Y坐标
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			if(isOnTouch)
			{
				if(x>=ConstantUtil.MagicHouseDrawable_Police_Left_X&&x<=ConstantUtil.MagicHouseDrawable_Police_Right_X
						&&y>=ConstantUtil.MagicHouseDrawable_Police_Up_Y&&y<=ConstantUtil.MagicHouseDrawable_Police_Down_Y)//点击警车图标
				{
					isDrawResult=true;//画已经选择的图片
					status=-1;
					drawIndex=5;//画警车图片
					magicFunction(drawIndex);
					//坐牢三天
				}else if(x>=ConstantUtil.MagicHouseDrawable_Ambulance_Left_X&&x<=ConstantUtil.MagicHouseDrawable_Ambulance_Right_X
						&&y>=ConstantUtil.MagicHouseDrawable_Ambulance_Up_Y&&y<=ConstantUtil.MagicHouseDrawable_Ambulance_Down_Y)//点击救护车图标
				{
					isDrawResult=true;
					status=-1;
					drawIndex=6;//画救护车图片
					magicFunction(drawIndex);
					//住医院三天
				}else if(x>=ConstantUtil.MagicHouseDrawable_BuildHouse_Left_X&&x<=ConstantUtil.MagicHouseDrawable_BuildHouse_Right_X
						&&y>=ConstantUtil.MagicHouseDrawable_BuildHouse_Up_Y&&y<=ConstantUtil.MagicHouseDrawable_BuildHouse_Down_Y)//点击建房子图标
				{
					isDrawResult=true;
					status=-1;
					drawIndex=7;//画建房子图片
					magicFunction(drawIndex);
					//当前位置房子加盖一层
				}else if(x>=ConstantUtil.MagicHouseDrawable_ExcreteHouse_Left_X&&x<=ConstantUtil.MagicHouseDrawable_ExcreteHouse_Right_X
						&&y>=ConstantUtil.MagicHouseDrawable_ExcreteHouse_Up_Y&&y<=ConstantUtil.MagicHouseDrawable_ExcreteHouse_Down_Y)//点击拆房子图标
				{
					isDrawResult=true;
					status=-1;
					drawIndex=8;//画拆房子图片
					//就地拆毁房屋
					magicFunction(drawIndex);
				}else if(x>=ConstantUtil.MagicHouseDrawable_Stayforatime_Left_X&&x<=ConstantUtil.MagicHouseDrawable_Stayforatime_Right_X
						&&y>=ConstantUtil.MagicHouseDrawable_Stayforatime_Up_Y&&y<=ConstantUtil.MagicHouseDrawable_Stayforatime_Down_Y)//点击原地停留图标
				{
					isDrawResult=true;
					status=-1;
					drawIndex=9;//画原地停留图片
					//原地停留一次
					magicFunction(drawIndex);
				}else if(x>=ConstantUtil.MagicHouseDrawable_TurnTo_Left_X&&x<=ConstantUtil.MagicHouseDrawable_TurnTo_Right_X
						&&y>=ConstantUtil.MagicHouseDrawable_TurnTo_Up_Y&&y<=ConstantUtil.MagicHouseDrawable_TurnTo_Down_Y)//点击转换方向图标
				{
					isDrawResult=true;
					status=-1;
					drawIndex=10;//画转向图片
					magicFunction(drawIndex);
					//转方向
				}else if(x>=ConstantUtil.MagicHouseDrawable_BianSaleCard_Left_X&&x<=ConstantUtil.MagicHouseDrawable_BianSaleCard_Right_X
						&&y>=ConstantUtil.MagicHouseDrawable_BianSaleCard_Up_Y&&y<=ConstantUtil.MagicHouseDrawable_BianSaleCard_Down_Y)//点击变卖卡片图标
				{
					isDrawResult=true;
					status=-1;
					drawIndex=11;//画变卖卡片图片
					magicFunction(drawIndex);
					//变卖手中所有卡片
				}else if(x>=ConstantUtil.MagicHouseDrawable_PaiSaleCard_Left_X&&x<=ConstantUtil.MagicHouseDrawable_PaiSaleCard_Right_X
						&&y>=ConstantUtil.MagicHouseDrawable_PaiSaleCard_Up_Y&&y<=ConstantUtil.MagicHouseDrawable_PaiSaleCard_Down_Y)//点击拍卖图标
				{
					isDrawResult=true;
					status=-1;
					drawIndex=12;//画拍卖图片
					magicFunction(drawIndex);
					//拍卖房屋
				}else if(x>=ConstantUtil.MagicHouseDrawable_Save_Left_X&&x<=ConstantUtil.MagicHouseDrawable_Save_Right_X
						&&y>=ConstantUtil.MagicHouseDrawable_Save_Up_Y&&y<=ConstantUtil.MagicHouseDrawable_Save_Down_Y)//点击存钱图标
				{
					isDrawResult=true;
					status=-1;
					drawIndex=13;//画存钱图片
					magicFunction(drawIndex);
					//将所有现金存入银行
				}else if(x>=ConstantUtil.MagicHouseDrawable_Card_Left_X&&x<=ConstantUtil.MagicHouseDrawable_Card_Right_X
						&&y>=ConstantUtil.MagicHouseDrawable_Card_Up_Y&&y<=ConstantUtil.MagicHouseDrawable_Card_Down_Y)//点击卡片图标
				{
					isDrawResult=true;
					status=-1;
					drawIndex=14;//画卡片图片
					magicFunction(drawIndex);
					//随机得到一张卡片
				}
				try{//歇一秒
					Thread.sleep(500);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				if(drawIndex!=12)//如果不用拍卖
				{
					recoverGame();//就返回游戏
				}
			}
		}
		return true;
	}
	@SuppressWarnings("static-access")
	public void magicFunction(int index)//点击魔法屋各项的功能
	{
		switch(index)//点击第几张图
		{
		case 5://点击警车图片
			if(resultFigure!=null)
			{
				police=new PoliceCarAnimation(resultFigure);
				police.startAnimation();
				police.isPolice=true;//播放警车动画
				resultFigure.day=3;
				resultFigure.father.showDialog.day=3;
			}
			break;
		case 6://点击救护车图片
			if(resultFigure!=null)
			{
				police=new PoliceCarAnimation(resultFigure);
				police.startAnimation();
				police.isAmbulance=true;//播放救护车动画
				resultFigure.day=3;
				resultFigure.father.showDialog.day=3;
			}
			break;
		case 7://建房子
			Room.addRoom(resultFigure);
			break;
		case 8://拆房子
			Room.cutRoom(resultFigure);
			break;
		case 9://原地停留
			if(resultFigure.k==tempFigure.father.figure0.k)
			{
				resultFigure.father.indext=0;
				resultFigure.startToGo(indext);
			}else
			{
				tempFigure.father.gvt.setChanging(true);//启动人物变换线程
			}
			break;
		case 10:
			int col = resultFigure.col;
			int row = resultFigure.row;
			Layer l = (Layer)resultFigure.father.layerList.layers.get(0);//获得底层的图层
			MyDrawable[][] mapMatrix=l.getMapMatrix();
			switch((Math.abs(resultFigure.direction-3))%4)
			{
			case 0://向下
				if(resultFigure.father.notInMatrix[row+1][col] == 0&&mapMatrix[row+1][col].indext==1)//检查下边是否可通过
				{
					resultFigure.direction=(Math.abs(resultFigure.direction-3))%4;//设置为静态向下
				}
				break;
			case 1://向左
				if(resultFigure.father.notInMatrix[row][col-1] == 0&&mapMatrix[row][col-1].indext==1)//检查左边是否可通过
				{
					resultFigure.direction=(Math.abs(resultFigure.direction-3))%4;//设置为静态向左
				}
				break;
			case 2://向右
				if(resultFigure.father.notInMatrix[row][col+1] == 0&&mapMatrix[row][col+1].indext==1)//检查右边是否可通过
				{
					resultFigure.direction=(Math.abs(resultFigure.direction-3))%4;//设置为静态向右
				}
				break;
			case 3://向上
				if(resultFigure.father.notInMatrix[row-1][col] == 0&&mapMatrix[row-1][col].indext==1)//检查上边是否可通过
				{
					resultFigure.direction=(Math.abs(resultFigure.direction-3))%4;//设置为静态向上
				}
				break;
			}
			break;
		case 11://变卖所有卡片
			for(int i=0;i<resultFigure.CardNum.length;i++)
			{
				if(resultFigure.CardNum[i]==-1)
					break;
				else{
					resultFigure.mhz.gameDian+=ConstantUtil.CardsPriceInt[i];
					resultFigure.CardNum[i]=-1;
				}
			}
			break;
		case 12://点击拍卖图标
			recoverGame();//返回游戏
			break;
		case 13://将所有现金存入银行
			nowMoney=resultFigure.mhz.xMoney;
			resultFigure.mhz.xMoney=0;
			resultFigure.mhz.zMoney+=nowMoney;
			break;
		case 14://随机获得一张卡片
			for(int i=0;i<resultFigure.CardNum.length;i++)
			{
				if(resultFigure.CardNum[i]==-1)
				{
					resultFigure.CardNum[i]=(int)Math.random()*21;
					break;
				}
			}
			break;
		}
	}
	public int[] selectFigure(int random)//根据随机产生的条件选择对应的人物
	{
		int result[] = new int[3];
		for(int i=0;i<result.length;i++)
		{
			result[i]=-1;
		}
		switch(random)
		{
		case 0://土地最多的人
			result=getResult
			(tempFigure.father.figure.count,tempFigure.father.figure1.count,tempFigure.father.figure2.count);
			break;
		case 1://存款最多的人
			result=getResult
			(tempFigure.father.figure.mhz.zMoney,tempFigure.father.figure1.mhz.zMoney,tempFigure.father.figure2.mhz.zMoney);
			break;
		case 2://现金最多的人
			result=getResult
			(tempFigure.father.figure.mhz.xMoney,tempFigure.father.figure1.mhz.xMoney,tempFigure.father.figure2.mhz.xMoney);
			break;
		case 3://走路的人
			result[2]=2;//金贝贝
			break;
		case 4://卡片最多的人
			int figure0CardCount=figureCardCount(tempFigure.father.figure.CardNum);//获得英雄1的卡片总数
			int figure1CardCount=figureCardCount(tempFigure.father.figure1.CardNum);//获得英雄2的卡片总数
			int figure2CardCount=figureCardCount(tempFigure.father.figure2.CardNum);//获得英雄3的卡片总数
			result=getResult
			(figure0CardCount,figure1CardCount,figure2CardCount);//求得最大值 比较谁的卡片最多
			break;
		case 5://女人
			result[1]=1;//金贝贝
			break;
		}
		return result;
	}
	public int figureCardCount(int[] cardNum)//获得每个英雄的卡片总数
	{
		int result=0;
		for(int index:cardNum)//循环遍历卡片数组
		{
			if(index!=-1)//如果有卡片
			{
				result++;//计数器加1
			}
		}
		return result;//返回总卡片数
	}
	public int[] getResult(int x,int y,int z)//根据给的条件选出对应的英雄
	{
		int result[] = new int[3];
		int figure0=-1;
		int figure1=-1;
		for(int i=0;i<result.length;i++){//赋值-1
			result[i]=-1;
		}
		if(x>y)//如果人物1>人物2
		{
			figure0=x;//记录最大值
			result[0]=0;//记录第一个人物
			resultFigure=tempFigure.father.figure;
		}else
		{
			figure1=y;//记录最小值
			result[1]=1;//记录第二个人物
			resultFigure=tempFigure.father.figure1;
		}
		if(figure0!=-1)//如果最大值是人物1
		{
			if(figure0<z)//如果人物1<人物3
			{
				result[0]=-1;
				result[2]=2;//记录第三个人物
				resultFigure=tempFigure.father.figure2;
			}
		}else if(figure1!=-1)//如果最大值是人物2
		{
			if(figure1<z)//如果人物2<人物3
			{
				result[1]=-1;
				result[2]=2;//记录第3个人物
				resultFigure=tempFigure.father.figure2;
			}
		}
		return result;
	}
	public void drawString(Canvas canvas,String string){//将字符串写到背景框中
		Paint paint = new Paint();
		paint.setARGB(255, 0,0,0);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setTextSize(22);//设置文字大小
		int lines = string.length()/11+(string.length()%11==0?0:1);//求出需要画几行文字
		for(int i=0;i<lines;i++){
			String str="";
			if(i == lines-1){//如果是最后一行那个不太整的汉字
				str = string.substring(i*11);
			}
			else{
				str = string.substring(i*11, (i+1)*11);
			}
			canvas.drawText(str, 300, 335+22*i, paint);//将文字画到背景框中
		}
	}
	public void recoverGame()//返回游戏
	{
		status=0;//画图状态还原
		isOnTouch=false;//是否触碰还原
		isDrawResult=false;//是否画 还原
		nowMoney=0;//当前钱数还原
		if(drawIndex!=9)//如果没有点击原地停留
		{
			tempFigure.father.setCurrentDrawable(null);//置空记录引用的变量
			tempFigure.father.setOnTouchListener(tempFigure.father);//返还监听器
			tempFigure.father.setStatus(0);//重新设置GameView为待命状态
			if(drawIndex==-1||drawIndex==7||drawIndex==8||drawIndex==10||drawIndex==11||drawIndex==12||drawIndex==13||drawIndex==14)
			{
				tempFigure.father.gvt.setChanging(true);//启动人物变换线程
			}
		}else
		{
			if(resultFigure.k!=tempFigure.father.isFigure)
			{
				tempFigure.father.setCurrentDrawable(null);//置空记录引用的变量
				tempFigure.father.setOnTouchListener(tempFigure.father);//返还监听器
				tempFigure.father.setStatus(0);//重新设置GameView为待命状态
				tempFigure.father.gvt.setChanging(true);//启动人物变换线程
			}
		}
	}
	//绘制警车类/救护车类
	public void drawPoliceCar(Canvas canvas,GameView gameview,Figure figure,Figure figure1,Figure figure2)
	{
		resultFigure=null;//符合条件的人物
		for(int i=0;i<figureResult.length;i++)
		{
			if(figureResult[i]!=-1)//如果不等于-1
			{
				if(i==0)
				{
					resultFigure=figure;//记录 人物1
				}else if(i==1)
				{
					resultFigure=figure1;//记录 人物2
				}else if(i==2)
				{
					resultFigure=figure2;//记录 人物3
				}
			}
		}
		Figure nowFigure=null;
		if(gameview.isFigure==0)//如果当前是人物1
		{
			nowFigure=figure;
		}else if(gameview.isFigure==1)//如果当前是人物2
		{
			nowFigure=figure1;
		}else if(gameview.isFigure==2)//如果当前是人物3
		{
			nowFigure=figure2;
		}else if(gameview.isFigure==10||gameview.isFigure==11)//如果当前是监狱/医院
		{
			nowFigure=null;
		}
		if(PoliceCarAnimation.isPolice)//如果点击了警车图标
		{
			if(resultFigure!=null)//如果有符合条件的人物
			{
				if(nowFigure!=null)//如果当前人物不为空
				{
					if(police!=null)//如果是魔法屋调用警车类
					{
						gameview.isFigure=resultFigure.k;//将地图换到符合条件的人物
						police.drawSelf(canvas,10,resultFigure.topLeftCornerY-20,resultFigure.topLeftCornerX);//画警车
					}
					if(TrapCard.police!=null){//如果使用卡片调用警车类
						TrapCard.police.drawSelf(canvas, 10,TrapCard.police.tempFigure.topLeftCornerY-20,TrapCard.police.tempFigure.topLeftCornerX);
					}
				}
				else//如果当前人物为空（在监狱）
				{
					if(police!=null)//如果是魔法屋调用警车类
					{
						police.drawSelf(canvas,10,150,250);//监狱坐标
					}
					if(TrapCard.police!=null){//如果使用卡片调用警车类
						TrapCard.police.drawSelf(canvas, 10,150,250);
					}
				}
			}
		}
		if(PoliceCarAnimation.isAmbulance)//如果点击了救护车图标
		{
			if(resultFigure!=null)//如果符合条件的人物不为空
			{
				if(nowFigure!=null)//如果当前人物不为空
				{
					if(police!=null)//如果是魔法屋调用救护车类
					{
						gameview.isFigure=resultFigure.k;//将地图换到符合人物那
						police.drawSelf(canvas,10,resultFigure.topLeftCornerY-20,resultFigure.topLeftCornerX);//画救护车
					}else if(gameview.aa!=null&&gameview.aa.police!=null)// if(DogGodAnimation.police!=null)
					{
						gameview.aa.police.drawSelf(canvas, 10,gameview.figure0.topLeftCornerY-20,nowFigure.topLeftCornerX);				
					}else
					{
						FigureGoThread.police.drawSelf(canvas,10,FigureGoThread.police.tempFigure.topLeftCornerY-20,FigureGoThread.police.tempFigure.topLeftCornerX);//画救护车
					}
				}else//如果当前人物为空（医院)
				{
					if(police!=null)//
					{
						police.drawSelf(canvas,10,150,380);//医院坐标
					}else if(gameview.aa!=null&&gameview.aa.police!=null)//(DogGodAnimation.police!=null)
					{
						gameview.aa.police.drawSelf(canvas,10,150,380);
					}else
					{
						FigureGoThread.police.drawSelf(canvas,10,150,380);
					}
				}
			}
		}
	}
}
