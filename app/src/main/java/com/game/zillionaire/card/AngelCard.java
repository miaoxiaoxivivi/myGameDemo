package com.game.zillionaire.card;

import static com.game.zillionaire.util.ConstantUtil.GAME_VIEW_SCREEN_COLS;
import static com.game.zillionaire.util.ConstantUtil.GAME_VIEW_SCREEN_ROWS;
import static com.game.zillionaire.util.ConstantUtil.MAP_COLS;
import static com.game.zillionaire.util.ConstantUtil.MAP_ROWS;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;

import com.game.zillionaire.map.Layer;
import com.game.zillionaire.map.MyDrawable;
import com.game.zillionaire.meetdrawable.GroundDrawable;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.util.ScreenTransUtil;
import com.game.zillionaire.view.GameView;

public class AngelCard extends UsedCard
{
	public AngelCard(GameView gv, Bitmap bitmap) {
		super(gv, bitmap);
	}
	int count=0;//计算可加盖的房屋栋数
	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		int x=ScreenTransUtil.xFromRealToNorm((int)event.getX());//得到点击的X坐标
		int y=ScreenTransUtil.yFromRealToNorm((int)event.getY());//得到点击的Y坐标
		if(drawdialog)//绘制选择是否确定使用该卡片
		{
			q:for(int i=0; i<=GAME_VIEW_SCREEN_ROWS; i++)
			{   
				for(int j=0; j<=GAME_VIEW_SCREEN_COLS; j++)
				{
					Layer l = (Layer)gv.layerList.layers.get(1);//获取上层地图
					MyDrawable[][] mapMatrix=l.getMapMatrix(); 
					if(mapMatrix[i+gv.tempStartRow][j+gv.tempStartCol]!= null)
					{
						MyDrawable mmd=mapMatrix[i+gv.tempStartRow][j+gv.tempStartCol];
						Bitmap selfBmp=PicManager.getPic(mmd.bpName, gv.activity.getResources());
						if((mmd.da==1||mmd.da==2)&&mmd.ss!=-1)
						{
							if(x>=mmd.bitmapx&&x<=(mmd.bitmapx+64)&&y>=mmd.bitmapy&&y<=(mmd.bitmapy+selfBmp.getHeight()))
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
			if(x>=ConstantUtil.AngelCard_NoUseCard_RecoverGame_Left_X&&x<=ConstantUtil.AngelCard_NoUseCard_RecoverGame_Right_X
					&&y>=ConstantUtil.AngelCard_RecoverGame_Up_Y&&y<=ConstantUtil.AngelCard_RecoverGame_Down_Y)//不使用卡片
			{
				recoverGame();
			}else if(x>=ConstantUtil.AngelCard_UseCard_RecoverGame_Left_X&&x<=ConstantUtil.AngelCard_UseCard_RecoverGame_Right_X
					&&y>=ConstantUtil.AngelCard_RecoverGame_Up_Y&&y<=ConstantUtil.AngelCard_RecoverGame_Down_Y)//使用卡片
			{
				setTS();
				recoverGame();
			}
		}
		return true;
	}
	public void setTS()//天使卡
	{
		for(int i=md.col; i>=0; i--)//向左搜索
		{ 
			Layer l = (Layer)gv.layerList.layers.get(1);
			MyDrawable[][] mapMatrix=l.getMapMatrix();
			if((i-gv.tempStartCol)>=0&&(i-gv.tempStartCol)<=GAME_VIEW_SCREEN_ROWS)
			{
				MyDrawable mm=mapMatrix[md.row][i];
				if(!(setRoom(mm)))//判断是否加盖房屋
				{
					break;
				}
			}
		}
		for(int i=md.col+1; i<=MAP_COLS; i++)//向右搜索
		{ 
			Layer l = (Layer)gv.layerList.layers.get(1);
			MyDrawable[][] mapMatrix=l.getMapMatrix();
			if((i-gv.tempStartCol)>=0&&(i-gv.tempStartCol)<=GAME_VIEW_SCREEN_COLS)
			{
				MyDrawable mm=mapMatrix[md.row][i];
				if(!(setRoom(mm)))//判断是否加盖房屋
				{
					break;
				}
			}
		}
		if(count>1)
		{
			return;
		}
		for(int i=md.row+1; i<=MAP_ROWS; i++)//向下搜索
		{  
			Layer l = (Layer)gv.layerList.layers.get(1);
			MyDrawable[][] mapMatrix=l.getMapMatrix();
			if((i-gv.tempStartRow)>=0&&(i-gv.tempStartRow)<=GAME_VIEW_SCREEN_ROWS)
			{
				MyDrawable mm=mapMatrix[i][md.col];
				if(!(setRoom(mm)))//判断是否加盖房屋
				{
					break;
				}
			}
		}
		for(int i=md.row-1; i>=0; i--)//向上搜索
		{  
			Layer l = (Layer)gv.layerList.layers.get(1);
			MyDrawable[][] mapMatrix=l.getMapMatrix();
			if((i-gv.tempStartRow)>=0&&(i-gv.tempStartRow)<=GAME_VIEW_SCREEN_ROWS)
			{
				MyDrawable mm=mapMatrix[i][md.col];
				if(!(setRoom(mm)))//判断是否加盖房屋
				{
					break;
				}
			}
		}
	}
	public boolean setRoom(MyDrawable mm)//是否加盖房屋
	{
		if(mm!= null&&mm.ss!=-1)
		{
			if(mm.da==2)//小土地
			{
				if(mm.k<5)
				{
					mm.k++;
					mm.bpName=GroundDrawable.bitmaps[mm.kk][mm.k];
					count++;
					return true;
				}
			}else if(mm.da==1)//大土地
			{
				if(mm.k>=0)
				{
					if(mm.zb!=0&&mm.zb!=1&&mm.k<4)
					{
						mm.k++;
						mm.dbpName=GroundDrawable.bitmap2[mm.zb][mm.k];
						count++;
						return true;
					}
				}
			}
		}
		return false;
	}
}
