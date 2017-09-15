package com.game.zillionaire.card;

import static com.game.zillionaire.util.ConstantUtil.GAME_VIEW_SCREEN_COLS;
import static com.game.zillionaire.util.ConstantUtil.GAME_VIEW_SCREEN_ROWS;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;

import com.game.zillionaire.map.Layer;
import com.game.zillionaire.map.MyDrawable;
import com.game.zillionaire.meetdrawable.ShipAnimation;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.util.ScreenTransUtil;
import com.game.zillionaire.view.GameView;

public class MonsterCard extends UsedCard
{
	public MonsterCard(GameView gv, Bitmap bitmap) {
		super(gv, bitmap);
	}
	public static ShipAnimation ship=null;
	public boolean onTouch(View view, MotionEvent event)
	{
		int x=ScreenTransUtil.xFromRealToNorm((int)event.getX());//得到点击的X坐标
		int y=ScreenTransUtil.yFromRealToNorm((int)event.getY());//得到点击的Y坐标
		if(drawdialog)
		{
			q:for(int i=0; i<=GAME_VIEW_SCREEN_ROWS; i++)
			{   
				for(int j=0; j<=GAME_VIEW_SCREEN_COLS; j++)
				{
					Layer l = (Layer)gv.layerList.layers.get(1);
					MyDrawable[][] mapMatrix=l.getMapMatrix();
					if(mapMatrix[i+gv.tempStartRow][j+gv.tempStartCol]!= null)
					{
						MyDrawable mmd=mapMatrix[i+gv.tempStartRow][j+gv.tempStartCol];
						Bitmap selfBmp=PicManager.getPic(mmd.bpName, gv.activity.getResources());
						if((mmd.da==1||mmd.da==2)&&mmd.ss!=-1)
						{
							if(x>=mmd.bitmapx&&x<=(mmd.bitmapx+64)&&y>=mmd.bitmapy&&y<=(mmd.bitmapy+selfBmp.getHeight()))
							{
								if(mmd.k>0)
								{
									if(mmd.da==1)//大土地
									{
										pp=1;
										this.x=mmd.bitmapx+selfBmp.getWidth()-128;
										this.y=mmd.bitmapy+selfBmp.getHeight()-96;
									}else if(mmd.da==2)//小土地
									{
										pp=0;
										this.x=mmd.bitmapx+selfBmp.getWidth()-64;
										this.y=mmd.bitmapy+selfBmp.getHeight()-48;
									}
									md=mmd;
									break q;
								}
							}
						}
					}
				}
			}
			if(x>=ConstantUtil.AngelCard_NoUseCard_RecoverGame_Left_X&&x<=ConstantUtil.AngelCard_NoUseCard_RecoverGame_Right_X
					&&y>=ConstantUtil.AngelCard_RecoverGame_Up_Y&&y<=ConstantUtil.AngelCard_RecoverGame_Down_Y)//不使用卡片
			{
				recoverGame();
			}else if(x>=ConstantUtil.AngelCard_UseCard_RecoverGame_Left_X&&x<=ConstantUtil.AngelCard_UseCard_RecoverGame_Right_X
					&&y>=ConstantUtil.AngelCard_RecoverGame_Up_Y&&y<=ConstantUtil.AngelCard_RecoverGame_Down_Y)//使用卡片
			{
				setDemon();
				recoverGame();
			}
		}
		return true;
	}
	@SuppressWarnings("static-access")
	public void setDemon()//怪兽卡
	{
		ship=new ShipAnimation(gv.figure0);
		ship.startAnimation();
		ship.isDemon=true;
		ship.drawable=md;
	}
}
