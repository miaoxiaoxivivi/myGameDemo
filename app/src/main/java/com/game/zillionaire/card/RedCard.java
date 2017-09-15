package com.game.zillionaire.card;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.ScreenTransUtil;
import com.game.zillionaire.util.SharesDetails;
import com.game.zillionaire.view.GameView;

public class RedCard extends UsedCard{
	public int sharesnum=7;//定义有几支股票
	float eventY=0;//按下时Y值
	float eventX=0;//按下时X值
	public int lineNumber=0;//首行行号
	int lineHeight;//每行高度
	public int offset=0;//偏移量
	int height=280;
	int offTop;//显示区域距屏幕上边距离
	int screenHeight;//显示区域高度
	int offLeft=80;
	SharesDetails sd;//获得股票基本信息的引用
	int startY=50;
	int leftTemp=80;//距左边的距离
	int isshares=0;//判断点击的是那个股票
	int lineTemp;//当前行的行号
	public static boolean isCardRed=false;//使用红卡
	public static boolean isCardBlack=false;//使用黑卡
	public static int days;//起效的天数
	public static boolean isRed=false,isBlack=false;//使用红黑卡

	public RedCard(GameView gv, Bitmap bitmap){
		super(gv, bitmap);
		this.isChange=false;
		this.gv=gv;
		this.isDrawCard=true;
		this.bitmap0=bitmap;
		sd=new SharesDetails(gv);
		screenHeight=height;
		offTop=ScreenTransUtil.yFromRealToNorm(300);;
		lineHeight=screenHeight/4;
	}
	public void onDraw(Canvas canvas)
	{
		canvas.drawBitmap(UseCardView.redCardBack, 0, 0, null);
		canvas.save();
		canvas.clipRect(leftTemp, offTop, 900, 720);
		
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setTextSize(19);//设置文字大小
		paint.setFakeBoldText(true);//字体加粗
		
		int topBase=(offset>=0)?offset%lineHeight:(offset%lineHeight+lineHeight);//距离上面的距离
		int lineTemp=lineNumber-((offset>=0)?offset/lineHeight:(offset/lineHeight-1));//滑动的行数
		
		if(lineTemp-1>=0&&lineTemp-1<sharesnum)
		{
			int topTemp1=offTop+topBase-lineHeight;
			canvas.drawBitmap(UseCardView.sharescontent, leftTemp, topTemp1, null);
			for(int m=0;m<sd.datails.get(lineTemp-1).length;m++)
			{
				String str=sd.datails.get(lineTemp-1)[m];
				canvas.drawText(str,130+110*m,topTemp1+startY, paint);
			}
		}
		if(lineTemp>=0&&lineTemp<sharesnum)
		{
			int topTemp2=offTop+topBase;
			canvas.drawBitmap(UseCardView.sharescontent, leftTemp, topTemp2, null);
			for(int m=0;m<sd.datails.get(lineTemp).length;m++)
			{
				String str=sd.datails.get(lineTemp)[m];
				canvas.drawText(str,130+110*m,topTemp2+startY, paint);
			}
		}
		if(lineTemp+1>=0&&lineTemp+1<sharesnum)
		{
			int topTemp3=offTop+topBase+lineHeight;
			canvas.drawBitmap(UseCardView.sharescontent, leftTemp, topTemp3, null);
			for(int m=0;m<sd.datails.get(lineTemp+1).length;m++)
			{
				String str=sd.datails.get(lineTemp+1)[m];
				canvas.drawText(str,130+110*m,topTemp3+startY, paint);
			}
		}
		if(lineTemp+2>=0&&lineTemp+2<sharesnum)
		{
			int topTemp4=offTop+topBase+lineHeight*2;
			canvas.drawBitmap(UseCardView.sharescontent, leftTemp, topTemp4, null);
			for(int m=0;m<sd.datails.get(lineTemp+2).length;m++)
			{
				String str=sd.datails.get(lineTemp+2)[m];
				canvas.drawText(str,130+110*m,topTemp4+startY, paint);
			}
		}
		if(lineTemp+3>=0&&lineTemp+3<sharesnum)
		{
			int topTemp5=offTop+topBase+lineHeight*3;
			canvas.drawBitmap(UseCardView.sharescontent, leftTemp, topTemp5, null);
			for(int m=0;m<sd.datails.get(lineTemp+3).length;m++)
			{
				String str=sd.datails.get(lineTemp+3)[m];
				canvas.drawText(str,130+110*m,topTemp5+startY, paint);
			}
		}
		canvas.drawBitmap(UseCardView.kuangjia, leftTemp-10,195+(isshares-lineNumber)*70+offset , null);//绘制选中框架
		canvas.restore();
	}
	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		int x=ScreenTransUtil.xFromRealToNorm((int)event.getX());
		int y=ScreenTransUtil.yFromRealToNorm((int)event.getY());
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				eventY=event.getY();
				if(x>=ConstantUtil.RedCard_RecoverGame_Left_X&&x<=ConstantUtil.RedCard_RecoverGame_Right_X
						&&y>=ConstantUtil.RedCard_RecoverGame_Top_Y&&y<=ConstantUtil.RedCard_RecoverGame_Bottom_Y)//退出
				{
					recoverGame();
				}else if(x>=ConstantUtil.RedCard_UseCard_Left_X&&x<=ConstantUtil.RedCard_UseCard_Right_X
						&&y>=ConstantUtil.RedCard_UseCard_Top_Y&&y<=ConstantUtil.RedCard_UseCard_Bottom_Y)//
				{
					days=gv.date;
					if(RedCard.isRed)
					{
						sd.Up=true;
						isCardRed=true;//使用红卡
						RedCard.isRed=false;
					}else if(RedCard.isBlack)
					{
						sd.Low=true;
						isCardRed=true;//使用黑卡
						RedCard.isBlack=false;
					}
					recoverGame();
				}
				else if(y>ConstantUtil.RedCard_SelectOneLine_Top_Y&&y<ConstantUtil.RedCard_SelectOneLine_Bottom_Y)
				{
					isshares=lineNumber;//滑动的行数
				}
				else if(y>ConstantUtil.RedCard_SelectTwoLine_Top_Y&&y<ConstantUtil.RedCard_SelectTwoLine_Bottom_Y)
				{
					isshares=lineNumber+1;//滑动的行数isshares=lineNumber;
				}
				else if(y>ConstantUtil.RedCard_SelectThreeLine_Top_Y&y<ConstantUtil.RedCard_SelectThreeLine_Bottom_Y)
				{
					isshares=lineNumber+2;//滑动的行数
				}
				else if(y>ConstantUtil.RedCard_SelectFourLine_Top_Y&&y<ConstantUtil.RedCard_SelectFourLine_Bottom_Y)
				{
					isshares=lineNumber+3;//滑动的行数
				}
			case MotionEvent.ACTION_MOVE:
				offset=(int) (event.getY()-eventY);
				break;
			case MotionEvent.ACTION_UP:
				if(!(event.getY()==eventY))//判断是否为点击事件  即按下就抬起
				{
					lineNumber=lineNumber-((offset>=0)?offset/lineHeight:(offset/lineHeight-1));
					offset=(offset>0)?offset%lineHeight:(offset%lineHeight+lineHeight);
					if(lineNumber<0)
					{
						offset=offset+(0-lineNumber)*lineHeight;
						lineNumber=0;
					}
					if(lineNumber>sharesnum-4)
					{
						offset=offset+(sharesnum-4-lineNumber)*lineHeight;
						lineNumber=sharesnum-4;
					}
					new Thread()
					{
						public void run()
						{
							while(offset!=0)
							{
								offset=(int) (offset*0.8);
								if(offset<5&&offset>-5)
								{
									offset=0;
								}
								try
								{
									Thread.sleep(20);
								}
								catch(Exception e)
								{
									e.printStackTrace();
								}
							}
						}
					}.start();
				}
			break;
		}
		
		return true;
	}
}
