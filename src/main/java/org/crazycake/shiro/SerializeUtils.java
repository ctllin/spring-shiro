package org.crazycake.shiro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

/**
 * <p>Title: SerializeUtils</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: www.hanshow.com</p>
 *
 * @author guolin
 * @version 1.0
 * @date 2018-12-18 20:30
 */
public class SerializeUtils {
    private static final JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
    public static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }
    private static Logger logger = LoggerFactory.getLogger(com.github.zhangkaitao.shiro.spring.SerializeUtils.class);

    /**
     * 序列化对象
     *
     * @param
     * @return
     */
    public static <T> byte[] serialize(T obj) {
        try {
            return jdkSerializationRedisSerializer.serialize(obj);
        } catch (Exception e) {
            throw new RuntimeException("序列化失败!", e);
        }
    }
//    public static <T> byte[] serialize(T object) throws SerializationException {
//        byte[] result = null;
//
//        if (object == null) {
//            return new byte[0];
//        }
//        try (
//                ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
//                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream)
//        ){
//
//            if (!(object instanceof Serializable)) {
//                throw new IllegalArgumentException(SerializeUtils.class.getSimpleName() + " requires a Serializable payload " +
//                        "but received an object of type [" + object.getClass().getName() + "]");
//            }
//
//            objectOutputStream.writeObject(object);
//            objectOutputStream.flush();
//            result =  byteStream.toByteArray();
//        } catch (Exception ex) {
//            logger.error("Failed to serialize",ex);
//        }
//        return result;
//    }

    /**
     * 反序列化对象
     *
     * @param bytes 字节数组
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] bytes) {
        try {
            return (T) jdkSerializationRedisSerializer.deserialize(bytes);
        } catch (Exception e) {
            throw new RuntimeException("反序列化失败!", e);
        }
    }
//    public static <T> T deserialize(byte[] bytes) throws SerializationException {
//
//        T result = null;
//
//        if (isEmpty(bytes)) {
//            return null;
//        }
//
//        try (
//                ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
//                ObjectInputStream objectInputStream = new ObjectInputStream(byteStream)
//        ){
//            result = (T)objectInputStream.readObject();
//        } catch (Exception e) {
//            logger.error("Failed to deserialize",e);
//        }
//        return result;
//    }

}
