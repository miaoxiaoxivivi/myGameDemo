package com.game.zillionaire.card;

import static com.game.zillionaire.util.ConstantUtil.GAME_VIEW_SCREEN_COLS;
import static com.game.zillionaire.util.ConstantUtil.GAME_VIEW_SCREEN_ROWS;
import static com.game.zillionaire.util.ConstantUtil.TILE_SIZE_X;
import static com.game.zillionaire.util.ConstantUtil.TILE_SIZE_Y;

import com.game.zillionaire.map.Layer;
import com.game.zillionaire.map.MyDrawable;
import com.game.zillionaire.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Card 
{
	public int refCol;
	public int refRow;
	public int row,col,indext;
	private Bitmap bitmap;
	GameView gv;
	public Card(GameView gv,Bitmap bitmap,int row,int col,int refCol,int refRow,int indext)
	{
		this.gv=gv;
		this.bitmap=bitmap;
		this.row=row;
		this.col=col;
		this.indext=indext;
		this.refCol=refCol;
		this.refRow=refRow;
		
	}
	public void onDraw(Canvas canvas)//绘制方法
	{
		q:for(int i=0; i<=GAME_VIEW_SCREEN_ROWS; i++)
		{  
			for(int j=0; j<=GAME_VIEW_SCREEN_COLS; j++)
			{
				Layer l = (Layer)gv.layerList.layers.get(0);//获得底层的图层
				MyDrawable[][] mapMatrix=l.getMapMatrix();
				if(mapMatrix[i+gv.tempStartRow][j+gv.tempStartCol] != null)
				{
					if((i+gv.tempStartRow)==row&&(j+gv.tempStartCol==col))
					{
						int x= (j-refCol)*TILE_SIZE_X;//求出自己所拥有的块数中左上角块的x坐标
						int y =i*TILE_SIZE_Y+(refRow+1)*TILE_SIZE_Y-bitmap.getHeight();//求出自己所拥有的块数中左上角块的y坐标
						canvas.drawBitmap(bitmap, x-gv.tempOffsetX, y-gv.tempOffsetY, null);//根据自己的左上角的xy坐标画出自己
						break q;
					}
				}
			}
		}
	}
}
