package com.game.zillionaire.view;

import java.util.ArrayList;
import com.game.zillionaire.activity.ZActivity;
import com.game.zillionaire.animation.MeetableAnimation;
import com.game.zillionaire.card.Card;
import com.game.zillionaire.card.RedCard;
import com.game.zillionaire.card.UseCardView;
import com.game.zillionaire.dialog.DialogEnum;
import com.game.zillionaire.dialog.ShowDialog;
import com.game.zillionaire.figure.CrashFigure;
import com.game.zillionaire.figure.Dice;
import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.map.Layer;
import com.game.zillionaire.map.LayerList;
import com.game.zillionaire.map.MeetableLayer;
import com.game.zillionaire.map.MyDrawable;
import com.game.zillionaire.map.MyMeetableDrawable;
import com.game.zillionaire.meetdrawable.AccidentDrawable;
import com.game.zillionaire.meetdrawable.BankLiXi;
import com.game.zillionaire.meetdrawable.CaiPiaoWinning;
import com.game.zillionaire.meetdrawable.MagicHouseDrawable;
import com.game.zillionaire.meetdrawable.MessageEnum;
import com.game.zillionaire.thread.GameViewThread;
import com.game.zillionaire.util.ChangeSitting;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.GameViewUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.util.Room;
import com.game.zillionaire.util.ScreenTransUtil;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import static com.game.zillionaire.util.ConstantUtil.*;
 
@SuppressLint("SimpleDateFormat")
public class GameView extends SurfaceView implements SurfaceHolder.Callback,View.OnTouchListener
{
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
	public ZActivity activity;//activity的引用
	public SharesView shares;//股票索引
	public ShowDialog showDialog;//信息对话框
	public DrawThread drawThread;//刷帧的线程
	public GameViewThread gvt;//后台修改数据的线程
	public LayerList layerList;//所有的层
	MyMeetableDrawable [][] meetableMatrix;//存放大地图的可遇矩阵
	public MyMeetableDrawable currentDrawable;//记录当前碰到的可遇Drawable对象引用
	public MeetableLayer meetableChecker;
	
	public int [][] notInMatrix=new int[MAP_ROWS][MAP_COLS];//存放整个大地图的不可通过矩阵
	public ArrayList<CrashFigure> cfigure = new ArrayList<CrashFigure>();//存放碰撞人物
	public ArrayList<Card> card = new ArrayList<Card>();//存放道具物体
	
	public int status = 0;//绘制时的状态，0正常游戏，1,英雄在走动
	Paint paint;//画笔
	public static Resources resources;  //声明资源对象引用
	public int currentSteps;//记录本次掷骰子需要走几步
	
	public Figure figure;//操作人物
	public Figure figure1;//系统人物1
	public Figure figure2;//系统人物1
	public Figure figure0=null;//实际人物
	public int isFigure;//判断人物对象
	static Bitmap[] bmpFigure;//存放英雄的图片,分为静态和动态,代表英雄静止还是走路
	
	public int indext=0;//骰子图片的索引
	//骰子的坐标
	public int diceX=-100;
	public int diceY=-100;
	public Dice di;//骰子类
	
	public boolean isDraw=false;//是否绘制GO图标的标志位  --false绘制   --true不绘制
	public boolean isYuanBao=true;//是否显示元宝的标志位  ---true是第一帧     --false 不是第一帧
	boolean isFrist=true;//是否为第一个标志位  ---true是第一帧     --false 不是第一帧
	public boolean isSheZhi=false;//是否显示设置图标的标志位，默认为false,表示不查看设置
	public boolean isMenu=true;//是否显示主菜单的标志位，true显示     false不显示
	public boolean isDrawAlliance=false;
	
	public boolean isDialog=false;//是否绘制对话框
	public BankLiXi blx;
	public int tempStartRow=0;//记录本次绘制时的定位行
	public int tempStartCol=0;//记录本次绘制时的定位列
	public int tempOffsetX=0;//记录本次绘制时的x偏移
	public int tempOffsetY=0;//记录本次绘制时的y偏移
	public int[] room={-1,-1,-1,-1,-1,-1};//六个地区	
	public boolean isGoTo=true;//判断人物是否前进
	
	
	public int date=01;//1号
	public CaiPiaoWinning caiPiaoWinning;
	public int count=0;//计天数
	public int count1=0;//买卖股票计数
	public int count2=0;//系统人物卖股票
	public MeetableAnimation aa;
	public int temp=0;//用于记录碰撞神明的生存期
	public boolean isWW=false;//绘制机器娃娃
	public boolean isWeekend=false;//判断是否是周末
	
	public UseCardView useCard;//卡片界面类
	MagicHouseDrawable magic;//魔法屋类
	AccidentDrawable accident;//机遇类
	CheckFigureInfomation checkFigureInfo;
	WinView winView;
	RedCard cr;//获得使用卡片的引用
	public ChangeSitting cs;//新闻交换位置工具类
	public GameViewUtil gvu;//gameview工具类
	public ArrayList<Integer> mpCP=new ArrayList<Integer>();//用于存放彩票
	public boolean isFigureMove=false;//是否播放特殊人物动画
	
 	public GameView(ZActivity activity) 
	{
		super(activity);
		getHolder().addCallback(this);
		this.activity = activity;//activity的引用
		resources = this.getResources();
		paint = new Paint();
		magic=new MagicHouseDrawable();
		accident=new AccidentDrawable();
		checkFigureInfo=new CheckFigureInfomation(this);
		winView=new WinView(this);
		figure= new Figure(this, 8, 4,0,0,0,0,0,MessageEnum.Artificial_Control,2,activity.peopleindext);//创建英雄--玩家操控
		int[] indext=new int[2];
		for(int i=0,j=0;i<3;i++)
		{
			if(i!=activity.peopleindext)
			{
				indext[j]=i;
				j++;
			}
		}
        figure1= new Figure(this, 8, 9,4,0,0,0,1,MessageEnum.System_Control_1,2,indext[0]);//创建英雄--系统操控1
        figure2= new Figure(this, 9, 9,4,0,0,0,2,MessageEnum.System_Control_2,2,indext[1]);//创建英雄--系统操控2
        isFigure=0;//起始时为操作人物对象
        di=new Dice(this);//初始化骰子
        this.drawThread = new DrawThread(getHolder(), this);//初始化刷帧线程
        this.gvt = new GameViewThread(this);//初始化后台数据修改线程
        gvu=new GameViewUtil(this);//给GameViewUtil的对象赋值
        useCard=new UseCardView(this);//定义卡片使用的对象
        caiPiaoWinning=new CaiPiaoWinning(this);
        showDialog=new ShowDialog(this);//创建对话框
        blx=new BankLiXi(this);
        shares=new SharesView(this);
        cs=new ChangeSitting(this);//给ChangeSitting的对象赋值
        initMap();
        if(activity.isBackSound)
        {
        	activity.sm.playBackMusic(activity.soundName);
    	}
        
	}
	//方法:初始化地图
	public void initMap()
	{
		layerList = new LayerList(activity,getResources());
		this.notInMatrix = layerList.getTotalNotIn();//得到总不可通过矩阵
		this.meetableChecker = (MeetableLayer)layerList.layers.get(1);
	}
	//方法:绘制屏幕
	public void onDraw(Canvas canvas)//自己写的绘制方法
	{
		if(canvas==null)//canvas为空，返回
		{
			return;
		}
		gvu.initBitmap();//初始化图片
		CrashFigure.initBitmap(this.getResources());
		if(!figure.isWG)
		{
			gvu.go=gvu.gos[0];//初始化骰子图片
		}
		canvas.save();
		canvas.translate(ConstantUtil.LOX, ConstantUtil.LOY);
		canvas.scale(ConstantUtil.RADIO, ConstantUtil.RADIO);
		canvas.clipRect(0,0,1280,720);
	
		if(isWW)
		{
			tempStartRow =figure0.startRow;//记录本次绘制时的定位行
			tempStartCol = figure0.startCol;//记录本次绘制时的定位列
			tempOffsetX = figure0.offsetX;//记录本次绘制时的x偏移
			tempOffsetY = figure0.offsetY;//记录本次绘制时的y偏移
		}else
		{
			Room.isGotoWhere(this);//选取参考点
		}
		cs.isCSitting();//新闻交换位置
		int hCol = figure.x/TILE_SIZE_X;//计算操作人物中心点位于哪个格子
		int hRow = figure.y/TILE_SIZE_Y;//计算操作人物中心点位于哪个格子

		int hCol1 = figure1.x/TILE_SIZE_X;//计算系统人物1中心点位于哪个格子
		int hRow1 = figure1.y/TILE_SIZE_Y;//计算系统人物1中心点位于哪个格子
	
		int hCol2 = figure2.x/TILE_SIZE_X;//计算系统人物2中心点位于哪个格子
		int hRow2 = figure2.y/TILE_SIZE_Y;//计算系统人物2中心点位于哪个格子
		/*
		 * 绘制底层
		 * 这里为了实现无级滚屏，绘制屏幕所囊括的地图时必须多绘制一圈，但是有时定位点已然是边缘了，就continue一下
		 */ 
		for(int i=-1; i<=GAME_VIEW_SCREEN_ROWS; i++)//声明数组
		{     
			if(tempStartRow+i < 0 || tempStartRow+i>MAP_ROWS)//如果多画的那一行不存在，就继续
			{
				continue;
			}
			for(int j=-1; j<=GAME_VIEW_SCREEN_COLS; j++)
			{
				if(tempStartCol+j <0 || tempStartCol+j>MAP_COLS)//如果多画的那一列不存在，就继续
				{
					continue;
				}
				Layer l = (Layer)layerList.layers.get(0);//获得底层的图层
				MyDrawable[][] mapMatrix=l.getMapMatrix();
				if(mapMatrix[i+tempStartRow][j+tempStartCol] != null)
				{
					mapMatrix[i+tempStartRow][j+tempStartCol].drawSelf(canvas,activity,i,j,tempOffsetX,tempOffsetY);
				}
			} 
		}
		while(cfigure.size()<10)
		{//在地图中添加碰撞人物
			Layer l = (Layer)layerList.layers.get(0);//获得底层的图层
			MyDrawable[][] mapMatrix=l.getMapMatrix();
			int x=(int)(Math.random()*(MAP_COLS-2)+1);
			int y=(int)(Math.random()*(MAP_ROWS-2)+1);
			if(mapMatrix[x][y]!=null&&mapMatrix[x][y].flag==false
					&&mapMatrix[x-1][y].flag==false&&mapMatrix[x+1][y].flag==false
					&&mapMatrix[x][y-1].flag==false&&mapMatrix[x][y+1].flag==false)
			{
				gvu.addCrashFigure(mapMatrix[x][y],x,y);
			}
		}
		//绘制上层
		for(int i=-1; i<=GAME_VIEW_SCREEN_ROWS; i++)
		{
			if(tempStartRow+i < 0 || tempStartRow+i>MAP_ROWS)//如果多画的那一行不存在，就继续
			{
				continue;
			}
			for(int j=-1; j<=GAME_VIEW_SCREEN_COLS; j++)
			{
				if(tempStartCol+j <0 || tempStartCol+j>MAP_COLS)//如果多画的那一列不存在，就继续
				{
					continue;
				}
				Layer l = (Layer)layerList.layers.get(1);//获得上层的图层
				MyDrawable[][] mapMatrix=l.getMapMatrix();
				if(mapMatrix[i+tempStartRow][j+tempStartCol] != null)
				{ 
					mapMatrix[i+tempStartRow][j+tempStartCol].drawSelf(canvas,activity,i,j,tempOffsetX,tempOffsetY);
				}
				//绘制人物
				if(hRow-tempStartRow == i && hCol-tempStartCol == j&&figure.isHero)//英雄在这里
				{
					if(figure.isDreaw)
					{
						figure.drawSelf(canvas,tempStartRow,tempStartCol,tempOffsetX,tempOffsetY);
						gvu.drawFigure(canvas,figure);
						if(date>temp)
						{
							CrashFigure.isMeet1=false;
							figure.id=-1;
							temp=0;
						}
						if(CrashFigure.isMeet1&&figure.id<=10)
						{
							canvas.drawBitmap(hm1.get(figure.id), figure.topLeftCornerX+20, figure.topLeftCornerY-30, null);
						}	
					}
				}
				if(hRow1-tempStartRow == i && hCol1-tempStartCol == j&&figure1.isHero)//英雄在这里
				{
					if(figure1.isDreaw)
					{
						figure1.drawSelf(canvas,tempStartRow,tempStartCol,tempOffsetX,tempOffsetY);
						gvu.drawFigure(canvas,figure1);
						if(date>temp)
						{
							CrashFigure.isMeet0=false;
							figure1.id=-1;
							temp=0;
						}
						if(CrashFigure.isMeet0&&figure1.id<=10)
						{
							canvas.drawBitmap(hm1.get(figure1.id), figure1.topLeftCornerX+20, figure1.topLeftCornerY-30, null);
						}	
					}
				}
				if(hRow2-tempStartRow == i && hCol2-tempStartCol == j&&figure2.isHero)//英雄在这里
				{
					if(figure2.isDreaw)
					{
						figure2.drawSelf(canvas,tempStartRow,tempStartCol,tempOffsetX,tempOffsetY);
						gvu.drawFigure(canvas,figure2);
						if(date>temp)
						{
							CrashFigure.isMeet=false;
							figure2.id=-1;
							temp=0;
						}
						if(CrashFigure.isMeet&&figure2.id<=10)
						{
							canvas.drawBitmap(hm1.get(figure2.id), figure2.topLeftCornerX+20, figure2.topLeftCornerY-30, null);
						}	
					}
				}
				if(isWW)//绘制机器娃娃
				{
					if(figure0.y/TILE_SIZE_Y-tempStartRow == i && figure0.x/TILE_SIZE_X-tempStartCol == j)//英雄在这里
					{
						figure0.drawSelf(canvas,tempStartRow,tempStartCol,tempOffsetX,tempOffsetY);
					}
				}
				//绘制大土地上的房子
				if(mapMatrix[i+tempStartRow][j+tempStartCol] != null&&mapMatrix[i+tempStartRow][j+tempStartCol].da==1&&mapMatrix[i+tempStartRow][j+tempStartCol].ss!=-1)
				{
					MyDrawable mmd=mapMatrix[i+tempStartRow][j+tempStartCol];
					Bitmap bigHouseBmp=PicManager.getPic(mmd.dbpName, activity.getResources());
					canvas.drawBitmap(bigHouseBmp, mmd.bitmapx, mmd.bitmapy+96-bigHouseBmp.getHeight(),paint);
				}
			}
		}
		//绘制碰撞人物
		for(int i=-1; i<=GAME_VIEW_SCREEN_ROWS; i++)
		{
			if(tempStartRow+i < 0 || tempStartRow+i>MAP_ROWS)//如果多画的那一行不存在，就继续
			{
				continue;
			}
			for(int j=-1; j<=GAME_VIEW_SCREEN_COLS; j++)
			{
				if(tempStartCol+j <0 || tempStartCol+j>MAP_COLS)//如果多画的那一列不存在，就继续
				{
					continue;
				}
				Layer l = (Layer)layerList.layers.get(0);//获得下层的图层
				MyDrawable[][] mapMatrix=l.getMapMatrix();
				if(mapMatrix[i+tempStartRow][j+tempStartCol].flag)
				{ 
					try
					{
						for(CrashFigure c:cfigure)
						{
							if(c.x== i+tempStartRow && c.y== j+tempStartCol)
							{
								c.drawSelf(canvas,i,j,tempOffsetX,tempOffsetY);
								break;
							}
						}
					}
					catch(Exception e){}
				}
			}
		}
		//绘制卡片物体
		try
		{
			for(Card cd:card)
			{
				cd.onDraw(canvas);
			}
		}catch(Exception e){}
		if(isFigureMove&&aa!=null&&aa.isAngel)//播放特殊人物动画
		{
			aa.drawGod(canvas);
		}
		//绘制日期
		gvu.drawDate(canvas);
		//绘制屏幕最下方的头像
		gvu.drawTouImage(canvas);
    	if(!isFigureMove&&!isDialog&&currentDrawable != null)//判断是否需要绘制可遇实体的对话框
		{
			isDraw=true;//不绘制GO图标
			isMenu=false;//不绘制菜单
			isYuanBao=false;//不显示元宝图标
			currentDrawable.drawDialog(canvas, figure0);
		}
    	if(status==2)//股票
		{
			shares.onDraw(canvas);
		}
    	else
    	{
    		shares.sd.initDetails();//随机改变股票成交价
    	}
    	//绘制彩票中奖
    	if(date==15)
    	{
    		caiPiaoWinning.drawCaiPiaoWinningF(canvas);
    	}    
    	//系统人物购买股票
    	if(date==5&&count1==0&&figure0.k!=0)
    	{
    		if(shares.sd.systemBuy())
    		{
				showDialog.guPiaoShu=shares.sd.name;//股票数
				showDialog.initMessage(shares.sd.SharesName,figure0.k,true,true,DialogEnum.MESSAGE_ONE,28,-1);
				this.isDialog=true;//绘制对话框
				count1++;
    		}
    	}
    	else if(date!=5)
    	{
    		count1=0;
    	}
    	//系统人物卖股票
    	if(date==25&&count2==0&&figure0.k!=0)
    	{
    		if(shares.sd.systemSale())
    		{
    			showDialog.guPiaoShu=shares.sd.name;//股票数
    			showDialog.initMessage(shares.sd.SharesName,figure0.k,true,true,DialogEnum.MESSAGE_ONE,29,-1);
    			this.isDialog=true;//绘制对话框
    			count2++;
    		}
    	}
    	else if(date!=25)
    	{
    		count2=0;
    	}
    	//绘制月底银行发红利
    	if(date==28)
    	{
    		blx.drawDialog(canvas);
    	}    	    
    	if(!isFigureMove&&showDialog!=null&&isDialog)//绘制相应的对话框
    	{
    		showDialog.drawDialog(canvas);
    	}
    	//绘制同盟标志
    	gvu.drawAlliance(canvas);
    	//绘制骰子
		if(!isFigureMove&&!isDialog&&di.flag)
		{
			di.draw(canvas);
		}
		if(RedCard.days+3<date&&RedCard.isCardRed)//卡片生效的期限
		{
			shares.sd.Byperson(shares.isshares);
		}
		else
		{
			RedCard.isCardRed=false;
			shares.sd.Up=false;
			shares.sd.Low=false;
		}
		if(!isDialog)
		{
			gvu.drawMenu(canvas);//绘制游戏主界面菜单
			//绘制Go
			gvu.drawGo(canvas);
		}
    	//绘制警车/救护车
    	magic.drawPoliceCar(canvas, this, figure, figure1, figure2);
    	//绘制飞船
    	accident.drawShip(canvas, this, figure, figure1, figure2);
		if(status==3)//使用卡片
		{
			useCard.onDraw(canvas);
		}
		if(status==4)//查看英雄信息
		{
			checkFigureInfo.onDraw(canvas);
		}
		if(useCard.cd!=null&&useCard.cd.isDrawCard)
		{
			useCard.cd.onDraw(canvas);
		}
		if(figure.mhz.zMoney>=300000)//总资产大于30万  即胜利
		{
			winView.onDraw(canvas,figure);
		}else if(figure1.mhz.zMoney>=300000)
		{
			winView.onDraw(canvas,figure1);
		}else if(figure2.mhz.zMoney>=300000)
		{
			winView.onDraw(canvas,figure2);
		}
		canvas.restore();
	}
	public void setCurrentDrawable(MyMeetableDrawable currentDrawable)
	{
		this.currentDrawable = currentDrawable;
	}
	
	//方法：设置GameView的状态
	public void setStatus(int status)
	{
		this.status = status;
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.drawThread.setFlag(true);
		drawThread.setIsViewOn(true);
        if(!drawThread.isAlive())//如果后台重绘线程没起来,就启动它
        {
        	try
        	{
        		drawThread.start();
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
        this.setOnTouchListener(this);
        if(!gvt.isAlive())//如果后台线程没起来,就启动它
        {
        	try
        	{
        		gvt.start();
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.drawThread.setIsViewOn(false);
	}
	public class DrawThread extends Thread{//刷帧线程

		private int sleepSpan = ConstantUtil.GAME_VIEW_SLEEP_SPAN;//睡眠的毫秒数 
		private SurfaceHolder surfaceHolder;
		private GameView gameView;
		private boolean isViewOn = false;
		private boolean flag = true;
        public DrawThread(SurfaceHolder surfaceHolder, GameView gameView) {//构造器
        	super.setName("==GameView.DrawThread");
            this.surfaceHolder = surfaceHolder;
            this.gameView = gameView;
        }
        public void setFlag(boolean flag) {//设置循环标记位
        	this.flag = flag;
        }
        public void setIsViewOn(boolean isViewOn){
        	this.isViewOn = isViewOn;
        }
		public void run() 
		{
			Canvas c;
			while(flag)
			{
	            while (isViewOn) 
	            {
	                c = null;
	                try 
	                {
	                	// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
	                    c = this.surfaceHolder.lockCanvas(null);
	                    synchronized (this.surfaceHolder) 
	                    {
	                    	gameView.onDraw(c);
	                    }
	                } finally 
	                {
	                    if (c != null) 
	                    {
	                    	//更新屏幕显示内容
	                        this.surfaceHolder.unlockCanvasAndPost(c);
	                    }
	                }
	                try
	                {
	                	Thread.sleep(sleepSpan);//睡眠指定毫秒数
	                }
	                catch(Exception e)
	                {
	                	e.printStackTrace();
	                }
	            }
	            try
	            {
	            	Thread.sleep(1500);//睡眠指定毫秒数
	            }
	            catch(Exception e)
	            {
	            	e.printStackTrace();
	            }
			}
		}
	}
	@Override
	public boolean onTouch(View view, MotionEvent event)
	{		
		int x=ScreenTransUtil.xFromRealToNorm((int)event.getX());
		int y=ScreenTransUtil.yFromRealToNorm((int)event.getY());
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			if((!isDraw)&&x>=ConstantUtil.GameView_Go_Left_X&&x<=ConstantUtil.GameView_Go_Right_X
					&&y>=ConstantUtil.GameView_Go_Up_Y&&y<=ConstantUtil.GameView_Go_Down_Y)//当手触碰GO图标时
			{
				gvu.isGoDice(figure);//操作人物掷骰子	
			}else if(isMenu&&x>ConstantUtil.GameView_TheIcon_Left_X&&x<=ConstantUtil.GameView_TheIcon_Right_X
					&&y>=ConstantUtil.GameView_TheFistIcon_Up_Y&&y<=ConstantUtil.GameView_TheFistIcon_Down_Y)//菜单中第一个图标
			{
				int cardCount=0;
				for(int index:figure.CardNum)
				{
					if(index!=-1)//如果索引值等于-1
					{
						cardCount++;//计数器加1
					}
				}
				if(cardCount>0)//如果20张不全为-1
				{
					isDraw=true;//不绘制GO图标
					isMenu=false;//不绘制菜单
					this.isYuanBao=false;
					status=3;//绘制卡片显示
					this.setOnTouchListener(useCard);
				}else//如果没有购买任何卡片
				{
				}
			}else if(isMenu&&x>=ConstantUtil.GameView_TheIcon_Left_X&&x<=ConstantUtil.GameView_TheIcon_Right_X
					&&y>=ConstantUtil.GameView_TheSecondIcon_Up_Y&&y<=ConstantUtil.GameView_TheSecondIcon_Down_Y)//菜单中第二个图标
			{				
				isMenu=false;//不绘制菜单
				isDraw=true;//不绘制GO图标
				this.isYuanBao=false;
				status=2;
				this.setOnTouchListener(shares);
			}else if(isMenu&&x>=ConstantUtil.GameView_TheIcon_Left_X&&x<=ConstantUtil.GameView_TheIcon_Right_X
					&&y>=ConstantUtil.GameView_TheThirdIcon_Up_Y&&y<=ConstantUtil.GameView_TheThirdIcon_Down_Y)//菜单中第三个图标
			{		
				isMenu=false;//不绘制菜单
				isDraw=true;//不绘制GO图标
				status=4;//绘制卡片显示
				this.setOnTouchListener(checkFigureInfo);
			}else if(isMenu&&x>ConstantUtil.GameView_TheIcon_Left_X&&x<=ConstantUtil.GameView_TheIcon_Right_X
					&&y>=ConstantUtil.GameView_TheFourthIcon_Up_Y&&y<=ConstantUtil.GameView_TheFourthIcon_Down_Y)//菜单中第四个图标
			{
				if(isSheZhi)
				{
					isSheZhi=false;
				}else
				{
					isSheZhi=true;
				}				
			}else if(isSheZhi&&isMenu&&x>ConstantUtil.GameView_Save_Left_X&&x<=ConstantUtil.GameView_Save_Right_X
					&&y>=ConstantUtil.GameView_Up_Y&&y<=ConstantUtil.GameView_Down_Y)
			{//存储
				isSheZhi=false;
				Message msg1 = this.activity.myHandler.obtainMessage(12);
				this.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
			}else if(isSheZhi&&isMenu&&x>ConstantUtil.GameView_ChooseMenu_Left_X&&x<=ConstantUtil.GameView_ChooseMenu_Right_X
					&&y>=ConstantUtil.GameView_Up_Y&&y<=ConstantUtil.GameView_Down_Y)
			{//选单
				isSheZhi=false;
				Message msg1 = this.activity.myHandler.obtainMessage(0);
				this.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
				if(activity.sm.mp.isPlaying())//返回主界面--关闭停止播放音乐
				{
					activity.sm.mp.stop();
					activity.sm.mp.release();
				}
			}else if(isSheZhi&&isMenu&&x>ConstantUtil.GameView_ReadShedule_Left_X&&x<=ConstantUtil.GameView_ReadShedule_Right_X
					&&y>=ConstantUtil.GameView_Up_Y&&y<=ConstantUtil.GameView_Down_Y)
			{//读取
				isSheZhi=false;
				Message msg1 = this.activity.myHandler.obtainMessage(11);
				this.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
			}
		}
		return true;
	}
}
