package com.springboot.common.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Pattern;

public class StringUtil {
	public static final String CHARSET_NAME_UTF8 = "UTF-8";
	public static final char[] digital = "0123456789ABCDEF".toCharArray();

	public static String encodeHexStr(final byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		char[] result = new char[bytes.length * 2];
		for (int i = 0; i < bytes.length; i++) {
			result[i * 2] = digital[(bytes[i] & 0xf0) >> 4];
			result[i * 2 + 1] = digital[bytes[i] & 0x0f];
		}
		return new String(result);
	}

	/**
	 * Insert the method's description here. Creation date: (2/22/2002 11:21:30
	 * AM)
	 * 
	 * @return java.lang.String
	 * @param str
	 *            java.lang.String
	 */
	public static String filterString(String str) {
		if (str == null) {
			return "";
		}
		StringBuffer tempStr = new StringBuffer(str);
		int i;
		for (i = 0; i < tempStr.length(); i++) {
			if (tempStr.charAt(i) == '\'') {
				tempStr.insert(i, '\'');
				i++;
			}
		}
		return tempStr.toString();
	}

	/**
	 * Insert the method's description here. Creation date: (3/14/2003 1:39:40
	 * PM)
	 * 
	 * @return boolean
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.trim().equals(""));
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static Boolean isPassword(String password) {
		String parttern = "^[a-zA-Z0-9]{6,20}$";
		if (Pattern.matches(parttern, password)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/**
	 * Constructs an array of string token for the specified string. All
	 * characters in the <code>delim</code> argument are the delimiters for
	 * separating tokens.
	 * <p>
	 * If the <code>returnDelims</code> flag is <code>true</code>, then the
	 * delimiter characters are also returned as tokens. Each delimiter is
	 * returned as a string of length one. If the flag is <code>false</code>,
	 * the delimiter characters are skipped and only serve as separators between
	 * tokens.
	 * <p>
	 * Note that if <tt>delim</tt> is <tt>null</tt>, this constructor does not
	 * throw an exception. However, trying to invoke other methods on the
	 * resulting <tt>StringTokenizer</tt> may result in a
	 * <tt>NullPointerException</tt>.
	 * 
	 * @param str
	 *            a string to be parsed.
	 * @param delim
	 *            the delimiters.
	 * @param returnDelims
	 *            flag indicating whether to return the delimiters as tokens.
	 * @return String[]
	 */
	public static String[] stringToArray(String str, String delim, boolean returnDelims) {
		String[] result = null;
		if (str == null || "".equals(str)) {
			return new String[0];
		}
		StringTokenizer st = new StringTokenizer(str, delim, returnDelims);
		int attCnt = st.countTokens();
		if (attCnt > 0) {
			result = new String[attCnt];
		}
		int i = 0;
		while (st.hasMoreTokens()) {
			result[i] = (String) st.nextToken();
			i++;
		}
		return result;
	}

	/**
	 * Constructs an array of string token for the specified string. The
	 * characters in the <code>delim</code> argument are the delimiters for
	 * separating tokens. Delimiter characters themselves will not be treated as
	 * tokens.
	 * 
	 * @param str
	 *            a string to be parsed.
	 * @param delim
	 *            the delimiters.
	 * @return String[]
	 */
	public static String[] stringToArray(String str, String delim) {
		return stringToArray(str, delim, false);
	}

	/**
	 * Constructs a vector of string token for the specified string. All
	 * characters in the <code>delim</code> argument are the delimiters for
	 * separating tokens.
	 * 
	 * <p>
	 * If the <code>returnDelims</code> flag is <code>true</code>, then the
	 * delimiter characters are also returned as tokens. Each delimiter is
	 * returned as a string of length one. If the flag is <code>false</code>,
	 * the delimiter characters are skipped and only serve as separators between
	 * tokens.
	 * <p>
	 * Note that if <tt>delim</tt> is <tt>null</tt>, this constructor does not
	 * throw an exception. However, trying to invoke other methods on the
	 * resulting <tt>StringTokenizer</tt> may result in a
	 * <tt>NullPointerException</tt>.
	 * 
	 * @param str
	 *            a string to be parsed.
	 * @param delim
	 *            the delimiters.
	 * @param returnDelims
	 *            flag indicating whether to return the delimiters as tokens.
	 * 
	 * @return Vector
	 */
	public static Vector stringToVector(String str, String delim, boolean returnDelims) {
		Vector v = new Vector();
		if (str == null) {
			return v;
		}
		StringTokenizer st = new StringTokenizer(str, delim, returnDelims);
		while (st.hasMoreTokens()) {
			v.add(st.nextToken());
		}
		return v;
	}

	/**
	 * Constructs a vector of string token for the specified string. The
	 * characters in the <code>delim</code> argument are the delimiters for
	 * separating tokens. Delimiter characters themselves will not be treated as
	 * tokens.
	 * 
	 * @param str
	 *            a string to be parsed.
	 * @param delim
	 *            the delimiters.
	 * @return Vector
	 */
	public static Vector stringToVector(String str, String delim) {
		return stringToVector(str, delim, false);
	}

	/**
	 * Constructs a list of string token for the specified string. All
	 * characters in the <code>delim</code> argument are the delimiters for
	 * separating tokens.
	 * 
	 * <p>
	 * If the <code>returnDelims</code> flag is <code>true</code>, then the
	 * delimiter characters are also returned as tokens. Each delimiter is
	 * returned as a string of length one. If the flag is <code>false</code>,
	 * the delimiter characters are skipped and only serve as separators between
	 * tokens.
	 * <p>
	 * Note that if <tt>delim</tt> is <tt>null</tt>, this constructor does not
	 * throw an exception. However, trying to invoke other methods on the
	 * resulting <tt>StringTokenizer</tt> may result in a
	 * <tt>NullPointerException</tt>.
	 * 
	 * @param str
	 *            a string to be parsed.
	 * @param delim
	 *            the delimiters.
	 * @param returnDelims
	 *            flag indicating whether to return the delimiters as tokens.
	 * 
	 * @return List
	 */
	public static List stringToList(String str, String delim, boolean returnDelims) {
		List v = new Vector();
		if (str == null) {
			return v;
		}
		StringTokenizer st = new StringTokenizer(str, delim);
		while (st.hasMoreTokens()) {
			String temp = st.nextToken().trim();
			if (!isEmpty(temp)) {
				v.add(temp);
			}
			// v.add(st.nextToken());
		}

		return v;
	}

	/**
	 * Constructs a list of string token for the specified string. The
	 * characters in the <code>delim</code> argument are the delimiters for
	 * separating tokens. Delimiter characters themselves will not be treated as
	 * tokens.
	 * 
	 * @param str
	 *            a string to be parsed.
	 * @param delim
	 *            the delimiters.
	 * @return List
	 */
	public static List stringToList(String str, String delim) {
		return stringToList(str, delim, false);
	}

	public static String trimString(String str) {
		return (null == str) ? "" : str.trim();
	}

	/**
	 * Insert the method's description here. Creation date: (3/10/2003 3:44:30
	 * PM)
	 * 
	 * @return java.lang.String
	 * @param v
	 *            java.util.Vector
	 * @param delim
	 *            java.lang.String
	 */
	public static String listToString(List v, String delim) {
		String result;
		if (v == null || v.isEmpty()) {
			result = "";
		} else {
			StringBuffer sb = new StringBuffer();
			Iterator i = v.iterator();
			// boolean first = true;
			while (i.hasNext()) {
				String temp = (String) i.next();
				if (temp != null) {
					sb.append(temp).append(delim);
				}
			}
			result = sb.toString();
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	/**
	 * Convert String to UTF encoding
	 * 
	 * @param String
	 * @return String in UTF
	 */
	public static String getBytes(String name) throws UnsupportedEncodingException {
		String utfname = null;
		try {
			utfname = new String(name.getBytes("8859_1"), "UTF8");
		} catch (UnsupportedEncodingException e) {
			// utfname = name;
			throw e;
		}
		return utfname;
	}

	/**
	 * Convert String array to UTF encoding
	 * 
	 * @param String
	 *            array
	 * @return String array in UTF
	 */
	public static String[] getBytes(String[] name) throws UnsupportedEncodingException {
		String[] utfname = new String[name.length];
		try {
			for (int i = 0; i < name.length; i++) {
				utfname[i] = new String(name[i].getBytes("8859_1"), "UTF8");

			}
		} catch (UnsupportedEncodingException e) {
			// utfname = name;
			throw e;
		}
		return utfname;
	}

	/**
	 * Insert the method's description here. Creation date: (3/10/2003 3:44:30
	 * PM)
	 * 
	 * @return java.lang.String
	 * @param v
	 *            java.util.Vector
	 * @param delim
	 *            java.lang.String
	 */
	public static String arrayToString(String[] s, String delim) {
		String result;
		if (s == null) {
			result = "";
		} else {
			StringBuffer sb = new StringBuffer();
			int i = 0;
			int len = s.length;
			boolean first = true;
			while (i < len) {
				if (!first) {
					sb.append(delim);
				} else {
					first = false;
				}
				sb.append(s[i]);
				i++;
			}
			result = sb.toString();
		}
		return result;
	}

	/**
	 * Insert the method's description here. Creation date: (3/10/2003 3:44:30
	 * PM)
	 * 
	 * @return java.lang.String
	 * @param v
	 *            java.util.Vector
	 * @param delim
	 *            java.lang.String
	 */
	public static String vectorToString(Vector v, String delim) {
		String result;
		if (v == null || v.isEmpty()) {
			result = "";
		} else {
			StringBuffer sb = new StringBuffer();
			Iterator i = v.iterator();
			boolean first = true;
			while (i.hasNext()) {
				if (!first) {
					sb.append(delim);
				} else {
					first = false;
				}
				sb.append(i.next());
			}
			result = sb.toString();
		}
		return result;
	}

	public static String vectorToStringForSql(Vector v) {
		String result;
		if (v == null || v.isEmpty()) {
			result = "";
		} else {
			StringBuffer sb = new StringBuffer();
			Iterator i = v.iterator();
			boolean first = true;

			boolean isString;
			Object o = v.get(0);
			if (o instanceof String) {
				isString = true;
			} else {
				isString = false;
			}

			while (i.hasNext()) {
				if (!first) {
					sb.append(",");
				} else {
					first = false;
				}
				if (isString) {
					sb.append("'").append(i.next()).append("'");
				} else {
					sb.append(i.next());
				}

			}
			result = sb.toString();
		}
		return result;
	}

	public static String wrapParagraph(String input, int requiredLength) {
		StringBuffer result = new StringBuffer();
		int startPos = 0;
		int index = 0;
		while ((index = input.indexOf('\n', startPos)) != -1) {
			result.append(wrapLine(input.substring(startPos, index).trim(), requiredLength));
			startPos = index + 1;
		}
		result.append(wrapLine(input.substring(startPos), requiredLength));
		return result.toString();
	}

	public static String wrapLine(String input, int requiredLength) {
		StringBuffer result = new StringBuffer();
		int length = requiredLength;
		int lastBeginPos = 0;

		while (length < input.length() - 1) {
			char c = input.charAt(length);
			if (c != '\t' && c != ' ') {
				int check = length;
				length = getLatestTokenPosition(input, length);
				if (length == -1 || (check - length >= requiredLength)) {
					if (length == -1) {
						length = getFirstTokenPosition(input, length);
					} else {
						length = getFirstTokenPosition(input, check);
					}
				}
				if (length == Integer.MAX_VALUE) {
					length = input.length();
				}
			}
			String seg = input.substring(lastBeginPos, length).trim();
			if (seg.length() > 0) {
				result.append(seg).append('\n');
			}
			lastBeginPos = length;
			length += requiredLength;
		}
		String seg = input.substring(lastBeginPos, input.length()).trim();
		if (seg.length() > 0) {
			result.append(seg).append('\n');
		}
		return result.toString();
	}

	private static int getLatestTokenPosition(String input, int length) {
		int pos1 = input.lastIndexOf('\t', length);
		int pos2 = input.lastIndexOf(' ', length);
		return pos1 >= pos2 ? pos1 : pos2;
	}

	private static int getFirstTokenPosition(String input, int length) {
		int pos1 = input.indexOf('\t', length);
		int pos2 = input.indexOf(' ', length);
		pos1 = pos1 == -1 ? Integer.MAX_VALUE : pos1;
		pos2 = pos2 == -1 ? Integer.MAX_VALUE : pos2;

		return pos1 <= pos2 ? pos1 : pos2;
	}

	/**
	 * lPadString returns String, left-padded to length <i>n</i> with <i>s</i>,
	 * replicated as many times as necessary; If <i>input</i> is longer than
	 * <i>length</i>, then this method returns the portion of <i>input</i> that
	 * fits in <i>length</i>. (Similar to Oracle lpad)
	 */
	public static String lPadString(String input, String s, int length) {
		if (input == null) {
			return input;
		} else if (input.length() >= length) {
			return input.substring(0, length);
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length - input.length(); i++) {
			sb.append(s);
			if (sb.length() >= (length - input.length())) {
				break;
			}
		}
		sb = new StringBuffer(sb.substring(0, length - input.length()));
		return sb.append(input).toString();
	}

	/**
	 * rPadString returns String, right-padded to length <i>n</i> with <i>s</i>,
	 * replicated as many times as necessary; If <i>input</i> is longer than
	 * <i>length</i>, then this method returns the portion of <i>input</i> that
	 * fits in <i>length</i>. (Similar to Oracle rpad)
	 */
	public static String rPadString(String input, String s, int length) {
		if (input == null) {
			return input;
		} else if (input.length() >= length) {
			return input.substring(0, length);
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length - input.length(); i++) {
			sb.append(s);
			if (sb.length() >= (length - input.length())) {
				break;
			}
		}
		return sb.insert(0, input).substring(0, length).toString();
	}

	/**
	 * @return java.lang.String
	 * @param d
	 *            Double
	 * @param s
	 *            Integer
	 */
	public static String doubletostr(Double d, Integer s) {
		if (d == null) {
			return "";
		}
		String pattern = rPadString("0.", "0", 2 + s.intValue());
		if (s.intValue() == 0) {
			pattern = "0";
		}
		NumberFormat nf = new DecimalFormat(pattern);
		return nf.format(d);
	}

	public static String replaceString(String original, String toReplace, String replacement) {

		String newString = original;

		int index = 0;
		int count = 0;
		while (index != -1) {
			index = newString.indexOf(toReplace, count);
			if (index != -1) {
				if (index == 0) {
					newString = replacement + newString.substring(toReplace.length());
				} else {
					newString = newString.substring(0, index) + replacement
							+ newString.substring(index + toReplace.length());

				}
				count = index - toReplace.length() + replacement.length() + 1;

			}
		}
		return newString;

	}

	public static boolean isInt(String str) {
		str = str.trim();
		try {
			Integer int_num = new Integer(0);
			int int_out = int_num.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static boolean compareStringWithEmpty(String source1, String source2) {
		if (isEmpty(source1) && isEmpty(source2)) {
			return true;
		}
		if (!isEmpty(source1) && !isEmpty(source1)) {
			return source1.equals(source2);
		} else {
			return false;
		}
	}

	public static boolean isEmail(String email) {
		if (isEmpty(email)) {
			return false;
		}
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		return Pattern.matches(emailPattern, email);
	}

	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static String fuzzyString(String input) {
		String regex = "(\\S{1,2})(\\S{1,3})(\\S+)";
		return input.replaceAll(regex, "$1***$3");
	}

	public static byte[] toBytes(final String str) {
		if (str == null) {
			return null;
		}
		try {
			return str.getBytes(CHARSET_NAME_UTF8);
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/* 随机生成字母数字组合 */
	public static String getCharAndNumr(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) { // 字符串
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

	/**
	 * 变量夹下划线
	 *
	 * @param srcStr
	 * @return
	 */
	public static String convertStringAddChar(String srcStr,String replaceChar) {
		StringBuffer resultBuffer=new StringBuffer();
		for(int i=0;i<srcStr.length();i++){
			char charStr=srcStr.charAt(i);
			int  ascChar=srcStr.charAt(i);
			if(ascChar>=65 && ascChar<=90){
				ascChar=ascChar+32;
				charStr=(char)ascChar;
				resultBuffer.append(replaceChar);
				resultBuffer.append(charStr);
			}else{
				resultBuffer.append(charStr);
			}
		}
		return resultBuffer.toString();
	}

	/**
	 * 第一个字符大写转换
	 *
	 * @param srcStr
	 * @return
	 */
	public static String firstCharacterToUpper(String srcStr) {
		return srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);
	}
	/**
	 * 将字符转换成去掉下划线并首字母大写
	 * @param srcStr
	 * @param org
	 * @param ob
	 * @return
	 */
	public static String replaceUnderlineAndfirstToUpper(String srcStr,String org,String ob)
	{
		String newString = "";
		srcStr=srcStr.toLowerCase();
		int first=0;
		while(srcStr.indexOf(org)!=-1)
		{
			first=srcStr.indexOf(org);
			if(first!=srcStr.length())
			{
				newString=newString+srcStr.substring(0,first)+ob;
				srcStr=srcStr.substring(first+org.length(),srcStr.length());
				srcStr=firstCharacterToUpper(srcStr);
			}
		}
		newString=newString+srcStr;
		return newString;
	}
	public static String generUUId(){
		String uid=UUID.randomUUID().toString();
		String radomId= uid.replace("-","");
		return radomId;
	}
}
