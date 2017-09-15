package com.game.zillionaire.util;

import java.util.ArrayList;

import com.game.zillionaire.view.GameView;


public class SharesDetails{
	
	//0--红豆杉   1--柳杉   2--华山松    3--相思树   4--梧桐   5--樟木  6--紫婵木
	GameView gv;
	String[] personshares=new String[2];//存放系统人物购买股票信息   personshares[0]:1--系统人物1   2--系统人物2
	public int sharesnum=7;//定义有几支股票
	String[][] mess=new String[sharesnum][6];//显示股票信息
	int flag=0;//设置涨跌或哪个系统人物购买股票
	double status=20;//涨跌范围
	public boolean Up=false,Low=false;//是否使用红卡、黑卡
	public ArrayList<String[]> datails=new ArrayList<String[]>();//存放股票信息
	int systemsnum=0;//系统人物购买的股票数
	int buymoney;//购买股票花费的金钱
	int witchshares;//股票编号
	double levels=0.6;//系统人物买股票的程度
	public String name;//股票名
	public static String BigName=null;//最大股市客户名称
	int person=-1;//记录哪位为股市最大户
	int count=0;
	public String SharesName;//买卖股票系统人物的姓名
	public SharesDetails(GameView gv)
	{
		this.gv=gv;
		setDetails();
	}
	//0--名称  1--成交价  2--涨跌 3--交易量    4--持股数    5--平均成本
	public void setDetails()//设置基本的股票信息
	{
		mess[0][0]="红豆杉";
		mess[0][1]="50";
		mess[0][2]="0";
		mess[0][3]="2000";
		mess[0][4]="0";
		mess[0][5]="0";
		
		mess[1][0]="柳杉";
		mess[1][1]="50";
		mess[1][2]="0";
		mess[1][3]="2000";
		mess[1][4]="0";
		mess[1][5]="0";
		
		mess[2][0]="华山松";
		mess[2][1]="50";
		mess[2][2]="0";
		mess[2][3]="2000";
		mess[2][4]="0";
		mess[2][5]="0";
		
		mess[3][0]="相思树";
		mess[3][1]="50";
		mess[3][2]="0";
		mess[3][3]="2000";
		mess[3][4]="0";
		mess[3][5]="0";
		
		mess[4][0]="梧桐";
		mess[4][1]="50";
		mess[4][2]="0";
		mess[4][3]="2000";
		mess[4][4]="0";
		mess[4][5]="0";
		
		mess[5][0]="樟木";
		mess[5][1]="50";
		mess[5][2]="0";
		mess[5][3]="2000";
		mess[5][4]="0";
		mess[5][5]="0";
		
		mess[6][0]="紫婵木";
		mess[6][1]="50";
		mess[6][2]="0";
		mess[6][3]="2000";
		mess[6][4]="0";
		mess[6][5]="0";
		for(int i=0;i<mess.length;i++)
		{
			datails.add(mess[i]);
		}
	}
	public void initDetails()//股票成交价的变化
	{
		uporDown();
		for(int i=0;i<datails.size();i++)//平均成本=((成交价1)
		{
			String str[]=new String[3];//获得股票信息
			str[0]=datails.get(i)[1];//成交价
			str[1]=datails.get(i)[2];//涨跌
			str[2]=datails.get(i)[4];//持股数
			if(Integer.parseInt(str[2])==0)//购买股票了
			{
				datails.get(i)[5]=0+"";
			}
			str[0]=Double.parseDouble(str[0])+Double.parseDouble(str[1])+"";
			if(Double.parseDouble(str[0])<10||Double.parseDouble(str[0])>350)//当不为10-350时
			{
				datails.get(i)[1]=50+"";
			}
			else
			{
				String mess=changeShip(str[0]);//获得修改过的数据
				if(mess!=null)
				{
					datails.get(i)[1]=mess;
				}
				mess=null;
			}
			str=null;
		}
	}
	public String changeShip(String str)//使数据
	{
		for(int i=0;i<str.length();i++)
		{
			if(str.charAt(i)=='.')
			{
				str=str.substring(0, i+2);
				return str;
			}
		}
		return null;
	}
	
	public void BigShares()//股市最大户
	{	
		int[] count=new int[3];
		for(int i=0;i<7;i++)
		{
			count[0] +=gv.figure.mps.get(i);
			count[1] +=gv.figure1.mps.get(i);
			count[2] +=gv.figure2.mps.get(i);
		}
		if(count[0]!=0||count[1]!=0||count[2]!=0)//有人购买了股票
		{
			if(count[0]>count[1])//玩家>系统1
			{
				if(count[0]>count[2])//玩家>系统2
				{
					person=0;
					BigName=gv.figure.figureName;
					gv.figure.mhz.xMoney +=10000;//奖励10000元
				}
			}
			else
			{
				if(count[1]>count[2])//系统1>系统2
				{
					person=1;
					BigName=gv.figure1.figureName;
					gv.figure1.mhz.xMoney +=10000;//奖励10000元
				}
				else
				{
					person=2;
					BigName=gv.figure2.figureName;
					gv.figure2.mhz.xMoney +=10000;//奖励10000元
				}
			}
		}else//没有人购买
		{
			person=-1;
		}
	}
	public String[] BuyShares(int num)//购买相应的股票
	{
		String[] buymess=new String[4];//获得对应股票的名称、成交价、交易量、持股数
		buymess[0]=datails.get(num)[1];//成交价
		buymess[1]=datails.get(num)[3];//交易量
		buymess[2]=datails.get(num)[4];//持股数
		buymess[3]=datails.get(num)[0];//名称
		return buymess;
	}
	public void uporDown()//控制股票的涨跌
	{
		for(int i=0;i<datails.size();i++)
		{
			flag=(int)(2*Math.random());//正负标志位 
			
			status=(-status)*Math.random();
			
			if(flag==0)//跌
			{
				status=(-status)*Math.random();
			}
			else//升
			{
				status=(status)*Math.random();
			}
			String mess=changeShip(Double.parseDouble(datails.get(i)[2])+status+"");//获得修改过的数据
			if(mess!=null)
			{
				datails.get(i)[2]=mess;
			}
			mess=null;
		}
		flag=0;
	}
	
	public void Byperson(int num)//对指定的股票使用红黑卡
	{
		if(!Up==Low||!Low==Up)
		{
			if(Up)//不为0就行
			{
				flag=2;
				//Up=false;
			}
			else if(Low)
			{
				flag=0;
				//Low=false;
			}
		}
		if(flag==0)//跌
		{
			status=(-status)*Math.random();
		}
		else//升
		{
			status=(status)*Math.random();
		}
		String mess=changeShip(Double.parseDouble(datails.get(num)[2])+status+"");//获得修改过的数据
		if(mess!=null)
		{
			datails.get(num)[2]=mess;
		}
		mess=null;
	}
	public boolean  systemSale()//系统人物卖股票
	{	
		//成功卖出返回true  卖出失败返回false	
		flag=(int)(2*Math.random());//正负标志位 
		if(flag==0)//系统人物1卖
		{
			for(int i=0;i<sharesnum;i++)
			{
				if(gv.figure1.mps!=null)
				{
					int zhi=gv.figure1.mps.get(i);
					if(zhi>0)//卖出股票
					{
						buymoney=(int)(zhi*(Float.parseFloat(datails.get(i)[1])+Float.parseFloat(datails.get(i)[2])));//卖股票获得的金钱
						gv.figure1.mhz.cMoney +=buymoney;//刷新系统人物的钱
						datails.get(i)[3]=Integer.parseInt(datails.get(i)[3])+zhi+"";
						name=datails.get(i)[0]+zhi+"股";//股票名称
						SharesName=gv.figure1.figureName;//系统人物1
						gv.figure1.mps.put(i, 0);//更新持股数
						gv.figure1.mpsName.remove(i);//删除名称
						gv.figure1.mpsCost.remove(i);
						return true;
					}
				}
			}
			return false;
		}
		else//系统人物2卖
		{
			for(int i=0;i<sharesnum;i++)
			{
				int zhi=gv.figure2.mps.get(i);
				if(zhi>0)//卖出股票
				{
					buymoney=(int)(zhi*(Float.parseFloat(datails.get(i)[1])+Float.parseFloat(datails.get(i)[2])));//卖股票获得的金钱
					gv.figure2.mhz.cMoney +=buymoney;//刷新系统人物的钱
					datails.get(i)[3]=Integer.parseInt(datails.get(i)[3])+zhi+"";
					name=datails.get(i)[0]+zhi+"股";//股票名称
					SharesName=gv.figure2.figureName;//系统人物2
					gv.figure2.mps.put(i, 0);//更新持股数
					gv.figure2.mpsName.remove(i);//删除名称
					gv.figure2.mpsCost.remove(i);
					return true;
				}
			}
			return false;
		}
	}
	public boolean systemBuy()//系统人物购买股票
	{//成功购买返回true  购买失败返回false
		int money;//存款
		witchshares=(int)(sharesnum*Math.random());//购买随机数股票
		int changenum=Integer.parseInt(datails.get(witchshares)[3]);
		
		flag=(int)(2*Math.random());//哪个人物购买
		if(changenum>100)//获得购买量
		{
			systemsnum=(int)(levels*changenum/4);
		}
		else//购买失败
		{
			return false;
		}
		if(flag==0)//系统人物1购买
		{
			money=gv.figure1.mhz.cMoney;//获得系统人物的存款
			buymoney=(int)(systemsnum*Float.parseFloat(datails.get(witchshares)[1]));//购买股票花费的金钱
			personshares[0]=1+"";//存放系统人物股票属性
			personshares[1]=systemsnum+"";//获得购买的股票数量
			if(money>buymoney)//存款大于花费的执行
			{
				gv.figure1.mhz.cMoney -=buymoney;//刷新系统人物的钱
				//改变该股票的交易量
				datails.get(witchshares)[3]=Integer.parseInt(datails.get(witchshares)[3])-systemsnum+"";
				name=datails.get(witchshares)[0]+systemsnum+"股";//股票名称
				SharesName=gv.figure1.figureName;//系统人物1
				gv.figure1.mps.put(witchshares, systemsnum);
				gv.figure1.mpsName.put(witchshares, datails.get(witchshares)[0]);
				gv.figure1.mpsCost.put(witchshares, (double) buymoney);
				return true;
			}
			return false;
		}
		else//系统人物2购买
		{
			money=gv.figure2.mhz.cMoney;//获得系统人物的存款
			buymoney=(int)(systemsnum*Float.parseFloat(datails.get(witchshares)[1]));//购买股票花费的金钱
			personshares[0]=2+"";//存放系统人物股票属性
			personshares[1]=systemsnum+"";//获得购买的股票数量
			if(money>buymoney)
			{
				gv.figure2.mhz.cMoney -=buymoney;//刷新系统人物的钱
				datails.get(witchshares)[3]=Integer.parseInt(datails.get(witchshares)[3])-systemsnum+"";
				name=datails.get(witchshares)[0]+systemsnum+"股";//股票名称
				SharesName=gv.figure2.figureName;//系统人物2
				gv.figure2.mps.put(witchshares, systemsnum);
				gv.figure2.mpsName.put(witchshares, datails.get(witchshares)[0]);
				gv.figure2.mpsCost.put(witchshares, (double) buymoney);
				return true;
			}
			return false;
		}
		
	}

	public void operationShares(int num)//获得指定的股票信息
	{//平均成本=((第一次交易时的成交价)
		String justMess[]=new String[5];//临时存放数据
		justMess[0]=datails.get(num)[1];//成交价
		justMess[1]=datails.get(num)[2];//涨跌
		justMess[2]=datails.get(num)[3];//交易量
		justMess[3]=datails.get(num)[4];//持股数
		justMess[4]=datails.get(num)[5];//平均成本
		if(!justMess[3].equals("0"))
		{//如果持股数不为0，则有平均成本不为0
			justMess[4]=justMess[0];
		}
		else
		{//如果持股数为0，则平均成本为0
			justMess[4]="0";
		}	
		
		if(justMess[4].length()>4)
		{
			datails.get(num)[5]=justMess[4].substring(0, 5);
		}
		else
		{
			datails.get(num)[5]=justMess[4];
		}
		justMess=null;
	}
}
