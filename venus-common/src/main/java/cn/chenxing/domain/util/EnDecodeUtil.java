package cn.chenxing.domain.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @Description 加密解密工具
 * @Author maogen.ymg
 * @Date 2020/2/29 13:30
 */
@Slf4j
public class EnDecodeUtil {
    private EnDecodeUtil() {}

    /**
     * md5加密
     * @param plain 数字明文
     * @return 数字密文
     */
    public static String md5(String plain) {
        StringBuilder sb = new StringBuilder();
        try {
            // 获取MD5加密器
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = plain.getBytes();
            byte[] digest = md.digest(bytes);
            for (byte b : digest) {
                //把每个字节转换成16进制数 (0x00 00 00 00 ff)
                int d = b & 0xff;
                String hexString = Integer.toHexString(d);
                if (hexString.length() == 1) {
                    hexString = "0" + hexString;
                }
                sb.append(hexString);
            }
        } catch (Exception e) {
            log.error("md5加密失败{}", e.getMessage(), e);
        }
        return sb + "";
    }

    /**
     * 将字符串转换为base64
     * @param plain 待编码的字符串
     * @return base64编码后的字符串
     */
    public static String toBase64(String plain) {
        if (plain == null || plain.isEmpty()) {
            return "";
        }
        byte[] textByte = plain.getBytes(StandardCharsets.UTF_8);
        return new Base64().encodeToString(textByte);
    }

    /**
     * 将编码后的Base64解码
     *
     * @param cipherText 密文
     * @return base64解码后的字符串
     */
    public static String decodeBase64(String cipherText) {
        if (cipherText == null || cipherText.isEmpty()) {
            return "";
        }
        return new String(Base64.decodeBase64(cipherText));
    }
}
