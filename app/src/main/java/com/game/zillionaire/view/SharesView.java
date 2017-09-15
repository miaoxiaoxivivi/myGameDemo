package com.game.zillionaire.view;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.util.ScreenTransUtil;
import com.game.zillionaire.util.SharesDetails;

public class SharesView implements View.OnTouchListener{
	public static Bitmap bm;//股票背景图
	public static Bitmap sharescontent;//股票详细图
	public static Bitmap buyOrOut[]=new Bitmap[2];//购买股票
	public static Bitmap kuangjia;//股票框架
	public static Bitmap lookshares;//查看持股数
	
	public static String numbers;//购买股票的股数
	GameView gv;
	int status=0;//绘制股票下面显示界面 
	public int sharesnum=7;//定义有几支股票
	public String str[][]=new String[sharesnum][6];//显示股票信息
	StringBuffer sb=new StringBuffer("0");//贷款金额的字符串
	public double price=55.5;//成交价-----------------------------------------------------
	public int jiaoYiNuber=100;//交易量----------------------------------------------------
	public int chiGu=0;//持股数-------------------------
    public String name=null;//名称
	
	public static Bitmap buyshares;//购买股票
	
	public String Str[][]=new String[sharesnum][6];//显示股票信息
	float eventY=0;//按下时Y值
	float eventX=0;//按下时X值
	public int lineNumber=0;//首行行号
	int lineHeight;//每行高度
	public int offset=0;//偏移量
	int height=280;//显示股票信息的位置高度
	int offTop;//显示区域距屏幕上边距离
	int screenHeight;//显示区域高度
	public SharesDetails sd;//获得股票基本信息的引用
	int startY=50;//绘制股票信息开始的y坐标
	int leftTemp=80;//距左边的距离
	int isshares=0;//判断点击的是那个股票
	int lineTemp;//当前行的行号
	ArrayList<String[]> as=new ArrayList<String[]>();//获得每个人物的持股数
	boolean isBuy=false;//是否买进股票
	Bitmap [] figureHead=new Bitmap[3];
	String[][] perShares=new String[sharesnum][5];
	
	public SharesView(GameView gv)
	{
		this.gv = gv;//activity的引用
		screenHeight=height;//显示股票信息的位置高度
		
		offTop=200+ConstantUtil.LOY;//开始显示股票信息的位置
		lineHeight=screenHeight/4;//每支股票信息界面所占的屏幕高度
		sd=new SharesDetails(gv);//给SharesDetails的对象赋值
	}
	
	public void initBitmap()//初始化图片
	{
		bm=PicManager.getPic("sharesback",gv.activity.getResources());
		sharescontent=PicManager.getPic("show_sharescontents",gv.activity.getResources());
		buyOrOut[0]=PicManager.getPic("buyin",gv.activity.getResources());
		buyOrOut[1]=PicManager.getPic("buyout",gv.activity.getResources());
		kuangjia=PicManager.getPic("kuangjia",gv.activity.getResources());
		lookshares=PicManager.getPic("lookshares",gv.activity.getResources());
		
		figureHead[0]=PicManager.getPic(gv.figure.headBitmapName,gv.activity.getResources());
		figureHead[1]=PicManager.getPic(gv.figure1.headBitmapName,gv.activity.getResources());
		figureHead[2]=PicManager.getPic(gv.figure2.headBitmapName,gv.activity.getResources());
	}
	public void onDraw(Canvas canvas)
	{
		if(canvas==null)
		{
			return;
		}
		initBitmap();//初始化图片
		getSharesPer();//获得每个人的股票数
		for(int i=0;i<perShares.length;i++)//获得每支股票的信息
		{
			perShares[i][0]=sd.datails.get(i)[0];//获得股票名称
			perShares[i][1]=as.get(0)[i];//获得玩家
			perShares[i][2]=as.get(1)[i];		//系统人物1
			perShares[i][3]	=as.get(2)[i];		//系统人物2
			perShares[i][4]	=sd.datails.get(i)[3];	//股票交易量
		}
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setTextSize(19);//设置文字大小
		paint.setFakeBoldText(true);//字体加粗
		//刷新股票信息
		if(this.status==0)//显示股票信息主界面
		{
			canvas.drawBitmap(bm, 0, 0, null);//先画背景
			canvas.drawText(gv.figure.mhz.cMoney+"",588,50, paint);//绘制操作人物的总存款
			canvas.save();
			canvas.clipRect(leftTemp, offTop, 900, 720);
			int topBase=(offset>=0)?offset%lineHeight:(offset%lineHeight+lineHeight);//首只股票显示信息距股票界面最上面的距离
			int lineTemp=lineNumber-((offset>=0)?offset/lineHeight:(offset/lineHeight-1));//滑动的行数
			
			if(lineTemp-1>=0&&lineTemp-1<sharesnum)
			{
				int topTemp1=offTop+topBase-lineHeight;//显示股票信息界面的第一支股票位置
				canvas.drawBitmap(sharescontent, leftTemp, topTemp1, null);
				for(int m=0;m<sd.datails.get(lineTemp-1).length;m++)//绘制股票信息
				{
					String str=sd.datails.get(lineTemp-1)[m];//获得股票基本信息
					canvas.drawText(str,130+110*m,topTemp1+startY, paint);
					str=null;
				}
				
			}
			if(lineTemp>=0&&lineTemp<sharesnum)
			{
				int topTemp2=offTop+topBase;//显示股票信息界面的第二支股票位置
				canvas.drawBitmap(sharescontent, leftTemp, topTemp2, null);
				for(int m=0;m<sd.datails.get(lineTemp).length;m++)//绘制股票信息
				{
					String str=sd.datails.get(lineTemp)[m];//获得股票基本信息
					canvas.drawText(str,130+110*m,topTemp2+startY, paint);
					str=null;
				}
				
			}
			if(lineTemp+1>=0&&lineTemp+1<sharesnum)
			{
				int topTemp3=offTop+topBase+lineHeight;//显示股票信息界面的第三支股票位置
				canvas.drawBitmap(sharescontent, leftTemp, topTemp3, null);
				for(int m=0;m<sd.datails.get(lineTemp+1).length;m++)//绘制股票信息
				{
					String str=sd.datails.get(lineTemp+1)[m];//获得股票基本信息
					canvas.drawText(str,130+110*m,topTemp3+startY, paint);
					str=null;
				}
				
			}
			if(lineTemp+2>=0&&lineTemp+2<sharesnum)
			{
				int topTemp4=offTop+topBase+lineHeight*2;//显示股票信息界面的第四支股票位置
				canvas.drawBitmap(sharescontent, leftTemp, topTemp4, null);
				for(int m=0;m<sd.datails.get(lineTemp+2).length;m++)//绘制股票信息
				{
					String str=sd.datails.get(lineTemp+2)[m];//获得股票基本信息
					canvas.drawText(str,130+110*m,topTemp4+startY, paint);
					str=null;
				}
				
			}
			if(lineTemp+3>=0&&lineTemp+3<sharesnum)
			{
				int topTemp5=offTop+topBase+lineHeight*3;//显示股票信息界面的第五支股票位置
				canvas.drawBitmap(sharescontent, leftTemp, topTemp5, null);
				for(int m=0;m<sd.datails.get(lineTemp+3).length;m++)//绘制股票信息
				{
					String str=sd.datails.get(lineTemp+3)[m];//获得股票基本信息
					canvas.drawText(str,130+110*m,topTemp5+startY, paint);
					str=null;
				}
			}
			canvas.drawBitmap(kuangjia, leftTemp-10,195+(isshares-lineNumber)*70+offset, null);//绘制选中框架
			canvas.restore();
			
		}
		else if(this.status==3)//每个人物的显示持股数
		{
			canvas.drawBitmap(lookshares, 0, 0, null);//先画背景
			paint.setColor(Color.WHITE);
			paint.setTextSize(20);
			//绘制持股数界面的人物
			canvas.drawText(gv.figure.figureName,255,180,paint);
			canvas.drawText(gv.figure1.figureName,255+110,180,paint);
			canvas.drawText(gv.figure2.figureName,255+110*2,180,paint);
			
			canvas.drawBitmap(figureHead[0], 235, 35, paint);
			canvas.drawBitmap(figureHead[1], 350, 35, paint);
			canvas.drawBitmap(figureHead[2], 465, 35, paint);
			
			canvas.save();
			canvas.clipRect(leftTemp, offTop, 900, 720);
			
			int topBase=(offset>=0)?offset%lineHeight:(offset%lineHeight+lineHeight);//距离上面的距离
			int lineTemp=lineNumber-((offset>=0)?offset/lineHeight:(offset/lineHeight-1));//滑动的行数
			
			if(lineTemp-1>=0&&lineTemp-1<sharesnum)
			{
				int topTemp1=offTop+topBase-lineHeight;//显示股票信息界面的第一支股票位置(即只显示一部分的股票)
				canvas.drawBitmap(sharescontent, leftTemp, topTemp1, null);
				for(int m=0;m<perShares[lineTemp-1].length;m++)//绘制股票信息
				{
					String str=perShares[lineTemp-1][m];//获得股票基本信息
					if(m==4)//第四项没有数据
					{
						m=5;
					}
					canvas.drawText(str,130+110*m,topTemp1+startY, paint);
					str=null;
				}
				
			}
			if(lineTemp>=0&&lineTemp<sharesnum)
			{
				int topTemp2=offTop+topBase;//显示股票信息界面的第二支股票位置
				canvas.drawBitmap(sharescontent, leftTemp, topTemp2, null);
				for(int m=0;m<perShares[lineTemp].length;m++)//绘制股票信息
				{
					String str=perShares[lineTemp][m];//获得股票基本信息
					if(m==4)//第四项没有数据
					{
						m=5;
					}
					canvas.drawText(str,130+110*m,topTemp2+startY, paint);
					str=null;
				}
			}
			if(lineTemp+1>=0&&lineTemp+1<sharesnum)
			{
				int topTemp3=offTop+topBase+lineHeight;//显示股票信息界面的第三支股票位置
				canvas.drawBitmap(sharescontent, leftTemp, topTemp3, null);
				for(int m=0;m<perShares[lineTemp+1].length;m++)//绘制股票信息
				{
					String str=perShares[lineTemp+1][m];//获得股票基本信息
					if(m==4)//第四项没有数据
					{
						m=5;
					}
					canvas.drawText(str,130+110*m,topTemp3+startY, paint);
					str=null;
				}
				
			}
			if(lineTemp+2>=0&&lineTemp+2<sharesnum)
			{
				int topTemp4=offTop+topBase+lineHeight*2;//显示股票信息界面的第四支股票位置
				canvas.drawBitmap(sharescontent, leftTemp, topTemp4, null);
				for(int m=0;m<perShares[lineTemp+2].length;m++)//绘制股票信息
				{
					String str=perShares[lineTemp+2][m];//获得股票基本信息
					if(m==4)//第四项没有数据
					{
						m=5;
					}
					canvas.drawText(str,130+110*m,topTemp4+startY, paint);
					str=null;
				}
				
			}
			if(lineTemp+3>=0&&lineTemp+3<sharesnum)//显示股票信息界面的第五支股票位置
			{
				int topTemp5=offTop+topBase+lineHeight*3;//显示股票信息界面的第五支股票位置
				canvas.drawBitmap(sharescontent, leftTemp, topTemp5, null);
				for(int m=0;m<perShares[lineTemp+3].length;m++)
				{
					String str=perShares[lineTemp+3][m];//获得股票基本信息
					if(m==4)
					{
						m=5;
					}
					canvas.drawText(str,130+110*m,topTemp5+startY, paint);
					str=null;
				}
			}		
			canvas.restore();
		}
		else if(this.status==1||this.status==2)
		{
			if(this.status==1)
			{//买进
				canvas.drawBitmap(buyOrOut[0], 0, 0, null);
			}
			else if(status==2)
			{//卖出
				canvas.drawBitmap(buyOrOut[1], 0, 0, null);
			}
			Paint p=initPaint();//获取画笔
			p.setTextSize(25);//设置字体大小			
			canvas.drawText(price+"",230,130, p);//绘制成交价
			canvas.drawText(jiaoYiNuber+"",230,170, p);//绘制交易量
			int num=Integer.parseInt(sb.toString());
			canvas.drawText(num+"",260,237, p);//绘制购买股数的数字
			p.setColor(Color.WHITE);//设置白色字体
			canvas.drawText(chiGu+"",219,434, p);//绘制持股的数字
			int cast=(int) (num*price);
			canvas.drawText(cast+"",219,400, p);//绘制花费的数字
			canvas.drawText(name+"",200,74, p);//绘制名称
			
			p.setTextSize(16);//设置字体
			p.setColor(Color.BLACK);//黑色
			String showString=gv.figure.mhz.cMoney+"";//拿到玩家的银行存款
			showString=ConstantUtil.translateString(showString);
			canvas.drawText(showString,205,346, p);//绘制存款			
			showString=null;//置为null
		}
	}
	
	public void getSharesPer()//获得每个人的股票数
	{
		if(as.size()>0)
		{
			as.clear();
		}
		as.add(peopleGuPiao(gv.figure));//玩家持股数
		as.add(peopleGuPiao(gv.figure1));//系统人物1
		as.add(peopleGuPiao(gv.figure2));//系统人物2
	}
	public Paint initPaint()//设置字体画笔
	{
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setFakeBoldText(true);//字体加粗
		return paint;
	}
	
	@Override
	public boolean onTouch(View view,MotionEvent event){
		int x=ScreenTransUtil.xFromRealToNorm((int)event.getX());
		int y=ScreenTransUtil.yFromRealToNorm((int)event.getY());
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				eventY=event.getY();
				if(this.status==0)//显示股票信息主界面
				{
					if(x>=ConstantUtil.SharesView_RetunMainMenu_Left_X&&x<=ConstantUtil.SharesView_RetunMainMenu_Right_X
							&&y>=ConstantUtil.SharesView_RetunMainMenu_Up_Y&&y<=ConstantUtil.SharesView_RetunMainMenu_Down_Y)//返回主界面
					{
						gv.setStatus(0);
						gv.isDraw=false;//绘制GO图标
						gv.isMenu=true;
						this.gv.setOnTouchListener(this.gv);//返还监听器
					}
					else if(x>=ConstantUtil.SharesView_ShowHaveStockNum_Left_X&&x<=ConstantUtil.SharesView_ShowHaveStockNum_Right_X
							&&y>=ConstantUtil.SharesView_ShowHaveStockNum_Up_Y&&y<=ConstantUtil.SharesView_ShowHaveStockNum_Down_Y)//显示持股数界面
					{
						this.status=3;
					}
					else if(x>=ConstantUtil.SharesView_TurnToBuyView_Left_X&&x<=ConstantUtil.SharesView_TurnToBuyView_Right_X
							&&y>=ConstantUtil.SharesView_TurnToBuyView_Up_Y&&y<=ConstantUtil.SharesView_TurnToBuyView_Down_Y)//切换到购买界面
					{
						String buymess[]=sd.BuyShares(isshares);
						price=Double.parseDouble(buymess[0]);//成交价
						name=buymess[3];//名称
						jiaoYiNuber=(int)Float.parseFloat(buymess[1].replace(",", ""));//交易量
						chiGu=this.gv.figure.mps.get(isshares);//持股数
						this.status=1;
						buymess=null;
					}
					else if(x>=ConstantUtil.SharesView_Sale_Left_X&&x<=ConstantUtil.SharesView_Sale_Right_X
							&&y>=ConstantUtil.SharesView_Sale_Up_Y&&y<=ConstantUtil.SharesView_Sale_Down_Y)
					{//卖出
						String buymess[]=sd.BuyShares(isshares);
						chiGu=this.gv.figure.mps.get(isshares);//持股数
						name=buymess[3];//名称
						this.status=2;
						buymess=null;
					}
					else if(y>ConstantUtil.SharesView_Slid1_Up_Y&&y<ConstantUtil.SharesView_Slid1_Down_Y)
					{
						isshares=lineNumber;//滑动的行数
					}
					else if(y>ConstantUtil.SharesView_Slid2_Up_Y&&y<ConstantUtil.SharesView_Slid2_Down_Y)
					{
						isshares=lineNumber+1;//滑动的行数isshares=lineNumber;
					}
					else if(y>ConstantUtil.SharesView_Slid3_Up_Y&y<ConstantUtil.SharesView_Slid3_Down_Y)
					{
						isshares=lineNumber+2;//滑动的行数
					}
					else if(y>ConstantUtil.SharesView_Slid4_Up_Y&&y<ConstantUtil.SharesView_Slid4_Down_Y)
					{
						isshares=lineNumber+3;//滑动的行数
					}
				}
				else if(this.status==1)//切换到购买股票显示股数界面
				{	
					isBuy=true;//进入购买股票界面
					if(x>=ConstantUtil.SharesView_StarButton_Left_X&&x<=ConstantUtil.SharesView_StarButton_Right_X
							&&y>=ConstantUtil.SharesView_StarButton_Up_Y&&y<=ConstantUtil.SharesView_StarButton_Down_Y)
					{//点击*

						if(!sb.equals("0"))
						{
							float num=Integer.parseInt(sb.toString());//贷款资金
							if((gv.figure.mhz.cMoney-num*price)<0)
							{
								num=0;
							}
							else
							{
								gv.figure.mhz.zMoney-=num*price;//计算人物总资金
								gv.figure.mhz.cMoney-=num*price;//计算人物现金								
							}
							chiGu+=num;//持股数更新
							jiaoYiNuber-=num;//交易量更新
							sd.datails.get(isshares)[4]=chiGu+"";
							sd.datails.get(isshares)[3]=jiaoYiNuber+"";//购买之后更新股票信息
							sd.operationShares(isshares);//购买股票显示平均成本
							
							this.gv.figure.mps.put(isshares, chiGu);//更新持股数
							this.gv.figure.mpsName.put(isshares, name);//股票名称
							this.gv.figure.mpsCost.put(isshares, num*price);//成本
						}
						this.status=0;
						this.gv.setOnTouchListener(this);
						sb.replace(0, sb.length(),0+"");
					}
					else if(x>=ConstantUtil.SharesView_NumKeyboard_Left_X&&x<=ConstantUtil.SharesView_NumKeyboard_Right_X
							&&y>=ConstantUtil.SharesView_NumKeyboard_Up_Y&&y<=ConstantUtil.SharesView_NumKeyboard_Down_Y)
					{//数字键盘区
						check(x,y,this.status);//判断触控到的数字，
					}
				}
				else if(this.status==2)//切换到卖出股票界面
				{
					isBuy=false;//进入卖出股票界面
					if(x>=ConstantUtil.SharesView_StarButton_Left_X&&x<=ConstantUtil.SharesView_StarButton_Right_X
							&&y>=ConstantUtil.SharesView_StarButton_Up_Y&&y<=ConstantUtil.SharesView_StarButton_Down_Y)
					{//点击*	

						if(!sb.equals("0"))
						{
							int num=Integer.parseInt(sb.toString());//贷款资金
							gv.figure.mhz.zMoney+=num*price;//计算人物总资金
							gv.figure.mhz.cMoney+=num*price;//计算人物现金
							if(chiGu-num<=0)
							{
								chiGu=0;
							}
							else
							{
								chiGu-=num;//持股数更新
							}			
							jiaoYiNuber+=num;//交易量更新
							
							sd.datails.get(isshares)[4]=(Integer.parseInt(sd.datails.get(isshares)[4])-num)+"";
							
							sd.datails.get(isshares)[3]=jiaoYiNuber+"";//购买之后更新股票信息		
							
							if(Integer.parseInt(sd.datails.get(isshares)[4])==0)//更新卖出后的信息
							{
								sd.datails.get(isshares)[5]=0+"";
							}
							if(chiGu==0)
							{									
								this.gv.figure.mps.put(isshares, chiGu);//更细持股数
								this.gv.figure.mpsName.remove(isshares);//股票名称
							}
							else
							{
								this.gv.figure.mps.put(isshares, chiGu);//更新持股数
								this.gv.figure.mpsName.put(isshares, name);//股票名称
							}
						}
						this.status=0;
						this.gv.setOnTouchListener(this);
						sb.replace(0, sb.length(),0+"");
					}
					else if(x>=ConstantUtil.SharesView_NumKeyboard_Left_X&&x<=ConstantUtil.SharesView_NumKeyboard_Right_X
							&&y>=ConstantUtil.SharesView_NumKeyboard_Up_Y&&y<=ConstantUtil.SharesView_NumKeyboard_Down_Y)
					{//数字键盘区
						check(x,y,this.status);//判断触控到的数字，
					}
				}
				else if(this.status==3)//持股数查看界面
				{
					if(x>=80&&x<=210&&y>=20&&y<=100)//返回股票主界面
					{
						this.status=0;
					}
					else if(y>ConstantUtil.SharesView_Slid1_Up_Y&&y<ConstantUtil.SharesView_Slid1_Down_Y)//显示在股票信息界面的第一行
					{
						isshares=lineNumber;//获得显示在股票信息界面的第一行股票信息
					}
					else if(y>ConstantUtil.SharesView_Slid2_Up_Y&&y<ConstantUtil.SharesView_Slid2_Down_Y)//显示在股票信息界面的第二行
					{
						isshares=lineNumber+1;//获得显示在股票信息界面的第二行股票信息
					}
					else if(y>ConstantUtil.SharesView_Slid3_Up_Y&y<ConstantUtil.SharesView_Slid3_Down_Y)//显示在股票信息界面的第三行
					{
						isshares=lineNumber+2;//获得显示在股票信息界面的第三行股票信息
					}
					else if(y>ConstantUtil.SharesView_Slid4_Up_Y&&y<ConstantUtil.SharesView_Slid4_Down_Y)//显示在股票信息界面的第四行
					{
						isshares=lineNumber+3;//获得显示在股票信息界面的第四行股票信息
					}
				}
				break;
			case MotionEvent.ACTION_MOVE:
				offset=(int) (event.getY()-eventY);//获得滑动的距离--offset>0向下滑
				break;
			case MotionEvent.ACTION_UP:
				if(!(event.getY()==eventY))//判断是否为点击事件  即按下就抬起
				{
					lineNumber=lineNumber-((offset>=0)?offset/lineHeight:(offset/lineHeight-1));//获得首行的股票索引
					offset=(offset>0)?offset%lineHeight:(offset%lineHeight+lineHeight);
					if(lineNumber<0)//向下滑出显示股票信息界面
					{
						offset=offset+(0-lineNumber)*lineHeight;
						lineNumber=0;
					}
					if(lineNumber>sharesnum-4)//向上滑出显示股票信息界面
					{
						offset=offset+(sharesnum-4-lineNumber)*lineHeight;
						lineNumber=sharesnum-4;
					}
					new Thread()//恢复股票信息显示界面（如第一个或最后一个被滑出股票显示界面，则在手指抬起时恢复）
					{
						public void run()
						{
							while(offset!=0)
							{
								offset=(int) (offset*0.8);
								if(offset<5&&offset>-5)
								{
									offset=0;
								}
								try
								{
									Thread.sleep(20);
								}
								catch(Exception e)
								{
									e.printStackTrace();
								}
							}
						}
					}.start();
				}
			break;
		}
		return true;
	}
	
	public String[] peopleGuPiao(Figure f)//获得每个人物的持股票数
	{
		String guPiao[]=new String[7];
		if(f==null)
		{
			return null;
		}
		int length=f.mps.size();
		for(int i=0;i<length;i++)//获得不同股票的持股数
		{
			guPiao[i]=f.mps.get(i)+"";
		}
		return guPiao;
	}
	
	//方法:根据触控点查找当前触控数值，并记录触控的数字-----------买进
	public void check(int x,int y,int status)
	{
		for(int j=0;j<3;j++)
		{//列
			for(int i=0;i<4;i++)
			{//行
				if(x>=(485+j*100-50)&&x<=(485+j*100+50)&&y>=(159+i*81-31)&&y<=(159+i*81+31)&&((gv.figure.mhz.cMoney>0&&isBuy)||!isBuy))
				{						
					if(ConstantUtil.NUMBER[i][j]==10)
					{//如果点击的是清空键,则全部置零
						sb=sb.replace(0, sb.length(), "0");
					}
					else if(ConstantUtil.NUMBER[i][j]==11)
					{//如果点击的是最大数值键，则显示交易量数值
						sb=sb.delete(0, sb.length());
						if(status==1)
						{
							sb=sb.append(jiaoYiNuber+"");//最大交易量
						}						
						else if(status==2)
						{
							sb=sb.append(chiGu+"");//最大为持股数
						}
					}
					else if(ConstantUtil.NUMBER[i][j]!=10&&ConstantUtil.NUMBER[i][j]!=11)
					{//不是清空键和最大值键								 
						if(sb.length()<4)
						{//否则记录数字
							String str=sb.substring(0,1);//得到字符串的第一个字母，判断是否是零。
							//如果不是零，则直接在后加点击的数字，否则删掉零，添加点击数字
							if(str.equals("0"))
							{//如果第一个数字是零，则删除
								sb=sb.delete(0, sb.length());
							}
							str=null;
							sb=sb.append(ConstantUtil.NUMBER[i][j]+"");//构造字符串
							if(status==1)
							{//如果数字大于交易量，则输出交易量的数值，即交易量为最大值
								if(Integer.parseInt(sb.toString())>jiaoYiNuber)
								{
									sb=sb.replace(0, sb.length(), jiaoYiNuber+"");
								}									
							}
							else if(status==2)
							{//如果数字大于持股数，则输出持股数
								if(Integer.parseInt(sb.toString())>chiGu)
								{
									sb=sb.replace(0, sb.length(), chiGu+"");
								}									
							}
						}
					}						  
				}
			}
		}
	}
}