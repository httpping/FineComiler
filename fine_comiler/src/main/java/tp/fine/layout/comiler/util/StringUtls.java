package tp.fine.layout.comiler.util;

import java.util.List;

public class StringUtls {

	/**
	 * 首字母大写
	 * @param fldName
	 * @return
	 */
	public static String  fristUper(String fldName) {
		String first = fldName.substring(0, 1).toUpperCase();
		String rest = fldName.substring(1, fldName.length());
		String newStr = new StringBuffer(first).append(rest).toString();
		return newStr;
	}
	
	
	/***
	 * 首字母 小写的 驼峰标识
	 * @param name
	 * @return
	 */
	public static String parseFristLowParamName(String name) {
		String[] strings = name.toLowerCase().split("_");
		String result = strings[0];
		if (strings.length > 1) {
			for (int i = 1; i < strings.length; i++) {
				String first = strings[i].substring(0, 1).toUpperCase();
				String rest = strings[i].substring(1, strings[i].length());
				String newStr = new StringBuffer(first).append(rest).toString();
				result += newStr;
			}
		}

		return result;
	}
	

	
	/***
	 * 首字母 大写的 驼峰标识
	 * @param name
	 * @return
	 */
	public static String parseFristUpdateParamName(String name) {
		String[] strings = name.toLowerCase().split("_");
		String result = fristUper(strings[0]);
		if (strings.length > 1) {
			for (int i = 1; i < strings.length; i++) {
				String first = strings[i].substring(0, 1).toUpperCase();
				String rest = strings[i].substring(1, strings[i].length());
				String newStr = new StringBuffer(first).append(rest).toString();
				result += newStr;
			}
		}

		return result;
	}




    public static boolean isEmpty(String str){
        return str == null || "".equals(str.trim());
    }

    public static boolean isNotEmpty(String str){
        return str != null && !str.trim().equals("") && !str.trim().equals("null");
    }

    public static boolean isEmpty(List list){
        return list == null || list.size() == 0;
    }
    public static boolean isNotEmpty(List list){
        return list != null && list.size() != 0;
    }
}
