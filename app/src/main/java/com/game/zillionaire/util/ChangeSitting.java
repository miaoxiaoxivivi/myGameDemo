package com.game.zillionaire.util;
import com.game.zillionaire.view.GameView;

public class ChangeSitting {
	public int changeNum=0;//用于显示交换的位置    --0不交换   --1两个人交换   --2三个人交换
	public int cx,cy,direction;//临时存放交换坐标
	public GameView gv;//获得GameView的引用

	public ChangeSitting(GameView gv)
	{
		this.gv=gv;
	}
	public void isCSitting()//交换人物的位置
	{
		if(changeNum==1)//两个人交换
		{
			cx=gv.figure.x;
			cy=gv.figure.y;
			gv.figure.x=gv.figure1.x;
			gv.figure.y=gv.figure1.y;
			gv.figure1.x=cx;
			gv.figure1.y=cy;
			
			cx=-1;
			cy=-1;
			
			cy=gv.figure.row;
			cx=gv.figure.col;
			gv.figure.row=gv.figure1.row;
			gv.figure.col=gv.figure1.col;
			gv.figure1.col=cx;
			gv.figure1.row=cy;
			cy=-1;
			cx=-1;
			
			cx=gv.figure.offsetX;
			cy=gv.figure.offsetY;
			gv.figure.offsetX=gv.figure1.offsetX;
			gv.figure.offsetY=gv.figure1.offsetY;
			gv.figure1.offsetX=cx;
			gv.figure1.offsetY=cy;
			cx=-1;
			cy=-1;
			
			cx=gv.figure.startCol;
			cy=gv.figure.startRow;
			gv.figure.startCol=gv.figure1.startCol;
			gv.figure.startRow=gv.figure1.startRow;
			gv.figure1.startCol=cx;
			gv.figure1.startRow=cy;
			cx=-1;
			cy=-1;
			
			//改变方向
			direction=gv.figure.direction;
			gv.figure.direction=gv.figure1.direction;
			gv.figure1.direction=direction;
			direction=-1;

		}
		else if(changeNum==2)//三个人交换
		{
			cx=gv.figure.x;
			cy=gv.figure.y;
			
			gv.figure.x=gv.figure1.x;
			gv.figure.y=gv.figure1.y;
			
			gv.figure1.x=gv.figure2.x;
			gv.figure1.y=gv.figure2.y;
			
			gv.figure2.x=cx;
			gv.figure2.y=cy;
		
			cx=-1;
			cy=-1;
			
			cy=gv.figure.row;
			cx=gv.figure.col;
			gv.figure.row=gv.figure1.row;
			gv.figure.col=gv.figure1.col;
			gv.figure1.col=gv.figure2.col;
			gv.figure1.row=gv.figure2.row;
			gv.figure2.col=cx;
			gv.figure2.row=cy;
			cy=-1;
			cx=-1;
			
			cx=gv.figure.offsetX;
			cy=gv.figure.offsetY;
			gv.figure.offsetX=gv.figure1.offsetX;
			gv.figure.offsetY=gv.figure1.offsetY;
			gv.figure1.offsetX=gv.figure2.offsetX;
			gv.figure1.offsetY=gv.figure2.offsetY;
			gv.figure2.offsetX=cx;
			gv.figure2.offsetY=cy;
			cy=-1;
			cx=-1;
			
			cx=gv.figure.startCol;
			cy=gv.figure.startRow;
			gv.figure.startCol=gv.figure1.startCol;
			gv.figure.startRow=gv.figure1.startRow;
			gv.figure1.startCol=gv.figure2.startCol;
			gv.figure1.startRow=gv.figure2.startRow;
			gv.figure2.startCol=cx;
			gv.figure2.startRow=cy;
			cy=-1;
			cx=-1;
			
			//改变方向
			direction=gv.figure.direction;
			gv.figure.direction=gv.figure1.direction;
			gv.figure1.direction=gv.figure2.direction;
			gv.figure2.direction=direction;
			direction=-1;	
		}
		changeNum=0;
	}
}
