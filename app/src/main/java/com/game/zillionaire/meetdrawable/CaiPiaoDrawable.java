package com.game.zillionaire.meetdrawable;

import static com.game.zillionaire.util.ConstantUtil.*;
import static com.game.zillionaire.util.ConstantUtil.DIALOG_WORD_SIZE;
import java.io.Serializable;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import com.game.zillionaire.activity.ZActivity;
import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.map.MyMeetableDrawable;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.util.ScreenTransUtil;

public class CaiPiaoDrawable  extends MyMeetableDrawable implements Serializable
{
	private static final long serialVersionUID = -1398304314875930683L;
	String dialogMessage[] = 
	{
			"一券在手,希望无穷！",
			"只要一千元，就有获得大奖的机会！",
		    "请圈选您的幸运号码！",
		    "祝您中奖！"
	};
	int result;//彩票获奖金额
	int xTouch;//x触控
	int yTouch;//y触控
	Figure tempFigure;//临时记录人物
    static Bitmap[] bitmap=new Bitmap[4];//存放图片
    boolean isOnTouch=false;//是否选中彩票编号
    int status=0;//控制时间
    int index=0;//索引值
    int frist=0;//0表示系统人物为第一次购买
    
	public CaiPiaoDrawable(){}
	public CaiPiaoDrawable(ZActivity at,String bmpSelf,String dbitmap,String bmpDialogBack,boolean meetable,int width,int height,int col,int row,
			int refCol,int refRow,int [][] noThrough,int [][] meetableMatrix,int da) {
		super(at,bmpSelf,dbitmap, col, row, width, height, refCol, refRow, noThrough, meetable,
				meetableMatrix, da,bmpDialogBack);
	}
	@Override
	public void drawDialog(final Canvas canvas, Figure figure)
	{		
		result=ConstantUtil.CAIPIAO[index];
		tempFigure=figure;//存放临时人物
		bitmap[0]=PicManager.getPic("caipiao2",at.getResources());//加载图片
		bitmap[1]=PicManager.getPic("caipiao1",at.getResources());
		bitmap[2]=PicManager.getPic("cpn",at.getResources());
		bitmap[3]=PicManager.getPic("caipiao3",at.getResources());
		if(tempFigure.figureFlag==MessageEnum.System_Control_1||tempFigure.figureFlag==MessageEnum.System_Control_2)
		{//如果是系统人物,随即产生编号
			int cpbh=(int)(Math.random()*(32-1)+1);
			if(tempFigure.father.mpCP.size()>0)
			{
				for(int num:tempFigure.father.mpCP)
				{
					if(cpbh!=num&&frist==0)
					{//如果该编号的彩票未被买过，则退出循环，将该编号放入mp中
						frist++;
						break;
					}
					else if(cpbh==num)
					{//如果某个编号的彩票被买了，则该编号的彩票不允许再被买
						lowCols=returnIJ(cpbh);//获得该编号的行列值
						if(lowCols[0]!=-1&&lowCols[1]!=-1)
						{
							CAIPIAO_INT[lowCols[0]][lowCols[1]]=1;
							CAIPIAO_BOOL[lowCols[0]][lowCols[1]]=true;
							 cpbh=(int)(Math.random()*(32-1)+1);
							 return;
						}
						else
						{
							continue;
						}
					}
				}
			}
			else if(tempFigure.father.mpCP.size()==0&&frist==0)
			{
				lowCols=returnIJ(cpbh);//获得该编号的行列值
				if(lowCols[0]!=-1&&lowCols[1]!=-1)
				{
					CAIPIAO_INT[lowCols[0]][lowCols[1]]=1;
					CAIPIAO_BOOL[lowCols[0]][lowCols[1]]=true;
					 cpbh=(int)(Math.random()*(32-1)+1);
				}
				else
				{
					cpbh=(int)(Math.random()*(32-1)+1);
				}
			}
			frist++;
			tempFigure.mp.add(cpbh);//放入彩票集合
			if(status>0&&status<3)
			{
				canvas.drawBitmap(bitmap[2], 200, 170, null);
				drawString(canvas,"购买彩票中.......",3);
			}
			else if(status==3)
			{
				recoverGame();
			}
			status++;
		}
		else
		{//如果是b不是系统人物
			canvas.drawBitmap(bitmap[1], 50,30, null);//绘制背景	
			tempFigure.father.mpCP=traverseFigure(tempFigure,tempFigure.father.mpCP);//获得已经被购买的所有的彩票编号
			for(int cpbh:tempFigure.father.mpCP)
			{
				vertex=returnXY(cpbh);//获得该编号左上角的坐标
				lowCol=returnIJ(cpbh);//获得该编号的行列值
				if(lowCol[0]==-1&&lowCol[1]==-1)
				{
					dialogMessage[3]="已被别人买过！";
				}
				else if(lowCol[0]!=-1&&lowCol[1]!=-1)
				{
					if(CAIPIAO_INT[lowCol[0]][lowCol[1]]==1&&CAIPIAO_BOOL[lowCol[0]][lowCol[1]])
					{//已经被买过的彩票
						canvas.drawBitmap(bitmap[3], vertex[0],vertex[1], null);//绘制被选中的彩票编号
					}		
				}		
			}
			
			drawString(canvas,result+"",2);		
			if(status>0&&status<3)
			{
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(status<2)
			{
				drawString(canvas,dialogMessage[status],1);		
				status++;
			}
			else if(status==2)
			{
				drawString(canvas,dialogMessage[status],1);	
			}
			if(isOnTouch&&lowCol[0]!=-1&&lowCol[1]!=-1)
			{
				status=3;		
				drawString(canvas,dialogMessage[status],1);		
				if(CAIPIAO_INT[lowCol[0]][lowCol[1]]==1&&!CAIPIAO_BOOL[lowCol[0]][lowCol[1]])
				{
					canvas.drawBitmap(bitmap[0], xTouch,yTouch, null);	
					CAIPIAO_BOOL[lowCol[0]][lowCol[1]]=true;
				}				
				new Thread()
				{
					public void run()
					{
						try {
							Thread.sleep(700);
							recoverGame();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}.start();					
			}
			else if(isOnTouch&&(lowCol[0]==-1||lowCol[1]==-1))
			{
				new Thread()
				{
					public void run()
					{
						try {
							Thread.sleep(1000);
							recoverGame();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}.start();			
			}
		}
	}
	//绘制给定的字符串到对话框上
	//@Override
	public void drawString(Canvas canvas,String string,int id)
	{
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setTextSize(22);//设置文字大小
		paint.setFakeBoldText(true);//字体加粗
		int lines = string.length()/BANK_DIALOG_WORD_EACH_LINE+(string.length()%BANK_DIALOG_WORD_EACH_LINE==0?0:1);//求出需要画几行文字
		for(int i=0;i<lines;i++)
		{
			String str="";
			if(i == lines-1)//如果是最后一行那个不太整的汉字
			{
				str = string.substring(i*BANK_DIALOG_WORD_EACH_LINE);
			}
			else
			{
				str = string.substring(i*BANK_DIALOG_WORD_EACH_LINE, (i+1)*BANK_DIALOG_WORD_EACH_LINE);
			}
			if(id==1)
			{
				canvas.drawText(str,70,98+DIALOG_WORD_SIZE*i,paint);
			}		
			else if(id==2)
			{//彩票中奖金额
				str=ConstantUtil.translateString(str);
				canvas.drawText(str,420,115+DIALOG_WORD_SIZE*i,paint);
			}else if(id==3)
			{//绘制购买彩票中
				canvas.drawText(str,300,210+DIALOG_WORD_SIZE*i,paint);
			}
		}
	}
		
	
    @Override
	public boolean onTouch(View arg0, MotionEvent arg1)
	{
		int x=ScreenTransUtil.xFromRealToNorm((int)arg1.getX());
		int y=ScreenTransUtil.yFromRealToNorm((int)arg1.getY());
    	if(arg1.getAction()==MotionEvent.ACTION_DOWN)
    	{
    		if(x>=ConstantUtil.CaiPiaoDrawable_RecoverGame_Left_X&&x<=ConstantUtil.CaiPiaoDrawable_RecoverGame_Right_X
    				&&y>=ConstantUtil.CaiPiaoDrawable_RecoverGame_Up_Y&&y<=ConstantUtil.CaiPiaoDrawable_RecoverGame_Down_Y)
    		{//点击X
    			recoverGame();
    		}
    		else if(x>=ConstantUtil.CaiPiaoDrawable_Search_Left_X&&x<=ConstantUtil.CaiPiaoDrawable_Search_Right_X
    				&&y>=ConstantUtil.CaiPiaoDrawable_Search_Up_Y&&y<=ConstantUtil.CaiPiaoDrawable_Search_Down_Y)
    		{
    			lowCol=search(x,y);
    			
    		}
    	}
		return true;
	}
    
    public int[] search(int x,int y)
    {
    	int[] result={-1,-1};
    	outer:for(int i=0;i<5;i++)
		{//4行
			for(int j=0;j<10;j++)
			{//9列
				if(x>=(210+j*57)&&x<=(210+(j+1)*57)&&y>=(165+i*53)&&y<=(165+(i+1)*53)&&!CAIPIAO_BOOL[i][j])
				{
					xTouch=210+j*57;//表示圈中位图的x坐标
					yTouch=165+i*53;//表示圈中位图的y坐标
					if(ConstantUtil.CAIPIAO_INT[i][j]==0)
					{//如果本彩票未被买，则购买该彩票，并return
						int bianhao=ConstantUtil.CAIPIAO_BIANHAO[i][j];
						ConstantUtil.CAIPIAO_INT[i][j]=1;
						tempFigure.mp.add(bianhao);
						isOnTouch=true;		
						result[0]=i;
						result[1]=j;
						break outer;
					}
				}
			}    				
		}    	
    	return result;
    }
    
    //根据彩票编号获得该编号左上角的坐标
    public float[] returnXY(int num)
    {
    	float[] result=new float[2];
    	for(int i=0;i<4;i++)
    	{
    		for(int j=0;j<9;j++)
    		{
    			if(num==ConstantUtil.CAIPIAO_BIANHAO[i][j])
    			{
    				result[0]=210+j*57;
    				result[1]=165+i*53;
    			}
    		}
    	}
    	return result;
    }
    
    //根据彩票编号获得该编号的行列值
    public int[] returnIJ(int num)
    {
    	int[] result=new int[2];
    	for(int i=0;i<4;i++)
    	{
    		for(int j=0;j<9;j++)
    		{
    			if(num==ConstantUtil.CAIPIAO_BIANHAO[i][j])
    			{
    				result[0]=i;
    				result[1]=j;
    			}
    		}
    	}
    	return result;
    }
    
    //遍历所有人物的彩票列表
    public ArrayList<Integer> traverseFigure(Figure tempFigure,ArrayList<Integer> mpCPs)
    {    	
    	mpCPs=new ArrayList<Integer>();
    	for(int num:tempFigure.father.figure.mp)
    	{//遍历玩家的彩票列表
    		mpCPs.add(num);
    	}
    	for(int num:tempFigure.father.figure1.mp)
    	{//遍历系统人物1的彩票列表
    		mpCPs.add(num);
    	}
    	for(int num:tempFigure.father.figure2.mp)
    	{//遍历系统人物2的彩票列表
    		mpCPs.add(num);
    	}
    	return mpCPs;
    }
	//方法：返还监听器，回复游戏状态
	public void recoverGame()
	{
		tempFigure.father.setOnTouchListener(tempFigure.father);//返还监听器
		tempFigure.father.setCurrentDrawable(null);//置空记录引用的变量
		tempFigure.father.setStatus(0);//重新设置GameView为待命状态
		tempFigure.father.gvt.setChanging(true);//启动变换人物的线程
		
		isOnTouch=false;
		status=0;
	    index=0;
	    frist=0;
	}
}