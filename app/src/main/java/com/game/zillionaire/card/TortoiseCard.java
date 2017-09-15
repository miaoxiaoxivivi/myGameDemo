package com.game.zillionaire.card;

import android.graphics.Bitmap;

import com.game.zillionaire.dialog.DialogEnum;
import com.game.zillionaire.view.GameView;

public class TortoiseCard extends UsedCard
{
	public TortoiseCard(GameView gv, Bitmap bitmap) {
		super(gv, bitmap);
		this.isChange=false;
		this.gv=gv;
		this.isDrawCard=true;
		this.bitmap0=bitmap;
	}
	public void setUsed()
	{
		setWG();
		recoverGame();
	}
	public void setWG()//乌龟卡
	{
		if(gv.isFigure==0)
		{
			gv.gvu.go=gv.gvu.gos[1];
		}
		gv.figure0.isWG=true;
		
		gv.showDialog.initMessage(gv.figure0.figureName,gv.figure0.k,true,true,DialogEnum.MESSAGE_ONE,18,-1);
		gv.isDialog=true;//绘制对话框
		gv.figure0.day=3;
	}
}
