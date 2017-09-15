package com.game.zillionaire.card;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;

import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.meetdrawable.PoliceCarAnimation;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.ScreenTransUtil;
import com.game.zillionaire.view.GameView;

public class TrapCard extends UsedCard
{
	public static PoliceCarAnimation police=null;
	public TrapCard(GameView gv, Bitmap bitmap) {
		super(gv, bitmap);
		this.isChange=false;
		this.gv=gv;
		this.isDrawCard=true;
		this.bitmap0=bitmap;
	}
	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		int x=ScreenTransUtil.xFromRealToNorm((int)event.getX());//得到点击的X坐标
		int y=ScreenTransUtil.yFromRealToNorm((int)event.getY());//得到点击的Y坐标
		if(drawdialog)
		{
			if(x>=ConstantUtil.AllianceCard_RecoverGame_Left_X&&x<=ConstantUtil.AllianceCard_RecoverGame_Right_X
					&&y>=ConstantUtil.AllianceCard_RecoverGame_Up_Y&&y<=ConstantUtil.AllianceCard_RecoverGame_Down_Y)
			{
				recoverGame();//返回游戏
			}else
			{
				if(count==1)
				{//只有一个人物
					if(x>=ConstantUtil.AllianceCard_Count1_Left_X&&x<=ConstantUtil.AllianceCard_Count1_Right_X
							&&y>=ConstantUtil.AllianceCard_Count1_Up_Y&&y<=ConstantUtil.AllianceCard_Count1_Down_Y)
					{
						setTrap(xx[0][1]);
					}
				}else if(count==2)
				{//只有两个人物
					if(x>=ConstantUtil.AllianceCard_Count2_Figure1_Left_X&&x<=ConstantUtil.AllianceCard_Count2_Figure1_Right_X
							&&y>=ConstantUtil.AllianceCard_Count2_Figure1_Up_Y&&y<=ConstantUtil.AllianceCard_Count2_Figure1_Down_Y)
					{
						setTrap(xx[0][1]);
					}else if(x>=ConstantUtil.AllianceCard_Count2_Figure2_Left_X&&x<=ConstantUtil.AllianceCard_Count2_Figure2_Right_X
							&&y>=ConstantUtil.AllianceCard_Count2_Figure2_Up_Y&&y<=ConstantUtil.AllianceCard_Count2_Figure2_Down_Y)
					{
						setTrap(xx[1][1]);
					}
				}else if(count==3)
				{//只有三个人物
					if(x>=ConstantUtil.AllianceCard_Count3_Figure1_Left_X&&x<=ConstantUtil.AllianceCard_Count3_Figure1_Right_X
							&&y>=ConstantUtil.AllianceCard_Count3_Figure1_Up_Y&&y<=ConstantUtil.AllianceCard_Count3_Figure1_Down_Y)
					{
						setTrap(xx[0][1]);	
					}else if(x>=ConstantUtil.AllianceCard_Count3_Figure2_Left_X&&x<=ConstantUtil.AllianceCard_Count3_Figure2_Right_X
							&&y>=ConstantUtil.AllianceCard_Count3_Figure2_Up_Y&&y<=ConstantUtil.AllianceCard_Count3_Figure2_Down_Y)
					{
						setTrap(xx[1][1]);
					}else if(x>=ConstantUtil.AllianceCard_Count3_Figure3_Left_X&&x<=ConstantUtil.AllianceCard_Count3_Figure3_Right_X
							&&y>=ConstantUtil.AllianceCard_Count3_Figure3_Up_Y&&y<=ConstantUtil.AllianceCard_Count3_Figure3_Down_Y)
					{
						setTrap(xx[2][1]);
					}
				}
			}
		}
		return true;
	}
	@SuppressWarnings("static-access")
	public void setTrap(int isFigure)//陷害
	{
		Figure figure=null;
		if(isFigure==0)
		{
			figure=gv.figure;
		}else if(isFigure==1)
		{
			figure=gv.figure1;
		}else if(isFigure==2)
		{
			figure=gv.figure2;
		}
		police = new PoliceCarAnimation(figure);//将指定英雄送进监狱
		police.startAnimation();
		police.isPolice=true;
		gv.showDialog.day=5;
		figure.day=5;
		recoverGame();
	}
}
