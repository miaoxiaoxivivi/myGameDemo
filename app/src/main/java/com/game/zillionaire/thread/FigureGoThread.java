package com.game.zillionaire.thread;
/*
 * 需要注意的是，英雄的动画段的改变是每次无级走之前设置 成动态的方向动画段
 * 而当走完一个格子检查是否需要拐弯时，再设置为静态方向的动画段
 */
import static com.game.zillionaire.util.ConstantUtil.*;
import com.game.zillionaire.animation.AngelGodAnimation;
import com.game.zillionaire.animation.BigEmGodAnimation;
import com.game.zillionaire.animation.CaiGodAnimation;
import com.game.zillionaire.animation.DogGodAnimation;
import com.game.zillionaire.animation.FuGodAnimation;
import com.game.zillionaire.animation.GroundGodAnimation;
import com.game.zillionaire.animation.QiongGodAnimation;
import com.game.zillionaire.animation.ShuaiGodAnimation;
import com.game.zillionaire.card.Card;
import com.game.zillionaire.card.UsedCard;
import com.game.zillionaire.dialog.DialogEnum;
import com.game.zillionaire.figure.CrashFigure;
import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.map.Layer;
import com.game.zillionaire.map.MyDrawable;
import com.game.zillionaire.map.MyMeetableDrawable;
import com.game.zillionaire.meetdrawable.GroundDrawable;
import com.game.zillionaire.meetdrawable.PoliceCarAnimation;
import com.game.zillionaire.util.Room;
import com.game.zillionaire.view.GameView;

public class FigureGoThread extends Thread 
{
	Figure figure;//英雄的引用
	GameView gv;//游戏试图对象的引用
	public boolean flag;//线程是否执行标志位
	boolean isMoving;//英雄是否在走标志位
	int sleepSpan = FIGURE_MOVING_SLEEP_SPAN;//英雄走路时没一小步的休眠时间
	int waitSpan = FIGURE_WAIT_SPAN;//英雄不走时线程空转的等待时间
	int steps =0;//记录需要走的步数，即格子数
	int [][] notIn;//不可通过矩阵的引用
	public static boolean OneMoreTimeFlag=false;
	public int idGod=-1;//记录碰撞人物的编号
	public double scale=-1;//付款的倍数
	public int days=3;//神明存在的天数
	public static PoliceCarAnimation police;
	public boolean isDog=false;//是否碰到狗
	public FigureGoThread(){}
	//构造器
	public FigureGoThread(GameView gv,Figure figure)
	{
		super.setName("==HeroGoThread");
		this.gv = gv;
		this.figure=figure;
		this.flag = true;
	}
	//线程执行方法
	@SuppressWarnings("static-access")
	public void run()
	{
		while(flag)
		{
			qq:while(isMoving)
			{
				for(int i=0;i<steps;i++)//对每一格子进行无极移动
				{
					int moves=0;//求出这个格子需要几个小步来完成
					if(figure.direction%4==1||figure.direction%4==2)
					{
						moves = TILE_SIZE_X/FIGURE_MOVING_SPANX;//求出这个格子需要几个小步来完成
					}else
					{
						moves = TILE_SIZE_Y/FIGURE_MOVING_SPANY;//求出这个格子需要几个小步来完成
					}
					int hCol = figure.col;//英雄当前在大地图中的列
					int hRow = figure.row;//英雄当前在大地图中的行
					int destCol=hCol;//目标格子列数
					int destRow=hRow;//目标格子行数
					//先求出目的点的格子行和列,之所以模4是因为动态向上和静止朝上正好差4
					switch(figure.direction%4)
					{
						case 0://向下
							destRow = hRow+1;
							figure.setAnimationDirection(DOWN);//将英雄的方向和动画段设置为动态向下
						break;
						case 1://向左
							destCol = hCol-1;
							figure.setAnimationDirection(LEFT);//将英雄的方向和动画段设置为动态向左
						break;
						case 2://向右
							destCol = hCol+1;
							figure.setAnimationDirection(RIGHT);//将英雄的方向和动画段设置为动态向右
						break;
						case 3://向上
							destRow = hRow-1;
							figure.setAnimationDirection(UP);//将英雄的方向和动画段设置为动态向上
						break;						
					}
					int destX=destCol*TILE_SIZE_X+TILE_SIZE_X/2+1;//目的点x坐标，已经转换成中心点的了
					int destY=destRow*TILE_SIZE_Y+TILE_SIZE_Y/2+1;//目的点y坐标,已经转换成中心点的了
					int hx = figure.x;
					int hy = figure.y;
					//从下面开始无级从一个格子走到另一个格子
					for(int j=0;j<moves;j++)
					{
						//计算英雄的x位移
						if(hx<destX)
						{
							figure.x = hx+j*FIGURE_MOVING_SPANX;
							figure.col = figure.x/TILE_SIZE_X;//及时更新英雄的行列值
							checkIfRollScreen(figure.direction);
						}else if(hx>destX)
						{
							figure.x = hx-j*FIGURE_MOVING_SPANX;
							figure.col = figure.x/TILE_SIZE_X;//及时更新英雄的行列值
							checkIfRollScreen(figure.direction);
						}
						//计算英雄的y位移
						if(hy<destY)
						{
							figure.y = hy+j*FIGURE_MOVING_SPANY;
							figure.row = figure.y/TILE_SIZE_Y;//及时更新英雄的行列值
							checkIfRollScreen(figure.direction);
						}else if(hy>destY)
						{
							figure.y = hy-j*FIGURE_MOVING_SPANY;
							figure.row = figure.y/TILE_SIZE_Y;//及时更新英雄的行列值
							checkIfRollScreen(figure.direction);
						}
						try{//先睡一下
							Thread.sleep(FIGURE_MOVING_SLEEP_SPAN);
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
					//修正x和y坐标,修改英雄的占位格子
					figure.x = destX;
					figure.y = destY;
					figure.col = destCol;
					figure.row = destRow;
					//修正offsetX、y
					if(figure.offsetX<FIGURE_MOVING_SPANX)//舍去
					{
						figure.offsetX = 0;
					}else if(figure.offsetX>TILE_SIZE_X- FIGURE_MOVING_SPANX)//进位
					{
						if(figure.startCol + GAME_VIEW_SCREEN_COLS < MAP_COLS -1)
						{
							figure.offsetX=0;
							figure.startCol+=1;
						}						
					}
					if(figure.offsetY<FIGURE_MOVING_SPANY)//舍去
					{
						figure.offsetY = 0;
					}else if(figure.offsetY>TILE_SIZE_Y - FIGURE_MOVING_SPANY)//进位
					{
						if(figure.startRow + GAME_VIEW_SCREEN_ROWS < MAP_ROWS -1)
						{
							figure.startRow+=1;
							figure.offsetY = 0;
						}						
					}
					try//检测是否碰到障碍物或炸弹、机器娃娃
					{
						for(Card cd:gv.card)
						{
							if(cd.row==figure.row&&cd.col==figure.col)//机器娃娃
							{
								if(gv.isWW)
								{
									gv.card.remove(cd);
									break;
								}else
								{
									if(cd.indext==0)//障碍物
									{
										i=steps-1;//停止前进
										break;
									}
								}
							}
						}
					}catch(Exception e){}
					figure.direction = checkIfTurn();//检查是否需要拐弯
					if(figure.isZD)//头上带有炸弹
					{
						police=new PoliceCarAnimation(figure);
						figure.zdDay--;//步数减一
						if(figure.zdDay<=0)
						{
							figure.isZD=false;//头上的炸弹消失
							police.startAnimation();
							police.isAmbulance=true;
							figure.day=3;//住进医院3天
							figure.father.showDialog.day=3;
							this.setMoving(false);//停止走动
							continue qq;
						}
					}
				}//到此走完了指定的格子数，应该检查有没有遇到东西了				
				//先停下来
				this.setMoving(false);//停止走动
				figure.setAnimationDirection(figure.direction%4);//设置动画段为相应的静止态
				try{//先睡一下
					Thread.sleep(200);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				if(gv.isWW)//机器娃娃行走不用进行检测
				{
					gv.isWW=false;
					if(gv.isFigure==0)//操作人物
					{
						gv.isFigure=2;//换到系统人物的线程
					}else
					{
						gv.isFigure--;//换到上一个人物的线程
					}
					this.gv.setOnTouchListener(gv);
					this.gv.setStatus(0);//重新设置GameView的状态0为待命状态
					gv.gvt.setChanging(true);//启动变换人物的线程
				}else if(figure.isDreaw)//人物在屏幕上时进行碰撞检测
				{	
					for(CrashFigure cf:gv.cfigure)//特殊人物检测
					{	
						if(isCrash(figure,cf,gv))
						{//如果碰撞到神明
							cf.myDrawable.flag=false;//神明碰撞完毕
							break;
						}			
					}
					if(isDog)//碰到狗
					{
						isDog=false;//没有碰到狗
					}else
					{
						if(figure.isDreaw&&(!checkIfMeet()))//人物没有遇到可遇的东西
						{
							this.gv.setOnTouchListener(gv);
							this.gv.setStatus(0);//重新设置GameView的状态0为待命状态
							gv.gvt.setChanging(true);//启动变换人物的线程
						}
					}
				}
			}
			try{//线程空转等待
				Thread.sleep(waitSpan);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	//方法：设置需要走的步数,使用时先设置要走的步数，再设置走路标志位
	public void setSteps(int steps)
	{
		this.steps = steps;
	}
	//方法：设置是否走路标志位
	public void setMoving(boolean isMoving)
	{
		this.isMoving = isMoving;
	}
	/*
	 * 方法：检查是否需要滚屏，以像素为单位
	 */
	public void checkIfRollScreen(int direction){//方向，下0，左1，右2，上3
		int figureX = figure.x;
		int figureY = figure.y;
		int tempOffsetX =figure.offsetX;
		int tempOffsetY =figure.offsetY;
		switch(direction%4)
		{
			case 0://向下检查
				if(figureY - figure.startRow*TILE_SIZE_Y -tempOffsetY + ROLL_SCREEN_SPACE_DOWN >= SCREEN_HEIGHT)//检查是否需要下滚
				{
					if(figure.startRow + GAME_VIEW_SCREEN_ROWS < MAP_ROWS -1)//可以接受进位就加
					{
						figure.offsetY += FIGURE_MOVING_SPANY;
						if(figure.offsetY > TILE_SIZE_Y)//需要进位
						{
							figure.startRow += 1;
							figure.offsetY = 0;
						}
					}
				}
			break;
			case 1://向左检查
				if(figureX - figure.startCol*TILE_SIZE_X - tempOffsetX <= ROLL_SCREEN_SPACE_LEFT)//检查是否需要左滚屏
				{
					if(figure.startCol > 0)//startCol还够减
					{
						figure.offsetX -= FIGURE_MOVING_SPANY;//向左偏移英雄步进的像素数
						if(figure.offsetX < 0)
						{					
							figure.startCol -=1;
							figure.offsetX = TILE_SIZE_X-FIGURE_MOVING_SPANX;//有待商议		
						}
					}else if(figure.offsetX > FIGURE_MOVING_SPANX)//如果格子数不够减，但是偏移量还有
					{
						figure.offsetX -= FIGURE_MOVING_SPANX;//向左偏移英雄步进的像素数
					}
				}			
			break;
			case 2://向右检查
				if(figureX - figure.startCol*TILE_SIZE_X - tempOffsetX + ROLL_SCREEN_SPACE_RIGHT >= SCREEN_WIDTH)//检查是否需要右滚屏
				{
					if(figure.startCol + GAME_VIEW_SCREEN_COLS < MAP_COLS -1)//startCol还能加
					{
						figure.offsetX += FIGURE_MOVING_SPANX;//向右偏移英雄步进的像素数
						if(figure.offsetX > TILE_SIZE_X)//需要进位
						{
							figure.startCol += 1;
							figure.offsetX = 0;//有待商议
						}
					}
				}
			break;
			case 3://向上检查
				if(figureY -figure.startRow*TILE_SIZE_Y - tempOffsetY <= ROLL_SCREEN_SPACE_UP)//检查是否需要左滚屏
				{
					if(figure.startRow >0)//startRow 还能借
					{
						figure.offsetY -= FIGURE_MOVING_SPANY;
						if(figure.offsetY < 0)
						{
							figure.startRow -=1;;
							figure.offsetY = TILE_SIZE_Y-FIGURE_MOVING_SPANY;
						}
					}else if(figure.offsetY > FIGURE_MOVING_SPANY)//格子数不够减了，但是偏移量还有
					{
						figure.offsetY -= FIGURE_MOVING_SPANY;
					}
				}
			break;
		}
	}
	/*
	 * 方法：检查是否需要拐弯,根据英雄的运动方向检查左右和前方3个格子是否可通过
	 * 如果多于一个格子可通过，则随机选取一个。最后返回一个方向赋值给英雄
	 */	
	public int checkIfTurn()
	{
		int [] directions = new int[3];//存放可选方向，最多3个，分别代表相对于当前方向的左、右和前
		int choices=0;//记录可选方向的个数
		int col = figure.col;
		int row = figure.row;
		Layer l = (Layer)gv.layerList.layers.get(0);//获得底层的图层
		MyDrawable[][] mapMatrix=l.getMapMatrix();
		if(mapMatrix[row][col].indext!=1)//泥土路
		{
			switch(figure.direction%4)
			{
				case 0://向下
					if(gv.notInMatrix[row][col-1] == 0&&mapMatrix[row][col-1].indext!=0)//检查左边是否可通过
					{
						directions[choices++] = 1;//设置为静态向左
					}
					if(gv.notInMatrix[row][col+1] == 0&&mapMatrix[row][col+1].indext!=0)//检查右边是否可通过
					{
						directions[choices++] = 2;//设置为静态向右
					}
					if(gv.notInMatrix[row+1][col] == 0&&mapMatrix[row+1][col].indext!=0)//检查下边是否可通过
					{
						directions[choices++] = 4;//设置为静态向下
					}
				break;
				case 1://向左
					if(gv.notInMatrix[row][col-1] == 0&&mapMatrix[row][col-1].indext!=0)//检查左边是否可通过
					{
						directions[choices++] = 1;//设置为静态向左
					}
					if(gv.notInMatrix[row-1][col] == 0&&mapMatrix[row-1][col].indext!=0)//检查上边是否可通过
					{
						directions[choices++] = 3;//设置为静态向上
					}
					if(gv.notInMatrix[row+1][col] == 0&&mapMatrix[row+1][col].indext!=0)//检查下边是否可通过
					{
						directions[choices++] = 4;//设置为静态向下
					}
				break;
				case 2://向右
					if(gv.notInMatrix[row][col+1] == 0&&mapMatrix[row][col+1].indext!=0)//检查右边是否可通过
					{
						directions[choices++] = 2;//设置为静态向右
					}
					if(gv.notInMatrix[row-1][col] == 0&&mapMatrix[row-1][col].indext!=0)//检查上边是否可通过
					{
						directions[choices++] = 3;//设置为静态向上
					}
					if(gv.notInMatrix[row+1][col] == 0&&mapMatrix[row+1][col].indext!=0)//检查下边是否可通过
					{
						directions[choices++] = 4;//设置为静态向下
					}
				break;
				case 3://向上
					if(gv.notInMatrix[row][col-1] == 0&&mapMatrix[row][col-1].indext!=0)//检查左边是否可通过
					{
						directions[choices++] = 1;//设置为静态向左
					}
					if(gv.notInMatrix[row][col+1] == 0&&mapMatrix[row][col+1].indext!=0)//检查右边是否可通过
					{
						directions[choices++] = 2;//设置为静态向右
					}
					if(gv.notInMatrix[row-1][col] == 0&&mapMatrix[row-1][col].indext!=0)//检查上边是否可通过
					{
						directions[choices++] = 3;//设置为静态向上
					}
				break;						
			}
		}else if(mapMatrix[row][col].indext==1)//水泥路
		{
			switch(figure.direction%4)
			{
				case 0://向下
					if(gv.notInMatrix[row][col-1] == 0&&mapMatrix[row][col-1].indext==1)//检查左边是否可通过
					{
						directions[choices++] = 1;//设置为静态向左
					}
					if(gv.notInMatrix[row][col+1] == 0&&mapMatrix[row][col+1].indext==1)//检查右边是否可通过
					{
						directions[choices++] = 2;//设置为静态向右
					}
					if(gv.notInMatrix[row+1][col] == 0&&mapMatrix[row+1][col].indext==1)//检查下边是否可通过
					{
						directions[choices++] = 4;//设置为静态向下
					}
				break;
				case 1://向左
					if(gv.notInMatrix[row][col-1] == 0&&mapMatrix[row][col-1].indext==1)//检查左边是否可通过
					{
						directions[choices++] = 1;//设置为静态向左
					}
					if(gv.notInMatrix[row-1][col] == 0&&mapMatrix[row-1][col].indext==1)//检查上边是否可通过
					{
						directions[choices++] = 3;//设置为静态向上
					}
					if(gv.notInMatrix[row+1][col] == 0&&mapMatrix[row+1][col].indext==1)//检查下边是否可通过
					{
						directions[choices++] = 4;//设置为静态向下
					}
				break;
				case 2://向右
					if(gv.notInMatrix[row][col+1] == 0&&mapMatrix[row][col+1].indext==1)//检查右边是否可通过
					{
						directions[choices++] = 2;//设置为静态向右
					}
					if(gv.notInMatrix[row-1][col] == 0&&mapMatrix[row-1][col].indext!=2)//检查上边是否可通过
					{
						directions[choices++] = 3;//设置为静态向上
					}
					if(gv.notInMatrix[row+1][col] == 0&&mapMatrix[row+1][col].indext==1)//检查下边是否可通过
					{
						directions[choices++] = 4;//设置为静态向下
					}
				break;
				case 3://向上
					if(gv.notInMatrix[row][col-1] == 0&&mapMatrix[row][col-1].indext==1)//检查左边是否可通过
					{
						directions[choices++] = 1;//设置为静态向左
					}
					if(gv.notInMatrix[row][col+1] == 0&&mapMatrix[row][col+1].indext==1)//检查右边是否可通过
					{
						directions[choices++] = 2;//设置为静态向右
					}
					if(gv.notInMatrix[row-1][col] == 0&&mapMatrix[row-1][col].indext==1)//检查上边是否可通过
					{
						directions[choices++] = 3;//设置为静态向上
					}
				break;						
			}
		}
		return directions[(int)(Math.random()*100)%choices];//从可选方向中随机选取一个返回
	}
	/*
	 * 该方法检测英雄停留的位置及其位置左右有没有什么可遇的东西
	 * 如土地，房子，商店等等
	 */
	public boolean checkIfMeet()
	{
		try//检测是否碰到障碍物或炸弹
		{
			for(Card cd:gv.card)
			{
				if(cd.row==figure.row&&cd.col==figure.col)
				{
					if(cd.indext==1)//炸弹
					{
						gv.card.remove(cd);
						gv.figure0.isZD=true;
						gv.figure0.zdDay=3;
						break;
					}else if(cd.indext==0)//障碍对应的对话框
					{
						gv.showDialog.initMessage(gv.figure0.figureName,gv.figure0.k,true,true,DialogEnum.MESSAGE_ONE,12,-1);
						gv.isDialog=true;//绘制对话框
						gv.card.remove(cd);
						break;
					}
				}
			}
		}catch(Exception e){}
		MyMeetableDrawable mmd = gv.meetableChecker.check(figure);
		figure.previousDrawable=null;
		if(mmd != null)
		{
			gv.setOnTouchListener(mmd);
			if(mmd.da==3)//所站位置的碰撞
			{
				if(figure.isDreaw)//人物在屏幕上时
				{
					gv.currentDrawable = mmd;
					OneMoreTimeFlag=true;
					gv.currentDrawable.accidentRandom=(int)(Math.random()*13);//机遇产生随机数
					gv.currentDrawable.figure0MagicRandom=(int)(Math.random()*6);//魔法屋产生随机数
					gv.currentDrawable.figure1MagicRandom=(int)(Math.random()*10);//魔法屋产生随机数
					return true;
				}
			}
			if((mmd.ss!=-1)&&(mmd.ss!=figure.ss))//上交过路费
			{
				if(gv.isDrawAlliance&&mmd.ss==UsedCard.currentFigure)//使用同盟卡
				{
					figure.father.gvt.setChanging(true);
					return true;
				}else
				{
					if(figure==gv.figure&&CrashFigure.isMeet1)
					{//玩家
						setMoney(mmd,figure.id);
					}else if(figure==gv.figure1&&CrashFigure.isMeet0)
					{
						setMoney(mmd,figure.id);
					}else if(figure==gv.figure2&&CrashFigure.isMeet)
					{
						setMoney(mmd,figure.id);
					}else 
					{
						setMoney(mmd,-1);
					}
				}
			}else if((mmd.ss==-1)||(mmd.ss==figure.ss))//土地的碰撞,如果碰到了可遇物
			{
				gv.currentDrawable = mmd;
				GroundDrawable.godId=figure.id;//获取特殊人物的id
				if(gv.currentDrawable.ss==-1)
				{
					gv.currentDrawable.ss=figure.ss;
					gv.currentDrawable.kk=figure.k;//设置人物标志
				}
				if(gv.currentDrawable.kk!=0)//该人物不为操作人物
				{
					if(mmd.da==1&&mmd.first)//该位置为大地图，随机选取要建筑的房子
					{
						mmd.zb=(int)(5*Math.random());
					}
				}
				return true;
			}			
		}
		return false;
	}
	public void setMoney(MyMeetableDrawable mmd,int id)//上交过路费
	{
		Figure figure=null;//人物
		int first=-1,second =-1;//信息索引值
		DialogEnum message = null;//信息类型
		if(mmd.ss==0)
		{//英雄
			figure=gv.figure;
		}else if(mmd.ss==1)
		{//人物1
			figure=gv.figure1;
		}else if(mmd.ss==2)
		{//人物2
			figure=gv.figure2;
		}
		if(figure.isDreaw&&mmd.ss!=-1)//人物在屏幕上时,收过路费
		{
			if(mmd.da==2)//小土地
			{
				first=22;//第一句话索引
				second=(int)(3*Math.random());//第二句话索引
				message=DialogEnum.MESSAGE_ONE;//信息类型
				gv.showDialog.value=mmd.value;
				scale=1;
			}else if(mmd.da==1)//大土地
			{
				if(mmd.zb==4&&mmd.k>=0)//旅店
				{
					first=25;//第一句话索引
					second=(int)(3*Math.random());//第二句话索引
					message=DialogEnum.MESSAGE_ONE;//信息类型
					gv.showDialog.isHotel=true;//住人旅店
					gv.showDialog.day=(int)(1+7*Math.random());//设置天数
					scale=gv.showDialog.day;
					gv.showDialog.value=mmd.value*gv.showDialog.day;//设置钱数
					gv.figure0.day=gv.showDialog.day;
				}else if(mmd.zb==3&&mmd.k>=0)//购物中心
				{
					first=26;//第一句话索引
					second=(int)(3*Math.random());//第二句话索引
					message=DialogEnum.MESSAGE_ONE;//信息类型
					gv.showDialog.day=(int)(1+7*Math.random());//设置天数
					scale=gv.showDialog.day;
					gv.showDialog.value=mmd.value*gv.showDialog.day;//设置钱数
				}else if(mmd.zb==1&&mmd.k==0)//加气站
				{
					first=27;//第一句话索引
					second=(int)(3*Math.random());//第二句话索引
					message=DialogEnum.MESSAGE_ONE;//信息类型
					gv.showDialog.day=(int)(1+5*Math.random());//设置天数
					scale=gv.showDialog.day;
					gv.showDialog.value=mmd.value*gv.showDialog.day;//设置钱数
				}
			}
			if(id==0)
			{//免收过路费
				gv.showDialog.three=0;//第一句话索引
				second=(int)(3+2*Math.random());//第二句话索引
				gv.showDialog.meet=true;//碰到大财神
				scale=0;
			}else if(id==2)
			{
				Room.changeRoom(gv.figure0, figure);//强占土地
				gv.showDialog.three=2;//第一句话索引
				second=(int)(3+2*Math.random());//第二句话索引
				gv.showDialog.meet=true;//碰到土地公公
				scale=1;
			}else if(id==3)
			{//1表示付一半金额
				gv.showDialog.three=3;//第一句话索引
				second=(int)(3+2*Math.random());//第二句话索引
				gv.showDialog.meet=true;//碰到小财神
				scale=0.5;
			}else if(id==4)
			{//小天使---加盖一层
				Room.addRoom(gv.figure0);//加盖一层
				gv.showDialog.three=4;//第一句话索引
				second=(int)(3+2*Math.random());//第二句话索引
				gv.showDialog.meet=true;//碰到小天使
				scale=1;
			}else if(id==6)
			{//2表示多付一半的金额
				gv.showDialog.three=6;//第一句话索引
				second=(int)(3*Math.random());//第二句话索引
				gv.showDialog.meet=true;//碰到小穷神
				scale=1.5;
			}else if(id==7)
			{//3表示多付一倍
				gv.showDialog.three=7;//第一句话索引
				second=(int)(3*Math.random());//第二句话索引
				gv.showDialog.meet=true;//碰到大穷神
				scale=2;
			}else if(id==10)
			{
				Room.cutRoom(gv.figure0);//摧毁一层
				gv.showDialog.three=10;//第一句话索引
				second=(int)(3*Math.random());//第二句话索引
				gv.showDialog.meet=true;//碰到小恶魔
				scale=1;
			}
		}else 
		{
			if(figure.isWhere==0)//人物住院中
			{
				first=23;//第一句话索引
				second=(int)(3+2*Math.random());//第二句话索引
				message=DialogEnum.MESSAGE_ONE;//信息类型
				scale=0;
			}else if(figure.isWhere==1)//人物入狱中
			{
				first=24;//第一句话索引
				second=(int)(3+2*Math.random());//第二句话索引
				message=DialogEnum.MESSAGE_ONE;//信息类型
				scale=0;
			}else if(figure.isWhere==2)//人物住店中
			{
				first=33;//第一句话索引
				second=(int)(3+2*Math.random());//第二句话索引
				message=DialogEnum.MESSAGE_ONE;//信息类型
				scale=0;
			}else if(figure.isWhere==4)//人物消失中
			{
				first=34;//第一句话索引
				second=(int)(3+2*Math.random());//第二句话索引
				message=DialogEnum.MESSAGE_ONE;//信息类型
				scale=0;
			}
		}
		if(first!=-1) 
		{
			figure.mhz.zMoney+=(mmd.value*scale);//土地拥有者收取过路费
			if(gv.figure0.mhz.zMoney>(mmd.value*scale))//总资产够减
			{
				if(gv.figure0.mhz.xMoney>(mmd.value*scale))//现金够减时
				{
					gv.figure0.mhz.xMoney-=(mmd.value*scale);//过路人上交过路费--现金
				}else if(gv.figure0.mhz.cMoney>(mmd.value*scale))//存款够减时
				{
					gv.figure0.mhz.cMoney-=(mmd.value*scale);//过路人上交过路费--存款
				}
				gv.figure0.mhz.zMoney-=(mmd.value*scale);//总资产减少
			}else//破产
			{
				gv.figure0.mhz.zMoney=0;
				gv.figure0.mhz.xMoney=0;
				gv.figure0.mhz.cMoney=0;
			}
			gv.showDialog.initMessage(figure.figureName,figure.k,true,false,message,first,second);
			gv.isDialog=true;//绘制对话框
			scale=-1;//恢复初始值
		}
	}
	//方法：判断是否碰撞神明
	public boolean isCrash(Figure fg,CrashFigure cf,GameView gv)
	{		
		if(figure.row==cf.x&&figure.col==cf.y)
		{
			idGod=cf.id;
			switch(idGod)
			{
			case 0://大财神
			case 3://小财神
				gv.aa=new CaiGodAnimation(gv);
				gv.aa.isAngel=true;//设置标志位为true
				gv.aa.startAnimation();//启动动画
				gv.isFigureMove=true;//播放特殊人物动画
				gv.aa.setClass(figure.figureFlag);//设置碰撞时的英雄类型：是玩家操控还是系统控制
				figure.id=cf.id;//记录碰撞神明的类型
				gv.aa.setBitmapnum(figure.id);//指定播放相应动画
				gv.temp=gv.date+days;//设置神明存活期
				gv.cfigure.remove(cf);
				return true;
			case 1://大福神
			case 5://小福神
				gv.aa=new FuGodAnimation(gv);
				gv.aa.isAngel=true;//设置标志位为true
				gv.aa.startAnimation();//启动动画
				gv.isFigureMove=true;//播放特殊人物动画
				gv.aa.setClass(figure.figureFlag);//设置碰撞时的英雄类型：是玩家操控还是系统控制
				figure.id=cf.id;//记录碰撞神明的类型
				gv.aa.setBitmapnum(figure.id);//指定播放相应动画
				gv.temp=gv.date+days;//设置神明存活期
				int tempcount=0;
				for(int i:this.gv.figure.CardNum)
				{
					if(i==-1)							
					{
						break;
					}
					tempcount++;
				}
				if(idGod==1)
				{//大福神
					if(tempcount<19)
					{
						for(int i=0;i<2;i++)
						{
							fg.CardNum[tempcount+i]=(int) (Math.random()*21);
						}
					}
				}
				else if(idGod==5)
				{//小福神
					if(tempcount<20)
					{
						fg.CardNum[tempcount]=(int) (Math.random()*21);
					}
				}
				gv.cfigure.remove(cf);
				return true;
			case 2://土地公
				gv.aa=new GroundGodAnimation(gv);
				gv.aa.isAngel=true;//设置标志位为true
				gv.aa.startAnimation();//启动动画
				gv.isFigureMove=true;//播放特殊人物动画
				gv.aa.setClass(figure.figureFlag);//设置碰撞时的英雄类型：是玩家操控还是系统控制
				figure.id=cf.id;//记录碰撞神明的类型
				gv.aa.setBitmapnum(figure.id);//指定播放相应动画
				gv.temp=gv.date+days;//设置神明存活期
				gv.cfigure.remove(cf);
				return true;
			case 4://小天使
				gv.aa=new AngelGodAnimation(gv);
				gv.aa.isAngel=true;//设置标志位为true
				gv.aa.startAnimation();//启动动画
				gv.isFigureMove=true;//播放特殊人物动画
				gv.aa.setClass(figure.figureFlag);//设置碰撞时的英雄类型：是玩家操控还是系统控制
				figure.id=cf.id;//记录碰撞神明的类型
				gv.aa.setBitmapnum(figure.id);//指定播放相应动画
				gv.temp=gv.date+days;//设置神明存活期
				gv.cfigure.remove(cf);
				return true;
			case 6://小穷神
			case 7://大穷神
				gv.aa=new QiongGodAnimation(gv);
				gv.aa.isAngel=true;//设置标志位为true
				gv.aa.startAnimation();//启动动画
				gv.isFigureMove=true;//播放特殊人物动画
				gv.aa.setClass(figure.figureFlag);//设置碰撞时的英雄类型：是玩家操控还是系统控制
				figure.id=cf.id;//记录碰撞神明的类型
				gv.aa.setBitmapnum(figure.id);//指定播放相应动画
				gv.temp=gv.date+days;//设置神明存活期
				gv.cfigure.remove(cf);
				return true;
			case 8://小衰神
			case 9://大衰神
				gv.aa=new ShuaiGodAnimation(gv);
				gv.aa.isAngel=true;//设置标志位为true
				gv.aa.startAnimation();//启动动画
				gv.isFigureMove=true;//播放特殊人物动画
				gv.aa.setClass(figure.figureFlag);//设置碰撞时的英雄类型：是玩家操控还是系统控制
				figure.id=cf.id;//记录碰撞神明的类型
				gv.aa.setBitmapnum(figure.id);//指定播放相应动画
				gv.temp=gv.date+days;//设置神明存活期
				int tempCount=0;//记录卡片张数
				if(idGod==9)
				{//大衰神
					for(int i:this.gv.figure.CardNum)//遗失两张卡片
					{
						if(i!=-1)
						{
							i=-1;
							tempCount++;
						}
						if(tempCount==2)
						{
							break;
						}
					}
				}
				else if(idGod==8)
				{//小衰神
					for(int i:this.gv.figure.CardNum)//遗失一张卡片
					{
						if(i!=-1)
						{
							i=-1;
							break;
						}
					}
				}		
				gv.cfigure.remove(cf);
				return true;
			case 10://小恶魔
				gv.aa=new BigEmGodAnimation(gv);
				gv.aa.isAngel=true;//设置标志位为true
				gv.aa.startAnimation();//启动动画
				gv.isFigureMove=true;//播放特殊人物动画
				gv.aa.setClass(figure.figureFlag);//设置碰撞时的英雄类型：是玩家操控还是系统控制
				figure.id=cf.id;//记录碰撞神明的类型
				gv.aa.setBitmapnum(figure.id);//指定播放相应动画
				gv.temp=gv.date+days;//设置神明存活期			
				gv.cfigure.remove(cf);
				return true;
			case 11://恶犬
				gv.aa=new DogGodAnimation(gv);
				gv.aa.isAngel=true;//设置标志位为true
				gv.aa.startAnimation();//启动动画	
				gv.isFigureMove=true;//播放特殊人物动画
				gv.aa.setClass(figure.figureFlag);//设置碰撞时的英雄类型：是玩家操控还是系统控制
				gv.aa.setXAndY(figure.topLeftCornerX, figure.topLeftCornerY);
				figure.id=cf.id;//记录碰撞神明的类型
				gv.aa.setBitmapnum(figure.id);//指定播放相应动画
				isDog=true;//碰到狗
				gv.cfigure.remove(cf);
				return true;
			}
		}
		return false;
	}
}