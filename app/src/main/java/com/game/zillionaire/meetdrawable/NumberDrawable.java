package com.game.zillionaire.meetdrawable;

import java.io.Serializable;

import com.game.zillionaire.activity.ZActivity;
import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.map.GameData2;
import com.game.zillionaire.map.MyMeetableDrawable;
import com.game.zillionaire.util.ConstantUtil;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;					//引入相关类
import android.view.View.OnTouchListener;
import static com.game.zillionaire.util.ConstantUtil.*;

public class NumberDrawable extends MyMeetableDrawable implements OnTouchListener, Serializable{
	private static final long serialVersionUID = -8273047164560657219L;
	int num;
	String  StrNum;//获得点数语句
	String [][] dialogMessage=ConstantUtil.dialogMess;
	int status=-1;//状态位
	int flag=GameData2.flag;;
	Figure tempfigure;//英雄的引用,用于拷贝数据防止污染
	Thread thread;//显示点数线程
	private int count=0;
	
	public NumberDrawable(){}
	public NumberDrawable(ZActivity at,String bmpSelf,String dbitmap,String bmpDialogBack,boolean meetable,int width,int height,int col,int row,
			int refCol,int refRow,int [][] noThrough,int [][] meetableMatrix,int da) {
		super(at,bmpSelf,dbitmap, col, row, width, height, refCol, refRow, noThrough, meetable,
				meetableMatrix, da,bmpDialogBack);
	}
	@Override
	public void drawDialog(Canvas canvas, Figure figure){
		String showString = "";//需要显示到对话框中的字符串
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setFakeBoldText(true);//字体加粗
		paint.setTextSize(26);//设置文字大小
		tempfigure = figure;
		SelectNumber();//获得对应点数
		//先画背景
		if(count<10)
		{
			canvas.drawBitmap(tempfigure.father.gvu.dialogBack,NUM_X, NUM_Y, null);
			showString = StrNum;//确定要显示的字符串
			canvas.drawText(showString,NUM_X+NUM_WORDL,NUM_Y+DIALOG_WORD_SIZE*3, paint);
		}else
		{
			canvas.drawBitmap(tempfigure.father.gvu.dialogBack,NUM_X, NUM_Y, null);
			if(tempfigure.father.figure0==tempfigure.father.figure)//玩家
			{
				num=dialogMessage[0].length-1;
				if(count==10)
				{
					status=(int) (num*Math.random())+1;
					
				}
				showString=dialogMessage[0][status];
				canvas.drawText(showString,NUM_X+NUM_WORDL,NUM_Y+DIALOG_WORD_SIZE*3, paint);
			}
			else if(tempfigure.father.figure0==tempfigure.father.figure1)//系统人物1
			{
				num=dialogMessage[1].length-1;
				if(count==10)
				{
					status=(int) (num*Math.random())+1;
				}
				showString=dialogMessage[1][status];
				canvas.drawText(showString,NUM_X+NUM_WORDL,NUM_Y+DIALOG_WORD_SIZE*3, paint);
				
			}
			else//系统人物2
			{
				num=dialogMessage[2].length-1;
				if(count==10)
				{
					status=(int) (num*Math.random())+1;
				}
				showString=dialogMessage[2][status];
				canvas.drawText(showString,NUM_X+NUM_WORDL,NUM_Y+DIALOG_WORD_SIZE*3, paint);	
			}
			switch(status){
				case 0://给出获得的点数
					TotalNum();
					break;
				case 1://后话
				case 2:
				case 3:
					if(count>=15)
					recoverGame();
					break;		
			}
		}
		count++;
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return true;
	}
	//方法：返还监听器，回复游戏状态
	public void recoverGame(){
		this.count=0;
		tempfigure.father.setCurrentDrawable(null);//置空记录引用的变量
		tempfigure.father.setOnTouchListener(tempfigure.father);//返还监听器
		tempfigure.father.setStatus(0);//重新设置GameView为待命状态
		tempfigure.father.gvt.setChanging(true);//启动变换人物的线程
		status =-1;//状态复位
	}
	public void SelectNumber()//显示点数
	{
		switch(flag)
		{
		case 12:
			StrNum="获得30点";
			break;
		case 13:
			StrNum="获得50点";
			break;
		case 14:
			StrNum="获得80点";
			break;
		}
	}
	public void TotalNum()//获得点数
	{
		switch(flag)
		{
		case 12:
			tempfigure.mhz.gameDian +=30;
			break;
		case 13:
			tempfigure.mhz.gameDian +=50;
			break;
		case 14:
			tempfigure.mhz.gameDian +=80;
			break;
		}
	}
}
