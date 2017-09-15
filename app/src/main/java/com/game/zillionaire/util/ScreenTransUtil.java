package com.game.zillionaire.util;

public class ScreenTransUtil 
{
	//将x坐标从实际分辨率转化为标准分辨率
	public static int xFromRealToNorm(int x)
	{
		int xx=(int) (x/ConstantUtil.RADIO-ConstantUtil.LOX/ConstantUtil.RADIO);
		return xx;
	}
	//将y坐标从实际分辨率转化为标准分辨率
	public static int yFromRealToNorm(int y)
	{
		int yy=(int) (y/ConstantUtil.RADIO-ConstantUtil.LOY/ConstantUtil.RADIO);;
		return yy;
	}
}
