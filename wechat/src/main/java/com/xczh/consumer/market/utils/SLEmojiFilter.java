package com.xczh.consumer.market.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

/**
 * emoji工具过滤类
 */
public class SLEmojiFilter {

	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(SLEmojiFilter.class);

	/**
	 * 检测是否有emoji字符
	 *
	 * @param source
	 * @return 一旦含有就抛出
	 */
	public static boolean containsEmoji(String source) {
		if (StringUtils.isBlank(source)) {
			return false;
		}
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (isEmojiCharacter(codePoint)) {
				// do nothing，判断到了这里表明，确认有表情字符
				return true;
			}
		}
		return false;
	}

	private static boolean isEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
				|| (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
				|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 *
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source) {
		
		if (source == null) {
			return null;
		}
		source = source.replaceAll(
				"[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
		
		if (!containsEmoji(source)) {
			return source;// 如果不包含，直接返回
		}
		// 到这里铁定包含
		StringBuilder buf = null;

		int len = source.length();

		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);

			if (isEmojiCharacter(codePoint)) {
				if (buf == null) {
					buf = new StringBuilder(source.length());
				}

				buf.append(codePoint);
			} else {
				buf.append("*");
			}
		}

		if (buf == null) {
			return source;// 如果没有找到 emoji表情，则返回源字符串
		} else {
			if (buf.length() == len) {// 这里的意义在于尽可能少的toString，因为会重新生成字符串
				buf = null;
				return source;
			} else {
				return buf.toString();
			}
		}
	}

	/**
	 * @Description 将字符串中的emoji表情转换成可以在utf-8字符集数据库中保存的格式（表情占4个字节，需要utf8mb4字符集）
	 * @param str
	 *            待转换字符串
	 * @return 转换后字符串
	 * @throws UnsupportedEncodingException
	 *             exception
	 */
	public static String emojiConvert1(String str)
			throws UnsupportedEncodingException {
		String patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";

		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			try {
				matcher.appendReplacement(sb,"[[" + URLEncoder.encode(matcher.group(1), "UTF-8")+ "]]");
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("emojiConvert error", e);
				throw e;
			}
		}
		matcher.appendTail(sb);
		LOGGER.debug("emojiConvert " + str + " to " + sb.toString() + ", len："+ sb.length());
		return sb.toString();
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		String str  = emojiConvert1("11");
		
		System.out.println(str);
	}
	
	
	
	/**
	 * @Description 还原utf8数据库中保存的含转换后emoji表情的字符串
	 * @param str
	 *            转换后的字符串
	 * @return 转换前的字符串
	 * @throws UnsupportedEncodingException
	 *             exception
	 */
	public static String emojiRecovery2(String str)
			throws UnsupportedEncodingException {
		String patternString = "\\[\\[(.*?)\\]\\]";

		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(str);

		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			try {
				
				matcher.appendReplacement(sb,URLDecoder.decode(matcher.group(1), "UTF-8"));
				
				
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("emojiRecovery error", e);
				throw e;
			}
		}
		matcher.appendTail(sb);
		LOGGER.debug("emojiRecovery " + str + " to " + sb.toString());
		return sb.toString();
	}

	/**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    public static String filterEmojiToNullStr11(String source) {
        if (source == null) {
            return null;
        }
        source = source.replaceAll(
                "[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
        if (!containsEmoji(source)) {
            return source;// 如果不包含，直接返回
        }
        // 到这里铁定包含
        StringBuilder buf = null;

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }

                buf.append(codePoint);
            } else {
                buf.append("*");
            }
        }

        if (buf == null) {
            return source;// 如果没有找到 emoji表情，则返回源字符串
        } else {
            if (buf.length() == len) {// 这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
    }
	
	
	
}