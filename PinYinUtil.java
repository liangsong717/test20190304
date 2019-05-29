package com.fineway.pinyinTest;

import org.junit.Test;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtil {
	
	//获取拼音
	public String getFullPinyin(String text) throws BadHanyuPinyinOutputFormatCombination {
		if(text==null) {return null;}
		char[] charArray = text.toCharArray();
		StringBuilder pinyin = new StringBuilder();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		//设置大小写格式 defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		//设置声调格式： defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < charArray.length; i++) {
			//匹配中文,非中文转换会转换成null
			if (Character.toString(charArray[i]).matches("[\\u4E00-\\u9FA5]+")) {
				String[] hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(charArray[i],defaultFormat);
				String string =hanyuPinyinStringArray[0]; pinyin.append(string);
			} else {
				pinyin.append(charArray[i]);
			}
		}
		//System.out.println(pinyin);
		return pinyin.toString();
	}

	//获取中文的首字母 
	@Test
	public String getFirstPinyin(String text) throws BadHanyuPinyinOutputFormatCombination {
		if(text==null) {return null;}
		char[] charArray = text.toCharArray();
		StringBuilder pinyin = new StringBuilder();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		// 设置大小写格式 defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		// 设置声调格式： defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < charArray.length; i++) {
			//匹配中文,非中文转换会转换成null
			if (Character.toString(charArray[i]).matches("[\\u4E00-\\u9FA5]+")) {
				String[] hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(charArray[i], defaultFormat);
				if (hanyuPinyinStringArray != null) {
					pinyin.append(hanyuPinyinStringArray[0].charAt(0));
				}
			}
		}
		//System.out.println(pinyin);
		return pinyin.toString();
	}

}
