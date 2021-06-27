package cn.yansui.domain.util;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Description
 * @Author maogen.ymg
 * @Date 2020/3/17 16:17
 */
@Slf4j
public class IOConvertUtil {
    private IOConvertUtil() {}

    /**
     * 将BufferedImage转换为InputStream
     * @param image BufferedImage
     * @param formatName {"png", "jpg", "gif"}
     * @return InputStream
     */
    public static InputStream bufferedImageToInputStream(BufferedImage image, String formatName){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, formatName, os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (Exception e) {
            log.error("BufferedImage转InputStream failed{}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * inputStream转outputStream
     * @param in InputStream
     * @return ByteArrayOutputStream
     */
    public static ByteArrayOutputStream inputToOutputStream(InputStream in) {
        try{
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            int ch;
            while ((ch = in.read()) != -1) {
                swapStream.write(ch);
            }
            return swapStream;
        }catch (Exception e){
            log.error("inputStream转outputStream failed {}", e.getMessage(), e);
            return null;
        }

    }

    /**
     * outputStream转inputStream
     * @param out OutputStream
     * @return ByteArrayInputStream
     */
    public static ByteArrayInputStream outputToInputStream(OutputStream out) {
        ByteArrayOutputStream baos = (ByteArrayOutputStream) out;
        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * inputStream转String
     * @param in InputStream
     * @return String
     */
    public static String inputStreamToString(final InputStream in) {
        try{
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            int ch;
            while ((ch = in.read()) != -1) {
                swapStream.write(ch);
            }
            return swapStream.toString();
        }catch (Exception e){
            log.error("inputStream转String failed {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * OutputStream 转String
     * @param out OutputStream
     * @return String
     */
    public static String outputStreamToString(final OutputStream out) {
        ByteArrayOutputStream baos = (ByteArrayOutputStream) out;
        return new ByteArrayInputStream(baos.toByteArray()).toString();
    }

    /**
     * String转inputStream
     * @param in String
     * @return ByteArrayInputStream
     */
    public static ByteArrayInputStream stringToInputStream(String in) {
        return new ByteArrayInputStream(in.getBytes());
    }

    /**
     * String 转outputStream
     * @param in String
     * @return outputStream
     */
    public static ByteArrayOutputStream stringToOutStream(String in) {
        return inputToOutputStream(stringToInputStream(in));
    }
}
