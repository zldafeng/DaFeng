package com.zl.dafeng.util;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.widget.EditText;

import com.zl.dafeng.ui.widgetview.CustomToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author yarine.gao
 * @date 2011-8-31
 * @version 1.0.0
 */
public class StringUtils {
	/**
	 * 判断字符串是否为"空格",""或null
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank("")        = true
	 * StringUtils.isBlank(" ")       = true
	 * StringUtils.isBlank("bob")     = false
	 * StringUtils.isBlank("  bob  ") = false
	 * @param cs
	 * @return
	 */
	public static boolean isBlank(CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(cs.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串是否不�?空格",""或null
	 * @see StringUtils#isBlank(CharSequence)
	 * @param cs
	 * @return
	 */
	public static boolean isNotBlank(CharSequence cs) {
		return !StringUtils.isBlank(cs);
	}

	/**
	 * 判断字符串是�?"或null
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty("")        = true
	 * StringUtils.isEmpty(" ")       = false
	 * StringUtils.isEmpty("bob")     = false
	 * StringUtils.isEmpty("  bob  ") = false
	 * @param cs
	 * @return
	 */
	public static boolean isEmpty(CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	/**
	 * 判断字符串是不为""或null
	 * @see StringUtils#isEmpty(CharSequence)
	 * @param cs
	 * @return
	 */
	public static boolean isNotEmpty(CharSequence cs) {
		return !StringUtils.isEmpty(cs);
	}

	/**
	 * 判断字符串是否只包含unicode数字
	 * StringUtils.isNumeric(null)   = false
	 * StringUtils.isNumeric("")     = false
	 * StringUtils.isNumeric("  ")   = false
	 * StringUtils.isNumeric("123")  = true
	 * StringUtils.isNumeric("12 3") = false
	 * StringUtils.isNumeric("ab2c") = false
	 * StringUtils.isNumeric("12-3") = false
	 * StringUtils.isNumeric("12.3") = false
	 * @param cs
	 * @return
	 */
	public static boolean isNumeric(CharSequence cs) {
		if (cs == null || cs.length() == 0) {
			return false;
		}
		int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 注意,只替换首字符replace("112341234", "1", "c") 结果�?c12341234
	 * @param line
	 * @param oldString
	 * @param newString
	 * @return
	 */
	public static final String replace(String line, String oldString,
                                       String newString) {
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			buf.append(line2, i, line2.length - i);
			return buf.toString();
		}
		return line;
	}

	public static int toInt(String str, int def) {

		if(str == null || isBlank(str) || !isNumeric(str)) {
			return def;
		}

		return Integer.valueOf(str);
	}

	public static byte[] getBytesUtf8(String string) {
		if (string == null) {
			return null;
		}

		try {
			return string.getBytes("UTF-8");
		} catch (Exception e) {
//            System.out.println(e);
			return null;
		}
	}
	public static String getSysTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" , Locale.getDefault());//设置日期格式
		return df.format(new Date());// new Date()为获取当前系统时�?
	}
	public static String toDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i< c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}if (c[i]> 65280&& c[i]< 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}
	public static String josnExist(JSONObject obj, String key){
		if(obj.has(key))
			try {
				if("null".equals( obj.getString(key))){
					return "";
				}else{
					return obj.getString(key);
				}

			} catch (JSONException e) {
				e.printStackTrace();
				return "";
			}

		return "";
	}
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	/**
	 * UTF-8 一个汉字占三个字节
	 * @param str 源字符串
	 * 转换成字节数组的字符串
	 * @return
	 */
	public static byte[] StringToByte(String str, String charEncode) {
		byte[] destObj = null;
		try {
			if(null == str || str.trim().equals("")){
				destObj = new byte[0];
				return destObj;
			}else{
				destObj = str.getBytes(charEncode);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return destObj;
	}

	/***
	 * MD5加密
	 * @param s
	 * @param charset
	 * @return
	 */
	public final static String MD5Encoder(String s, String charset) {
		try {
			byte[] btInput = s.getBytes(charset);
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < md.length; i++) {
				int val = ((int) md[i]) & 0xff;
				if (val < 16){
					sb.append("0");
				}
				sb.append(Integer.toHexString(val));
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}


	/**
	 * 判断两个String 是否相等， 如果都是null 也表示相等
	 *
	 * @param s1
	 * @param s2
	 * @return boolean
	 */
	public static boolean isEqual(String s1, String s2) {
		if (s1 == null) {
			s1 = "";
		}
		if (s2 == null) {
			s2 = "";
		}

		return (s1.equals(s2));
	}


	/**
	 * int 转换成String
	 *
	 * @param value
	 * @return String
	 */
	public static String parseInt(Integer value) {
		if (value == null) {
			return "";
		} else {
			return String.valueOf(value);
		}
	}

	public static String getString(String str){
		if(str==null){
			return "";
		}else {
			return str;
		}
	}

	/**
	 * 将阿拉伯数字转化成中文数字
	 * @param d
	 */
	public static String math2CHA(int d) {
//        String[] str = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String[] str = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
//        String ss[] = new String[] { "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿" };
		String ss[] = new String[] { "", "十", "百", "千", "万", "十", "百", "千", "亿" };
		String s = String.valueOf(d);
		System.out.println(s);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			String index = String.valueOf(s.charAt(i));
			sb = sb.append(str[Integer.parseInt(index)]);
		}
		String sss = String.valueOf(sb);
		int i = 0;
		for (int j = sss.length(); j > 0; j--) {
			sb = sb.insert(j, ss[i++]);
		}
		return sb.toString();
	}

	/** 设置EditText隐藏字体的大小*/
	public static void setHintSize(EditText editText, String msg){
		SpannableString ss = new SpannableString(msg);//定义hint的值
		AbsoluteSizeSpan ass = new AbsoluteSizeSpan(12,true);//设置字体大小 true表示单位是sp
		ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		editText.setHint(new SpannedString(ss));
	}

	/** 判断手机号码*/
	public static boolean checkPhone(Context context, String phoneNum){
		if (StringUtils.isEmpty(phoneNum)) {
			CustomToast.showShortToast(context,"请输入手机号");
			return false;
		}else if (!isMobileNO(phoneNum)) {
			CustomToast.showShortToast(context,"您输入的手机号不正确");
			return false;
		}else   {
			return true;
		}
	}

	/** 判断密码*/
	public static boolean checkPassword(Context context, String password){
		if (StringUtils.isEmpty(password)) {
			CustomToast.showShortToast(context,"请输入密码");
			return false;
		}else if(password.matches("[0-9a-zA-Z]{6,30}") == false){
			CustomToast.showShortToast(context,"请输入6-30位的密码");
			return false;
		}else {
			return true;
		}
	}

	/** 判断验证码*/
	public static boolean checkVerifyCode(Context context, String verify){
		if (StringUtils.isEmpty(verify)) {
			CustomToast.showShortToast(context,"请输入验证码");
			return false;
		}else {
			return true;
		}
	}

}
