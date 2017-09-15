package com.game.zillionaire.util;

import static com.game.zillionaire.util.ConstantUtil.TILE_SIZE_X;
import static com.game.zillionaire.util.ConstantUtil.TILE_SIZE_Y;
import static com.game.zillionaire.util.ConstantUtil.hm;
import static com.game.zillionaire.util.ConstantUtil.translateString;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.game.zillionaire.card.UseCardView;
import com.game.zillionaire.card.UsedCard;
import com.game.zillionaire.dialog.DialogEnum;
import com.game.zillionaire.figure.CrashFigure;
import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.map.MyDrawable;
import com.game.zillionaire.view.GameView;

public class GameViewUtil {
	GameView gv;
	public Date d;//日期类
	public Calendar cl;
	boolean isCheck=false;//是否查看标志位      --false不查看   --true查看
	
	public int count0=0;//计数(消失)
	public Bitmap[] gos=new Bitmap[2];//甩骰子图标
	public Bitmap bMenu;//游戏主界面的菜单
	public Bitmap shezhi;//设置图标，菜单中第四个选项
	public Bitmap check;//查看	,菜单中第三个选项  
	public Bitmap kaPianZero;
	public Bitmap dialogBack;//背景图片
	public Bitmap go;//甩骰子图标1
	public Bitmap sDate;//日期
	public GameViewUtil(GameView gv)
	{
		this.gv=gv;
		cl=Calendar.getInstance();//获取当前时间
		cl.clear();
		cl.set(2014, 00, 01);
	}
	
	//初始化游戏部分图片
	public void initBitmap()
	{	
		bMenu=PicManager.getPic("bmenu",gv.activity.getResources());//菜单
		kaPianZero=PicManager.getPic("kapianzero",gv.activity.getResources());
		dialogBack=PicManager.getPic("dialog_back",gv.activity.getResources());//对话框背景
		shezhi=PicManager.getPic("shezhi",gv.activity.getResources());//设置
		check=PicManager.getPic("check",gv.activity.getResources());
		gos[0]=PicManager.getPic("go",gv.activity.getResources());//Go图标
		gos[1]=PicManager.getPic("go1",gv.activity.getResources());//Go图标(乌龟)
		sDate=PicManager.getPic("date",gv.activity.getResources());
		go=gos[0];//初始化骰子图片   
	}
	
	@SuppressLint("SimpleDateFormat")
	public void setWeekend(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");  
        String week = sdf.format(date);  
        if(week.equals("星期六")||week.equals("星期日"))
        {
        	gv.isWeekend=true;//是周末
        }else{
        	gv.isWeekend=false;//不是周末
        }
	}
	//绘制同盟标志
	public void drawAlliance(Canvas canvas)
	{
		if(gv.count==2)
		{
			gv.isDrawAlliance=false;
		}
		if(gv.isDrawAlliance)
		{
			canvas.drawBitmap(UseCardView.alliance, 153, 360, null);
			if(UsedCard.currentFigure==0)
			{
				canvas.drawBitmap(UseCardView.allianceTou[0], 160, 360, null);
			}else if(UsedCard.currentFigure==1)
			{
				canvas.drawBitmap(UseCardView.allianceTou[1], 160, 360, null);
			}else if(UsedCard.currentFigure==1)
			{
				canvas.drawBitmap(UseCardView.allianceTou[2], 160, 360, null);
			}
		}
	}
	
	//绘制日期
	public void drawDate(Canvas canvas)
	{
		canvas.drawBitmap(sDate, 0,0,  null);//绘制图片
		d=DateUtil.getDateAfter(cl.getTime(),gv.count);//获取n天后的日期
		String str=DateUtil.getWeekAndYMD(d);//获取指定形式的字符串
		DateUtil.drawString(canvas,str);//绘制指定形式的字符串
		gv.date=DateUtil.getDate(str);//获得日期
		setWeekend(d);//判断是否是周末
	}
	
	//方法：绘制游戏界面菜单
	public void drawMenu(Canvas canvas)
	{
		if(gv.isMenu)
		{
			canvas.drawBitmap(bMenu, 776,0,  null);
			int cardCount=0;
			for(int index:gv.figure.CardNum)
			{
				if(index!=-1)//如果索引值等于-1
				{
					cardCount++;//计数器加1
				}
			}
			if(cardCount==0)
			{
				canvas.drawBitmap(kaPianZero, 771,0, null);
			}
			if(gv.isSheZhi)
			{						
				canvas.drawBitmap(shezhi, 396,210 ,null);
			}
		}
	}
	//绘制左下角人物头像
	public void drawTouImage(Canvas canvas)
	{
		if(canvas==null)
		{
			return ;
		}
		Bitmap bmpSelf=PicManager.getPic(gv.figure0.tImageName, gv.activity.getResources());
		canvas.drawBitmap(bmpSelf, 0, 310, null);
		int[] shuzi=new int[4];
		shuzi[0]=gv.figure0.mhz.xMoney;
		shuzi[1]=gv.figure0.mhz.cMoney;
		shuzi[2]=gv.figure0.mhz.zMoney;
		Paint paint = new Paint();
		paint.setARGB(255, 255, 255, 0);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setTextSize(22);//设置文字大小
		paint.setFakeBoldText(true);//字体加粗
		canvas.drawText(translateString(shuzi[0]+""),220,472,paint);
		canvas.drawText(translateString(shuzi[1]+""),390,472,paint);
		canvas.drawText(translateString(shuzi[2]+""),560,472,paint);
	}
	//添加碰撞人物
	public void addCrashFigure(MyDrawable myDrawable,int x,int y)
	{
		if(myDrawable.indext==1&&y!=8&&y!=9&&x!=4&&x!=9)
		{
			int refCol=myDrawable.refCol;
			int refRow=myDrawable.refRow;
			int rw1=(int)(2*Math.random());
			int rw2=(int)(6*Math.random());
			Bitmap temp=CrashFigure.figureBitmap[rw1][rw2];
			gv.cfigure.add(new CrashFigure(refCol,refRow,rw1,rw2,x,y,hm.get(temp),myDrawable));
			myDrawable.flag=true;//标志路面上是否有物体
		}
	}
	//绘制go图标
	public void drawGo(Canvas canvas)
	{
		if(!gv.isDraw)
		{
			if(gv.figure0.isWG)
			{
				canvas.drawBitmap(gos[1], 700, 320, null);
			}else
			{
				canvas.drawBitmap(go, 700, 320, null);
			}
		}		
	}
	
	//方法：绘制Check
	public void drawCheck(Canvas canvas)
	{
		if(isCheck)
		{//true绘制Check
			canvas.drawBitmap(check, 80,0 ,null);
			gv.isMenu=false;
		}	
	}
	public void drawFigure(Canvas canvas,Figure figure)//绘制人物头上的物体
	{
		int x=0,y=0;
		Figure tempFigure=figure;
		x=tempFigure.topLeftCornerX;
		y=tempFigure.topLeftCornerY;
		if(tempFigure.isZD)//绘制炸弹
		{
			canvas.drawBitmap(UseCardView.bitmaps[0],x,y-50,null);
		}
	}
	
	//掷骰子
	public void isGoDice(Figure figure)
	{
		if(!figure.isDreaw)//消失的天数
		{
			if(figure.day<=1)
			{
				count0=0;
				figure.day=0;
				figure.isDreaw=true;
				figure.isWhere=-1;
				//初始化人物坐标
				figure.topLeftCornerX=figure.x-TILE_SIZE_X/2-1 -gv.tempStartCol*TILE_SIZE_X-gv.tempOffsetX-20;
				figure.topLeftCornerY=figure.y+TILE_SIZE_Y/2-95-gv.tempStartRow*TILE_SIZE_Y-gv.tempOffsetY-22;
				gv.showDialog.day=figure.day;//信息天数
				gv.isGoTo=true;
				figure.isStop=false;
				if(figure.k==0)
				{
					gv.isMenu=true;//绘制菜单
					gv.isDraw=false;//绘制GO图标
					gv.isYuanBao=false;//显示元宝图标
					return;
				}
			}
		}else if(!gv.isGoTo)//停留一个回合
		{
			if(count0>=3)
			{
				count0=0;
				gv.isGoTo=true;
			}
		}
		gv.isMenu=false;//不绘制菜单
		gv.isDraw=true;//不绘制GO图标
		gv.isYuanBao=false;//不显示元宝图标
		gv.indext=(int)(6*Math.random());
		if(!gv.isGoTo||(!figure.isDreaw))//停留一回合  消失
		{
			gv.indext=-1;
			if(!gv.isGoTo)
			{
				count0++;
			}
			if(!figure.isDreaw)
			{
				figure.day--;
				gv.showDialog.day=figure.day;//信息天数
			}
			if(!figure.isDreaw)
			{
				if(figure.isWhere==0)//医院
				{
					gv.showDialog.initMessage(figure.figureName,figure.k,true,false,DialogEnum.MESSAGE_ONE,6,7);
					gv.isDialog=true;//绘制对话框
				}else if(figure.isWhere==1)//监狱
				{
					gv.showDialog.initMessage(figure.figureName,figure.k,true,false,DialogEnum.MESSAGE_ONE,9,10);
					gv.isDialog=true;//绘制对话框
				}else if(figure.isWhere==2)//住店中
				{
					gv.showDialog.initMessage(figure.figureName,figure.k,true,false,DialogEnum.MESSAGE_ONE,31,-1);
					gv.isDialog=true;//绘制对话框
				}else if(figure.isWhere==4)//外星人带走
				{
					gv.showDialog.initMessage(figure.figureName,figure.k,true,false,DialogEnum.MESSAGE_ONE,32,-1);
					gv.isDialog=true;//绘制对话框
				}
			}
		}else if(figure.isWG)//使用了乌龟卡
		{
			gv.indext=0;
			figure.day--;
			if(figure.day<=0)
			{
				if(figure.isWG)
				{
					figure.day=0;
					this.go=gos[0];
					figure.isWG=false;
				}
			}
		}else if(figure.isStop)//停止一次
		{
			gv.indext=-1;
			figure.day--;
			
			gv.showDialog.initMessage(figure.figureName,figure.k,true,true,DialogEnum.MESSAGE_ONE,19,-1);
			gv.isDialog=true;//绘制对话框
			if(figure.isStop)
			{
				if(figure.day==0)
				{
					figure.day=0;
					figure.isStop=false;
				}
			}
		}
		gv.di.setBitmap(figure.topLeftCornerX,figure.topLeftCornerY);
	}
}
