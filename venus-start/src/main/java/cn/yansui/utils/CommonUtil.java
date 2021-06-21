package cn.yansui.utils;


import java.security.SecureRandom;
import java.util.UUID;

public class CommonUtil {
        private static char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        private static SecureRandom ng = new SecureRandom();

        public CommonUtil() {
        }

        private static String toUnsignedString(long i, int shift) {
            char[] buf = new char[64];
            int charPos = 64;
            int radix = 1 << shift;
            long mask = (long)(radix - 1);

            do {
                --charPos;
                buf[charPos] = digits[(int)(i & mask)];
                i >>>= shift;
            } while(i != 0L);

            return new String(buf, charPos, 64 - charPos);
        }

        public static String random16() {
            byte[] data = new byte[10];
            ng.nextBytes(data);
            long msb = 0L;
            long lsb = 0L;

            int i;
            for(i = 0; i < 5; ++i) {
                msb = msb << 8 | (long)(data[i] & 255);
            }

            for(i = 5; i < 10; ++i) {
                lsb = lsb << 8 | (long)(data[i] & 255);
            }

            String random = toUnsignedString(msb, 5) + toUnsignedString(lsb, 5);
            if (random.length() < 16) {
                for(int n = 0; n < 16 - random.length(); ++i) {
                    random = digits[ng.nextInt(36)] + random;
                }
            }

            return random;
        }

        public static String random32() {
            return UUID.randomUUID().toString();
        }

        public static void main(String[] args) {
            for(int i = 0; i < 1000; ++i) {
                System.out.println(random32());
            }

        }
}

