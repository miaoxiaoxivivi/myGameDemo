package com.game.zillionaire.map;				//声明包语句
			//引入相关类
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import com.game.zillionaire.activity.ZActivity;
import com.game.zillionaire.figure.Figure;
import android.graphics.Canvas;				//引入相关类
import android.graphics.Paint;
import android.view.MotionEvent;			//引入相关类
import android.view.View;
import static com.game.zillionaire.util.ConstantUtil.*;

public abstract class MyMeetableDrawable extends MyDrawable implements View.OnTouchListener,Externalizable {
	public int [][] meetableMatrix;//可遇矩阵，相对于本MyDrawable所占的块数
	public String bmpDialogBack;//对话框背景图片名称
	protected Figure tempFigure;//英雄的引用,用于拷贝数据防止污染
	public int accidentRandom=-1;//机遇（遇到机遇时产生的随机条件）
	public int figure0MagicRandom=-1;//魔法屋（操作人物遇到魔法屋后随机产生的条件）
	public int figure1MagicRandom=-1;//魔法屋(系统人物遇到魔法屋后随机产生的条件)
	public int random=-1;
	public String money1=null;
	public String money2=null;
	public String money3=null;
	//构造器
	public MyMeetableDrawable(){}
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
	public MyMeetableDrawable(
			ZActivity at,String bmpSelf,String dbitmap,int col,int row,int width,int height,
			int refCol,int refRow,int [][] noThrough,boolean meetable,int [][] meetableMatrix,int da,
			String bmpDialogBack)
	{
		super(at,bmpSelf,dbitmap,meetable,width,height,col,row,refCol,refRow,noThrough,0,da);
		this.meetableMatrix = meetableMatrix;
		this.bmpDialogBack = bmpDialogBack;
	}
	//在游戏屏幕上绘制对话框的方法
	public abstract void drawDialog(Canvas canvas,Figure figure);
	//绘制给定的字符串到对话框上
	public void drawString(Canvas canvas,String string,int location_x,int location_y)
	{
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setTextSize(DIALOG_WORD_SIZE);//设置文字大小
		int lines = string.length()/DIALOG_WORD_EACH_LINE+(string.length()%DIALOG_WORD_EACH_LINE==0?0:1);//求出需要画几行文字
		for(int i=0;i<lines;i++)
		{
			String str="";
			if(i == lines-1)//如果是最后一行那个不太整的汉字
			{
				str = string.substring(i*DIALOG_WORD_EACH_LINE);
			}else
			{
				str = string.substring(i*DIALOG_WORD_EACH_LINE, (i+1)*DIALOG_WORD_EACH_LINE);
			}
			canvas.drawText(str, location_x, location_y+DIALOG_WORD_SIZE*i, paint);
		}
	}
	public void drawTitleString(Canvas canvas,String string)
	{
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setTextSize(28);//设置文字大小
		canvas.drawText(string, 625, 110, paint);
	}
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		return false;
	}
}
