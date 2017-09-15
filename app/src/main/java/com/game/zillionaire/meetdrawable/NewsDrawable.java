package com.game.zillionaire.meetdrawable;

import java.io.Serializable;

import com.game.zillionaire.activity.ZActivity;
import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.map.MyMeetableDrawable;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.util.Room;
import com.game.zillionaire.util.SharesDetails;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import static com.game.zillionaire.util.ConstantUtil.*;

public class NewsDrawable extends MyMeetableDrawable implements View.OnTouchListener, Serializable{
	
	public static Bitmap[] Wind=new Bitmap[4];//获得龙卷风图片
	public static Bitmap WindPic=null;//获得龙卷风一整张图片
	static Bitmap Nbenefit_bank;//新闻的背景图
	static Bitmap free_house;
	static Bitmap traffic_jam;
	static Bitmap fired_house;
	static Bitmap landlord;
	static Bitmap typhoond_house;
	static Bitmap stop_loans;
	static Bitmap change_sitting;
	static Bitmap destory_house;
	static Bitmap land_away;
	static Bitmap pay_tax;
	static Bitmap shares;
	static Bitmap benefit_bank;
	static Bitmap aliencome;
	static Bitmap[] bitmaps;//存放各个新闻
	static Bitmap[] fireBitps=new Bitmap[7];//存放房屋着火图片
	static Bitmap[] person=new Bitmap[3];//存放人物头像
	String bigLand="";//地主第一大户姓名
	String[] newsDialogM={
			"政府红利大放送,部分地区建筑免费加盖一层",
			"交通阻塞汽车停止一回合",
			"xx一处民宅瓦斯爆炸房屋失火",
			"公开表扬第一大地主,xx获得$10000元奖励",
			"超级台风侵袭 xx,多处房屋受损",
			"银行挤兑停止放款7天",
			"银行加发10%存款红利",
			"龙卷风侵袭,所有人交换位子",
			"龙卷风侵袭 xx,摧毁房屋一栋",
			"xx山洪爆发土地流失",
			"所有人缴交所得税5%",
			"公开表扬股市第一大户,xx获得$10000元奖励",
			"外星人入侵地球,部分地区建筑被摧毁损坏,夷为平地"
		};
	public int messagenum;//随机选取的新闻信息
	private int num=-1;
	private int indext=-1;//火焰图片的索引
	private boolean first=true;//判断是否是第一次标志位
	private String[] message0={"茶晶","蓝宝","翡翠","黑耀","粉晶","紫晶"};//各个地区名
	private int iscount=-1;//判断绘制画框的次数
	private int isFigure;//存储原来的人物标志位
	
	public boolean isFire=false;//判断是否绘制火焰
	public boolean isSitting=false;//是否交换位置
	SharesDetails sd;
	public int tempx=0,tempy=0;
	public int tempCol1,tempCol2,tempRow1,tempRow2;//地区的坐标范围
	
	public NewsDrawable(){}
	public NewsDrawable(ZActivity at,String bmpSelf,String dbitmap,String bmpDialogBack,boolean meetable,int width,int height,int col,int row,
			int refCol,int refRow,int [][] noThrough,int [][] meetableMatrix,int da) {
		super(at,bmpSelf,dbitmap, col, row, width, height, refCol, refRow, noThrough, meetable,
				meetableMatrix, da,bmpDialogBack);
	}
	@Override
	public void drawDialog(Canvas canvas, Figure figure) 
	{
		String showString=null;//获得新闻内容
		tempFigure = figure;//获取人物
		WindPic=PicManager.getPic("person1",tempFigure.father.activity.getResources());//获得龙卷风袭击交换位置的动画图片
		for(int i=0;i<3;i++)
		{
			person[i]=Bitmap.createBitmap(WindPic,48*i, 0,48, 38);
		}
		WindPic=null;
		WindPic=PicManager.getPic("wind1",tempFigure.father.activity.getResources());//获得龙卷风袭击交换位置的动画图片
		for(int i=0;i<4;i++)
		{
			Wind[i]=Bitmap.createBitmap(WindPic, 20+110*i, 0,100, 230);
		}
		WindPic=null;
		bitmaps=new Bitmap[]
		{
				PicManager.getPic("free_house",tempFigure.father.activity.getResources()),
				PicManager.getPic("traffic_jam",tempFigure.father.activity.getResources()),
				PicManager.getPic("fired_house",tempFigure.father.activity.getResources()),
				PicManager.getPic("landlord",tempFigure.father.activity.getResources()),
				PicManager.getPic("tornado_destory",tempFigure.father.activity.getResources()),
				PicManager.getPic("stop_loans",tempFigure.father.activity.getResources()),
				PicManager.getPic("shares",tempFigure.father.activity.getResources()),
				PicManager.getPic("change_sitting",tempFigure.father.activity.getResources()),
				PicManager.getPic("typhoond_house",tempFigure.father.activity.getResources()),
				PicManager.getPic("land_away",tempFigure.father.activity.getResources()),
				PicManager.getPic("pay_tax",tempFigure.father.activity.getResources()),
				PicManager.getPic("benefit_bank",tempFigure.father.activity.getResources()),
				PicManager.getPic("aliencome",tempFigure.father.activity.getResources())
		};
		fireBitps=new Bitmap[7];
		fireBitps[0]=PicManager.getPic("fire0",tempFigure.father.activity.getResources());
		fireBitps[1]=PicManager.getPic("fire1",tempFigure.father.activity.getResources());
		fireBitps[2]=PicManager.getPic("fire2",tempFigure.father.activity.getResources());
		fireBitps[3]=PicManager.getPic("fire3",tempFigure.father.activity.getResources());
		fireBitps[4]=PicManager.getPic("fire4",tempFigure.father.activity.getResources());
		fireBitps[5]=PicManager.getPic("fire5",tempFigure.father.activity.getResources());
		fireBitps[6]=PicManager.getPic("fire6",tempFigure.father.activity.getResources());
		
		if(first)
		{
			this.isFigure=tempFigure.father.isFigure;
			ReportNews();//显示新闻内容
			first=false;
		}
		if(iscount<10)//绘制10次后，不再绘制
		{
			canvas.drawBitmap(bitmaps[messagenum],NEWS_X, NEWS_Y, null);//绘制新闻对话框
			showString=newsDialogM[messagenum];
			if(messagenum==2||messagenum==4||messagenum==8||messagenum==9)
			{
				showString = showString.replaceFirst("xx", message0[num]);//替换掉城市名称字符串
			}
			else if(messagenum==11)//绘制股市
			{
				showString = showString.replaceFirst("xx", SharesDetails.BigName);
			}
			else if(messagenum==3)//土地第一大户
			{
				showString = showString.replaceFirst("xx", bigLand);
			}
			drawString(canvas,showString);//绘制新闻对话框中的文字说明
			iscount++;
		}else
		{	
			if(iscount>20)//返回到游戏主界面
			{
				recoverGame();
			}else if(iscount==10)//跳到相应的地区上
			{
				isnum();
			}else  if(iscount==15)//各个新闻的各个具体操作
			{
				isAction();
			}
			if(isFire)//绘制火焰
			{
				indext++;
				canvas.drawBitmap(fireBitps[indext], tempx,tempy, null);
				
			}
			if(isSitting)//交换位置
			{
				indext++;
				if(indext<4)
				{
					canvas.drawBitmap(Wind[indext], 400,200, null);
					canvas.drawBitmap(person[0], 400,200, null);
					canvas.drawBitmap(person[1], 470,200, null);
					canvas.drawBitmap(person[2], 430,240, null);
				}
			}
			iscount++;
		}
	}
	//绘制给定的字符串到对话框上
	public void drawString(Canvas canvas,String string)
	{
		Paint paint = new Paint();
		paint.setARGB(255,255,255,255);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setFakeBoldText(true);//字体加粗
		paint.setTextSize(NEWS_WORD_SIZE);//设置文字大小
		int lines = string.length()/NEWS_WORD_EACH_LINE+(string.length()%NEWS_WORD_EACH_LINE==0?0:1);//求出需要画几行文字
		for(int i=0;i<lines;i++)
		{
			String str="";
			if(i == lines-1)
			{//如果是最后一行那个不太整的汉字
				str = string.substring(i*NEWS_WORD_EACH_LINE);
			}else
			{
				str = string.substring(i*NEWS_WORD_EACH_LINE, (i+1)*NEWS_WORD_EACH_LINE);
			}
			canvas.drawText(str, NEWS_WORDX, NEWS_WORDY+NEWS_WORD_SIZE*i, paint);
		}
	}
	public void isAction()
	{
		switch(messagenum)
		{
		case 0://部分地区建筑免费加盖一层
			Room.addRoomNews(tempFigure.father, tempCol1, tempCol2, tempRow1, tempRow2);
			break;
		case 1://停止一回合
			tempFigure.father.isGoTo=false;
			break;
		case 2://xx一处民宅瓦斯爆炸房屋失火
			Room.CutRoomNews(tempFigure.father,1, this);
			break;
		case 3://第一大地主,xx获得$10000元奖励
			
			break;
		case 4://超级台风侵袭 xx,多处房屋受损
			Room.CutRoomNews(tempFigure.father, 0, this);
			break;
		case 5://银行挤兑停止放款7天
			BankDrawable.isWork=false;
			BankDrawable.existDay=7;
			break;
		case 6://银行加发10%存款红利
			int temp=(int) (tempFigure.father.figure.mhz.cMoney*0.1);
			tempFigure.father.figure.mhz.cMoney +=temp;
			tempFigure.father.figure.mhz.zMoney+=temp;
			
			temp=(int) (tempFigure.father.figure1.mhz.cMoney*0.1);
			tempFigure.father.figure1.mhz.cMoney +=temp;
			tempFigure.father.figure1.mhz.zMoney+=temp;
			
			temp=(int) (tempFigure.father.figure2.mhz.cMoney*0.1);
			tempFigure.father.figure2.mhz.cMoney +=temp;
			tempFigure.father.figure2.mhz.zMoney+=temp;
			break;
		case 7://龙卷风侵袭,所有人交换位子
			tempFigure.father.cs.changeNum=(int)(3*Math.random());
			isSitting=true;
			break;
		case 8://龙卷风侵袭 xx,摧毁房屋一栋
			Room.CutRoomNews(tempFigure.father, 1, this);
			break;
		case 9://xx山洪爆发土地流失
			Room.CutRoomNews(tempFigure.father, 0,this);
			break;
		case 10://所有人缴交所得税5%
			//现金减少5%
			int tax=(int) (tempFigure.father.figure.mhz.xMoney*0.05);
			tempFigure.father.figure.mhz.xMoney -=tax;
			tempFigure.father.figure.mhz.zMoney -=tax;
			
			tax=(int) (tempFigure.father.figure1.mhz.xMoney*0.05);
			tempFigure.father.figure1.mhz.xMoney -=tax;
			tempFigure.father.figure1.mhz.zMoney -=tax;
			
			tax=(int) (tempFigure.father.figure2.mhz.xMoney*0.05);
			tempFigure.father.figure2.mhz.xMoney -=tax;
			tempFigure.father.figure2.mhz.zMoney -=tax;
			break;
		case 11://股市第一大户,xx获得$10000元奖励
			break;
		case 12://外星人入侵地球,部分地区建筑被摧毁损坏,夷为平地
			Room.CutRoomNews(tempFigure.father, 0,this);
			break;
		}
	}
	public void isnum()//跳到相应的地区
	{
		switch(num)
		{
			case 0:
				tempFigure.father.isFigure=4;
				this.tempCol1=7;
				this.tempCol2=13;
				this.tempRow1=5;
				this.tempRow2=8;
			break;
			case 1:
				tempFigure.father.isFigure=5;
				this.tempCol1=15;
				this.tempCol2=23;
				this.tempRow1=5;
				this.tempRow2=10;
			break;
			case 2:
				tempFigure.father.isFigure=6;
				this.tempCol1=8;
				this.tempCol2=16;
				this.tempRow1=11;
				this.tempRow2=16;
			break;
			case 3:
				tempFigure.father.isFigure=7;
				this.tempCol1=6;
				this.tempCol2=13;
				this.tempRow1=18;
				this.tempRow2=24;
			break;
			case 4:
				tempFigure.father.isFigure=8;
				this.tempCol1=17;
				this.tempCol2=23;
				this.tempRow1=17;
				this.tempRow2=23;
			break;
			case 5:
				tempFigure.father.isFigure=9;
				this.tempCol1=24;
				this.tempCol2=24;
				this.tempRow1=12;
				this.tempRow2=15;
			break;
		}
	}
	//方法：返还监听器，回复游戏状态
	public void recoverGame()
	{
		tempFigure.father.setCurrentDrawable(null);//置空记录引用的变量
		tempFigure.father.setOnTouchListener(tempFigure.father);//返还监听器
		tempFigure.father.setStatus(0);//重新设置GameView为待命状态
		tempFigure.father.isFigure=this.isFigure;
		tempFigure.father.gvt.setChanging(true);//启动变换人物的线程
		//各个标志还原
		this.first=true;
		this.iscount=-1;
		this.num=-1;
		indext=-1;
		isFire=false;
		isSitting=false;
	}
	public int checkRoom(int temp)//检测几个区域土地是否被购买
	{
		int[] isnum=new int[6];
		int t=-1;
		for(int i=0;i<6;i++)
		{
			if(tempFigure.father.room[i]!=-1)
			{
				if(temp==0)//只需土地被购买
				{
					if(tempFigure.father.room[i]<6)
					{
						t++;
						isnum[t]=i;
					}
				}else if(temp==1)//该地必须有一层楼
				{
					if(tempFigure.father.room[i]>0)
					{
						t++;
						isnum[t]=i;
					}
				}
			}
		}
		int numble=(int)(t*Math.random());//随机选取一个地区
		if(t>-1)
		{
			return isnum[numble];
		}
		return -1;
	}
	public void ReportNews()//选取新闻
	{
		while(true)
		{
			messagenum=(int)(13*Math.random());
			if(messagenum==11)//股市奖励
			{
				sd=new SharesDetails(tempFigure.father);
				sd.BigShares();
				if(SharesDetails.BigName==null)//当没有人购买股票时
				{
					messagenum=12;
				}
				break;
			}
			else if(messagenum==0||messagenum==9||messagenum==12)//只需土地被购买
			{
				int i=checkRoom(0);
				if(i!=-1)
				{
					num=i;
					break;
				}
			}else if(messagenum==2||messagenum==4||messagenum==8)//需要房屋有一层
			{
				int i=checkRoom(1);
				if(i!=-1)
				{
					num=i;
					break;
				}
			}else if(messagenum==3)//需要有人购买了土地
			{
				if(tempFigure.father.figure.count>0||tempFigure.father.figure1.count>0||tempFigure.father.figure2.count>0)
				{
					if(tempFigure.father.figure.count>=tempFigure.father.figure1.count&&tempFigure.father.figure.count>tempFigure.father.figure2.count)
					{//玩家
						tempFigure.father.figure.mhz.zMoney+=10000;
						bigLand=tempFigure.father.figure.figureName;
					}else if(tempFigure.father.figure1.count>tempFigure.father.figure.count&&tempFigure.father.figure1.count>=tempFigure.father.figure2.count)
					{//系统人物1
						tempFigure.father.figure1.mhz.zMoney+=10000;
						bigLand=tempFigure.father.figure1.figureName;
					}else if(tempFigure.father.figure2.count>tempFigure.father.figure.count&&tempFigure.father.figure2.count>tempFigure.father.figure1.count)
					{//系统人物2
						tempFigure.father.figure2.mhz.zMoney+=10000;
						bigLand=tempFigure.father.figure2.figureName;
					}
					break;
				}
				else
				{
					messagenum=1;
				}
			}
			else
			{
				break;
			}
		}
	}
}
