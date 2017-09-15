package com.game.zillionaire.util;

import java.util.HashMap;

import android.graphics.Bitmap;

public class ConstantUtil 
{
	public static final int SCREEN_WIDTH = 480;//屏幕宽度    
	public static final int SCREEN_HEIGHT =854;//屏幕高度	

	//地图图元的大小
	public static final int TILE_SIZE_X=64;
	public static final int TILE_SIZE_Y=48;
	public static final int MAP_ROWS = 31;//地图有多少行
	public static final int MAP_COLS = 31;//地图有多少列
	//人物
	public static final int FIGURE_ANIMATION_SEGMENTS = 8;//英雄总共的动画段个数
	public static final int FIGURE_ANIMATION_FRAMES =2;//英雄每个动画段的总帧数
	public static final int FIGURE_WIDTH = 90;//英雄图片的宽度
	public static final int FIGURE_HEIGHT = 95;//英雄图片的高度
	public static final int FIGURE_ANIMATION_SLEEP_SPAN = 300;//英雄动画变换休眠的时间
	public static final int FIGURE_NO_ANIMATION_SLEEP_SPAN = 2000;//英雄没有动画变换时，换帧线程空转的睡眠时间
	public static final int FIGURE_MOVING_SPANX =3;//英雄走路的步进!!!!!!!!!注意，如果这个改了，那么无级滚屏那里也要检查需不需要该！！！！！
	public static final int FIGURE_MOVING_SPANY =3;//英雄走路的步进!!!!!!!!!注意，如果这个改了，那么无级滚屏那里也要检查需不需要该！！！！！
	public static final int FIGURE_MOVING_SLEEP_SPAN = 25;//英雄无级每走一小步休眠的时间
	public static final int FIGURE_WAIT_SPAN = 1000;//英雄没有在走，走路线程空转的等待时间
	public static final int UP = 7;//英雄的移动方向向上，也代表相应动画段
	public static final int DOWN = 4;//英雄的移动方向向下，也代表相应动画段
	public static final int LEFT = 5;//英雄的移动方向向左，也代表相应动画段
	public static final int RIGHT = 6;//英雄的移动方向向右，也代表相应动画段
	
	public static final int NUM_X=250;
	public static final int NUM_Y=100;
	public static final int NUM_WORDL=100;
	public static final int PERSON_TOTOP=10;//人物位置距最上方
	public static final int PERSON_TOLEFT=5;//人物位置距最左方
	//骰子
	public static final int Dice_ANIMATION_SEGMENTS=6;//骰子总共的动画段个数
	public static final int Dice_ANIMATION_FRAMES=5;//骰子每个动画段的总帧数
	public static final int Dice_WIDTH = 99;//骰子图片的宽度
	public static final int Dice_HEIGHT = 100;//骰子图片的高度	
	public static final int ROLL_SCREEN_SPACE_RIGHT =144;//英雄距屏幕右边界144个像素时就应该滚屏了480-(64*5+16)
	public static final int ROLL_SCREEN_SPACE_DOWN =502;//英雄距屏幕下边界216个像素时就应该滚屏了480-(31*8+16)
	public static final int ROLL_SCREEN_SPACE_LEFT =272;//英雄距屏幕左边界140个像素时就应该滚屏了31*4+16
	public static final int ROLL_SCREEN_SPACE_UP = 208;//英雄距屏幕上边界140个像素时就应该滚屏了31*4+16
	//对话框常量
	public static final int DIALOG_WORD_SIZE = 23;//对话框中文字的大小
	public static final int DIALOG_WORD_EACH_LINE = 8;//对话框每行的文字个数
	public static final int DIALOG_WORD_START_X = 24;//对话框中文字开始的x坐标
	public static final int DIALOG_WORD_START_Y = 390;//对话框文字开始的y坐标
	public static final int BANK_DIALOG_WORD_EACH_LINE = 7;//银行对话框每行的文字个数
    public static final int DIALOG_START_Y = 360;//游戏中对话框的绘制y坐标，x坐标为零	
	public static final int DIALOG_BTN_WORD_LEFT =35;//对话框中按钮上文字距左边的距离
	public static final int DIALOG_BTN_WORD_UP = 6;//对话框中按钮上文字距按钮上边沿的距离
	public static final int NEWS_Y =40;//新闻对话框距最上边的距离
	public static final int NEWS_X =170;//新闻对话框距最左边的距离
	public static final int NEWS_WORDX=250;
	public static final int NEWS_WORDY=330;
	public static final int NEWS_WORD_EACH_LINE=13;//新闻对话框里文字大小
	public static final int NEWS_DIALOG_WORD_SIZE =25;//对话框中文字的大小
	//碰撞显示对话框
	public static final int GOD_DIALOG_WORD_SIZE = 23;//对话框中文字的大小
	public static final int GOD_DIALOG_WORD_EACH_LINE =15;//对话框每行的文字个数
	public static final int GOD_DIALOG_WORD_START_X = 255;//对话框中文字开始的x坐标
	public static final int GOD_DIALOG_WORD_START_Y = 165;//对话框文字开始的y坐标
	//新闻
	public static final int NEWS_WORD_SIZE = 28;//对话框中文字的大小

	public static float UNIT_S=0.1f;
	//字符串转换方法
	public static String translateString(String  string)
	{
		String str=null;
		StringBuffer sbb=new StringBuffer(string.trim());
		if(sbb.length()<3)
		{//当长度小于3时直接返回
			str=string;
			return str;
		}//否则就添加“，”用来分割
		str=sbb.substring(sbb.length()-3, sbb.length());
		str=","+str;
		str=sbb.substring(0,sbb.length()-3)+str;
		sbb=null;
		return str;
	}
	//切割房子图片的坐标
	public static int Height0[][]={
		{0,60,130,207,298,394}, 
		{0,60,155,247,353,474},
		{0,50,137,236,346,457}
	};
	//切割房子的图片的高度
	public static int Height1[][]={
		{50,65,75,81,95,101},
		{50,77,89,97,106,112},
		{50,75,87,96,102,112}
	};

	//点数人物语句
	public static final String dialogMess[][]={
		{"呵呵，我好开心啦~~","我是不是在做梦啊？","太高兴了~"},
		{"感谢阿拉！！","哈哈~~","阿拉真主感谢您！"},{"今夜做梦也会笑~","我是女神~~"}};	

	

	//存放神明
	public static HashMap<Bitmap,Integer> hm;
	public static HashMap<Integer,Bitmap> hm1;
	//神明所用常量
	//切割碰撞人物图片的坐标
	public static int Height2[][]={
		{0,60,129,196,255,353},
		{0,57,130,184,242,355}
	};
	//切割碰撞人物的图片的高度
	public static int Height3[][]={
		{46,62,59,54,85,116},
		{54,68,49,52,112,86}
	};
	//切割碰撞人物图片的x坐标
	public static int  width0[][]={
		{0,116,240,355,475,592,720,920,1126}
	};
	//切割碰撞人物图片的宽度
	public static int  width1[][]={
		{116,124,110,120,117,128,200,206,162}
	};
	//切割特殊人物图片的高度(开始y坐标，图片大小)
	public static int[][] smgoddata={{5,50},{60,40},{115,47},{175,45},{230,52},
			{300,50},{355,48},{418,47},{480,40},{540,50},{603,50}};//特殊人物头像坐标
	/**
	 * card卡片类常量
	 */
	public static final int CARD_WIDTH = 373;//屏幕宽度    
	public static final int CARD_HEIGHT =500;//屏幕高度
	public static int CARDSELECTED_NUM=0;
	//卡片名称
	public static String[] CardsName={"天使卡","黑卡","路障","收税卡","免费卡","改建卡","恶魔卡","购地卡","怪兽卡","遥控骰子",
			"抢夺卡","机器娃娃","请神卡","红卡","送神卡","停留卡","同盟卡","乌龟卡","转向卡","陷害卡","定时炸弹"};
	//卡片功能简介
	public static String[] CardsFunctionIntro={"只要指定一处土地，整个路段的房屋都增盖一层。","股票上涨时用它可立即下跌。",
			"设路障使人停留在设路障处，可以挡自己也可以害别人。","可从指定对手的现金那里得到20%的税款，可以用免费、嫁祸卡解除。",
			"租金或罚金及缴税超过两千元或剩下的资金时，可抵用一次，免去费用。","变更住宅用地，让对手的旅馆或购物中心变成公园。",
			"可令整个路段的一排房子瞬间变为平地。","以市价强制收购别人的土地。",
			"彻底摧毁一栋建筑物，将它还原为平地的状态。","可以控制骰子的点数，决定前进步数。",
			"抢夺对手的卡片或道具。","机器人可以清除前方道路上的异物。",
			"立即请到身边距离最近的神仙。","股票下跌时用它可立即上涨。",
			"遇到坏神仙或定时炸弹马上送走。","指定自己或对手原地停走一次。",
			"同盟7天，相互不收过路费，与对手的房地产联合收费，得到的钱各算各的。","一次走一步，连续三天。",
			"使用后自己或对手立即掉头向后转。","使被选定的对手坐牢五天，丧失收租金的权利。",
			"走满38步后会自动爆炸，威力范围内车毁、屋塌、人住院五天。"};
	//卡片价格
	public static String[] CardsPrice={"点数：30","点数：30","点数：25","点数：35","点数：20","点数：25","点数：35","点数：25","点数：30","点数：25",
			"点数：30","点数：25","点数：35","点数：30","点数：35","点数：25","点数：35","点数：25","点数：30","点数：25","点数：30"};
	public static int[] CardsPriceInt={30,30,25,35,20,25,35,25,30,25,30,25,35,30,35,25,35,25,30,25,30};
	//切割恶魔图片的宽度
	public static int Width0[]={
		0,125,250,380,507,630,780,903,1040,1170,1300,1450,1600,1750
	};
	//切割选中骰子的宽度
	public static int Width1[]={
		3,101,200,300,399,498
	};

	/**
	 * 屏幕自适应所用常量
	 */
	public static int PMX1=0; //当前设备的宽高
	public static int PMY1=0;
	public static int LOX=0;  //屏幕左上角XY坐标
	public static int LOY=0;
	public static float RADIO=0;//缩放比例
	public static int PIX=30;
	
	
	public static int SheduleChooseViewSelected=-1;//选择个数
	public static int SheduleChooseViewSaved=-1;//存储个数
	public static int changeNum=-1;//存储覆盖量
	public static int MainMenuOrMenu=-1;//0--主菜单   1--游戏界面菜单
	public static String[] SaveString={"","",""};
	
	/**
	 * AllianceCard,GradCard,StopCard,TaxesCard,TrapCard,TurnCard
	 */
	public static final int AllianceCard_RecoverGame_Left_X=620;
	public static int AllianceCard_RecoverGame_Right_X=685;
	public static int AllianceCard_RecoverGame_Up_Y=180;
	public static int AllianceCard_RecoverGame_Down_Y=250;
	
	public static int AllianceCard_Count1_Left_X=450;
	public static int AllianceCard_Count1_Right_X=520;
	public static int AllianceCard_Count1_Up_Y=260;
	public static int AllianceCard_Count1_Down_Y=330;
	
	public static int AllianceCard_Count2_Figure1_Left_X=380;
	public static int AllianceCard_Count2_Figure1_Right_X=450;
	public static int AllianceCard_Count2_Figure1_Up_Y=260;
	public static int AllianceCard_Count2_Figure1_Down_Y=330;
	
	public static int AllianceCard_Count2_Figure2_Left_X=530;
	public static int AllianceCard_Count2_Figure2_Right_X=600;
	public static int AllianceCard_Count2_Figure2_Up_Y=260;
	public static int AllianceCard_Count2_Figure2_Down_Y=330;
	
	public static int AllianceCard_Count3_Figure1_Left_X=330;
	public static int AllianceCard_Count3_Figure1_Right_X=400;
	public static int AllianceCard_Count3_Figure1_Up_Y=230;
	public static int AllianceCard_Count3_Figure1_Down_Y=300;
	
	public static int AllianceCard_Count3_Figure2_Left_X=450;
	public static int AllianceCard_Count3_Figure2_Right_X=520;
	public static int AllianceCard_Count3_Figure2_Up_Y=260;
	public static int AllianceCard_Count3_Figure2_Down_Y=330;
	
	public static int AllianceCard_Count3_Figure3_Left_X=570;
	public static int AllianceCard_Count3_Figure3_Right_X=640;
	public static int AllianceCard_Count3_Figure3_Up_Y=260;
	public static int AllianceCard_Count3_Figure3_Down_Y=330;
	
	/**
	 * AngelCard,DemonCard,MonsterCard,RoadblockCard
	 */
	public static int AngelCard_NoUseCard_RecoverGame_Left_X=650;
	public static int AngelCard_NoUseCard_RecoverGame_Right_X=722;

	public static int AngelCard_UseCard_RecoverGame_Left_X=770;
	public static int AngelCard_UseCard_RecoverGame_Right_X=850;
	
	public static int AngelCard_RecoverGame_Up_Y=400;
	public static int AngelCard_RecoverGame_Down_Y=473;
	
	/**
	 * DiceCard
	 */
	public static int DiceCard_Step1_Left_X=143;
    public static int DiceCard_Step1_Right_X=240;
    
	public static int DiceCard_Step2_Left_X=243;
	public static int DiceCard_Step2_Right_X=340;
	
	public static int DiceCard_Step3_Left_X=343;
	public static int DiceCard_Step3_Right_X=440;
	
	public static int DiceCard_Step4_Left_X=443;
	public static int DiceCard_Step4_Right_X=540;
	
	public static int DiceCard_Step5_Left_X=543;
	public static int DiceCard_Step5_Right_X=640;
	
	public static int DiceCard_Step6_Left_X=643;
	public static int DiceCard_Step6_Right_X=740;
	
	public static int DiceCard_Step_Up_Y=121;
	public static int DiceCard_Step_Down_Y=220;
	
	/**
	 * RedCard
	 */
	public static int RedCard_RecoverGame_Left_X=710;
	public static int RedCard_RecoverGame_Right_X=770;
	public static int RedCard_RecoverGame_Top_Y=20;
	public static int RedCard_RecoverGame_Bottom_Y=50;
	
	public static int RedCard_UseCard_Left_X=570;
	public static int RedCard_UseCard_Right_X=670;
	public static int RedCard_UseCard_Top_Y=70;
	public static int RedCard_UseCard_Bottom_Y=100;
	
	public static int RedCard_SelectOneLine_Top_Y=200;
	public static int RedCard_SelectOneLine_Bottom_Y=280;
	public static int RedCard_SelectTwoLine_Top_Y=270;
	public static int RedCard_SelectTwoLine_Bottom_Y=350;
	public static int RedCard_SelectThreeLine_Top_Y=340;
	public static int RedCard_SelectThreeLine_Bottom_Y=420;
	public static int RedCard_SelectFourLine_Top_Y=420;
	public static int RedCard_SelectFourLine_Bottom_Y=500;
	
	/**
	 * UseCardView
	 */
	public static int UseCardView_CloseDialog_Left_X=650;
	public static int UseCardView_CloseDialog_Right_X=800;
	public static int UseCardView_CloseDialog_Up_Y=10;
	public static int UseCardView_CloseDialog_Down_Y=60;
	
	public static int UseCardView_UseThisCard_Left_X=400;
	public static int UseCardView_UseThisCard_Right_X=600;
	public static int UseCardView_UseThisCard_Up_Y=220;
	public static int UseCardView_UseThisCard_Down_Y=300;
	
	public static int UseCardView_Card1_Left_X=205;
	public static int UseCardView_Card1_Right_X=265;
	public static int UseCardView_Card2_Left_X=285;
	public static int UseCardView_Card2_Right_X=345;
	public static int UseCardView_Card3_Left_X=365;
	public static int UseCardView_Card3_Right_X=425;
	public static int UseCardView_Card4_Left_X=445;
	public static int UseCardView_Card4_Right_X=505;
	public static int UseCardView_Card5_Left_X=525;
	public static int UseCardView_Card5_Right_X=585;
	public static int UseCardView_Card6_Left_X=605;
	public static int UseCardView_Card6_Right_X=665;
	public static int UseCardView_Card_Up_Y=320;
	public static int UseCardView_Card_Down_Y=420;
	
	public static int UseCardView_CardGo_Left_X=165;
	public static int UseCardView_CardGo_Right_X=200;
	public static int UseCardView_CardBack_Left_X=680;
	public static int UseCardView_CardBack_Right_X=720;
	public static int UseCardView_CardMove_Up_Y=330;
	public static int UseCardView_CardMove_Down_Y=400;
	
	/**
	 * BankDrawable
	 */
	public static int BankDrawable_Slide_Left_X=240;
	public static int BankDrawable_Slide_Right_X=610;
	public static int BankDrawable_Slide_Up_Y=230;
	public static int BankDrawable_Slide_Down_Y=280;
	
	public static int BankDrawable_ATMView_Left_X=368;
	public static int BankDrawable_ATMView_Right_X=476;
	public static int BankDrawable_ATMView_Up_Y=361;
	public static int BankDrawable_ATMView_Down_Y=421;
	
	public static int BankDrawable_LoanOrRepaymentView_Left_X=240;
	public static int BankDrawable_LoanOrRepaymentView_Right_X=400;
	public static int BankDrawable_LoanOrRepaymentView_Up_Y=357;
	public static int BankDrawable_LoanOrRepaymentView_Down_Y=410;
	
	public static int BankDrawable_LoanOrRepaymentView_RecoverGame_Left_X=635;
	public static int BankDrawable_LoanOrRepaymentView_RecoverGame_Right_X=698;
	public static int BankDrawable_LoanView_ReturnToMainView_Left_X=648;
	public static int BankDrawable_LoanView_ReturnToMainView_Right_X=698;
	public static int BankDrawable_LoanOrRepaymentView_RecoverGame_Up_Y=25;
	public static int BankDrawable_LoanOrRepaymentView_RecoverGame_Down_Y=87;
	
	public static int BankDrawable_NumbericKeypad_left_X=420;
	public static int BankDrawable_NumbericKeypad_Right_X=670;
	public static int BankDrawable_NumbericKeypad_Up_Y=135;
	public static int BankDrawable_NumbericKeypad_Down_Y=394;
	//银行选项卡
	public static int[][] NUMBER=
	{
		{7,8,9},
		{4,5,6},
		{1,2,3},
		{10,0, 11}
	};
	
	/**
	 * CaiPiaoDrawable
	 */
	public static int CaiPiaoDrawable_RecoverGame_Left_X=694;
	public static int CaiPiaoDrawable_RecoverGame_Right_X=764;
	public static int CaiPiaoDrawable_RecoverGame_Up_Y=65;
	public static int CaiPiaoDrawable_RecoverGame_Down_Y=135;
	
	public static int CaiPiaoDrawable_Search_Left_X=235;
	public static int CaiPiaoDrawable_Search_Right_X=700;
	public static int CaiPiaoDrawable_Search_Up_Y=196;
	public static int CaiPiaoDrawable_Search_Down_Y=375;
	public static int[] CAIPIAO=
	{//彩票获奖金额
		1000,1500,2000,2500,3000
	};
	public static int[][] CAIPIAO_BIANHAO=
	{//彩票编号1--36
		{1,2,3,4,5,6,7,8,9},
		{10,11,12,13,14,15,16,17,18},
		{19,20,21,22,23,24,25,26,27},
		{28,29,30,31,32,33,34,35,36}
	};
	public static int[][] CAIPIAO_INT=
	{//彩票int的标志位
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0}
	};
	public static boolean[][] CAIPIAO_BOOL=
	{//彩票boolean 型的标志位
		{false,false,false,false,false,false,false,false,false},
		{false,false,false,false,false,false,false,false,false},
		{false,false,false,false,false,false,false,false,false},
		{false,false,false,false,false,false,false,false,false}
	};
    public static float[] vertex={0,0};//一维数组，用于记录x,y值
    public static int[] lowCol={0,0};//根据编号获得的行列值
    public static int[] lowCols={0,0};//根据触控点获得行列值
	
	/**
	 * CardDrawable
	 */
	public static int CardDrawable_CardSop_Col1_Left_X=220;
	public static int CardDrawable_CardSop_Col1_Right_X=270;
	public static int CardDrawable_CardSop_Col2_Left_X=300;
	public static int CardDrawable_CardSop_Col2_Right_X=350;
	public static int CardDrawable_CardSop_Col3_Left_X=380;
	public static int CardDrawable_CardSop_Col3_Right_X=430;
	public static int CardDrawable_CardSop_Col4_Left_X=460;
	public static int CardDrawable_CardSop_Col4_Right_X=510;
	public static int CardDrawable_CardSop_Row1_Up_Y=100;
	public static int CardDrawable_CardSop_Row1_Down_Y=160;
	public static int CardDrawable_CardSop_Row2_Up_Y=180;
	public static int CardDrawable_CardSop_Row2_Down_Y=240;
	public static int CardDrawable_CardSop_Row3_Up_Y=260;
	public static int CardDrawable_CardSop_Row3_Down_Y=320;
	
	public static int CardDrawable_HaveCard_Up_Y=370;
	public static int CardDrawable_HaveCard_Down_Y=430;
	public static int CardDrawable_HaveCardShow1_Left_X=110;
	public static int CardDrawable_HaveCardShow1_Right_X=165;
	public static int CardDrawable_HaveCardShow2_Left_X=180;
	public static int CardDrawable_HaveCardShow2_Right_X=235;
	public static int CardDrawable_HaveCardShow3_Left_X=250;
	public static int CardDrawable_HaveCardShow3_Right_X=305;
	public static int CardDrawable_HaveCardShow4_Left_X=320;
	public static int CardDrawable_HaveCardShow4_Right_X=375;
	public static int CardDrawable_HaveCardShow5_Left_X=390;
	public static int CardDrawable_HaveCardShow5_Right_X=445;
	
	public static int CardDrawable_BuyCardButton_left_X=630;
	public static int CardDrawable_BuyCardButton_Right_X=720;
	public static int CardDrawable_BuyCardButton_Up_Y=350;
	public static int CardDrawable_BuyCardButton_Down_Y=420;
	
	public static int CardDrawable_CheckCardGo_left_X=475;
	public static int CardDrawable_CheckCardGo_Right_X=515;
	public static int CardDrawable_CheckCardBack_left_X=80;
	public static int CardDrawable_CheckCardBack_Right_X=120;
	public static int CardDrawable_CheckCard_Up_Y=370;
	public static int CardDrawable_CheckCard_Down_Y=420;
	
	public static int CardDrawable_SaleCardButton_Left_X=540;
	public static int CardDrawable_SaleCardButton_Right_X=620;
	public static int CardDrawable_SaleCardButton_Up_Y=350;
	public static int CardDrawable_SaleCardButton_Down_Y=420;
	
	public static int CardDrawable_ExitCardShop_Left_X=670;
	public static int CardDrawable_ExitCardShop_Right_X=730;
	public static int CardDrawable_ExitCardShop_Up_Y=0;
	public static int CardDrawable_ExitCardShop_Down_Y=65;
	
	/**
	 * GroundDrawable
	 */
	public static int GroundDrawable_NoBuyGround_Left_X=630;
	public static int GroundDrawable_NoBuyGround_Right_X=700;
	public static int GroundDrawable_NoBuyGround_Up_Y=70;
	public static int GroundDrawable_NoBuyGround_Down_Y=140;
	
	public static int GroundDrawable_BuyGround_Left_X=500;
	public static int GroundDrawable_BuyGround_Right_X=610;
	public static int GroundDrawable_BuyGround_Up_Y=320;
	public static int GroundDrawable_BuyGround_Down_Y=370;
	
	public static int GroundDrawable_BuildHouse_Left_X=364;
	public static int GroundDrawable_BuildHouse_Right_X=477;
	public static int GroundDrawable_BuildHouse_Up_Y=340;
	public static int GroundDrawable_BuildHouse_Down_Y=394;

	public static int GroundDrawable_NoBuildHouse_Left_X=684;
	public static int GroundDrawable_NoBuildHouse_Right_X=754;
	public static int GroundDrawable_NoBuildHouse_Up_Y=66;
	public static int GroundDrawable_NoBuildHouse_Down_Y=136;
	
	public static int GroundDrawable_BuildPark_Left_X=101;
	public static int GroundDrawable_BuildPark_Right_X=245;
	public static int GroundDrawable_BuildJiaqizhan_Left_X=229;
	public static int GroundDrawable_BuildJiaqizhan_Right_X=353;
	public static int GroundDrawable_BuildKeyanshi_Left_X=357;
	public static int GroundDrawable_BuildKeyanshi_Right_X=481;
	public static int GroundDrawable_BuildShoppingCenter_Left_X=484;
	public static int GroundDrawable_BuildShoppingCenter_Right_X=608;
	public static int GroundDrawable_BuildHotel_Left_X=612;
	public static int GroundDrawable_BuildHotel_Right_X=736;
	public static int GroundDrawable_BuildThings_Up_Y=215;
	public static int GroundDrawable_BuildThings_Down_Y=335;
	
	
	/**
	 * MagicHouseDrawable
	 */
	public static int MagicHouseDrawable_Police_Left_X=380;
	public static int MagicHouseDrawable_Police_Right_X=470;
	public static int MagicHouseDrawable_Police_Up_Y=70;
	public static int MagicHouseDrawable_Police_Down_Y=140;
	
	public static int MagicHouseDrawable_Ambulance_Left_X=490;
	public static int MagicHouseDrawable_Ambulance_Right_X=570;
	public static int MagicHouseDrawable_Ambulance_Up_Y=80;
	public static int MagicHouseDrawable_Ambulance_Down_Y=140;
	
	public static int MagicHouseDrawable_BuildHouse_Left_X=520;
	public static int MagicHouseDrawable_BuildHouse_Right_X=580;
	public static int MagicHouseDrawable_BuildHouse_Up_Y=150;
	public static int MagicHouseDrawable_BuildHouse_Down_Y=230;
	
	public static int MagicHouseDrawable_ExcreteHouse_Left_X=530;
	public static int MagicHouseDrawable_ExcreteHouse_Right_X=620;
	public static int MagicHouseDrawable_ExcreteHouse_Up_Y=240;
	public static int MagicHouseDrawable_ExcreteHouse_Down_Y=330;
	
	public static int MagicHouseDrawable_Stayforatime_Left_X=480;
	public static int MagicHouseDrawable_Stayforatime_Right_X=530;
	public static int MagicHouseDrawable_Stayforatime_Up_Y=290;
	public static int MagicHouseDrawable_Stayforatime_Down_Y=370;
	
	public static int MagicHouseDrawable_TurnTo_Left_X=380;
	public static int MagicHouseDrawable_TurnTo_Right_X=460;
	public static int MagicHouseDrawable_TurnTo_Up_Y=340;
	public static int MagicHouseDrawable_TurnTo_Down_Y=420;
	
	public static int MagicHouseDrawable_BianSaleCard_Left_X=310;
	public static int MagicHouseDrawable_BianSaleCard_Right_X=380;
	public static int MagicHouseDrawable_BianSaleCard_Up_Y=300;
	public static int MagicHouseDrawable_BianSaleCard_Down_Y=370;
	
	public static int MagicHouseDrawable_PaiSaleCard_Left_X=230;
	public static int MagicHouseDrawable_PaiSaleCard_Right_X=290;
	public static int MagicHouseDrawable_PaiSaleCard_Up_Y=240;
	public static int MagicHouseDrawable_PaiSaleCard_Down_Y=330;
	
	public static int MagicHouseDrawable_Save_Left_X=250;
	public static int MagicHouseDrawable_Save_Right_X=330;
	public static int MagicHouseDrawable_Save_Up_Y=150;
	public static int MagicHouseDrawable_Save_Down_Y=230;
	
	public static int MagicHouseDrawable_Card_Left_X=290;
	public static int MagicHouseDrawable_Card_Right_X=340;
	public static int MagicHouseDrawable_Card_Up_Y=60;
	public static int MagicHouseDrawable_Card_Down_Y=140;
	/**
	 * OnSaleDrawable
	 */
	public static int OnSaleDrawable_ClickPass_Left_X=260;
	public static int OnSaleDrawable_ClickPass_Right_X=350;

	public static int OnSaleDrawable_Click1000_Left_X=360;
	public static int OnSaleDrawable_Click1000_Right_X=450;

	public static int OnSaleDrawable_Click100_Left_X=450;
	public static int OnSaleDrawable_Click100_Right_X=540;
	
	public static int OnSaleDrawable_Click50_Left_X=550;
	public static int OnSaleDrawable_Click50_Right_X=640;
	
	public static int OnSaleDrawable_ClickGiveUp_Left_X=650;
	public static int OnSaleDrawable_ClickGiveUp_Right_X=740;
	
	public static int OnSaleDrawable_Click_Up_Y=180;
	public static int OnSaleDrawable_Click_Down_Y=220;
	/**
	 * GameView
	 */
	public static int GameView_Go_Left_X=700;
	public static int GameView_Go_Right_X=860;
	public static int GameView_Go_Up_Y=320;
	public static int GameView_Go_Down_Y=482;	
	public static int GameView_TheIcon_Left_X=776;
	public static int GameView_TheIcon_Right_X=856;
	public static int GameView_TheFistIcon_Up_Y=10;
	public static int GameView_TheFistIcon_Down_Y=70;
	public static int GameView_TheSecondIcon_Up_Y=90;
	public static int GameView_TheSecondIcon_Down_Y=150;
	public static int GameView_TheThirdIcon_Up_Y=160;
	public static int GameView_TheThirdIcon_Down_Y=220;
	public static int GameView_TheFourthIcon_Up_Y=240;
	public static int GameView_TheFourthIcon_Down_Y=300;	
	public static int GameView_Save_Left_X=528;
	public static int GameView_Save_Right_X=628;
	public static int GameView_ChooseMenu_Left_X=640;
	public static int GameView_ChooseMenu_Right_X=740;
	public static int GameView_ReadShedule_Left_X=416;
	public static int GameView_ReadShedule_Right_X=516;
	public static int GameView_Up_Y=240;
	public static int GameView_Down_Y=300;	
	public static int GameView_XIcon_Left_X=700;
	public static int GameView_XIcon_Right_X=770;
	public static int GameView_XIcon_Up_Y=0;
	public static int GameView_XIcon_Down_Y=80;
	public static final int GAME_VIEW_SLEEP_SPAN = 100;//GameView界面刷帧线程睡眠时间
	public static final int GAME_VIEW_SCREEN_ROWS= 10;//GameView总共的行数
	public static final int GAME_VIEW_SCREEN_COLS =13;//GameView总共的列数
	
	/**
	 * HelpView
	 */
	public static int HelpView_ReturnMainMenu_Left_X=720;
	public static int HelpView_ReturnMainMenu_Right_X=790;
	public static int HelpView_ReturnMainMenu_Up_Y=0;
	public static int HelpView_ReturnMainMenu_Down_Y=70;
	
	public static int HelpView_System_Left_X=95;
	public static int HelpView_System_Right_X=260;

	public static int HelpView_SpecialFigure_Left_X=260;
	public static int HelpView_SpecialFigure_Right_X=428;
	
	public static int HelpView_Others_Left_X=430;
	public static int HelpView_Others_Right_X=596;
	
	public static int HelpView_Up_Y=78;
	public static int HelpView_Down_Y=120;
	
	public static int HelpView_OnTouchWord_Left_X=100;
	public static int HelpView_OnTouchWord_Right_X=760;
	public static int HelpView_OnTouchWord_Up_Y=120;
	public static int HelpView_OnTouchWord_Down_Y=457;
	/**
	 * SetView
	 */
	public static int SetView_ReturnMainMenu_Left_X=722;
	public static int SetView_ReturnMainMenu_Right_X=790;
	public static int SetView_ReturnMainMenu_Up_Y=0;
	public static int SetView_ReturnMainMenu_Down_Y=70;	
	public static int SetView_ChooseSongGo_Left_X=110;
	public static int SetView_ChooseSongGo_Right_X=130;	
	public static int SetView_ChooseSongBack_Left_X=385;
	public static int SetView_ChooseSongBack_Right_X=404;	
	public static int SetView_ChooseSong_Up_Y=164;
	public static int SetView_ChooseSong_Down_Y=207;	
	public static int SetView_MusicVolume_Left_X=526;
	public static int SetView_MusicVolume_Right_X=654;
	public static int SetView_MusicVolume_Up_Y=160;
	public static int SetView_MusicVolume_Down_Y=200;	
	public static int SetView_SetSoundEffect_Left_X=526;
	public static int SetView_SetSoundEffect_Right_X=654;
	public static int SetView_SetSoundEffect_Up_Y=270;
	public static int SetView_SetSoundEffect_Down_Y=310;	
	public static int SetView_SetSpeed_Left_X=526;
	public static int SetView_SetSpeed_Right_X=654;
	public static int SetView_SetSpeed_Up_Y=375;
	public static int SetView_SetSpeed_Down_Y=415;	
	public static int SetView_Reduce_Left_X=482;
	public static int SetView_Reduce_Right_X=523;
	public static int SetView_MusicVolumeReduce_Up_Y=160;
	public static int SetView_MusicVolumeReduce_Down_Y=200;
	public static int SetView_SetSoundEffectReduce_Up_Y=270;
	public static int SetView_SetSoundEffectReduce_Down_Y=310;
	public static int SetView_SetSpeedReduce_Up_Y=375;
	public static int SetView_SetSpeedReduce_Down_Y=415;	
	public static int SetView_Add_Left_X=657;
	public static int SetView_Add_Right_X=698;
	public static int SetView_MusicVolumeAdd_Up_Y=160;
	public static int SetView_MusicVolumeAdd_Down_Y=200;
	public static int SetView_SetSoundEffectAdd_Up_Y=270;
	public static int SetView_SetSoundEffectAdd_Down_Y=310;
	public static int SetView_SetSpeedAdd_Up_Y=375;
	public static int SetView_SetSpeedAdd_Down_Y=415;
	/**
	 * CheckFigureInfomation
	 */
	public static int CheckFigureInfomation_Close_Left_X=700;
	public static int CheckFigureInfomation_Close_Right_X=800;
	public static int CheckFigureInfomation_Close_Up_Y=10;
	public static int CheckFigureInfomation_Close_Down_Y=60;	
	public static int CheckFigureInfomation_ChooseFigure1_Left_X=220;
	public static int CheckFigureInfomation_ChooseFigure1_Right_X=320;
	public static int CheckFigureInfomation_ChooseFigure2_Left_X=380;
	public static int CheckFigureInfomation_ChooseFigure2_Right_X=480;
	public static int CheckFigureInfomation_ChooseFigure3_Left_X=520;
	public static int CheckFigureInfomation_ChooseFigure3_Right_X=650;
	public static int CheckFigureInfomation_ChooseFigure_Up_Y=20;
	public static int CheckFigureInfomation_ChooseFigure_Down_Y=100;	
	public static int CheckFigureInfomation_ChooseAndCheck_Left_X=100;
	public static int CheckFigureInfomation_ChooseAndCheck_Right_X=200;
	public static int CheckFigureInfomation_ChooseAndCheckMoney_Up_Y=150;
	public static int CheckFigureInfomation_ChooseAndCheckMoney_Down_Y=200;
	public static int CheckFigureInfomation_ChooseAndCheckGround_Up_Y=250;
	public static int CheckFigureInfomation_ChooseAndCheckGround_Down_Y=300;
	public static int CheckFigureInfomation_ChooseAndCheckStock_Up_Y=320;
	public static int CheckFigureInfomation_ChooseAndCheckStock_Down_Y=370;	
	public static int CheckFigureInfomation_ChooseAndCheckAllInfo_Up_Y=140;
	public static int CheckFigureInfomation_ChooseAndCheckAllInfo_Down_Y=200;
	public static int CheckFigureInfomation_AllGroundInfo_Left_X=370;
	public static int CheckFigureInfomation_AllGroundInfo_Right_X=470;	
	public static int CheckFigureInfomation_HouseGroundInfo_Left_X=500;
	public static int CheckFigureInfomation_HouseGroundInfo_Right_X=600;
	public static int CheckFigureInfomation_TradeGroundInfo_Left_X=620;
	public static int CheckFigureInfomation_TradeGroundInfo_Right_X=720;	
	public static int CheckFigureInfomation_CheckGroundInfo_Left_X=260;
	public static int CheckFigureInfomation_CheckGroundInfo_Right_X=700;
	public static int CheckFigureInfomation_CheckGroundInfo_Up_Y=250;
	public static int CheckFigureInfomation_CheckGroundInfo_Down_Y=400;	
	public static int CheckFigureInfomation_CheckStockInfo_Left_X=260;
	public static int CheckFigureInfomation_CheckStockInfo_Right_X=700;
	public static int CheckFigureInfomation_CheckStockInfo_Up_Y=225;
	public static int CheckFigureInfomation_CheckStockInfo_Down_Y=475;
	/**
	 * ChooseView
	 */
	public static int ChooseView_ReturnMenu_Left_X=70;
	public static int ChooseView_ReturnMenu_Right_X=136;
	public static int ChooseView_ReturnMenu_Up_Y=0;
	public static int ChooseView_ReturnMenu_Down_Y=70;	
	public static int ChooseView_EnterGame_Left_X=722;
	public static int ChooseView_EnterGame_Right_X=792;
	public static int ChooseView_EnterGame_Up_Y=0;
	public static int ChooseView_EnterGame_Down_Y=70;	
	public static int ChooseView_ChooseFigure1_Left_X=107;
	public static int ChooseView_ChooseFigure1_Right_X=187;
	public static int ChooseView_ChooseFigure1_Up_Y=366;
	public static int ChooseView_ChooseFigure1_Down_Y=446;	
	public static int ChooseView_ChooseFigure2_Left_X=259;
	public static int ChooseView_ChooseFigure2_Right_X=339;
	public static int ChooseView_ChooseFigure2_Up_Y=376;
	public static int ChooseView_ChooseFigure2_Down_Y=482;	
	public static int ChooseView_ChooseFigure3_Left_X=429;
	public static int ChooseView_ChooseFigure3_Right_X=541;
	public static int ChooseView_ChooseFigure3_Up_Y=369;
	public static int ChooseView_ChooseFigure3_Down_Y=481;
	/**
	 * GameMenuView
	 */
	public static int GameMenuView_RetunMainMenu_Left_X=63;
	public static int GameMenuView_RetunMainMenu_Right_X=138;
	public static int GameMenuView_RetunMainMenu_Up_Y=0;
	public static int GameMenuView_RetunMainMenu_Down_Y=73;	
	public static int GameMenuView_StartGame_Left_X=193;
	public static int GameMenuView_StartGame_Right_X=434;
	public static int GameMenuView_StartGame_Up_Y=120;
	public static int GameMenuView_StartGame_Down_Y=213;	
	public static int GameMenuView_ContinueGame_Left_X=500;
	public static int GameMenuView_ContinueGame_Right_X=690;
	public static int GameMenuView_ContinueGame_Up_Y=120;
	public static int GameMenuView_ContinueGame_Down_Y=213;	
	public static int GameMenuView_ReadShedule_Left_X=193;
	public static int GameMenuView_ReadShedule_Right_X=434;
	public static int GameMenuView_ReadShedule_Up_Y=286;
	public static int GameMenuView_ReadShedule_Down_Y=370;
	
	
	/**
	 * MenuView
	 */
	public static int MenuView_EnterGame_Left_X=181;
	public static int MenuView_EnterGame_Right_X=387;
	public static int MenuView_EnterGame_Up_Y=146;
	public static int MenuView_EnterGame_Down_Y=230;
	
	public static int MenuView_ExitGame_Left_X=724;
	public static int MenuView_ExitGame_Right_X=819;
	public static int MenuView_ExitGame_Up_Y=211;
	public static int MenuView_ExitGame_Down_Y=294;
	
	public static int MenuView_About_Left_X=718;
	public static int MenuView_About_Right_X=814;
	public static int MenuView_About_Up_Y=295;
	public static int MenuView_About_Down_Y=377;
	
	public static int MenuView_Set_Left_X=14;
	public static int MenuView_Set_Right_X=114;
	public static int MenuView_Set_Up_Y=383;
	public static int MenuView_Set_Down_Y=468;
	
	public static int MenuView_Help_Left_X=121;
	public static int MenuView_Help_Right_X=219;
	public static int MenuView_Help_Up_Y=383;
	public static int MenuView_Help_Down_Y=468;
	
	public static int MenuView_Music_Left_X=748;
	public static int MenuView_Music_Right_X=844;
	public static int MenuView_Music_Up_Y=386;
	public static int MenuView_Music_Down_Y=474;
	
	public static int MenuView_SmallAbout_Left_X=150;
	public static int MenuView_SmallAbout_Right_X=710;
	public static int MenuView_SmallAbout_Up_Y=110;
	public static int MenuView_SmallAbout_Down_Y=370;
	/**
	 * SharesView
	 */
	public static int SharesView_RetunMainMenu_Left_X=700;
	public static int SharesView_RetunMainMenu_Right_X=780;
	public static int SharesView_RetunMainMenu_Up_Y=0;
	public static int SharesView_RetunMainMenu_Down_Y=80;
	
	public static int SharesView_ShowHaveStockNum_Left_X=260;
	public static int SharesView_ShowHaveStockNum_Right_X=390;
	public static int SharesView_ShowHaveStockNum_Up_Y=60;
	public static int SharesView_ShowHaveStockNum_Down_Y=140;
	
	public static int SharesView_TurnToBuyView_Left_X=420;
	public static int SharesView_TurnToBuyView_Right_X=540;
	public static int SharesView_TurnToBuyView_Up_Y=60;
	public static int SharesView_TurnToBuyView_Down_Y=140;
	
	public static int SharesView_Sale_Left_X=568;
	public static int SharesView_Sale_Right_X=700;
	public static int SharesView_Sale_Up_Y=60;
	public static int SharesView_Sale_Down_Y=140;
	
	public static int SharesView_Slid1_Up_Y=200;
	public static int SharesView_Slid1_Down_Y=280;
	public static int SharesView_Slid2_Up_Y=270;
	public static int SharesView_Slid2_Down_Y=350;
	public static int SharesView_Slid3_Up_Y=340;
	public static int SharesView_Slid3_Down_Y=420;
	public static int SharesView_Slid4_Up_Y=420;
	public static int SharesView_Slid4_Down_Y=500;
	
	public static int SharesView_StarButton_Left_X=724;
	public static int SharesView_StarButton_Right_X=794;
	public static int SharesView_StarButton_Up_Y=0;
	public static int SharesView_StarButton_Down_Y=70;
	
	public static int SharesView_NumKeyboard_Left_X=420;
	public static int SharesView_NumKeyboard_Right_X=750;
	public static int SharesView_NumKeyboard_Up_Y=125;
	public static int SharesView_NumKeyboard_Down_Y=440;
	/**
	 * SheduleChooseView
	 */
	public static int SheduleChooseView_ReadShedule_Left_X=120;
	public static int SheduleChooseView_ReadShedule_Right_X=735;
	
	public static int SheduleChooseView_ReadShedule1_Up_Y=120;
	public static int SheduleChooseView_ReadShedule1_Down_Y=215;	
	public static int SheduleChooseView_ReadShedule2_Up_Y=230;
	public static int SheduleChooseView_ReadShedule2_Down_Y=320;
	public static int SheduleChooseView_ReadShedule3_Up_Y=335;
	public static int SheduleChooseView_ReadShedule3_Down_Y=430;
	
	public static int SheduleChooseView_ReturnMainMenu_Left_X=65;
	public static int SheduleChooseView_ReturnMainMenu_Right_X=135;
	public static int SheduleChooseView_ReturnMainMenu_Up_Y=0;
	public static int SheduleChooseView_ReturnMainMenu_Down_Y=70;
	
	public static int SheduleChooseView_YesFuGai_Left_X=325;
	public static int SheduleChooseView_YesFuGai_Right_X=395;
	public static int SheduleChooseView_YesFuGai_Up_Y=300;
	public static int SheduleChooseView_YesFuGai_Down_Y=440;
	
	public static int SheduleChooseView_NoFuGai_Left_X=550;
	public static int SheduleChooseView_NoFuGai_Right_X=635;
	public static int SheduleChooseView_NoFuGai_Up_Y=190;
	public static int SheduleChooseView_NoFuGai_Down_Y=275;
	/**
	 * SoundView
	 */
	public static int SoundView_YesButton_Left_X=8;
	public static int SoundView_YesButton_Right_X=120;
	public static int SoundView_NoButton_Left_X=739;
	public static int SoundView_NoButton_Right_X=850;
	public static int SoundView_Button_Up_Y=392;
	public static int SoundView_Button_Down_Y=441;
}
