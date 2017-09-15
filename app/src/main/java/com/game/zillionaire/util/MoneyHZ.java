package com.game.zillionaire.util;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class MoneyHZ implements Externalizable
{
	public int zMoney;//总资产
	public int cMoney;//存款
	public int xMoney;//现金
	public int gameDian;//游戏点数
	public int result=0;//现金的中间量
	public int lixi=0;//利息
	public MoneyHZ()
	{
		this.xMoney=100000;
		this.cMoney=100000;
		this.zMoney=xMoney+cMoney;
		this.gameDian=150;//起始点数为150点
	}
	public void writeExternal(ObjectOutput out) throws IOException{
		out.writeInt(xMoney);
		out.writeInt(cMoney);
		out.writeInt(zMoney);
		out.writeInt(gameDian);
		out.writeInt(result);
		out.writeInt(lixi);
	}
	public void readExternal(ObjectInput in) throws IOException,ClassNotFoundException
	{
		this.xMoney=in.readInt();
		this.cMoney=in.readInt();
		this.zMoney=in.readInt();
		this.gameDian=in.readInt();
		this.result=in.readInt();
		this.lixi=in.readInt();
	}
	public int get_result()
	{
		return result;
	}
	public void set_result(int result)
	{
		this.result=result;
	}
	public int get_lixi()
	{
		return lixi;
	}
	public void set_lixi(int lixi)
	{
		this.lixi=lixi;
	}
	public int get_xMoney()
	{
		return xMoney;
	}
	public void set_xMoney(int xMoney)
	{
		this.xMoney=xMoney;
	}
	public int get_cMoney()
	{
		return cMoney;
	}
	public void set_cMoney(int cMoney)
	{
		this.cMoney=cMoney;
	}
	public int get_zMoney()
	{
		return zMoney;
	}
	public void set_zMoney(int zMoney)
	{
		this.zMoney=zMoney;
	}
	public int get_gameDian()
	{
		return gameDian;
	}
	public void set_gameDian(int gameDian)
	{
		this.gameDian=gameDian;
	}
	//判断资金是否足够
	public boolean isCut(String string)
	{
		String str=null;
		if(string.length()>=4)//钱数大于1000元时
		{
			String[] message=string.split(",");
			str=message[0]+message[1];
		}else//钱数小于1000元时
		{
			str=string;
		}
		result=Integer.parseInt(str);
		if((xMoney-result)>=0)
		{
			return true;
		}
		return false;
	}
	//减少资产
	public void cutMoney(int isdo)
	{
		xMoney=xMoney-result;//计算现金的剩余数
		if(isdo!=0)//不是购买土地时,总资金要改变
		{
			this.zMoney=xMoney+cMoney;
		}
	}
	
	public void cutMoney(int isdo,int id)
	{
		if(id==0||id==2)
		{//大财神或土地公---不付钱
			xMoney=xMoney-0;
		}else if(id==3)
		{//小财神--少付一半
			xMoney=xMoney-(int)(0.5*result);//计算现金的剩余数
		}else if(id==6)
		{//小穷神--多付一半
			xMoney=xMoney-(int)(1.5*result);//计算现金的剩余数
		}else if(id==7)
		{//大穷神--多付一倍
			xMoney=xMoney-2*result;//计算现金的剩余数
		}
		if(isdo!=0)//不是购买土地时,总资金要改变
		{
			this.zMoney=xMoney+cMoney;
		}
	}
	//增加资产
	public void addMoney(String string)
	{
		String str=null;
		if(string.length()>=4)
		{
			String[] message=string.split("，");
			str=message[0]+message[1];
		}else
		{
			str=string;
		}
		result=Integer.parseInt(str);
		xMoney=xMoney+result;//计算现金的剩余数
		this.zMoney=xMoney+cMoney;
	}
	//游戏点数的增加
	public void addGameDian(String string)
	{
		result=Integer.parseInt(string);
		gameDian=gameDian+result;
	}
	//游戏点数是否够减
	public boolean isCutGameDiane(String string)
	{
		result=Integer.parseInt(string);
		if(result>gameDian)
		{
			return true;
		}
		return false;
	}
	//游戏点数的减少
	public void cutGameDian()
	{
		gameDian=gameDian-result;
	}
}
