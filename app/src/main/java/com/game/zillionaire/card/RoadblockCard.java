package com.game.zillionaire.card;

import static com.game.zillionaire.util.ConstantUtil.GAME_VIEW_SCREEN_COLS;
import static com.game.zillionaire.util.ConstantUtil.GAME_VIEW_SCREEN_ROWS;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;

import com.game.zillionaire.dialog.DialogEnum;
import com.game.zillionaire.map.Layer;
import com.game.zillionaire.map.MyDrawable;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.util.ScreenTransUtil;
import com.game.zillionaire.view.GameView;

public class RoadblockCard extends UsedCard
{
	public RoadblockCard(GameView gv, Bitmap bitmap) {
		super(gv, bitmap);
	}
	Bitmap bitmap;
	public int indext=0;//编号为0的是路障，编号为1的是炸弹
	int row,col;
	int refCol,refRow;
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
					Layer l = (Layer)gv.layerList.layers.get(0);
					MyDrawable[][] mapMatrix=l.getMapMatrix();
					if(mapMatrix[i+gv.tempStartRow][j+gv.tempStartCol]!= null)
					{
						MyDrawable mmd=mapMatrix[i+gv.tempStartRow][j+gv.tempStartCol];
						if(mmd.indext==1)
						{
							Bitmap bitmap=null;//触控图片
							while(bitmap==null)
							{
								bitmap=PicManager.getPic(mmd.dbpName,gv.activity.getResources());//图片
							}
							if(x>=mmd.bitmapx&&x<=(mmd.bitmapx+64)&&y>=mmd.bitmapy&&y<=(mmd.bitmapy+bitmap.getHeight()))
							{
								setCard(mmd);
								break q;
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
				setWT();
				recoverGame();
			}
		}
		return true;
	}
	public void setBitmap(Bitmap bm,int indext)
	{
		this.bitmap=bm;
		this.indext=indext;
	}
	public void setWT()
	{
		int first=-1;
		if(indext==0)
		{
			first=11;
		}else if(indext==1)
		{
			first=13;
		}
		if(first!=-1)
		{
			gv.showDialog.initMessage(gv.figure0.figureName,gv.figure0.k,true,true,DialogEnum.MESSAGE_ONE,first,-1);
			gv.isDialog=true;//绘制对话框
		}
		this.gv.card.add(new Card(gv,bitmap,row,col,refCol,refRow,indext));
	}
	public void setCard(MyDrawable md)//点击屏幕并绘制选中框
	{
		Bitmap bitmap=null;//触控图片
		while(bitmap==null)
		{
			bitmap=PicManager.getPic(md.dbpName,gv.activity.getResources());//图片
		}
		//黑框的绘制位置
		this.x=md.bitmapx+bitmap.getWidth()-64;
		this.y=md.bitmapy+bitmap.getHeight()-48;
		this.refRow=md.refRow;
		this.refCol=md.refCol;
		this.row=md.row;
		this.col=md.col;
	}
}
