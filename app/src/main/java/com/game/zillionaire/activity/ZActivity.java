package com.game.zillionaire.activity;

import com.game.zillionaire.map.GameData;
import com.game.zillionaire.map.GameData2;
import com.game.zillionaire.map.SerializableGame;
import com.game.zillionaire.menuview.HelpView;
import com.game.zillionaire.menuview.SetView;
import com.game.zillionaire.soundmanager.SoundManager;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.util.ScreenScaleResult;
import com.game.zillionaire.util.ScreenScaleUtil;
import com.game.zillionaire.view.ChooseView;
import com.game.zillionaire.view.GameMenuView;
import com.game.zillionaire.view.GameView;
import com.game.zillionaire.view.LoadingView;
import com.game.zillionaire.view.MenuView;
import com.game.zillionaire.view.SheduleChooseView;
import com.game.zillionaire.view.SoundView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class ZActivity extends Activity 
{
	LoadingView loadingView;//加载界面
	GameView gameView;//游戏主界面
	GameMenuView gameMenuView;//游戏菜单界面
	MenuView menuView;//菜单主界面 
	HelpView helpView;//帮助界面
	SetView setView;//设置界面
	ChooseView chooseView;//人物选择界面
	View currentView = null;//当前的View
	SoundView soundView;//刚开始时选择是否开启声音的界面
	public boolean isBackSound = true;//是否有背景声音
	public int soundCount=0;//音乐编号
	public SoundManager sm;//声音管理类
	public String[] musicPath={"midi01.mid","midi07.mid","rich08.mid","rich16.mid","rich20.mid"};//歌曲路径
	public String soundName=musicPath[0];//歌曲名
	public int peopleindext=0;//人物卡片索引
	public PicManager pic;
	public ScreenScaleResult screenScaleResult;
	SheduleChooseView shedulechooseView;
	//信息0为游戏菜单界面；信息1为游戏界面；信息2为帮助界面；信息3为菜单界面；信息4为设置界面；信息5为
	public Handler myHandler = new Handler()//用来更新UI线程中的控件
	{
        public void handleMessage(Message msg) 
        {
        	if(msg.what == 0)//进入游戏菜单界面
        	{
        		if(menuView!= null)
        		{
        			menuView.drawThread.setFlag(false);//停止线程
        			menuView = null;
        		}
        		if(chooseView!=null)
        		{
        			chooseView.drawThread.setFlag(false);//停止线程
        			chooseView=null;
        		}
        		initGameMenuView();//切换到游戏菜单界面
        	}else if(msg.what == 1)//进入游戏界面
        	{
        		if(gameMenuView!= null)
        		{
        			gameMenuView.drawThread.setFlag(false);//停止线程
        			gameMenuView = null;
        		}
        		if(chooseView!=null)
        		{
        			chooseView.drawThread.setFlag(false);//停止线程
        			chooseView=null;
        		}
        		initGameView();//切换到GameView
        	}else if(msg.what == 2)//进入帮助界面
        	{
        		if(menuView!= null)
        		{
        			menuView.drawThread.setFlag(false);//停止线程
        			menuView = null;
        		}
        		initHelpView();//切换到帮助界面
        	}else if(msg.what == 3)//进入菜单界面
        	{
        		if(helpView!= null)
        		{
        			helpView.drawThread.setFlag(false);//停止线程
        			helpView = null;
        		}
        		if(setView!= null)
        		{
        			setView.drawThread.setFlag(false);//停止线程
        			setView = null;
        		}
        		if(gameMenuView!= null)
        		{
        			gameMenuView.drawThread.setFlag(false);//停止线程
        			gameMenuView = null;
        		}
        		initMenuView();//切换到菜单界面
        	}else if(msg.what == 4)//进入设置界面
        	{
        		if(menuView!= null)
        		{
        			menuView.drawThread.setFlag(false);//停止线程
        			menuView = null;
        		}
        		initSetView();//切换到游戏菜单界面
        	}else if(msg.what == 5)//进入加载界面
        	{
        		if(soundView!= null)
        		{
        			soundView = null;
        		}
        		initLoadingView();//切换到加载界面
        	}else if(msg.what == 6)//进入人物选择界面
        	{
        		if(gameMenuView!= null)
        		{
        			gameMenuView.drawThread.setFlag(false);//停止线程
        			gameMenuView = null;
        		}
        		initChooseView();//切换到人物选择界面
        	}else if(msg.what == 7)//进入菜单界面
        	{
        		if(loadingView!= null)
        		{
        			loadingView.drawThread.setFlag(false);//停止线程
        			loadingView = null;
        		}
        		initMenuView();//切换到菜单界面
        	}else if(msg.what == 8)//点击选单后的界面
        	{//游戏失败，玩家飞机坠毁
        		if(gameView!= null)
        		{
        			gameView.drawThread.setFlag(false);//停止线程
        			gameView = null;
        			System.exit(0);
        		}
        	}else if(msg.what==9)//继续游戏
        	{
        		if(gameView==null)
				{
					gameView = new GameView(ZActivity.this);
				}
        		SerializableGame.loadSaveString(gameView);
        		if(ConstantUtil.SheduleChooseViewSelected>=0){
        			SerializableGame.loadingGameStatus(gameView);
        		}
        	}else if(msg.what==10)
        	{
       			if(gameView != null){
    				ZActivity.this.setContentView(gameView);
    			}
        	}else if(msg.what==11){//读取进度
        		ConstantUtil.MainMenuOrMenu=0;
        		if(gameView==null)
				{
					gameView = new GameView(ZActivity.this);
				}
        		SerializableGame.loadSaveString(gameView);
        		initSheduleChooseView(gameView);
        	}else if(msg.what==12){//点击存储后的界面
        		ConstantUtil.MainMenuOrMenu=1;
        		initSheduleChooseView(gameView);
        	}
        }
	};
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		//全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
				             WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//获取屏幕大小
		WindowManager windowManager = this.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		ConstantUtil.PMX1 = display.getWidth();
		ConstantUtil.PMY1 = display.getHeight();
		//屏幕自适应
		screenScaleResult = ScreenScaleUtil.calScale(ConstantUtil.PMX1,ConstantUtil.PMY1);
		//存储画布相关数据
		ConstantUtil.LOX = screenScaleResult.lucX;
		ConstantUtil.LOY = screenScaleResult.lucY;
		ConstantUtil.RADIO = screenScaleResult.ratio;
		
		soundView = new SoundView(this);
		sm=new SoundManager(this);//声音管理类
		this.setContentView(soundView);//先切到简单的声音开关界面
		//初始化底层地图数据
		GameData.resources=this.getResources();
		GameData.initMapImage();
		GameData.initMapData(this);
		//初始化上层地图数据
		GameData2.resources=this.getResources();
		GameData2.initBitmap();
		GameData2.initMapData(this);
	}
	public void initLoadingView()//加载界面
	{
		loadingView=new LoadingView(this);
		setContentView(loadingView);//跳到主加载界面
	}
	public void initMenuView()//菜单主界面
	{
		menuView = new MenuView(this);
      	setContentView(menuView);//跳到主界面
	}
	public void initHelpView()//帮助界面
	{
		helpView=new HelpView(this);
		setContentView(helpView);//跳到帮助界面
	}
	public void initSetView()//设置界面
	{
		setView=new SetView(this);
		setContentView(setView);//跳到设置界面
	}
	public void initGameMenuView()//游戏菜单主界面
	{
		gameMenuView = new GameMenuView(this);
      	setContentView(gameMenuView);//游戏菜单主界面
	}
	public void initChooseView()//人物选择界面
	{
		chooseView=new ChooseView(this);
		setContentView(chooseView);//人物选择界面
	}
	public void initGameView()//游戏主界面
	{
		gameView = new GameView(this);
		setContentView(gameView);//跳到主界面
	}
	public void initSheduleChooseView(GameView gameView){
		shedulechooseView=new SheduleChooseView(this,gameView);
		setContentView(shedulechooseView);
	}
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			if(gameView!=null) 
			{
				sm.mp.stop();
				sm.mp.release();
				gameView=null;
				this.finish();
				System.exit(0);
				return true;
			}
			if(menuView!=null)
			{
				menuView=null;
				this.finish();
				System.exit(0);
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
    }
}
