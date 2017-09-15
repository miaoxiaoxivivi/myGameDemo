package com.game.zillionaire.card;

import android.graphics.Bitmap;

import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.meetdrawable.MessageEnum;
import com.game.zillionaire.view.GameView;

public class JQWWCard extends UsedCard
{
	public JQWWCard(GameView gv, Bitmap bitmap) {
		super(gv, bitmap);
		this.isChange=false;
		this.gv=gv;
		this.isDrawCard=true;
		this.bitmap0=bitmap;
	}
	public void setUsed()
	{
		setWW();
	}
	public void setWW()
	{
		this.isDrawCard=false;
		this.isChange=true;
		this.count=0;
		this.pp=0;
		this.iscount=0;
		md=null;
		
		int direction=gv.figure0.direction;//获得方向
		gv.figure0=new Figure(
				gv,
				gv.figure0.col,
				gv.figure0.row,
				gv.figure0.startRow,
				gv.figure0.startCol,
				gv.figure0.offsetX,
				gv.figure0.offsetY,
				3,
				MessageEnum.Artificial_Control,
				direction,
				3
				);//创建机器娃娃
		gv.figure0.direction=direction;
		gv.isWW=true;
		gv.figure0.startToGo(10);
	}
}
