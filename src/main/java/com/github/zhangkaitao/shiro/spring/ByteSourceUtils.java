package com.github.zhangkaitao.shiro.spring;

/**
 * <p>Title: ByteSourceUtils</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: www.hanshow.com</p>
 *
 * @author guolin
 * @version 1.0
 * @date 2018-12-18 19:07
 */

import com.github.zhangkaitao.shiro.spring.po.SimpleByteSource;
import org.apache.shiro.util.ByteSource;

public class ByteSourceUtils {
    public static ByteSource bytes(byte[] bytes) {
        return new SimpleByteSource(bytes);
    }

    public static ByteSource bytes(String arg0) {
        return new SimpleByteSource(arg0.getBytes());
    }
}