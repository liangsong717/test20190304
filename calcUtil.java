package com.fineway.calcTest;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 计算工具类
 * @author ls
 * 2019年5月27日下午5:39:23
 */
public class calcUtil {
	
	/**
	 * 除法 保留X位小数 四舍五入
	 * @param num1	被除数
	 * @param num2	除数
	 * @param scale	保留位数
	 * @return
	 */
	private BigDecimal divide(BigDecimal num1,BigDecimal num2 ,int scale){
		//BigDecimal result = num1.divide(num2, scale, RoundingMode.HALF_UP);
		if(num2.doubleValue()==0){return new BigDecimal(0);}
		return num1.divide(num2, scale, RoundingMode.HALF_UP);
	}

}
