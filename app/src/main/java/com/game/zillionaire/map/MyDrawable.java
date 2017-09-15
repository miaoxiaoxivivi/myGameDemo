package com.game.zillionaire.map;							//声明包语句
import static com.game.zillionaire.util.ConstantUtil.TILE_SIZE_X;
import static com.game.zillionaire.util.ConstantUtil.TILE_SIZE_Y;

import java.io.Externalizable;						//引入相关类
import java.io.IOException;							//引入相关类
import java.io.ObjectInput;							//引入相关类
import java.io.ObjectOutput;						//引入相关类

import com.game.zillionaire.activity.ZActivity;
import com.game.zillionaire.util.PicManager;

import android.graphics.Bitmap;						//引入相关类
import android.graphics.Canvas;						//引入相关类
/*
 * 该类中封装了一个地图图元的信息，每个MyDrawable类是在地图上占有一个格子，
 * 该类中包含图片引用，图片宽高，图片位置（row，col），定位点坐标，不可通过矩阵
 * 以及是否可遇的标志位
 */
public class MyDrawable implements Externalizable
{
	public String bpName;//自己图片的名称
	public int width;//图元的宽度
	public int height;//图元的高度
	public int col;//在大地图中所在的列
	public int row;//在大地图中所在的行
	public int refCol;//定位参考点在本MyDrawable中所占的列，以左下角为原点
	public int refRow;//定位参考点在本MyDrawable中所占的行，以左下角为原点
	public int [][] noThrough;//不可通过矩阵
	public boolean meetable;//是否可以遇到
	public int indext;//判断是什么路
	public int da;
	public int ss=-1;//该土地是否被购买
	public int k=-1;//房子等级标志
	public int kk=-1;//谁的房子标志
	public int zb=0;//大土地选中项的标志
	public int value=0;//各个房屋价值
	public boolean first=true;//判断是否是第一次
	public boolean flagg=true;
	
	public boolean flag=false;//判断路面上是否有物体
	public int bitmapx,bitmapy;//图片坐标
	public String dbpName;//大土地上的房屋名称
	public ZActivity at;
	public MyDrawable(){}//无参构造器
	//构造器
	public MyDrawable(ZActivity at,String bpName,String dbpName,boolean meetable,int width,int height,int col,int row,int refCol,int refRow,int [][] noThrough,int indext,int da)
	{
		this.at=at;
		this.bpName=bpName;
		this.dbpName=dbpName;
		this.width = width;
		this.height = height;
		this.col = col;
		this.row = row;
		this.refCol = refCol;
		this.refRow = refRow;
		this.noThrough = noThrough;
		this.meetable = meetable;
		this.indext=indext;
		this.da=da;
	}
	//方法：绘制自己
	public void drawSelf(Canvas canvas,ZActivity at,int screenRow,int screenCol,int offsetX,int offsetY)
	{
		Bitmap bmpSelf=null;
		while(bmpSelf==null)
		{
			if(bpName==null){return;}
			bmpSelf=PicManager.getPic(bpName,at.getResources()); 
		}
		int x= (screenCol-refCol)*TILE_SIZE_X;//求出自己所拥有的块数中左上角块的x坐标
		int y =screenRow*TILE_SIZE_Y+(refRow+1)*TILE_SIZE_Y-bmpSelf.getHeight();//求出自己所拥有的块数中左上角块的y坐标
		bitmapx= x-offsetX;
		bitmapy=y-offsetY;
		canvas.drawBitmap(bmpSelf,bitmapx, bitmapy, null);//根据自己的左上角的xy坐标画出自己
	}
	
	public void writeExternal(ObjectOutput out) throws IOException 
	{
		out.writeUTF(bpName);
		out.writeInt(width);//图元的宽度
		out.writeInt(height);//图元的高度
		out.writeInt(col);//在大地图中所在的列
		out.writeInt(row);//在大地图中所在的行
		out.writeInt(refCol);//定位参考点在本MyDrawable中所占的列，以左下角为原点
		out.writeInt(refRow);//定位参考点在本MyDrawable中所占的行，以左下角为原点
		out.writeObject(noThrough);//不可通过矩阵
		out.writeBoolean(meetable);//是否可以遇到
		out.writeInt(indext);
		out.writeInt(da);
		out.writeInt(ss);
		out.writeInt(k);
		out.writeInt(kk);
		out.writeInt(zb);
		out.writeInt(value);
		out.writeBoolean(first);
		out.writeBoolean(flagg);
		out.writeBoolean(flag);
		out.writeInt(bitmapx);
		out.writeInt(bitmapy);
		out.writeUTF(dbpName);
	}
	public void readExternal(ObjectInput in) throws IOException,ClassNotFoundException 
	{
		bpName=in.readUTF();
		width = in.readInt();
		height = in.readInt();
		col = in.readInt();
		row = in.readInt();
		refCol = in.readInt();
		refRow = in.readInt();
		noThrough = (int[][]) in.readObject();
		meetable = in.readBoolean();
		indext=in.readInt();
		da=in.readInt();
		ss=in.readInt();
		k=in.readInt();
		kk=in.readInt();
		zb=in.readInt();
		value=in.readInt();
		first=in.readBoolean();
		flagg=in.readBoolean();
		flag=in.readBoolean();
		bitmapx=in.readInt();
		bitmapy=in.readInt();
		dbpName=in.readUTF();
	}
	public int get_bitmapy()
	{
		return bitmapy;
	}
	public void set_bitmapy(int bitmapy)
	{
		this.bitmapy=bitmapy;
	}
	public int get_bitmapx()
	{
		return bitmapx;
	}
	public void set_bitmapx(int bitmapx)
	{
		this.bitmapx=bitmapx;
	}
	public boolean get_flag()
	{
		return flag;
	}
	public void set_flag(boolean flag)
	{
		this.flag=flag;
	}
	public boolean get_flagg()
	{
		return flagg;
	}
	public void set_flagg(boolean flagg)
	{
		this.flagg=flagg;
	}
	public boolean get_first()
	{
		return first;
	}
	public void set_first(boolean first)
	{
		this.first=first;
	}
	public int get_value()
	{
		return value;
	}
	public void set_value(int value)
	{
		this.value=value;
	}
	public int get_zb()
	{
		return zb;
	}
	public void set_zb(int zb)
	{
		this.zb=zb;
	}
	public int get_k()
	{
		return k;
	}
	public void set_k(int k)
	{
		this.k=k;
	}
	public int get_kk()
	{
		return kk;
	}
	public void set_kk(int kk)
	{
		this.kk=kk;
	}
	public int get_ss()
	{
		return ss;
	}
	public void set_ss(int ss)
	{
		this.ss=ss;
	}
	public int get_da()
	{
		return da;
	}
	public void set_da(int da)
	{
		this.da=da;
	}
	public int get_indext()
	{
		return indext;
	}
	public void set_indext(int indext)
	{
		this.indext=indext;
	}
	public boolean get_meetable()
	{
		return meetable;
	}
	public void set_meetable(boolean meetable)
	{
		this.meetable=meetable;
	}
	public int[][] get_noThrough()
	{
		return noThrough;
	}
	public void set_noThrough(int[][] noThrough)
	{
		this.noThrough=noThrough;
	}
	public void set_refCol(int refCol)
	{
		this.refCol=refCol;
	}
	public int get_refCol()
	{
		return col;
	}
	public void set_refRow(int refRow)
	{
		this.refRow=refRow;
	}
	public int get_refRow()
	{
		return refRow;
	}
	public void set_col(int col)
	{
		this.col=col;
	}
	public int get_col()
	{
		return col;
	}
	public void set_row(int row)
	{
		this.row=row;
	}
	public int get_row()
	{
		return row;
	}
	public void set_height(int height)
	{
		this.height=height;
	}
	public int get_height()
	{
		return height;
	}
	public void set_width(int width)
	{
		this.width=width;
	}
	public int get_width()
	{
		return width;
	}
	public void set_bpName(String bpName)
	{
		this.bpName=bpName;
	}
	public String get_bpName()
	{
		return bpName;
	}
	public void set_dbpName(String dbpName)
	{
		this.dbpName=dbpName;
	}
	public String get_dbpName()
	{
		return dbpName;
	}
}