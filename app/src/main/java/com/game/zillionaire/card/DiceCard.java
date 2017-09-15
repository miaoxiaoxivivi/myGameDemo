package com.game.zillionaire.card;

import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.ScreenTransUtil;
import com.game.zillionaire.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class DiceCard extends UsedCard
{
	public DiceCard(GameView gv, Bitmap bitmap) 
	{
		super(gv, bitmap);
	}
	int drawIndex=-1;//需要画选中的骰子图片
	boolean drawSelect=false;//是否画选择骰子的图片
	boolean isTouch=false;
	public void onDraw(Canvas canvas)
	{
		canvas.drawBitmap(UseCardView.diceCard, 140, 120, null);//画选择骰子图片
		if(drawSelect)//如果已经进行了选择
		{
			int x=(drawIndex*100)+140;//变换x坐标
			canvas.drawBitmap(UseCardView.selectDiceCard[drawIndex], x, 120, null);//画选中骰子图片
			recoverGame();//返回游戏
			setDice();
		} 
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int x=ScreenTransUtil.xFromRealToNorm((int)event.getX());//得到点击的X坐标
		int y=ScreenTransUtil.yFromRealToNorm((int)event.getY());//得到点击的Y坐标
		if(x>=ConstantUtil.DiceCard_Step1_Left_X&&x<=ConstantUtil.DiceCard_Step1_Right_X
				&&y>=ConstantUtil.DiceCard_Step_Up_Y&&y<=ConstantUtil.DiceCard_Step_Down_Y)//选择走一步
		{
			drawIndex=0;
			drawSelect=true;
		}else if(x>=ConstantUtil.DiceCard_Step2_Left_X&&x<=ConstantUtil.DiceCard_Step2_Right_X
				&&y>=ConstantUtil.DiceCard_Step_Up_Y&&y<=ConstantUtil.DiceCard_Step_Down_Y)//选择走两步
		{
			drawIndex=1;
			drawSelect=true;
		}else if(x>=ConstantUtil.DiceCard_Step3_Left_X&&x<=ConstantUtil.DiceCard_Step3_Right_X
				&&y>=ConstantUtil.DiceCard_Step_Up_Y&&y<=ConstantUtil.DiceCard_Step_Down_Y)//选择走三步
		{
			drawIndex=2;
			drawSelect=true;
		}else if(x>=ConstantUtil.DiceCard_Step4_Left_X&&x<=ConstantUtil.DiceCard_Step4_Right_X
				&&y>=ConstantUtil.DiceCard_Step_Up_Y&&y<=ConstantUtil.DiceCard_Step_Down_Y)//选择走四步
		{
			drawIndex=3;
			drawSelect=true;
		}else if(x>=ConstantUtil.DiceCard_Step5_Left_X&&x<=ConstantUtil.DiceCard_Step5_Right_X
				&&y>=ConstantUtil.DiceCard_Step_Up_Y&&y<=ConstantUtil.DiceCard_Step_Down_Y)//选择走五步
		{
			drawIndex=4;
			drawSelect=true;
		}else if(x>=ConstantUtil.DiceCard_Step6_Left_X&&x<=ConstantUtil.DiceCard_Step6_Right_X
				&&y>=ConstantUtil.DiceCard_Step_Up_Y&&y<=ConstantUtil.DiceCard_Step_Down_Y)//选择走六步
		{
			drawIndex=5;
			drawSelect=true;
		}
		return true;
	}
	public void setDice()
	{
		gv.indext=drawIndex;//指定步数
		gv.di.setBitmap(gv.figure.topLeftCornerX,gv.figure.topLeftCornerY);//画骰子 并指定坐标
	}
}
