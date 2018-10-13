package com.github.geng.util;

import com.github.geng.bread.NullOptional;
import org.hashids.Hashids;

import java.util.Random;

/**
 * 数字类型id编码工具类
 * @author geng
 */
public class IdEncryptUtils {
    private IdEncryptUtils() {

    }

    /**
     * 加密盐
     */
    private static String hashSalt = "com.github.geng";
    /**
     * 最小长度
     */
    private static int minLength = 6;

    // 码表转换
//    private static char[][] codeTable = {
//            {'a', 'k', 'u', 'E', 'O'},
//            {'b', 'l', 'v', 'F', 'P'},
//            {'c', 'm', 'w', 'G', 'Q'},
//            {'d', 'n', 'x', 'H', 'R'},
//            {'e', 'o', 'y', 'I', 'S'},
//            {'f', 'p', 'z', 'J', 'T'},
//            {'g', 'q', 'A', 'K', 'U'},
//            {'h', 'r', 'B', 'L', 'V'},
//            {'i', 's', 'C', 'M', 'W'},
//            {'j', 't', 'D', 'N', 'X'}
//    };
//
//    /**
//     * id 加密
//     * @param id id
//     * @return 加密后id
//     */
//    public static String encode(Long id) {
//        return NullOptional.validate(id, value -> {
//            //获取各自位数上的值,数字转字母
//            String temp = String.valueOf(value);
//            StringBuilder convertValue = new StringBuilder();
//            //从高到低
//            for(int i = temp.length() - 1; i >= 0; i--) {
//                int digitValue = temp.charAt(i) - '0'; //直接转换获取的Ascii码
//                char[] chars = codeTable[digitValue];
//                //随机一个下标
//                Random rand = new Random();
//                int specificIndex = rand.nextInt(chars.length);
//                convertValue.append(chars[specificIndex]);
//            }
//            // 转base64，并且去掉 =
//            // base64 等号一定用作后缀，且数目一定是0个、1个或2个
//            String base64Str = Base64Util.encode(convertValue.toString());
//            // System.out.println("转base64后：" + base64Str);
//            if (base64Str.endsWith("==")) {
//                return base64Str.substring(0, base64Str.length() -2) + "@"; // 对应键盘shift + 2
//            } else if (base64Str.endsWith("=")) {
//                return base64Str.substring(0, base64Str.length() -1) + "!";// 对应键盘shift + 1
//            } else {
//                return base64Str;
//            }
//        });
//    }
//
//    /**
//     * id 解密
//     * @param base64Str 加密后id
//     * @return 加密前id
//     */
//    public static Long decode(String base64Str) {
//        return NullOptional.validate(base64Str , value -> {
//            //base64解码
//            if (value.endsWith("@")) {
//                value = value.substring(0, value.length() - 1) + "==";
//            } else if (value.endsWith("!")) {
//                value = value.substring(0, value.length() - 1) + "=";
//            }
//            String str = Base64Util.decode(value);
//            // System.out.println("解码后：" + str);
//            //字母转数字,从高到低
//            StringBuilder target = new StringBuilder();
//            for(int i = str.length() - 1; i >= 0; i--) {
//                char gotChar = str.charAt(i);
//                //判断对应数字
//                for (int j = 0; j < codeTable.length; j++) {
//                    for (int k = 0; k< codeTable[j].length; k++) {
//                        if (gotChar == codeTable[j][k]) {
//                            target.append(j);
//                            break;
//                        }
//                    }
//                }
//            }
//            return Long.parseLong(target.toString());
//        });
//    }

     // ----------------------------------------------------------------
     // 使用 hashids 实现
    /**
     * id 加密
     * @param id id
     * @return 加密后id
     */
     public static String encode(Long id) {
         Hashids hashids = new Hashids(hashSalt, minLength);
         if (null == id || id < 0) {
             return hashids.encode(0L);
         }
         return hashids.encode(id);
     }

    /**
     * id 解密
     * @param hashId 加密后id
     * @return 加密前id
     */
     public static Long decode(String hashId) {
         if (null == hashId) {
             return -1L;
         }
         Hashids hashids = new Hashids(hashSalt, minLength);
         long[] decodeIds = hashids.decode(hashId);
         if (decodeIds.length > 0 ) {
             return decodeIds[0];
         }
         return -1L;
     }

    public static void main(String[] args) {
        //System.out.println(Base64Util.encode("a"));
        String encodeStr = IdEncryptUtils.encode(1L);
        System.out.println("编码后："+ encodeStr);
    }

//     public static void main(String[] args) {
//         Hashids hashids = new Hashids(hashSalt, minLength);
//         System.out.printf(hashids.encode(-2L));
//     }
}
