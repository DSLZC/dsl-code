package cn.dslcode.common.core;

import cn.dslcode.common.core.string.StringUtil;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.util.Base64;

/**
 * 密码工具类
 * @author DSL
 *  2016-07-13
 */
public class EncryptUtil {

	/**
	 * MD5加密
	 * @param str 未加密字符串
	 * @return 加密字符串
	 */
	public static String MD5Encode(String str){
		if(StringUtil.isEmpty(str)) return null;
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		int byteLength = byteArray.length;
		for (int i = 0; i < byteLength; i++) {
			int hex = 0xFF & byteArray[i];
			if(hex < 16) md5StrBuff.append("0");
			md5StrBuff.append(Integer.toHexString(hex));
		}
		return md5StrBuff.toString();
	}

	/**
	 * Base64加密
	 * @param str 未加密字符串
	 * @return 加密字符串
	 */
    public static String BASE64Encode(String str) {
    	if(StringUtil.isEmpty(str)) return null;
        try {
			return new String(Base64.getEncoder().encode(str.getBytes("utf-8")), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    /**
     * Base64解密
     * @param str 加密字符串
     * @return 解密的字符串
     */
    public static String BASE64Decode(String str) {
		if(StringUtil.isEmpty(str)) return null;
    	try {
			return new String(Base64.getDecoder().decode(str.getBytes("utf-8")), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }


	/**
	 * Hex编码.
	 */
	public static String HexEncode(byte[] input) {
		return new String(Hex.encodeHex(input));
	}

	/**
	 * Hex解码.
	 */
	public static byte[] HexDecode(String input) throws DecoderException {
		return Hex.decodeHex(input.toCharArray());
	}

}
