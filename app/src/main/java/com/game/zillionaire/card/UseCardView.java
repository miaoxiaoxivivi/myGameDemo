package com.game.zillionaire.card;

import com.game.zillionaire.meetdrawable.PoliceCarAnimation;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.util.ScreenTransUtil;
import com.game.zillionaire.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class UseCardView implements View.OnTouchListener {
	GameView gameView;//定义一个GameView对象
	static Bitmap background;//定义画背景框的对象
	static Bitmap select;//定义画选中框的对象
	static Bitmap[][] divide=new Bitmap[3][7];//定义21张卡片的对象
	public static Bitmap[] useCard=new Bitmap[21];
	static Bitmap cardGO;
	static Bitmap cardBack;
	//天使卡、黑卡、路障、收税卡、免费卡、改建卡、恶魔卡、购地卡、怪兽卡、遥控骰子
	//抢夺卡、机器娃娃、请神卡、红卡、送神卡、停留卡、同盟卡、乌龟卡、转向卡、陷害卡、定时炸弹
	String[] cardName={"天使卡","黑卡","路障","收税卡","免费卡","改建卡","恶魔卡","购地卡","怪兽卡","遥控骰子",
			"抢夺卡","机器娃娃","请神卡","红卡","送神卡","停留卡","同盟卡","乌龟卡","转向卡","陷害卡","定时炸弹"};//对应卡片名称
	String[] cardFunction={"只要指定一处土地，整个路段的房屋都增盖一层。","股票上涨时用它可立即下跌。",
			"设路障使人停留在设路障处，可以挡自己也可以害别人。","可从指定对手的现金那里得到20%的税款，可以用免费、嫁祸卡解除。",
			"租金或罚金及缴税超过两千元或剩下的资金时，可抵用一次，免去费用。","变更住宅用地，让对手的旅馆或购物中心变成公园。",
			"可令整个路段的一排房子瞬间变为平地。","以市价强制收购别人的土地。",
			"彻底摧毁一栋建筑物，将它还原为平地的状态。","可以控制骰子的点数，决定前进步数。",
			"抢夺对手的卡片或道具。","机器人可以清除前方道路上的异物。",
			"立即请到身边距离最近的神仙。","股票下跌时用它可立即上涨。",
			"遇到坏神仙或定时炸弹马上送走。","指定自己或对手原地停走一次。",
			"同盟7天，相互不收过路费，与对手的房地产联合收费，得到的钱各算各的。","一次走一步，连续三天。",
			"使用后自己或对手立即掉头向后转。","使被选定的对手坐牢五天，丧失收租金的权利。",
			"走满38步后会自动爆炸，威力范围内车毁、屋塌、人住院五天。"};//对应卡片的作用
	int selectIndex=0;//选择的卡片的索引值
	boolean isDraw=false;//允许画其他
	public PoliceCarAnimation police=null;//定义一个英雄的对象
	public static Bitmap[] bbitmap=new Bitmap[2];//选中框
	public static Bitmap isbitmap;//是否使用卡片的图片
	public static Bitmap[] bitmaps=new Bitmap[2];//路上障碍物体
	public static Bitmap[] bitmap=new Bitmap[3];//头像人物图片
	public static Bitmap[] allianceTou=new Bitmap[3];
	public static Bitmap dialogBitmap;//对话框图片
	public static Bitmap alliance=null;
	static Bitmap redCardBack=null;
	static Bitmap redCardSelect=null;
	public UsedCard cd;//创建部分卡片功能的对象
	public static Bitmap diceCard;//加载选择骰子图片
	public static Bitmap[] selectDiceCard=new Bitmap[6];//加载选择后的骰子图片
	public static Bitmap sharescontent;//股票详细图
	public static Bitmap kuangjia;//股票框架
	
	boolean isDrawBack=false;//如果允许往前走
	boolean isDrawGo=false;//如果允许往后走
	int cardGOIndex=0;//卡片 往前走 -1 往后走 +1
	int functionIndex=0;//实现点击卡片的功能的索引值
	
	public UseCardView(GameView gameView)//构造器
	{
		this.gameView=gameView;
	}
	public void onDraw(Canvas canvas)//绘制方法
	{
		background=PicManager.getPic("usecard01",gameView.activity.getResources());
		select=PicManager.getPic("usecard00",gameView.activity.getResources());
		Bitmap bitmapTemp=PicManager.getPic("usecard",gameView.activity.getResources());//加载整图
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<7;j++)
			{
				divide[i][j]=Bitmap.createBitmap(bitmapTemp,75*j,91*i,75,91);//将一张整图分成21张图
			}
		}
		int kk=0;
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<7;j++)
			{
				useCard[kk]=divide[i][j];//将分割后的二维数组赋值给一维数组
				kk++;//变量自加
			}
		}
		bitmapTemp=PicManager.getPic("shaizi2",gameView.activity.getResources());
		for(int i=0;i<selectDiceCard.length;i++)
		{
			selectDiceCard[i]=Bitmap.createBitmap(bitmapTemp,ConstantUtil.Width1[i],0,100,109);
		}
		bbitmap[0]=PicManager.getPic("xzk",gameView.activity.getResources());//小选中框图片
		bbitmap[1]=PicManager.getPic("xzk1",gameView.activity.getResources());//大选中框图片
		isbitmap=PicManager.getPic("yesorno",gameView.activity.getResources());//是否使用卡片的图片
		bitmaps[0]=PicManager.getPic("zhadan0",gameView.activity.getResources());//加载炸弹图片
		bitmaps[1]=PicManager.getPic("stop1",gameView.activity.getResources());//加载路障图片
		dialogBitmap=PicManager.getPic("selectfigure",gameView.activity.getResources());//对话框图片
		diceCard=PicManager.getPic("shaizi",gameView.activity.getResources());//加载选择骰子的图片
		cardGO=PicManager.getPic("cardgo",gameView.activity.getResources());//加载往后看的图片
		cardBack=PicManager.getPic("cardback",gameView.activity.getResources());//加载往前看的图片
		redCardBack=PicManager.getPic("usehongka01",gameView.activity.getResources());//加载红、黑卡背景图片
		redCardSelect=PicManager.getPic("usehongka02",gameView.activity.getResources());//加载红、黑卡选中框
		sharescontent=PicManager.getPic("show_sharescontents",gameView.activity.getResources());//使用股票图
		kuangjia=PicManager.getPic("kuangjia",gameView.activity.getResources());
		//人物头像图片
		bitmap[0]=PicManager.getPic("tou0_1",gameView.activity.getResources());
		bitmap[1]=PicManager.getPic("tou2_1",gameView.activity.getResources());
		bitmap[2]=PicManager.getPic("tou1_1",gameView.activity.getResources());
		alliance=PicManager.getPic("alliance",gameView.activity.getResources());
		allianceTou[0]=PicManager.getPic("tou0",gameView.activity.getResources());
		allianceTou[1]=PicManager.getPic("tou1",gameView.activity.getResources());
		allianceTou[2]=PicManager.getPic("tou2",gameView.activity.getResources());
		
		canvas.drawBitmap(background, 100, 20, null);//画背景
		int k=0;//变量 用来自加
		int count=0;//计数器
		for(int index:gameView.figure.CardNum)//遍历在商店购买了卡片的数组
		{
			if(index!=-1)//如果不等于-1(已购买卡片)
			{
				count++;//计数器加1
			}
		}
		int temp=count+cardGOIndex;//当前需要画的图片的最大值
		if(count>6)//如果卡片大于6张
		{
			if(temp>6)//如果当前需要画的图片的最大值大于6
			{
				isDrawBack=true;//允许继续往前点
				canvas.drawBitmap(cardBack, 165, 350, null);//画 往前看的图片
			}else
			{
				isDrawBack=false;//不允许继续往前点
			}
			if(cardGOIndex<0)//如果点击了向左走的图片
			{
				isDrawGo=true;//允许继续往后点
				canvas.drawBitmap(cardGO, 680, 350, null);//画 往后看的图片
			}else
			{
				isDrawGo=false;//不允许继续往后走
			}
		    for(int i=temp-6;i<temp;i++)//循环画最后的六张卡片
			{
				int x=203+k*80;//横坐标变换值
				canvas.drawBitmap(useCard[gameView.figure.CardNum[i]], x, 320, null);//画最后的六张卡片
				k++;//变量自加
			}		
			if(!isDraw)//默认选中第一个
			{
				canvas.drawBitmap(select, 203, 312, null);//画选中框
				canvas.drawBitmap(useCard[gameView.figure.CardNum[count-6]], 300, 140, null);//画上面显示的卡片
				drawString(canvas,cardFunction[gameView.figure.CardNum[count-6]],9,420,170);//画卡片功能的文字
				drawString(canvas,cardName[gameView.figure.CardNum[count-6]],4,300,267);//画卡片名称的文字
			}else//根据点击选择相应卡片
			{
				functionIndex=temp+selectIndex-6;//赋值给实现相应卡片功能的索引值
				int width=(selectIndex)*80+203;//横坐标变换值
				canvas.drawBitmap(useCard[gameView.figure.CardNum[functionIndex]], 300, 140, null);//画选择的上面的卡片
				drawString(canvas,cardFunction[gameView.figure.CardNum[functionIndex]],9,420,170);//画选择的卡片功能的文字
				drawString(canvas,cardName[gameView.figure.CardNum[functionIndex]],4,300,267);//画选择的卡片名称的文字
				canvas.drawBitmap(select, width, 312, null);//画选中框
			}
		}else//如果拥有的卡片小于6张
		{
			for(int i=0;i<count;i++)//循环画出有的卡片
			{
				int x=203+k*80;//变换x坐标
				canvas.drawBitmap(useCard[gameView.figure.CardNum[i]], x, 320, null);
				k++;//变量自加
			}
			if(!isDraw)//默认选中第一个
			{
				canvas.drawBitmap(select, 203, 312, null);
				canvas.drawBitmap(useCard[gameView.figure.CardNum[0]], 300, 140, null);
				drawString(canvas,cardFunction[gameView.figure.CardNum[0]],9,420,170);
				drawString(canvas,cardName[gameView.figure.CardNum[0]],4,300,267);
			}else//根据点击选择相应卡片
			{
				functionIndex=selectIndex;
				int width=(selectIndex)*80+203;
				canvas.drawBitmap(useCard[gameView.figure.CardNum[selectIndex]], 300, 140, null);
				drawString(canvas,cardFunction[gameView.figure.CardNum[selectIndex]],9,420,170);
				drawString(canvas,cardName[gameView.figure.CardNum[selectIndex]],4,300,267);
				canvas.drawBitmap(select, width, 312, null);
			}
		}
	}
	
	public boolean onTouch(View view,MotionEvent event){
		int x=ScreenTransUtil.xFromRealToNorm((int)event.getX());//得到点击的X坐标
		int y=ScreenTransUtil.yFromRealToNorm((int)event.getY());//得到点击的Y坐标
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if(x>=ConstantUtil.UseCardView_CloseDialog_Left_X&&x<=ConstantUtil.UseCardView_CloseDialog_Right_X
					&&y>=ConstantUtil.UseCardView_CloseDialog_Up_Y&&y<=ConstantUtil.UseCardView_CloseDialog_Down_Y)//点击关闭对话框图标
			{
				gameView.isDraw=false;//绘制GO图标
				gameView.isMenu=true;
				gameView.status=0;
				this.gameView.setOnTouchListener(this.gameView);//返还监听器
			}else if(x>=ConstantUtil.UseCardView_UseThisCard_Left_X&&x<=ConstantUtil.UseCardView_UseThisCard_Right_X
					&&y>=ConstantUtil.UseCardView_UseThisCard_Up_Y&&y<=ConstantUtil.UseCardView_UseThisCard_Down_Y)//点击 使用此卡片 图标
			{
				gameView.status=0;
				CardFunction();
				for(int i=functionIndex;i<gameView.figure.CardNum.length-1;i++)
				{
					gameView.figure.CardNum[i]=gameView.figure.CardNum[i+1];
				}
				gameView.figure.CardNum[gameView.figure.CardNum.length-1]=-1;
				selectIndex=0;
				//关闭对话框 并使用卡片
			}else if(x>=ConstantUtil.UseCardView_Card1_Left_X&&x<=ConstantUtil.UseCardView_Card1_Right_X
					&&y>=ConstantUtil.UseCardView_Card_Up_Y&&y<=ConstantUtil.UseCardView_Card_Down_Y)//点击第一张卡片
			{
				selectIndex=0;
				isDraw=true;
			}else if(x>=ConstantUtil.UseCardView_Card2_Left_X&&x<=ConstantUtil.UseCardView_Card2_Right_X
					&&y>=ConstantUtil.UseCardView_Card_Up_Y&&y<=ConstantUtil.UseCardView_Card_Down_Y)//点击第二张卡片
			{
				selectIndex=1;
				isDraw=true;
			}else if(x>=ConstantUtil.UseCardView_Card3_Left_X&&x<=ConstantUtil.UseCardView_Card3_Right_X
					&&y>=ConstantUtil.UseCardView_Card_Up_Y&&y<=ConstantUtil.UseCardView_Card_Down_Y)//点击第三张卡片
			{
				selectIndex=2;
				isDraw=true;
			}else if(x>=ConstantUtil.UseCardView_Card4_Left_X&&x<=ConstantUtil.UseCardView_Card4_Right_X
					&&y>=ConstantUtil.UseCardView_Card_Up_Y&&y<=ConstantUtil.UseCardView_Card_Down_Y)//点击第四张卡片
			{
				selectIndex=3;
				isDraw=true;
			}else if(x>=ConstantUtil.UseCardView_Card5_Left_X&&x<=ConstantUtil.UseCardView_Card5_Right_X
					&&y>=ConstantUtil.UseCardView_Card_Up_Y&&y<=ConstantUtil.UseCardView_Card_Down_Y)//点击第五张卡片
			{
				selectIndex=4;
				isDraw=true;
			}else if(x>=ConstantUtil.UseCardView_Card6_Left_X&&x<=ConstantUtil.UseCardView_Card6_Right_X
					&&y>=ConstantUtil.UseCardView_Card_Up_Y&&y<=ConstantUtil.UseCardView_Card_Down_Y)//点击第六张卡片
			{
				selectIndex=5;
				isDraw=true;
			}
			if(x>=ConstantUtil.UseCardView_CardGo_Left_X&&x<=ConstantUtil.UseCardView_CardGo_Right_X
					&&y>=ConstantUtil.UseCardView_CardMove_Up_Y&&y<=ConstantUtil.UseCardView_CardMove_Down_Y)//向前走
			{
				if(isDrawBack)//如果允许画往前走的图片
				{
					cardGOIndex--;
				}
			}
			if(x>=ConstantUtil.UseCardView_CardBack_Left_X&&x<=ConstantUtil.UseCardView_CardBack_Right_X
					&&y>=ConstantUtil.UseCardView_CardMove_Up_Y&&y<=ConstantUtil.UseCardView_CardMove_Down_Y)//向后走
			{
				if(isDrawGo)//如果允许画往后走的图片
				{
					cardGOIndex++;
				}
			}
		}
		return true;
	}
	public void CardFunction()
	{
		switch(gameView.figure.CardNum[functionIndex])
		{
			case 0://天使卡
				cd=new AngelCard(gameView,useCard[0]);
				this.gameView.setOnTouchListener(cd);
			break;
			case 1://黑卡
				cd=new RedCard(gameView,useCard[13]);
				RedCard.isBlack=true;
				this.gameView.setOnTouchListener(cd);
			break;
			case 2://路障
				cd=new RoadblockCard(gameView,useCard[2]);
				cd.setBitmap(bitmaps[1],0);
				this.gameView.setOnTouchListener(cd);
			break;
			case 3://收税卡
				cd=new TaxesCard(gameView,useCard[3]);
				cd.isSS();//搜索人物
				this.gameView.setOnTouchListener(cd);
			break;
			case 4://免费卡
			break;
			case 5://改建卡
				cd=new RebuildCard(gameView,useCard[5]);
				this.gameView.setOnTouchListener(cd);
			break;
			case 6://恶魔卡
				cd=new DemonCard(gameView,useCard[6]);
				this.gameView.setOnTouchListener(cd);
			break;
			case 7://购地卡
				cd=new PurchaseCard(gameView,useCard[7]);
				this.gameView.setOnTouchListener(cd);
			break;
			case 8://怪兽卡
				cd=new MonsterCard(gameView,useCard[8]);
				this.gameView.setOnTouchListener(cd);
			break;
			case 9://遥控骰子
				cd=new DiceCard(gameView,useCard[9]);
				this.gameView.setOnTouchListener(cd);
			break;
			case 10://抢夺卡
				cd=new GradCard(gameView,useCard[10]);
				cd.isSS();//搜索人物
				this.gameView.setOnTouchListener(cd);
			break;
			case 11://机器娃娃
				cd=new JQWWCard(gameView,useCard[11]);
				this.gameView.setOnTouchListener(cd);
			break;
			case 12://请神卡
				cd=new AskForGodCard(gameView,useCard[12]);
				cd.setUsed();
				this.gameView.setOnTouchListener(cd);
			break;
			case 13://红卡
				cd=new RedCard(gameView,useCard[13]);
				RedCard.isRed=true;
				this.gameView.setOnTouchListener(cd);
				
			break;
			case 14://送神卡
				cd=new ToTheGodCard(gameView,useCard[14]);
				this.gameView.setOnTouchListener(cd);
				cd.setUsed();
			break;
			case 15://停留卡
				cd=new StopCard(gameView,useCard[15]);
				cd.isSS();//搜索人物
				this.gameView.setOnTouchListener(cd);
				
			break;
			case 16://同盟卡
				cd=new AllianceCard(gameView,useCard[16]);
				cd.isSS();//搜索人物
				this.gameView.setOnTouchListener(cd);
				
			break;
			case 17://乌龟卡
				cd=new TortoiseCard(gameView,useCard[17]);
				this.gameView.setOnTouchListener(cd);
			break;
			case 18://转向卡
				cd=new TurnCard(gameView,useCard[18]);
				cd.isSS();//搜索人物
				this.gameView.setOnTouchListener(cd);
				
			break;
			case 19://陷害卡
				cd=new TrapCard(gameView,useCard[19]);
				cd.isSS();//搜索人物
				this.gameView.setOnTouchListener(cd);
			break;
			case 20://定时炸弹
				cd=new RoadblockCard(gameView,useCard[20]);
				cd.setBitmap(bitmaps[0],1);
				this.gameView.setOnTouchListener(cd);
			break;
		}
	}
	public void setUsedCard(UsedCard ud)
	{
		this.cd=ud;
	}
	public void drawString(Canvas canvas,String string,int instance,int x,int y)//画文字
	{
		Paint paint = new Paint();
		paint.setARGB(255, 0,0,0);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setTextSize(24);//设置文字大小
		int lines = string.length()/instance+(string.length()%instance==0?0:1);//求出需要画几行文字
		for(int i=0;i<lines;i++){
			String str="";
			if(i == lines-1){//如果是最后一行那个不太整的汉字
				str = string.substring(i*instance);
			}
			else{
				str = string.substring(i*instance, (i+1)*instance);
			}
			canvas.drawText(str, x, y+24*i, paint);//将文字画到背景框中
		}
	}
}
