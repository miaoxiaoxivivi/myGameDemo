package com.game.zillionaire.card;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;

import com.game.zillionaire.dialog.DialogEnum;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.ScreenTransUtil;
import com.game.zillionaire.view.GameView;

public class StopCard extends UsedCard
{
	public StopCard(GameView gv, Bitmap bitmap) {
		super(gv, bitmap);
		this.isChange=false;//对话框类型赋值
		this.gv=gv;
		this.isDrawCard=true;//是否绘制卡片赋值
		this.bitmap0=bitmap;//使用的卡片图片赋值
	}
	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		int x=ScreenTransUtil.xFromRealToNorm((int)event.getX());//得到点击的X坐标
		int y=ScreenTransUtil.yFromRealToNorm((int)event.getY());//得到点击的Y坐标
		if(drawdialog)//绘制选择是否确定使用该卡片
		{
			if(x>=ConstantUtil.AllianceCard_RecoverGame_Left_X&&x<=ConstantUtil.AllianceCard_RecoverGame_Right_X
					&&y>=ConstantUtil.AllianceCard_RecoverGame_Up_Y&&y<=ConstantUtil.AllianceCard_RecoverGame_Down_Y)//不使用卡片
			{
				recoverGame();//返回游戏
			}else//使用卡片
			{
				if(count==1)//符合条件的英雄数为1
				{
					if(x>=ConstantUtil.AllianceCard_Count1_Left_X&&x<=ConstantUtil.AllianceCard_Count1_Right_X
							&&y>=ConstantUtil.AllianceCard_Count1_Up_Y&&y<=ConstantUtil.AllianceCard_Count1_Down_Y)
					{
						setStop(xx[0][1]);//停留卡
					}
				}else if(count==2)//符合条件的英雄数为2
				{
					if(x>=ConstantUtil.AllianceCard_Count2_Figure1_Left_X&&x<=ConstantUtil.AllianceCard_Count2_Figure1_Right_X
							&&y>=ConstantUtil.AllianceCard_Count2_Figure1_Up_Y&&y<=ConstantUtil.AllianceCard_Count2_Figure1_Down_Y)
					{
						setStop(xx[0][1]);//停留卡
					}else if(x>=ConstantUtil.AllianceCard_Count2_Figure2_Left_X&&x<=ConstantUtil.AllianceCard_Count2_Figure2_Right_X
							&&y>=ConstantUtil.AllianceCard_Count2_Figure2_Up_Y&&y<=ConstantUtil.AllianceCard_Count2_Figure2_Down_Y)
					{
						setStop(xx[1][1]);//停留卡
					}
				}else if(count==3)//符合条件的英雄数为3
				{
					if(x>=ConstantUtil.AllianceCard_Count3_Figure1_Left_X&&x<=ConstantUtil.AllianceCard_Count3_Figure1_Right_X
							&&y>=ConstantUtil.AllianceCard_Count3_Figure1_Up_Y&&y<=ConstantUtil.AllianceCard_Count3_Figure1_Down_Y)
					{
						setStop(xx[0][1]);//停留卡	
					}else if(x>=ConstantUtil.AllianceCard_Count3_Figure2_Left_X&&x<=ConstantUtil.AllianceCard_Count3_Figure2_Right_X
							&&y>=ConstantUtil.AllianceCard_Count3_Figure2_Up_Y&&y<=ConstantUtil.AllianceCard_Count3_Figure2_Down_Y)
					{
						setStop(xx[1][1]);//停留卡
					}else if(x>=ConstantUtil.AllianceCard_Count3_Figure3_Left_X&&x<=ConstantUtil.AllianceCard_Count3_Figure3_Right_X
							&&y>=ConstantUtil.AllianceCard_Count3_Figure3_Up_Y&&y<=ConstantUtil.AllianceCard_Count3_Figure3_Down_Y)
					{
						setStop(xx[2][1]);//停留卡
					}
				}
			}
		}
		return true;
	}
	public void setStop(int isFigure)//停留卡
	{
		this.isDrawCard=false;//不绘制卡片
		this.isChange=true;//对话框类型还原
		this.count=0;//头像数目还原
		this.pp=0;//选中框的索引还原
		this.iscount=0;//绘制次数还原
		md=null;//MyDrawable对象置空
		
		if(isFigure==0)//操作人物
		{
			gv.figure.isStop=true;//操作人物停留
			gv.figure.day=1;//停留一次
		}else if(isFigure==1)//系统人物1
		{
			gv.figure1.isStop=true;//系统人物1停留
			gv.figure1.day=1;//停留一次
		}else if(isFigure==2)//系统人物2
		{
			gv.figure2.isStop=true;//系统人物2停留
			gv.figure2.day=1;//停留一次
		}
		this.gv.setOnTouchListener(this.gv);//返还监听器
		this.gv.setStatus(0);//重新设置GameView为待命状态
		if(gv.figure0.isStop)//是人物本身时，当即停留一次
		{
			gv.showDialog.initMessage(gv.figure0.figureName,gv.figure0.k,true,true,DialogEnum.MESSAGE_ONE,19,-1);//停留对话框信息
			gv.isDialog=true;//绘制对话框
			
			gv.figure0.startToGo(0);//前进0步
			gv.figure0.isStop=false;//不再停留
			gv.figure0.day=0;
		}else if(gv.figure0.k==0)//人物是操作人物时，则要还原
		{
			gv.isDraw=false;//绘制GO图标
			gv.isMenu=true;//绘制游戏界面中的小菜单
		}else 
		{
			gv.gvu.isGoDice(gv.figure0);//系统人物掷骰子
		}
	}
}
