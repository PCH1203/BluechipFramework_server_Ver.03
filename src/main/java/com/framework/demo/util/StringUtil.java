package com.framework.demo.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

/**
 * 스트링 데이터를 다루기 위한 유틸리티 클래스
 */
public class StringUtil {
    /**
     * 맵에서 키에 해당하는 String 값을 반환한다.
     *
     * @param data
     *            데이터맵
     * @param key
     *            키값
     * @param defaultValue
     *            키값의 String 값이 존재하지 않을 경우에 반환하는 기본값
     * @return 키값에 해당하는 String 값 or 기본값
     */
    public static String getString(Map<String, Object> data, String key, String defaultValue) {
        if (data == null) {
            return defaultValue;
        }
        Object obj = data.get(key);
        if (obj == null) {
            return defaultValue;
        }
        if (obj instanceof String) {
            return ((String) obj).trim();
        } else {
            return obj.toString();
        }
    }

    /**
     * 맵에서 키에 해당하는 String 값을 반환한다. 키에 해당하는 값이 존재하지 않을 경우는 널스트링("")을 반환한다.
     *
     * @param data
     *            데이터맵
     * @param key
     *            키값
     * @return 키값에 해당하는 String 값 or 널스트링
     */
    public static String getString(Map<String, Object> data, String key) {
        return StringUtil.getString(data, key, "");
    }

    /**
     * 주어진 길이보다 길이가 작은 문자열을 앞에 0을 붙여 패딩한다 <BR>
     *
     * @param str
     *            문자열
     * @param len
     *            길이
     * @return 앞에 '0'으로 패딩된 문자열을 리턴한다. 단, 주어진 길이보다 크거나 같으면 원본문자열을 그대로 리턴한다
     */
    public static String paddingZero(String str, int len) {
        int strLen = str.length();
        int cab = 0;
        String tmp = "";

        if (strLen >= len)
            return str;
        else
            cab = len - strLen;

        for (int ii = 0; ii < cab; ii++) {
            tmp = "0" + tmp;
        }

        return tmp + str;
    }

    /**
     * 첫문자를 대문자로 바꿈 <BR>
     *
     * @param str
     *            입력문자열
     * @return 변환된 문자열을 리턴한다
     */
    public static String initCap(String str) {
        // A: 65, a: 97
        // 소문자일 경우 대문자로 변경
        String second = str.substring(1);
        char h = str.charAt(0);
        if (h >= 'a' && h <= 'z') {
            String first = String.valueOf((char) (h - 32));
            return first + second;
        } else
            return str;
    }

    /**
     * 원본문자열(str)에서 캐릭터(ch)를 찾아 제거한다 <BR>
     *
     * @param str
     *            입력문자열
     * @param 제거할
     *            문자
     * @return 변환된 문자열을 리턴한다
     */
    public static String removeChar(String str, char ch) {
        return removeChar(str, String.valueOf(ch));
    }

    /**
     * 원본문자열(str)에서 문자열(ch)을 찾아 제거한다 <BR>
     *
     * @param str
     *            입력문자열
     * @param 제거할
     *            문자열
     * @return 변환된 문자열을 리턴한다
     */

    public static String removeChar(String str, String ch) {
        if (isNull(str)) {
            return null;
        }

        StringBuffer buff = new StringBuffer();
        StringTokenizer token = new StringTokenizer(str, ch);
        while (token.hasMoreTokens()) {
            buff.append(token.nextToken());
        }

        return buff.toString();
    }

    /**
     * 문자열을 정수로 변환한다 <br>
     *
     * @param str
     *            입력문자열
     * @return 입력문자열이 NULL 일 경우에는 0, 그외는 변환된 정수를 리턴한다.
     */
    public static int str2int(String str) {
        if (str == null)
            return 0;
        else
            return Integer.valueOf(str);
    }

    /**
     * 정수를 문자열로 변환한다 <br>
     *
     * @param i
     *            입력정수
     * @return 변환된 문자열을 리턴한다.
     */
    public static String int2Str(int i) {
        return String.valueOf(i);
    }

    /**
     * 널인 문자열을 스페이스("")로 치환한다 <BR>
     * 단, 좌우 공백이 있는 문자열은 trim 한다 <br>
     *
     * @param s
     *            입력문자열
     * @return 치환된 문자열
     */
    public static String null2space(String s) {
        if (s == null || s.length() == 0)
            return "";
        else
            return s.trim();
    }

    /**
     * 널이거나 빈 문자열을 "&nbsp;"로 변환한다 <BR>
     * 단, 좌우 공백이 있는 문자열은 trim 한다 <br>
     *
     * @param s
     *            입력문자열
     * @return 치환된 문자열
     */
    public static String null2nbsp(String org) {
        if (org == null || (org.trim()).length() == 0)
            return "&nbsp;";
        else
            return org.trim();
    }

    /**
     * 널이거나 빈 문자열을 원하는 스트링으로 변환한다 <BR>
     * 단, 좌우 공백이 있는 문자열은 trim 한다 <br>
     *
     * @param s
     *            입력문자열
     * @return 치환된 문자열
     */
    public static String null2str(String org, String converted) {
        if (org == null || (org.trim()).length() == 0)
            return converted;
        else
            return org.trim();
    }

    /**
     * 문자열이 널이거나 빈 공백문자열인지 CHECK 한다 <br>
     *
     * @param s
     *            입력문자열
     * @return 널이거나 공백일 경우 true, 아닐경우 false 를 리턴한다
     */
    public static boolean isNull(String str) {
        if (str == null)
            return true;
        else
            return false;
    }

    /**
     * 문자열이 널이거나 빈 공백문자열인지 CHECK 한다 <br>
     *
     * @param s
     *            입력문자열
     * @return 널이거나 공백일 경우 true, 아닐경우 false 를 리턴한다
     */
    public static boolean isEmpty(String str) {
        if (isNull(str) || (str.trim()).length() == 0)
            return true;
        else
            return false;
    }

    /**
     * 문자열에서 주어진 separator 로 쪼개어 문자배열을 생성한다 <br>
     *
     * @param str
     *            원본문자열
     * @param separator
     *            원하는 token 문자열
     * @return 스트링배열
     */
    public static String[] split(String str, String separator) {
        StringTokenizer st = new StringTokenizer(str, separator);
        String[] values = new String[st.countTokens()];
        int pos = 0;
        while (st.hasMoreTokens()) {
            values[pos++] = st.nextToken();
        }

        return values;
    }

    public static String[] getParser(String str, String sep) {
        int count = 0;
        int index = -1;
        int index2 = 0;

        if (str == null)
            return null;

        do {
            ++count;
            ++index;
            index = str.indexOf(sep, index);
        } while (index != -1);
        // 마지막에 구분자가 있는지 check
        if (isNull(str.substring(index2))) {
            count--;
        }

        String[] substr = new String[count];
        index = 0;
        int endIndex = 0;
        for (int i = 0; i < (count); i++) {
            endIndex = str.indexOf(sep, index);
            if (endIndex == -1) {
                substr[i] = str.substring(index);
            } else {
                substr[i] = str.substring(index, endIndex);
            }
            index = endIndex + 1;
        }
        return substr;
    }

    /**
     * 문자열 배열을 주어진 separator 로 연결하여 문자열을 생성한다 <br>
     *
     * @param list
     *            스트링배열
     * @param separator
     *            원하는 token 문자열
     * @return 합쳐진 문자열을 리턴한다
     */
    public static String join(String list[], String separator) {
        StringBuffer csv = new StringBuffer();

        for (int i = 0; i < list.length; i++) {
            if (i > 0)
                csv.append(separator);
            csv.append(list[i]);
        }
        return csv.toString();
    }

    /**
     * Exception 객체로 에러메시지 문자열을 생성한다 <br>
     *
     * @param e
     *            Throwable 객체
     * @return 에러문자열
     */
    public static String stackTrace(Throwable e) {
        String str = "";
        try {
            ByteArrayOutputStream buff = new ByteArrayOutputStream();
            e.printStackTrace(new PrintWriter(buff, true));
            str = buff.toString();
        } catch (Exception ex) {
            ex.getStackTrace();
        }

        return str;
    }

    /**
     * 원본문자열에서 뒤로부터 주어진 문자열(ch)을 찾아 제거한다 <br>
     *
     * @param str
     *            원본문자열
     * @param ch
     *            제거할 문자열
     * @return 제거된문자열을 리턴한다
     * @exception 파싱에러시
     *                발생
     */

    public static String removeLastChar(String str, String ch) throws Exception {
        int pos = str.lastIndexOf(ch);
        if (pos == -1)
            return str;
        else
            return str.substring(0, pos) + str.substring(pos + 1);
    }

    /**
     * 문자열중 특정문자를 치환한다
     *
     * @param str
     *            대상문자열
     * @param src
     *            치환당할 문자
     * @param tgt
     *            치환할 문자
     * @return 완성된 문자열
     */
    public static String replace(String str, String src, String tgt) {
        String buf = "";
        String ch = null;

        if (str == null || str.length() == 0)
            return "";

        int len = src.length();
        for (int i = 0; i < str.length(); i++) {
            ch = str.substring(i, i + len);
            if (ch.equals(src))
                buf = buf + tgt;
            else
                buf = buf + ch;
        }
        return buf;
    }

    /**
     * MethodName: replaceAll<br>
     * 주어진 문자열(buffer)에서 특정문자열('src')를 찾아 특정문자열('dst')로 치환
     *
     * @param buffer
     *            java.lang.String
     * @param src
     *            java.lang.String
     * @param dst
     *            java.lang.String
     * @return java.lang.String
     */
    public static String replaceAll(String buffer, String src, String dst) {
        if (buffer == null)
            return null;
        if (isNull(src) || buffer.indexOf(src) < 0)
            return buffer;
        int bufLen = buffer.length();
        int srcLen = src.length();
        StringBuffer result = new StringBuffer();
        int i = 0;
        int j = 0;
        while (i < bufLen) {
            j = buffer.indexOf(src, j);
            if (j >= 0) {
                result.append(buffer.substring(i, j));

                result.append(dst);

                j += srcLen;
                i = j;
            } else
                break;
        }
        result.append(buffer.substring(i));
        return result.toString();
    }

    /**
     * 문자열중 특정문자열을 치환한다
     *
     * @param str
     *            대상문자열
     * @param src
     *            치환당할 문자열
     * @param tgt
     *            치환할 문자열
     * @return 완성된 문자열
     */
    public static String replaceString(String str, String src, String tgt) {
        StringBuffer ret = new StringBuffer();

        if (str == null)
            return null;
        if (str.equals(""))
            return "";

        int start = 0;
        int found = str.indexOf(src);
        while (found >= 0) {
            if (found > start)
                ret.append(str.substring(start, found));

            if (tgt != null)
                ret.append(tgt);

            start = found + src.length();
            found = str.indexOf(src, start);
        }

        if (str.length() > start)
            ret.append(str.substring(start, str.length()));

        return ret.toString();
    }

    /**
     * 입력받은 String을 원하는 길이만큼 원하는 문자로 오른쪽을 채워주는 함수
     *
     * @param calendar
     *            입력받은 String
     * @return 지정된 문자로 채워진 String
     */
    public static String rpad(String str, int len, char pad) {
        String result = str;
        int templen = len - result.getBytes().length;

        for (int i = 0; i < templen; i++) {
            result = result + pad;
        }

        return result;
    }

    /**
     * 입력받은 String을 원하는 길이만큼 원하는 문자로 왼쪽을 채워주는 함수
     *
     * @param calendar
     *            입력받은 String
     * @return 지정된 문자로 채워진 String
     */
    public static String lpad(String str, int len, char pad) {
        String result = str;
        int templen = len - result.getBytes().length;

        for (int i = 0; i < templen; i++)
            result = pad + result;

        return result;
    }

    /**
     * 문자가 길경우에 특정 바이트 단위 길이로 자른다. (by ssoon 2005.03.28)
     *
     * @param str
     *            문자열
     * @param byteSize
     *            남길 문자열의 길이
     * @return string 자르고 남은 문자열
     * @throws Exception
     */
    public static String getStrCut(String str, int byteSize) throws Exception {
        return getStrCut(str, byteSize, "...");
    }

    /**
     * 문자가 길경우에 특정 바이트 단위 길이로 자른다. (by ssoon 2005.03.28)
     *
     * @param str
     *            문자열
     * @param byteSize
     *            남길 문자열의 길이
     * @param str2
     *            남길 문자열 뒤에 적어줄 문자열
     * @return string 자르고 남은 문자열
     * @throws Exception
     */
    public static String getStrCut(String src, int byteSize, String str2) throws Exception {
        int rSize = 0;
        int len = 0;

        String str = src;
        if (str.getBytes().length > byteSize) {
            for (rSize = 0; rSize < str.length(); rSize++) {
                if (str.charAt(rSize) > 0x007F)
                    len += 2;
                else
                    len++;

                if (len > byteSize)
                    break;
            }
            str = str.substring(0, rSize) + str2;
        }
        return str;
    }

    /**
     * formatMoney(by ssoon 2005.03.28)
     *
     * @param str
     * @return String
     */
    public static String formatMoney(String str) {
        double iAmount = (new Double(str)).doubleValue();
        java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,###,###,###,###,###");
        return df.format(iAmount);
    }

    /**
     * 대상 str가 null이거나 ""인경우 경우 "&nbsp;"을 return(by ssoon 2005.03.28)
     *
     * @param str
     *            대상 스트링
     */
    public static String strToNbsp(String str) throws Exception {
        if (str == null || null2space(str).equals(""))
            return "&nbsp;";
        else
            return str;
    }

    /**
     * html --> text 로 변환
     *
     * @param strString
     *            데이터베이스에 있는 데이터 문자열이다.
     * @return 바뀌어진 값을 넘겨준다.
     */
    public static String changeHtmlToText(String strString) {
        String strNew = "";

        try {
            StringBuffer strTxt = new StringBuffer("");
            char chrBuff;
            int len = 0;
            int i = 0;

            len = strString.length();

            for (i = 0; i < len; i++) {
                chrBuff = (char) strString.charAt(i);
                switch (chrBuff) {
                    case '<':
                        strTxt.append("&lt");
                        break;
                    case '>':
                        strTxt.append("&gt");
                        break;
                    case 10:
                        strTxt.append("<br>");
                        break;
                    case 13:
                        // strTxt.append("<br>");
                        break;
                    case ' ':
                        strTxt.append(" ");
                        break;
                    default:
                        strTxt.append(chrBuff);
                        break;
                }// switch
            } // for

            strNew = strTxt.toString();

        } catch (Exception ex) {
            ex.getStackTrace();
        }

        return strNew;
    }

    public static String toHtmlString(String str) {
        if (str == null)
            return "";
        if ("".equals(str))
            return "-";

        String tempStr = replace(str, "\n", "<br>");

        tempStr = StringUtil.replaceString(tempStr, "<a href", "<ahref");
        tempStr = StringUtil.replace(tempStr, " ", "&nbsp;");
        tempStr = StringUtil.replaceString(tempStr, "<ahref", "<a href");
        return tempStr;
    }

    public static String toHtmlString(String str, String clsName) {
        if (str == null)
            return "";
        if ("".equals(str))
            return "-";

        String tempStr = replace(str, "\n", "<br>");

        tempStr = StringUtil.replaceString(tempStr, "<a href", "<ahref");
        tempStr = StringUtil.replace(tempStr, " ", "&nbsp;");
        tempStr = StringUtil.replaceString(tempStr, "<ahref", "<a class='" + clsName + "' href");
        return tempStr;
    }

    public static String tagRemove(String s) {
        StringBuffer stripHtml = new StringBuffer();
        char[] buf = s.toCharArray();
        int j = 0;
        while (j < buf.length) {
            // for (; j<buf.length; j++) {
            if (buf[j] == '<') {
                while (j < buf.length) {
                    // for(; j<buf.length; j++) {
                    if (buf[j] == '>') {
                        break;
                    }
                    j++;
                }

            } else {
                stripHtml.append(buf[j]);
            }
            j++;
        }
        return stripHtml.toString();
    }

    /**
     * ' --> &#39; 변환 " --> &quot; 변환
     *
     * @param strString
     *            데이터베이스에 있는 데이터 문자열이다.
     * @return 바뀌어진 값을 넘겨준다.
     */
    public static String replaceDang(String src) {
        String str = src;

        if (str != null && !str.equals("")) {
            str = StringUtil.null2space(str);
            str = StringUtil.replaceString(str, "'", "&#39;");
            str = StringUtil.replaceString(str, "\"", "&quot;");
        }
        return str;
    }

    /**
     * ' <-- &#39; 변환 " <-- &quot; 변환
     *
     * @param strString
     *            데이터베이스에 있는 데이터 문자열이다.
     * @return 바뀌어진 값을 넘겨준다.
     */
    public static String replaceDangRev(String src) {
        String str = src;
        if (str != null && !str.equals("")) {
            str = StringUtil.replaceString(str, "&#39;", "'");
            str = StringUtil.replaceString(str, "&quot;", "\"");
        }
        return str;
    }

    /**
     * & --> &amp; 변환 트림처리
     *
     * @param strString
     *            데이터베이스에 있는 데이터 문자열이다.
     * @return 바뀌어진 값을 넘겨준다.
     */
    public static String replaceAmp(String src) {
        String str = src;
        if (str != null && !str.equals("")) {
            str = StringUtil.null2space(str);
            str = StringUtil.replaceString(str, "&", "&amp;");
        }
        return str;
    }

    public static String replaceAmpRev(String src) {
        String str = src;
        if (str != null && !str.equals("")) {
            str = StringUtil.replaceString(str, "&amp;", "&");

        }
        return str;
    }

    public static String checkHtmlView(String strString) {
        String strNew = "";

        try {
            StringBuffer strTxt = new StringBuffer("");

            char chrBuff;
            int len = strString.length();

            for (int i = 0; i < len; i++) {
                chrBuff = (char) strString.charAt(i);

                switch (chrBuff) {
                    case '<':
                        strTxt.append("&lt;");
                        break;
                    case '>':
                        strTxt.append("&gt;");
                        break;
                    case '"':
                        strTxt.append("&quot;");
                        break;
                    case 10:
                        strTxt.append("<br>");
                        break;
                    // case 13 :
                    // strTxt.append("<br>");
                    // break;
                    case ' ':
                        strTxt.append("&nbsp;");
                        break;
                    // case '&' :
                    // strTxt.append("&amp;");
                    // break;
                    default:
                        strTxt.append(chrBuff);
                        break;
                }
            }

            strNew = strTxt.toString();

        } catch (Exception ex) {
            ex.getStackTrace();
        }

        return strNew;
    }

    public static String checkHtmlEdit(String strString) {
        String strNew = "";

        try {
            StringBuffer strTxt = new StringBuffer("");

            char chrBuff;
            int len = strString.length();

            for (int i = 0; i < len; i++) {
                chrBuff = (char) strString.charAt(i);

                switch (chrBuff) {
                    case '<':
                        strTxt.append("&lt;");
                        break;
                    case '>':
                        strTxt.append("&gt;");
                        break;
                    case '"':
                        strTxt.append("&quot;");
                        break;
                    // case '&' :
                    // strTxt.append("&amp;");
                    // break;
                    default:
                        strTxt.append(chrBuff);
                        break;
                }
            }

            strNew = strTxt.toString();

        } catch (Exception ex) {
            ex.getStackTrace();
        }

        return strNew;
    }

    public static String byteCutLine(String src, int cut, String pushChar) {
        String str = src;

        if (str == null)
            return null;

        int kk = str.lastIndexOf("&nbsp;");
        String nbstr = "";
        if (kk != (-1)) {
//			str.substring(0, kk + 6);
            str = str.substring(kk + 6);
        }

        byte[] bl = str.getBytes();
        if (bl.length <= cut)
            return str; // 한줄이면 리턴

        int size = (int) (Math.ceil((float) bl.length / (float) cut)); // 총 라인수

        StringBuffer reVal = new StringBuffer();
        String tempStr = str;
        for (int i = 0; i < size - 1; i++) { // 마지막 라인만 남기고 더한다.

            // int st = bl.length - cut;
            byte[] temp1 = new byte[cut];
            System.arraycopy(tempStr.getBytes(), 0, temp1, 0, cut);

            String val = new String(temp1);
            int idx = val.length();

            reVal.append(tempStr.substring(0, idx + 1) + "<br>" + pushChar);
            tempStr = tempStr.substring(idx + 1, tempStr.length());
        }

        reVal.append(tempStr); // 마지막 라인을 추가한다.

        return nbstr + reVal.toString();

    }

    /**
     * MethodName: replaceNull<br>
     * 널값이면 ''값을, 그렇지않으면 trim()한 값을 리턴
     *
     * @param value
     *            java.lang.String
     * @return java.lang.String
     */
    public static String replaceNull(String value) {
        return replaceNull(value, "");
    }

    /**
     * MethodName: replaceNull<br>
     * 널값이면 repStr 값을, 그렇지않으면 trim()한 값을 리턴
     *
     * @param value
     *            java.lang.String
     * @param repStr
     *            java.lang.String
     * @return java.lang.String
     */
    public static String replaceNull(String value, String repStr) {
        if (isNull(value))
            return repStr;

        return value.trim();
    }

    /**
     * MethodName : replaceNull<br>
     * 배열 String Null 값 리턴
     *
     * @param paramArr
     * @param idx
     * @param repStr
     * @return
     */
    public static String replaceNull(String[] paramArr, int idx, String repStr) {
        String param = null;

        if (paramArr == null) {
            param = "N";
        } else {
            param = StringUtil.replaceNull(paramArr[0], "N");
        }

        return param.trim();
    }

    /**
     * 우편번호 123456 -> 123-456 으로 변환
     *
     * @param str
     * @param ch
     * @return
     */
    public static String replaceZip(String str, String ch) {
        if(str == null)return null;
        String tmp = str.trim();
        String result = "";
        if (str != null && str.length() > 0) {
            result = tmp.substring(0, 3) + ch + tmp.substring(3, 6);
        } else {
            result = "";
        }
        return result;
    }

    /**
     * MethodName: replaceInt<br>
     * 널값이나 숫자가 아니면 0값을, 그렇지않으면 int값으로 치환한 값을 리턴
     *
     * @param value
     *            java.lang.String
     * @return java.lang.int
     */
    public static int replaceInt(String value) {
        return parseInt(value, 0);
    }

    /**
     * MethodName: replaceInt<br>
     * parseInt와 동일한 기능이라 deprecate했음
     *
     * @param value
     *            java.lang.String
     * @param repInt
     *            java.lang.int
     * @return java.lang.int
     */
    public static int replaceInt(String value, int repInt) {
        return parseInt(value, repInt);
    }

    /**
     * MethodName: parseInt<br>
     * NumberFormatException 을 처리<br>
     * Integer.parseInt 와 동일, 단 Exception 발생시 -1 리턴
     *
     * @param src
     *            java.lang.String
     * @return java.lang.int
     */
    public static int parseInt(String src) {
        return parseInt(src, -1);
    }

    /**
     * MethodName: parseInt<br>
     * NumberFormatException 을 처리<br>
     * Integer.parseInt 와 동일, 단 Exception 발생시 dft 리턴
     *
     * @param src
     *            java.lang.String
     * @param dft
     *            java.lang.int
     * @return java.lang.int
     */
    public static int parseInt(String src, int dft) {
        if (isNull(src))
            return 0;

        int result = dft;
        try {
            result = Integer.parseInt(src.trim());
        } catch (Exception ex) {
            result = dft;
            ex.getStackTrace();
        }
        return result;
    }

    /**
     * MethodName: sumLong<br>
     * NumberFormatException 을 처리<br>
     * Exception 발생시 dft 리턴
     *
     * @param src
     *            java.lang.String
     * @param src
     *            java.lang.String
     * @return java.lang.long
     */
    public static long sumLong(String src1, String src2) {
        return sumLong(src1, src2, -1);
    }

    /**
     * MethodName: sumLong<br>
     * NumberFormatException 을 처리<br>
     * Exception 발생시 dft 리턴
     *
     * @param src
     *            java.lang.String
     * @param src
     *            java.lang.String
     * @param dft
     *            java.lang.long
     * @return java.lang.long
     */
    public static long sumLong(String src1, String src2, long dft) {
        if (isNull(src1) || isNull(src2))
            return 0;

        long result = dft;
        try {
            result = Long.parseLong(src1.trim()) + Long.parseLong(src2.trim());
        } catch (Exception e) {
            result = dft;

        }
        return result;
    }

    /**
     * MethodName: minusLong<br>
     * NumberFormatException 을 처리<br>
     * Exception 발생시 dft 리턴
     *
     * @param src
     *            java.lang.String
     * @param src
     *            java.lang.String
     * @return java.lang.long
     */
    public static long minusLong(String src1, String src2) {
        return minusLong(src1, src2, -1);
    }

    /**
     * MethodName: minusLong<br>
     * NumberFormatException 을 처리<br>
     * Exception 발생시 dft 리턴
     *
     * @param src
     *            java.lang.String
     * @param src
     *            java.lang.String
     * @param dft
     *            java.lang.long
     * @return java.lang.long
     */
    public static long minusLong(String src1, String src2, long dft) {
        if (isNull(src1) || isNull(src2))
            return 0;

        long result = dft;
        try {
            result = Long.parseLong(src1.trim()) - Long.parseLong(src2.trim());
        } catch (Exception ex) {
            result = dft;
            ex.getStackTrace();
        }
        return result;
    }

    /**
     * 금액 형태로 바꾼다. ex)23232323 -> 23,232,323
     *
     * @param num
     *            java.lang.String
     * @return java.lang.String
     */
    public static String Digit2Comma(String src) {
        String num = src;
        String minusStr = "";

        if (num == null || "".equals(num))
            num = "0";

        BigDecimal bigA = new BigDecimal(num);

        if (bigA.compareTo(new BigDecimal(0)) < 0) {
            minusStr = "-";
            bigA = bigA.abs();
        }

        String paraNum = bigA.toString();

        String retValue = "";
        int numLen = paraNum.length();

        for (int i = 0; i < numLen; i++) {
            if (((numLen - i) % 3 == 1) && ((numLen - i) > 3)) {
                retValue = retValue + paraNum.substring(i, i + 1) + ",";
            } else {
                retValue = retValue + paraNum.substring(i, i + 1);
            }
        }

        return minusStr + retValue;
    }

    /**
     * 금액 형태로 바꾼다. ex)23232323 -> 23,232,323
     *
     * @param num
     *            double
     * @return java.lang.String
     */
    public static String Digit2Comma(double num) {
        String minusStr = "";
        BigDecimal bigA = new BigDecimal(num);
        if (bigA.compareTo(new BigDecimal(0)) < 0) {
            minusStr = "-";
            bigA = bigA.abs();
        }

        String paraNum = bigA.toString();

        String retValue = "";
        int numLen = paraNum.length();

        for (int i = 0; i < numLen; i++) {
            if (((numLen - i) % 3 == 1) && ((numLen - i) > 3)) {
                retValue = retValue + paraNum.substring(i, i + 1) + ",";
            } else {
                retValue = retValue + paraNum.substring(i, i + 1);
            }
        }

        return minusStr + retValue;
    }

    /**
     * 금액 형태로 바꾼다. ex)23232323.3 -> 23,232,323.3
     *
     * @param num
     *            java.lang.String
     * @return java.lang.String
     */
    public static String Digit2CommaDouble(String src) {
        String minusStr = "";
        String pointStr = "";
        String num = src;

        if (num == null || "".equals(num))
            num = "0";

        BigDecimal bigA = null;

        if (num.indexOf(".") == -1) {
            bigA = new BigDecimal(num);
        } else {
            pointStr = "." + num.substring(num.indexOf(".") + 1, num.length());
            bigA = new BigDecimal(num.substring(0, num.indexOf(".")));
        }

        if (bigA.compareTo(new BigDecimal(0)) < 0) {
            minusStr = "-";
            bigA = bigA.abs();
        }

        String paraNum = bigA.toString();

        String retValue = "";
        int numLen = paraNum.length();

        for (int i = 0; i < numLen; i++) {
            if (((numLen - i) % 3 == 1) && ((numLen - i) > 3)) {
                retValue = retValue + paraNum.substring(i, i + 1) + ",";
            } else {
                retValue = retValue + paraNum.substring(i, i + 1);
            }
        }

        return minusStr + retValue + pointStr;
    }

    /**
     * 시작문자 끝문자 사이 문자열 반환
     *
     * @param val java.lang.String
     * @param start ava.lang.Int
     * @param end java.lang.Int
     * @return
     */
    public static String subString(String val, int start, int end) {
        String str = null;
        str = val;
        if (str.length() >= end) {
            str = val.substring(start, end);
        }
        return str;
    }

    /**
     * MethodName: parseLong<br>
     * NumberFormatException 을 처리<br>
     * Long.parseLong 와 동일, 단 Exception 발생시 -1 리턴
     *
     * @param src
     *            java.lang.String
     * @return java.lang.long
     */
    public static long parseLong(String src) {
        return parseLong(src, -1);
    }

    /**
     * MethodName: parseLong<br>
     * NumberFormatException 을 처리<br>
     * Long.parseLong 와 동일, 단 Exception 발생시 dft 리턴
     *
     * @param src
     *            java.lang.String
     * @param dft
     *            java.lang.long
     * @return java.lang.long
     */
    public static long parseLong(String src, long dft) {
        if (isNull(src))
            return 0;

        long result = dft;
        try {
            result = Long.parseLong(src.trim());
        } catch (Exception ex) {
            result = dft;
            ex.getStackTrace();
        }
        return result;
    }

    /**
     * 예 : 주어진 이름의 속성을 같고 초기값을 <code>null</code>로 갖도록 생성 숫자로만 이루어 졌는지를 체크하여 숫자로만
     * 이루어였으면 넘겨받은 src 를 리턴 그렇지 않으면 넘겨받은 defaultVal 값을 리턴함.
     *
     * @param src
     * @param defaultVal
     * @return
     *
     */
    public static String isNumber(String src, String defaultVal) {
        String strChar = "0123456789";

        for (int i = 0; i < src.length(); i++) {
            if (strChar.indexOf(src.charAt(i)) == -1)
                return defaultVal;
        }

        return src;
    }

    /**
     * isCompareStrNum 함수 설명 comp1 과 comp2 가 숫자가 아닐시는 -2, comp1 과 comp2 가 숫자이고
     * comp1>comp2 이면 -1 , comp1 과 comp2 가 숫자이고 comp1==comp2 이면 0 , comp1 과
     * comp2 가 숫자이고 comp1<comp2 이면 1
     *
     * @param comp1
     * @param comp2
     * @return
     *
     */
    public static int isCompareStrNum(String comp1, String comp2) {
        int rtnInt = 0;
        if (parseLong(comp1) < 0 || parseLong(comp2) < 0) {
            rtnInt = -2;
            return rtnInt;
        } else {
            if (parseLong(comp1) > parseLong(comp2))
                rtnInt = -1;
            else if (parseLong(comp1) == parseLong(comp2))
                rtnInt = 0;
            else if (parseLong(comp1) < parseLong(comp2))
                rtnInt = 1;
        }
        return rtnInt;
    }

    /**
     * UTF-8의 변형을 위한 메소드
     *
     * @param sText
     *            : UTF-8로 변형될 값
     * @return
     */
    public static String getUTF(String sText) {
        String sResult = null;
        try {
            if (sText == null)
                return sResult;
            sResult = new String(sText.getBytes("8859_1"), "UTF-8");
            return sResult;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     *
     * existString String 배열에 해당하는 String이 존재하는지 여부.
     *
     * @param strArr
     * @param str
     * @return
     */
    public static boolean existString(String[] strArr, String str) {
        try {
            for (int i = 0, len = strArr.length; i < len; i++) {
                if (str.equals(strArr[i]))
                    return true;
            }
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }
        return false;
    }

    /**
     * 입력데이타 중복체크
     * @param chkKEy[]
     * @return boolean
     * @throws Exception
     */
    public static boolean checkDoubleData(String[] chkKey) {
        Set<String> set = new HashSet<String>();
        for (int i = 0; i< chkKey.length; i++) {
            if (!set.add(chkKey[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 입력데이터를 TRIM, REPLACE(' --> '')처리를 해준다.
     * @param data
     * @return String
     */
    public static String convString(String data) {
        String value = data;
        if (data != null) {
            value = value.trim().replaceAll("'", "''");
        }
        return value;
    }

    public static String getStringToLimit(String str, int limit) {
        if (str != null) {
            int len = str.length();

            if (len > limit) {
                return str.substring(0, limit);
            } else {
                return str;
            }
        } else {
            return "";
        }
    }

    public static String generateNumber(int certNumLength) {
        Random random = new Random(System.currentTimeMillis());

        int range = (int)Math.pow(10, certNumLength);
        int trim = (int)Math.pow(10, certNumLength-1);
        int result = random.nextInt(range)+trim;

        if(result>range){
            result = result - trim;
        }

        return String.valueOf(result);
    }
}
