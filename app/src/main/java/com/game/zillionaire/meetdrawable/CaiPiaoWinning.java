package com.game.zillionaire.meetdrawable;
import static com.game.zillionaire.util.ConstantUtil.*;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.view.GameView;

public class CaiPiaoWinning implements Serializable//implements View.OnTouchListener 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5082208806263434591L;
	GameView father;
	public int current = 0;//当前索引从零开始，字符串索引
	static Bitmap bitmap;//当前帧图片
    static Bitmap[] bitmaps=new Bitmap[3];
    static Bitmap[] numbers=new Bitmap[32];
    static Bitmap[] tous=new Bitmap[3];
	int index=0;//图片索引
	int prizeId=0;//获奖编号
	int  iscount=-1;//计数器
	int randromx=0;//彩票获奖金额的索引
	public int count=0;//计数---------每次进入本界面count加1
	public String[] dialogMessage=
	{
		"嗨~又到了每月十五号进行抽奖的日子！",
		"现在马上为您开出这一期的号码！",
		"恭喜孙小美获奖！",
		"恭喜金贝贝获奖！",
		"恭喜阿土伯获奖！",
		"sorry!本月份没有人得奖~",
		"奖金将累计到下个月！",
		"希望下次得奖者就是您！",""
	};
	int width[][]={{0,54,115,176,235,296,355,415},{0,54,115,176,235,296,355,415},
			{0,54,115,176,235,296,355,415},{0,54,115,176,235,296,355,415}};
	int height[][]={{0,0,0,0,0,0,0,0},{60,55,55,54,54,54,54,54},
			{120,120,118,116,115,115,111,111},{180,180,179,179,177,174,174,171}};
	public CaiPiaoWinning(GameView father)
	{
		this.father=father;
		randromx=(int)(Math.random()*5);
		dialogMessage[2]=this.father.figure.figureName;
		dialogMessage[3]=this.father.figure1.figureName;
		dialogMessage[4]=this.father.figure2.figureName;
	}
	//绘制背景以及字符串
	public void drawCaiPiaoWinningF(Canvas canvas)
	{
		Bitmap cq=PicManager.getPic("cq",father.activity.getResources());//加载图片
		for(int j=0;j<4;j++)
		{
			for(int i=0;i<8;i++)
			{
				numbers[8*j+i]=Bitmap.createBitmap(cq, width[j][i], height[j][i], 50, 50);
			}
		}
		cq=null;
		bitmap=PicManager.getPic("prize",father.activity.getResources());
		bitmaps[0]=PicManager.getPic("cp1",father.activity.getResources());
		bitmaps[1]=PicManager.getPic("cp2",father.activity.getResources());
		bitmaps[2]=PicManager.getPic("cp3",father.activity.getResources());
		
		tous[0]=PicManager.getPic(this.father.figure.touName, this.father.activity.getResources());//人物小头像
		tous[1]=PicManager.getPic(this.father.figure1.touName, this.father.activity.getResources());//人物小头像
		tous[2]=PicManager.getPic(this.father.figure2.touName, this.father.activity.getResources());//人物小头像
		
		if(count==0)
		{//第一次绘制
			iscount++;//计数，折算成睡眠时间=iscount*100
			if(iscount<63)
			{//循环次数<58，即GameView线程睡眠5800毫秒
				canvas.drawBitmap(bitmap, 150, 25, null);			//绘制背景		
				canvas.drawBitmap(bitmaps[2], 398, 219, null);			//绘制背景			
				drawString(canvas,ConstantUtil.CAIPIAO[randromx]+"",2);		//绘制金额				
				Paint paint = new Paint();//创建画笔
				paint.setColor(Color.WHITE);//设置字体颜色
				paint.setAntiAlias(true);//抗锯齿
				paint.setTextSize(19);//设置文字大小
				canvas.drawBitmap(tous[0], 195,345,paint);//人物1
				if(father.figure.mp.size()>=0)
				{
					canvas.drawText(father.figure.mp.size()+"",250,375,paint);		
				}
				canvas.drawBitmap(tous[1], 450,355, paint);//人物2
				if(father.figure1.mp.size()>=0)
				{
					canvas.drawText(father.figure1.mp.size()+"",500,375,paint);			
				}
				canvas.drawBitmap(tous[2],195,395, paint);//人物3
				if(father.figure2.mp.size()>=0)
				{
					canvas.drawText(father.figure2.mp.size()+"",250,425,paint);		
				}
				father.isDraw=true;//隐藏游戏菜单
				father.isYuanBao=false;
				father.isMenu=false;				
			}	
			else if(iscount>=64)
			{//恢复游戏菜单
				count++;
				recoverGame();
			}
			if(iscount==20||iscount==35||iscount==45||iscount==55||iscount==63)
			{//每隔10次字符串索引加1，即每隔1000毫秒绘制一句
				int j=0;
				for(;j<3;j++)
				{
					if(j==0)
					{//由玩家操控的人物
						int sizes=father.figure.mp.size();//系统人物1彩票编号的列表长度
						int i=0;
						for(;i<sizes;i++)
						{//遍历列表
							if(prizeId==father.figure.mp.get(i))
							{//如果人物获奖			
								if(current==2&&father.figure.figureFlag==MessageEnum.Artificial_Control)
								{	//如果系统人物1获奖				
									current=2;
								}
								father.figure.mhz.xMoney+=ConstantUtil.CAIPIAO[randromx];//现金增加
								father.figure.mhz.zMoney+=ConstantUtil.CAIPIAO[randromx];//总资产增加
								father.figure.mp.remove(father.figure.mps.get(i));//从列表中删掉该彩票的编号值
								current++;
								current++;
								break;
							}			
						}
					}
					else if(j==1)
					{//系统人物1
						int sizes=father.figure.mp.size();//系统人物1彩票编号的列表长度
						int i=0;
						for(;i<sizes;i++)
						{//遍历列表
							if(prizeId==father.figure.mp.get(i))//figure.mp.get(i))
							{//如果人物获奖			
								if(current==2&&father.figure.figureFlag==MessageEnum.System_Control_1)
								{	//如果系统人物1获奖				
									current=3;
								}
								current++;
								father.figure.mhz.xMoney+=ConstantUtil.CAIPIAO[randromx];//现金增加
								father.figure.mhz.zMoney+=ConstantUtil.CAIPIAO[randromx];//总资产增加
								father.figure.mp.remove(father.figure.mps.get(i));//从列表中删掉该彩票的编号值
								current++;
								break;
							}			
						}
					}
					else if(j==2)
					{//系统人物2
						int sizes=father.figure.mp.size();//系统人物1彩票编号的列表长度
						int i=0;
						for(;i<sizes;i++)
						{//遍历列表
							if(prizeId==father.figure.mp.get(i))//figure.mp.get(i))
							{//如果人物获奖			
								if(current==2&&father.figure.figureFlag==MessageEnum.System_Control_2)
								{	//如果系统人物1获奖				
									current=4;
								}
								father.figure.mhz.xMoney+=ConstantUtil.CAIPIAO[randromx];//现金增加
								father.figure.mhz.zMoney+=ConstantUtil.CAIPIAO[randromx];//总资产增加
								father.figure.mp.remove(father.figure.mps.get(i));//从列表中删掉该彩票的编号值
								current++;
								break;
							}			
						}
					}
					while(father.mpCP.size()>0)
					{
						father.mpCP.clear();
						father.figure.mp.clear();
						father.figure1.mp.clear();
						father.figure2.mp.clear();
					}					
					for(int m=0;m<4;m++)
					{//有人获奖，所以将标志位置为false，便于再次购买
						for(int n=0;n<9;n++)
						{
							ConstantUtil.CAIPIAO_INT[m][n]=0;
							ConstantUtil.CAIPIAO_BOOL[m][n]=false;
						}
					}
				    for(int i=0;i<2;i++)
				    {
				    	vertex[i]=0;
				    	lowCol[i]=0;
				    	lowCols[i]=0;
				    }
				}			
				if(j==3&&current==1)
				{//没人获奖
					current=4;//current=2
				}
				current++;
			}
			if(iscount<63&&current<(dialogMessage.length-1))
			{//循环次数<58，即GameView线程睡眠5800毫秒
				drawString(canvas,dialogMessage[current],1);	//绘制字符串dialogMessage[2]
			}		
			if(iscount<20)
			{//背景--彩票球的绘制
				canvas.drawBitmap(bitmaps[2], 398, 219, null);			//绘制背景
			}
			if(iscount>=20&&iscount<=28)
			{//彩票抽奖过程，循环6次，即绘制6次，在此位置睡眠600毫秒执行抽奖动画
				canvas.drawBitmap(bitmaps[index], 398, 219, null);			//绘制背景
				index=(index+1)%3;
				prizeId=(int)(Math.random()*31+1);//随即产生一个彩票编号
			}
			if(iscount>=29&&iscount<63)
			{
				canvas.drawBitmap(bitmaps[index], 398, 219, null);			//背景--彩票球的绘制
				canvas.drawBitmap(numbers[prizeId], 445, 170, null);			//背景--中奖彩票球的绘制
			}
		}
		else if(this.father.date!=15)
		{
			count=0;
		}
	}	
	
	//方法：绘制字符串
	public void drawString(Canvas canvas,String string,int id)
	{
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setTextSize(19);//设置文字大小
		paint.setFakeBoldText(true);//字体加粗
		int lines = string.length()/10+(string.length()%10==0?0:1);//求出需要画几行文字
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
			if(id==1)
			{//绘制dialogMessage字符串
				canvas.drawText(str,160,62+DIALOG_WORD_SIZE*i,paint);
			}
			else if(id==2)
			{//绘制彩票获奖的金额
				canvas.drawText(str,450,60+DIALOG_WORD_SIZE*i,paint);
			}
		}
	}
	
	//方法：返还监听器，回复游戏状态
	public void recoverGame()
	{
		father.setCurrentDrawable(null);//置空记录引用的变量
		father.setOnTouchListener(father);//返还监听器
		father.setStatus(0);//重新设置GameView为待命状态
		
		father.isDraw=false;
		father.isYuanBao=true;
		father.isMenu=true;
		index=0;
	    prizeId=0;
		iscount=-1;//计数器
		randromx=0;//彩票获奖金额的索引
	}
}
