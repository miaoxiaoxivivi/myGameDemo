package com.game.zillionaire.meetdrawable;
import static com.game.zillionaire.util.ConstantUtil.BANK_DIALOG_WORD_EACH_LINE;
import static com.game.zillionaire.util.ConstantUtil.DIALOG_WORD_SIZE;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.game.zillionaire.activity.ZActivity;
import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.map.MyMeetableDrawable;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.util.ScreenTransUtil;

public class BankDrawable  extends MyMeetableDrawable implements Serializable
{
	private static final long serialVersionUID = -5825366738048388051L;	
	String dialogMessage[] = 
	{
			"欢迎来到大富翁银行！",
			"需要我为您服务吗？","",
			"请输入您要贷款或还款的金额！"
	};
	Figure tempFigure;//临时记录人物	
	static Bitmap bitmaps[]=new Bitmap[8];//图片帧
	boolean isDaiAndHuanKuan=false;//是否贷款和还款界面的标志位，默认为false，表示绘制
	boolean isDaiKuan=false;//贷款界面的标志位，默认为false，表示不绘制
	public boolean isTiKuanJi=true;//提款机界面的标志位
	public static boolean isWork=true;//银行是否正常工作---------例如银行停止贷款，拒绝往来等都属于不正常工作，，，，被新闻等调用
	public static int existDay=0;//生存期--------------用于控制银行拒绝往来的时间
	int status=0;//控制时间
	int index=4;//索引值
	int sum;//现金+银行存款
	float radio;//比例值
	float xTouch=243;//触控点x---按钮园的中心坐标
	float yTouch=227;//触控点y---按钮园的中心坐标
	float preX=243;//记录前一个触控点的x坐标
	int cun=100;
	int frist=0;//0表示系统人物第一次购买
	StringBuffer sb=new StringBuffer("0");//贷款金额的字符串
	
	public BankDrawable(){}
	public BankDrawable(ZActivity at,String bmpSelf,String dbitmap,String bmpDialogBack,boolean meetable,int width,int height,int col,int row,
			int refCol,int refRow,int [][] noThrough,int [][] meetableMatrix,int da) {
		super(at,bmpSelf,dbitmap, col, row, width, height, refCol, refRow, noThrough, meetable,
				meetableMatrix, da,bmpDialogBack);
	}
	@Override
	public void drawDialog(final Canvas canvas, Figure figure)
	{
		tempFigure=figure;//临时人物
		bitmaps[0]=PicManager.getPic("daikuan",tempFigure.father.activity.getResources());//贷款界面
		bitmaps[1]=PicManager.getPic("daikuan2",tempFigure.father.activity.getResources());//还贷款界面
		bitmaps[2]=PicManager.getPic("anniu",tempFigure.father.activity.getResources());//提款机界面的滑动
		bitmaps[3]=PicManager.getPic("bank1",tempFigure.father.activity.getResources());
		bitmaps[4]=PicManager.getPic("tikuanji",tempFigure.father.activity.getResources());//背景
		bitmaps[5]=PicManager.getPic("bank2",tempFigure.father.activity.getResources());
		bitmaps[6]=PicManager.getPic("bank3",tempFigure.father.activity.getResources());
		bitmaps[7]=PicManager.getPic("bank4",tempFigure.father.activity.getResources());
		if(isWork)
		{//如果银行正常工作
			if(tempFigure.father.isWeekend)
			{//如果时周末
				index=3;
				canvas.drawBitmap(bitmaps[index], 220, 130, null);
				new Thread()
				{
					public void run()
					{
						try
						{//睡眠700毫秒返回游戏主界面
							Thread.sleep(700);					
							recoverGame();
						}
						catch(Exception e){}
					}
				}.start();
			}
			else
			{//如果不是周末---index=4
				if(tempFigure.k==0)
				{
					canvas.drawBitmap(bitmaps[index], 150, 25, null);			//绘制背景
					String showString=sb.toString();//转化为字符串
					if(isDaiAndHuanKuan)
					{//提款和贷款界面
						if(status==1)
						{
							try{
								Thread.sleep(700);
							}catch(Exception e){}
						}
						if(status<2)
						{
							drawString(canvas,dialogMessage[status],5);
							status++;
						}else
						{
							drawString(canvas,dialogMessage[1],5);
						}				
					}
					if(isDaiKuan)
					{//绘制数字
						drawString(canvas,showString,0);			
						drawString(canvas,dialogMessage[3],6);
						drawString(canvas,tempFigure.mhz.xMoney+"",1);
						drawString(canvas,tempFigure.mhz.zMoney+"",2);			
					}
					if(isTiKuanJi)
					{//如果是提款机界面
						showString=tempFigure.mhz.xMoney+"";
						drawString(canvas,showString+"",3);//绘制金额
						showString=tempFigure.mhz.cMoney+"";
						drawString(canvas,showString+"",4);//绘制金额
						canvas.drawBitmap(bitmaps[2], xTouch, yTouch, null);
					}		
				}	
				else if(tempFigure.k!=0)
				{//如果不是玩家
					frist++;
					if(frist==0)
					{//如果是第一次进入
						if(tempFigure.mhz.xMoney>cun)
						{//存款
							tempFigure.mhz.xMoney-=cun;
							tempFigure.mhz.cMoney+=cun;
							canvas.drawBitmap(bitmaps[6], 220, 130, null);
						}
						else if(tempFigure.mhz.xMoney<cun&&tempFigure.mhz.cMoney>10)
						{//取款
							tempFigure.mhz.xMoney+=cun;
							tempFigure.mhz.cMoney-=cun;
							canvas.drawBitmap(bitmaps[7], 220, 130, null);
						}
					}
					new Thread()
					{
						public void run()
						{
							try
							{//睡眠700毫秒返回游戏主界面
								Thread.sleep(700);					
								recoverGame();
							}
							catch(Exception e){}
						}
					}.start();
				}
			}		
		}else
		{//银行不正常工作时
			int days=existDay+tempFigure.father.date;
			if(tempFigure.father.date>days)
			{
				isWork=true;//改为正常工作
				existDay=0;
			}
			canvas.drawBitmap(bitmaps[5], 220, 130, null);
			new Thread()
			{
				public void run()
				{
					try
					{//睡眠700毫秒返回游戏主界面
						Thread.sleep(700);					
						recoverGame();
					}
					catch(Exception e){}
				}
			}.start();
		}
	}
	//绘制给定的字符串到对话框上
	//@Override
	public void drawString(Canvas canvas,String string,int id)
	{
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setTextSize(19);//设置文字大小
		paint.setFakeBoldText(true);//字体加粗
		int lines = string.length()/BANK_DIALOG_WORD_EACH_LINE+
				           (string.length()%BANK_DIALOG_WORD_EACH_LINE==0?0:1);//求出需要画几行文字
		for(int i=0;i<lines;i++)
		{
			String str="";
			if(i == lines-1)
			{//如果是最后一行那个不太整的汉字
				str = string.substring(i*BANK_DIALOG_WORD_EACH_LINE);
			}
			else
			{
				str = string.substring(i*BANK_DIALOG_WORD_EACH_LINE, (i+1)*BANK_DIALOG_WORD_EACH_LINE);
			}
			if(id==0)
			{//贷款金额绘制---第三个界面
				canvas.drawText(str,280,250+DIALOG_WORD_SIZE*i,paint);
			}
			else if(id==1)
			{//现金绘制----第三个界面
				str=ConstantUtil.translateString(str);
				canvas.drawText(str,236,348+DIALOG_WORD_SIZE*i,paint);
			}
			else if(id==2)
			{//总资产绘制---第三个界面
				str=ConstantUtil.translateString(str);
				canvas.drawText(str,236,390+DIALOG_WORD_SIZE*i,paint);
			}
			else if(id==3)
			{//存款绘制----第一个界面
				str=ConstantUtil.translateString(str);
				canvas.drawText(str,380,200+DIALOG_WORD_SIZE*i,paint);
			}
			else if(id==4)
			{//领款绘制------第一个界面
				str=ConstantUtil.translateString(str);
				canvas.drawText(str,380,325+DIALOG_WORD_SIZE*i,paint);
			}
			else if(id==5)
			{//第二个界面
				canvas.drawText(str,440,200+DIALOG_WORD_SIZE*i,paint);
			}
			else if(id==6)
			{
				canvas.drawText(str,280,150+DIALOG_WORD_SIZE*i,paint);
			}
		}
	}
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1)
	{
		int x=ScreenTransUtil.xFromRealToNorm((int)arg1.getX());
		int y=ScreenTransUtil.yFromRealToNorm((int)arg1.getY());
		if(arg1.getAction()==MotionEvent.ACTION_DOWN)
		{//按下
			if(isTiKuanJi&&x>=ConstantUtil.BankDrawable_Slide_Left_X&&x<=ConstantUtil.BankDrawable_Slide_Right_X
					&&y>=ConstantUtil.BankDrawable_Slide_Up_Y&&y<=ConstantUtil.BankDrawable_Slide_Down_Y)
			{//左右滑动
				int tempx=x;
				preX=xTouch;
				if(tempx<270)
				{
					xTouch=243;
				}else if(tempx>550)
				{
					xTouch=550;
				}else
				{
					xTouch=tempx-25;
				}
				int dx=(int) (xTouch-preX);				
				cunAndDaiKuan(dx);
			}
			else if(isTiKuanJi&&x>=ConstantUtil.BankDrawable_ATMView_Left_X&&x<=ConstantUtil.BankDrawable_ATMView_Right_X
					&&y>=ConstantUtil.BankDrawable_ATMView_Up_Y&&y<=ConstantUtil.BankDrawable_ATMView_Down_Y)
			{//提款机界面
				index=1;//贷款和还款选择对话框的界面
				isDaiAndHuanKuan=true;//绘制贷款和还款选择对话框的界面
				isTiKuanJi=false;//不绘制提款机界面
				isDaiKuan=false;
			}
			else if(isDaiAndHuanKuan&&x>ConstantUtil.BankDrawable_LoanOrRepaymentView_Left_X&&x<ConstantUtil.BankDrawable_LoanOrRepaymentView_Right_X
					&&y>ConstantUtil.BankDrawable_LoanOrRepaymentView_Up_Y&&y<ConstantUtil.BankDrawable_LoanOrRepaymentView_Down_Y)
			{//贷款和还款界面
				index=0;//贷款界面
				isDaiAndHuanKuan=false;//不绘制贷款和还款选择对话框的界面
				isTiKuanJi=false;//不绘制提款机界面
				isDaiKuan=true;//绘制贷款界
			}
			else if(isDaiAndHuanKuan&&x>ConstantUtil.BankDrawable_LoanOrRepaymentView_RecoverGame_Left_X&&x<ConstantUtil.BankDrawable_LoanOrRepaymentView_RecoverGame_Right_X
					&&y>ConstantUtil.BankDrawable_LoanOrRepaymentView_RecoverGame_Up_Y&&y<ConstantUtil.BankDrawable_LoanOrRepaymentView_RecoverGame_Down_Y)
			{//点击贷款和还款界面的X,返回游戏界面
				recoverGame();
			}
			else if(isDaiKuan&&x>ConstantUtil.BankDrawable_LoanView_ReturnToMainView_Left_X&&x<ConstantUtil.BankDrawable_LoanView_ReturnToMainView_Right_X
					&&y>ConstantUtil.BankDrawable_LoanOrRepaymentView_RecoverGame_Up_Y&&y<ConstantUtil.BankDrawable_LoanOrRepaymentView_RecoverGame_Down_Y)
			{//点击贷款界面的X,返回游戏主界面
				if(!sb.equals("0"))
				{
					int num=Integer.parseInt(sb.toString());//贷款资金
					tempFigure.mhz.zMoney+=num;//计算人物总资金
					tempFigure.mhz.xMoney+=num;//计算人物现金
				}
				recoverGame();//返回游戏主界面
			}
			else if(isDaiKuan&&x>ConstantUtil.BankDrawable_NumbericKeypad_left_X&&x<ConstantUtil.BankDrawable_NumbericKeypad_Right_X
					&&y>ConstantUtil.BankDrawable_NumbericKeypad_Up_Y&&y<ConstantUtil.BankDrawable_NumbericKeypad_Down_Y)
			{//数字键盘区域		
				check(x,y);//判断触控到的数字，
			}
		}
		if(arg1.getAction()==MotionEvent.ACTION_MOVE)
		{//按下
			if(isTiKuanJi&&x>=ConstantUtil.BankDrawable_Slide_Left_X&&x<=ConstantUtil.BankDrawable_Slide_Right_X
					&&y>=ConstantUtil.BankDrawable_Slide_Up_Y&&y<=ConstantUtil.BankDrawable_Slide_Down_Y)
			{
				preX=xTouch;
				if(x<270)
				{
					xTouch=243;
				}else if(x>550)
				{
					xTouch=550;
				}else
				{
					xTouch=x-25;
				}
				int dx=(int) (xTouch-preX);
				cunAndDaiKuan(dx);
			}
		}
		return true;
	}		
	//方法：存款和贷款
	public void cunAndDaiKuan(int dx)
	{
		sum=(int)(tempFigure.mhz.xMoney+tempFigure.mhz.cMoney);
		radio=sum/720;//计算金钱与提款机界面滑动条的宽度的比例
		if(dx>0)
		{	//如果x偏移量大于0，		
			tempFigure.mhz.xMoney+=dx*radio;//现金值增加
			tempFigure.mhz.cMoney-=dx*radio;//银行存款值减小
			if(tempFigure.mhz.xMoney>=sum)
			{
				tempFigure.mhz.xMoney=sum;
			}
			if(tempFigure.mhz.cMoney<=0||(xTouch==550))
			{//当移动到最右边时
				tempFigure.mhz.cMoney=0;
				tempFigure.mhz.xMoney=sum;
			}
		}
		else 
		{//如果x偏移量小于0	，现金值减少，银行存款值增加
			tempFigure.mhz.xMoney+=dx*radio;//现金值减少
			tempFigure.mhz.cMoney-=dx*radio;//银行存款值增加
			if(tempFigure.mhz.cMoney>=sum)
			{
				tempFigure.mhz.cMoney=sum;
			}
			if(tempFigure.mhz.xMoney<=0||(xTouch==243))
			{//当移动到最左边时
				tempFigure.mhz.xMoney=0;
				tempFigure.mhz.cMoney=sum;
			}
		}
	}
	//方法:根据触控点查找当前触控数值，并记录触控的数字
	public void check(int x,int y)
	{
		for(int j=0;j<3;j++)
		{//列
			for(int i=0;i<4;i++)
			{//行
				if(x>=(465+j*80-45)&&x<=(465+j*80+45)&&y>=(160+i*70-30)&&y<=(160+i*70+30))
				{
					if(ConstantUtil.NUMBER[i][j]==10)
					{//如果点击的是清空键,则全部置零
						sb=sb.replace(0, sb.length(), "0");
					}
					else if(ConstantUtil.NUMBER[i][j]==11)
					{//如果点击的是最大数值键，则显示“359,999”
						sb=sb.delete(0, sb.length());
						sb=sb.append("359,999");
					}
					else if(ConstantUtil.NUMBER[i][j]!=10&&ConstantUtil.NUMBER[i][j]!=11)
					{//不是清空键和最大值键								 
						if(sb.length()<6)
						{//否则记录数字
							String str=sb.substring(0,1);//得到字符串的第一个字母，判断是否是零。
							//如果不是零，则直接在后加点击的数字，否则删掉零，添加点击数字
							if(str.equals("0"))
							{//如果第一个数字是零，则删除
								sb=sb.delete(0, sb.length());
							}
							str=null;
							sb=sb.append(ConstantUtil.NUMBER[i][j]+"");//构造字符串
						}
					}						  
				}
			}
		}
	}
	//方法：返还监听器，回复游戏状态
	public void recoverGame()
	{
		tempFigure.father.setOnTouchListener(tempFigure.father);//返还监听器
		tempFigure.father.setCurrentDrawable(null);//置空记录引用的变量
		tempFigure.father.setStatus(0);//重新设置GameView为待命状态
		tempFigure.father.gvt.setChanging(true);//启动变换人物的线程
		
		isDaiAndHuanKuan=false;//是否贷款和还款界面的标志位，默认为false，表示绘制
		isDaiKuan=false;//贷款界面的标志位，默认为false，表示不绘制
		isTiKuanJi=true;//提款机界面的标志位
		status=0;
		index=4;
		frist=0;//系统人物第一次存款或取款
	}
}