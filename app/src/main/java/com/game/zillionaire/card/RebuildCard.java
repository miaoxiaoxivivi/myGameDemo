package com.game.zillionaire.card;

import android.graphics.Bitmap;

import com.game.zillionaire.meetdrawable.GroundDrawable;
import com.game.zillionaire.view.GameView;

public class RebuildCard extends UsedCard
{
	public RebuildCard(GameView gv, Bitmap bitmap) {
		super(gv, bitmap);
		this.isChange=false;
		this.gv=gv;
		this.isDrawCard=true;
		this.bitmap0=bitmap;
	}
	public void setUsed()
	{
		setGJ();//改建卡
		recoverGame();
	}
	public void setGJ()//改建卡
	{
		if(gv.figure0.previousDrawable!=null&&gv.figure0.previousDrawable.da==1)//大土地时
		{
			//该为公园
			gv.figure0.previousDrawable.zb=0;
			gv.figure0.previousDrawable.k=0;
			gv.figure0.previousDrawable.dbpName=GroundDrawable.bitmap2[0][0];
			gv.figure0.previousDrawable.flagg=true;
			gv.figure0.previousDrawable.first=false;
			gv.figure0.previousDrawable.value=0;//价值为0
		}
	}
}
