package com.game.zillionaire.card;

import android.graphics.Bitmap;

import com.game.zillionaire.figure.CrashFigure;
import com.game.zillionaire.view.GameView;

public class ToTheGodCard extends UsedCard
{
	public ToTheGodCard(GameView gv, Bitmap bitmap) {
		super(gv, bitmap);
	}
	public void setUsed()
	{
		if(gv.figure0.k==0)
		{
			CrashFigure.isMeet1=false;
		}else if(gv.figure0.k==1)
		{
			CrashFigure.isMeet0=false;
		}else if(gv.figure0.k==2)
		{
			CrashFigure.isMeet=false;
		}
		recoverGame();
	}
}
