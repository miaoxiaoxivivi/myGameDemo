package com.game.zillionaire.card;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;

import com.game.zillionaire.dialog.DialogEnum;
import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.ScreenTransUtil;
import com.game.zillionaire.view.GameView;

public class GradCard extends UsedCard 
{
	int cardIndext=0;//记录卡片编号
	public GradCard(GameView gv, Bitmap bitmap) {
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
				{
					if(x>=ConstantUtil.AllianceCard_Count1_Left_X&&x<=ConstantUtil.AllianceCard_Count1_Right_X
							&&y>=ConstantUtil.AllianceCard_Count1_Up_Y&&y<=ConstantUtil.AllianceCard_Count1_Down_Y)
					{
						setQiangDe(xx[0][1]);//抢得卡片
					}
				}else if(count==2)
				{
					if(x>=ConstantUtil.AllianceCard_Count2_Figure1_Left_X&&x<=ConstantUtil.AllianceCard_Count2_Figure1_Right_X
							&&y>=ConstantUtil.AllianceCard_Count2_Figure1_Up_Y&&y<=ConstantUtil.AllianceCard_Count2_Figure1_Down_Y)
					{
						setQiangDe(xx[0][1]);//抢得卡片
					}else if(x>=ConstantUtil.AllianceCard_Count2_Figure2_Left_X&&x<=ConstantUtil.AllianceCard_Count2_Figure2_Right_X
							&&y>=ConstantUtil.AllianceCard_Count2_Figure2_Up_Y&&y<=ConstantUtil.AllianceCard_Count2_Figure2_Down_Y)
					{
						setQiangDe(xx[1][1]);//抢得卡片
					}
				}else if(count==3)
				{
					if(x>=ConstantUtil.AllianceCard_Count3_Figure1_Left_X&&x<=ConstantUtil.AllianceCard_Count3_Figure1_Right_X
							&&y>=ConstantUtil.AllianceCard_Count3_Figure1_Up_Y&&y<=ConstantUtil.AllianceCard_Count3_Figure1_Down_Y)
					{
						setQiangDe(xx[0][1]);//抢得卡片	
					}else if(x>=ConstantUtil.AllianceCard_Count3_Figure2_Left_X&&x<=ConstantUtil.AllianceCard_Count3_Figure2_Right_X
							&&y>=ConstantUtil.AllianceCard_Count3_Figure2_Up_Y&&y<=ConstantUtil.AllianceCard_Count3_Figure2_Down_Y)
					{
						setQiangDe(xx[1][1]);//抢得卡片
					}else if(x>=ConstantUtil.AllianceCard_Count3_Figure3_Left_X&&x<=ConstantUtil.AllianceCard_Count3_Figure3_Right_X
							&&y>=ConstantUtil.AllianceCard_Count3_Figure3_Up_Y&&y<=ConstantUtil.AllianceCard_Count3_Figure3_Down_Y)
					{
						setQiangDe(xx[2][1]);//抢得卡片
					}
				}
			}
		}
		return true;
	}
	public void setQiangDe(int isFigure)//抢得卡片
	{
		if(isFigure==0)
		{
			if(!cardCount(gv.figure))//获取已购买的卡片
			{
				
			}
		}else if(isFigure==1)
		{
			if(!cardCount(gv.figure1))//获取已购买的卡片数
			{
				
			}
		}else if(isFigure==2)
		{
			if(!cardCount(gv.figure2))//获取已购买的卡片数
			{
				
			}
		}
		recoverGame();
	}
	public boolean cardCount(Figure figure)//获取已购买的卡片数
	{
		int cardCount=-1;
		for(int i=0;i<figure.CardNum.length;i++)
		{
			if(figure.CardNum[i]==-1)
			{
				cardCount=i;
				break;
			}
		}
		if(cardCount!=-1)
		{
			for(int i=0;i<gv.figure0.CardNum.length;i++)
			{
				if(gv.figure0.CardNum[i]==-1)
				{
					gv.figure0.CardNum[i]=figure.CardNum[cardCount-1];
					cardIndext=gv.figure0.CardNum[i];
					break;
				}
			}
			//绘制对话框
			gv.showDialog.initMessage(gv.figure0.figureName,gv.figure0.k,true,true,DialogEnum.MESSAGE_ONE,17,-1);
			gv.showDialog.cardBitmap=UseCardView.useCard[cardIndext];
			gv.isDialog=true;//绘制对话框
			return true;
		}
		return false;
	}
}
