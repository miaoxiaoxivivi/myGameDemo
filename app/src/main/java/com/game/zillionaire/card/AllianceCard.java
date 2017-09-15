package com.game.zillionaire.card;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;

import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.ScreenTransUtil;
import com.game.zillionaire.view.GameView;

public class AllianceCard extends UsedCard{//同盟卡
	public AllianceCard(GameView gv, Bitmap bitmap) {
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
		if(drawdialog)
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
						setAlliance(xx[0][1]);
						gv.isDrawAlliance=true;
					}
				}else if(count==2)//符合条件的英雄数为2
				{
					if(x>=ConstantUtil.AllianceCard_Count2_Figure1_Left_X&&x<=ConstantUtil.AllianceCard_Count2_Figure1_Right_X
							&&y>=ConstantUtil.AllianceCard_Count2_Figure1_Up_Y&&y<=ConstantUtil.AllianceCard_Count2_Figure1_Down_Y)
					{
						setAlliance(xx[0][1]);
						gv.isDrawAlliance=true;
					}else if(x>=ConstantUtil.AllianceCard_Count2_Figure2_Left_X&&x<=ConstantUtil.AllianceCard_Count2_Figure2_Right_X
							&&y>=ConstantUtil.AllianceCard_Count2_Figure2_Up_Y&&y<=ConstantUtil.AllianceCard_Count2_Figure2_Down_Y)
					{
						setAlliance(xx[1][1]);
						gv.isDrawAlliance=true;
					}
				}else if(count==3)//符合条件的英雄数为3
				{
					if(x>=ConstantUtil.AllianceCard_Count3_Figure1_Left_X&&x<=ConstantUtil.AllianceCard_Count3_Figure1_Right_X
							&&y>=ConstantUtil.AllianceCard_Count3_Figure1_Up_Y&&y<=ConstantUtil.AllianceCard_Count3_Figure1_Down_Y)
					{
						setAlliance(xx[0][1]);	
						gv.isDrawAlliance=true;
					}else if(x>=ConstantUtil.AllianceCard_Count3_Figure2_Left_X&&x<=ConstantUtil.AllianceCard_Count3_Figure2_Right_X
							&&y>=ConstantUtil.AllianceCard_Count3_Figure2_Up_Y&&y<=ConstantUtil.AllianceCard_Count3_Figure2_Down_Y)
					{
						setAlliance(xx[1][1]);
						gv.isDrawAlliance=true;
					}else if(x>=ConstantUtil.AllianceCard_Count3_Figure3_Left_X&&x<=ConstantUtil.AllianceCard_Count3_Figure3_Right_X
							&&y>=ConstantUtil.AllianceCard_Count3_Figure3_Up_Y&&y<=ConstantUtil.AllianceCard_Count3_Figure3_Down_Y)
					{
						setAlliance(xx[2][1]);
						gv.isDrawAlliance=true;
					}
				}
			}
		}
		return true;
	}
	public void setAlliance(int isFigure)
	{
		currentFigure=isFigure;//将当前的人物赋值
		recoverGame();//返回游戏
	}
}
