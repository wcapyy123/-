package com.tod.wtl.util.resource;

/**
 * Base64工具..
 * Created By Night 2018/1/10
 */
public final class Base64 {
    /**
     * 编码..
     *
     * @param str 字符..
     * @return ..
     */
    public static String encode(final String str) {
        return new String(java.util.Base64.getEncoder().encode(str.getBytes())).replaceAll("\n", "");
    }

    /**
     * 解码..
     *
     * @param str 字符..
     * @return ..
     */
    public static String decode(final String str) {
        return new String(java.util.Base64.getDecoder().decode(str.getBytes()));
    }

    /**
     * 解码.
     * @param str .
     * @return .
     */
    public static String urlDecode(final String str) {
        return new String(java.util.Base64.getUrlDecoder().decode(str.getBytes()));
    }

    /**
     * private..
     */
    private Base64() {
    }
}
