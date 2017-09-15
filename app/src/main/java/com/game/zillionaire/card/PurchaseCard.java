package com.game.zillionaire.card;

import android.graphics.Bitmap;

import com.game.zillionaire.meetdrawable.GroundDrawable;
import com.game.zillionaire.view.GameView;

public class PurchaseCard extends UsedCard
{
	public PurchaseCard(GameView gv, Bitmap bitmap) {
		super(gv, bitmap);
		this.isChange=false;
		this.gv=gv;
		this.isDrawCard=true;
		this.bitmap0=bitmap;
	}
	public void setUsed()
	{
		setGD();
		recoverGame();
	}
	public void setGD()//购地卡
	{
		if(gv.figure0.previousDrawable!=null&&gv.figure0.previousDrawable.ss!=-1)//土地时
		{
			md=gv.figure0.previousDrawable;
			//原来拥有该土地人土地减一
			if(md.ss==0)
			{
				gv.figure.count--;
			}else if(md.ss==1)
			{
				gv.figure1.count--;
			}else if(md.ss==2)
			{
				gv.figure2.count--;
			}
			//该土地变为实际人物所有
			md.kk=gv.figure0.k;
			md.ss=gv.figure0.ss;
			if(md.da==1)//大土地
			{
				if(md.k>=0)
				{
					md.bpName=GroundDrawable.bitmap2[md.zb][md.k];
					md.dbpName=GroundDrawable.bitmap[md.kk];
				}else if(md.k==-1)
				{
					md.bpName=GroundDrawable.bitmap[md.kk];
					md.dbpName=md.bpName;
				}
			}else if(md.da==2)//小土地
			{
				md.bpName=GroundDrawable.bitmaps[md.kk][md.k];
			}
			gv.figure0.count++;
		}
	}
}
