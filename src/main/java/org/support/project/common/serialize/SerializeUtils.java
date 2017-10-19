package org.support.project.common.serialize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.support.project.common.exception.SerializeException;
import org.support.project.common.serialize.impl.SerializerForJSONOnJSONICImpl;
import org.support.project.common.serialize.impl.SerializerForSerializableImpl;
import org.support.project.common.serialize.impl.SerializerForXmlOnJaxBImpl;
import org.support.project.common.serialize.impl.SerializerForXmlOnSimpleImpl;
import org.support.project.common.util.Base64Utils;

/**
 * バイト(文字列)とObjectの変換用ユーティリティ
 * 
 * @author Koda
 * 
 */
public final class SerializeUtils {

    /** エンコードに利用するCharset */
    public static final Charset CHARSET = Charset.forName("UTF-8");

    /**
     * Serializerのインスタンスを保持するマップ
     */
    private static Map<String, Serializer> xmlSerializerInstanceMap = null;

    /**
     * コンストラクタ
     */
    private SerializeUtils() {
    }

    /**
     * Serializerのインスタンスを取得する
     * 
     * @param <T>
     *            Serializer
     * @param class1
     *            インスタンスを取得するクラス
     * @return インスタンス
     * @throws SerializeException
     *             SerializeException
     */
    private static <T> Serializer getSerializerInstanse(final Class<? extends Serializer> class1) throws SerializeException {
        try {
            if (xmlSerializerInstanceMap == null) {
                xmlSerializerInstanceMap = new HashMap<String, Serializer>();
            }
            if (!xmlSerializerInstanceMap.containsKey(class1.getName())) {
                Serializer serializer = class1.newInstance();
                xmlSerializerInstanceMap.put(class1.getName(), serializer);
            }
            return xmlSerializerInstanceMap.get(class1.getName());
        } catch (Exception e) {
            throw new SerializeException(e);
        }
    }

    /**
     * オブジェクトをバイト表現に変換する
     * 
     * @param obj
     *            変換するオブジェクト
     * @return バイト
     * @throws SerializeException
     *             SerializeException
     */
    public static byte[] objectTobyte(final Object obj) throws SerializeException {
        return objectTobyte(obj, SerializerForJSONOnJSONICImpl.class); // デフォルトはJSON
    }
    
    /**
     * conv object to bytes
     * @param obj object
     * @param defaultSerializer Serializer
     * @return bytes
     * @throws SerializeException SerializeException
     */
    public static byte[] objectTobyte(final Object obj, final Class<? extends Serializer> defaultSerializer) throws SerializeException {
        Serializer serializer = null;
        // リフレクションでくるまれている場合、オリジナルのクラスを取得
        String className = obj.getClass().getName();
        if (className.indexOf("$$EnhancedBy") != -1) {
            className = className.substring(0, className.indexOf("$$EnhancedBy"));
        }
        Class<?> c;
        try {
            c = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new SerializeException("The class " + className + " is not find.");
        }

        if (c.isAnnotationPresent(Serialize.class)) {
            Serialize xmlSerializer = c.getAnnotation(Serialize.class);
            SerializerValue value = xmlSerializer.value();
            if (value.equals(SerializerValue.Jaxb)) {
                // JaxbでXMLを生成
                serializer = getSerializerInstanse(SerializerForXmlOnJaxBImpl.class);
            } else if (value.equals(SerializerValue.Simple)) {
                // SimpleでXMLを生成
                serializer = getSerializerInstanse(SerializerForXmlOnSimpleImpl.class);
            } else if (value.equals(SerializerValue.JSONIC)) {
                // JSONICでJSONを生成
                serializer = getSerializerInstanse(SerializerForJSONOnJSONICImpl.class);
            } else if (value.equals(SerializerValue.Serializer)) {
                // 独自定義のシリアライズクラスを利用
                Class<? extends Serializer> clazz = xmlSerializer.serializerClass();
                if (clazz == null) {
                    throw new SerializeException("The class that uses it for serializing is not specified. ");
                }
                serializer = getSerializerInstanse(clazz);
            } else if (value.equals(SerializerValue.Serializable)) {
                // Xmlにしないでjava.io.Serializableでシリアライズする
                serializer = getSerializerInstanse(SerializerForSerializableImpl.class);
            }
        }
        if (serializer == null) {
            serializer = getSerializerInstanse(defaultSerializer);
        }
        return serializer.objectTobytes(obj);
    }

    /**
     * オブジェクトをXMLに変換する
     * 
     * @param obj
     *            変換するオブジェクト
     * @return オブジェクトのXML表現
     * @throws SerializeException
     *             SerializeException
     */
    public static String objectToString(final Object obj) throws SerializeException {
        // シリアライズの形式によってStringの形式を変える
        String className = obj.getClass().getName();
        if (className.indexOf("$$EnhancedBy") != -1) {
            className = className.substring(0, className.indexOf("$$EnhancedBy"));
        }
        Class<?> c;
        try {
            c = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new SerializeException("The class " + className + " is not find.");
        }

        if (c.isAnnotationPresent(Serialize.class)) {
            Serialize xmlSerializer = c.getAnnotation(Serialize.class);
            SerializerValue value = xmlSerializer.value();
            if (value.equals(SerializerValue.Jaxb)) {
                // JaxbでXMLを生成
                return objectToXml(obj);
            } else if (value.equals(SerializerValue.Simple)) {
                // SimpleでXMLを生成
                return objectToXml(obj);
            } else if (value.equals(SerializerValue.JSONIC)) {
                // JSONICでJSONを生成
                return objectToJSON(obj);
            } else if (value.equals(SerializerValue.Serializer)) {
                // 独自定義のシリアライズクラスを利用
                Class<? extends Serializer> clazz = xmlSerializer.serializerClass();
                if (clazz == null) {
                    throw new SerializeException("The class that uses it for serializing is not specified. ");
                }
                SerializeOutputType outputType = xmlSerializer.serializeOutputType();
                if (outputType == null) {
                    throw new SerializeException("The class that uses it for serializing is not specified. ");
                }
                if (outputType.equals(SerializeOutputType.XML)) {
                    // XMLで出力
                    return objectToXml(obj);
                } else if (outputType.equals(SerializeOutputType.JSON)) {
                    // XMLで出力
                    return objectToJSON(obj);
                } else if (outputType.equals(SerializeOutputType.Serializable)) {
                    // シリアライズの結果をBase64で出力
                    return objectToBase64(obj);
                }
            } else if (value.equals(SerializerValue.Serializable)) {
                // シリアライズの結果をBase64で出力
                return objectToBase64(obj);
            }
        }
        return objectToBase64(obj);
    }

    /**
     * オブジェクトをJSON形式で出力する
     * 
     * @param obj object
     * @return json string
     * @throws SerializeException SerializeException
     */
    public static String objectToJSON(Object obj) throws SerializeException {
        byte[] bytes = objectTobyte(obj);
        String json = new String(bytes, CHARSET);
        return json;
    }

    /**
     * オブジェクトをbyte配列にし、それをBase64エンコードで文字列にする
     * 
     * @param obj object
     * @return base64 string 
     * @throws SerializeException SerializeException
     */
    public static String objectToBase64(Object obj) throws SerializeException {
        byte[] bytes = objectTobyte(obj, SerializerForSerializableImpl.class);
        String string = Base64Utils.toBase64(bytes);
        return string;
    }

    /**
     * Base64のエンコードの内容を、オブジェクトに変換する
     * 
     * @param base64 string
     * @param type type
     * @return object
     * @throws SerializeException SerializeException
     */
    public static  <T> T Base64ToObject(String base64, final Class<? extends T> type) throws SerializeException {
        byte[] bytes = Base64Utils.fromBase64(base64);
        return bytesToObject(bytes, type);
    }

    
    
    
    /**
     * オブジェクトをXML形式で出力する
     * 
     * @param obj Object
     * @return xml string 
     * @throws SerializeException SerializeException
     */
    public static String objectToXml(Object obj) throws SerializeException {
        byte[] bytes = objectTobyte(obj, SerializerForXmlOnSimpleImpl.class);
        String xml = new String(bytes, CHARSET);
        /*
         * if (xml != null) { // xmlの中で利用できない管理文字を消す xml = XmlUtils.convControlChar(xml); }
         */
        return xml;
    }

    /**
     * オブジェクトをXMLにし、アウトプットストリームに書き出す
     * 
     * @param obj
     *            変換するオブジェクト
     * @param outputStream
     *            OutputStream
     * @throws IOException
     *             IOException
     * @throws SerializeException
     *             SerializeException
     */
    public static void writeObject(final Object obj, final OutputStream outputStream) throws SerializeException, IOException {
        outputStream.write(objectTobyte(obj));
    }

    /**
     * オブジェクトをXMLにし、アウトプットストリームに書き出す
     * 
     * @param obj
     *            変換するオブジェクト
     * @param writer
     *            Writer
     * @throws IOException
     *             IOException
     * @throws SerializeException
     *             SerializeException
     */
    public static void writeObject(final Object obj, final Writer writer) throws SerializeException, IOException {
        writer.write(objectToString(obj));
    }

    /**
     * シリアライズに使うSerializerを取得（デフォルトはJSONから）
     * @param type
     * @return
     */
    private static Serializer getSerializer(final Class<?> type) {
        Serializer serializer = null;
        if (type.isAnnotationPresent(Serialize.class)) {
            Serialize xmlSerializer = type.getAnnotation(Serialize.class);
            SerializerValue value = xmlSerializer.value();
            if (value.equals(SerializerValue.Simple)) {
                // SimpleでXMLを生成
                serializer = getSerializerInstanse(SerializerForXmlOnSimpleImpl.class);
            } else if (value.equals(SerializerValue.Jaxb)) {
                // JaxBでXMLを生成
                serializer = getSerializerInstanse(SerializerForXmlOnJaxBImpl.class);
            } else if (value.equals(SerializerValue.JSONIC)) {
                // JSONICでJSONを生成
                serializer = getSerializerInstanse(SerializerForJSONOnJSONICImpl.class);
            } else if (value.equals(SerializerValue.Serializer)) {
                // 独自定義のシリアライズクラスを利用
                Class<? extends Serializer> clazz = xmlSerializer.serializerClass();
                if (clazz == null) {
                    throw new SerializeException("The class that uses it for serializing is not specified. ");
                }
                serializer = getSerializerInstanse(clazz);
            } else if (value.equals(SerializerValue.Serializable)) {
                // Xmlにしないでjava.io.Serializableでシリアライズする
                serializer = getSerializerInstanse(SerializerForSerializableImpl.class);
            }
        }
        if (serializer == null) {
            serializer = getSerializerInstanse(SerializerForJSONOnJSONICImpl.class);
        }
        return serializer;
    }
    
    /**
     * 入力をオブジェクトに変換する
     * 
     * @param <T>
     *            クラスの型
     * @param content
     *            バイト配列
     * @param type
     *            Class
     * @return オブジェクト
     * @throws SerializeException
     *             XmlException
     */
    public static <T> T bytesToObject(final byte[] content, final Class<? extends T> type) throws SerializeException {
        Serializer serializer = getSerializer(type); 
        return bytesToObject(content, type, serializer);
    }
    
    /**
     * 入力をオブジェクトに変換する
     * 
     * @param <T>
     *            クラスの型
     * @param content
     *            バイト配列
     * @param type
     *            Class
     * @param serializer
     *            Serializer
     * @return オブジェクト
     * @throws SerializeException
     *             XmlException
     */
    public static <T> T bytesToObject(final byte[] content, final Class<? extends T> type, Serializer serializer) throws SerializeException {
        return serializer.bytesToObject(content, type);
    }

    /**
     * 入力(XMLのストリーム)からオブジェクトに変換する
     * 
     * @param <T>
     *            クラスの型
     * @param inputStream
     *            InputStream
     * @param type
     *            Class
     * @param serializer
     *            Serializer
     * @return オブジェクト
     * @throws SerializeException
     *             XmlException
     * @throws IOException
     *             IOException
     */
    public static <T> T bytesToObject(final InputStream inputStream, final Class<? extends T> type, Serializer serializer) throws SerializeException, IOException {
        byte[] buf = new byte[256];
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            int n;
            while ((n = inputStream.read(buf, 0, buf.length)) != -1) {
                outputStream.write(buf, 0, n);
            }
            return bytesToObject(outputStream.toByteArray(), type, serializer);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
    /**
     * 入力(XMLのストリーム)からオブジェクトに変換する
     * 
     * @param <T>
     *            クラスの型
     * @param inputStream
     *            InputStream
     * @param type
     *            Class
     * @return オブジェクト
     * @throws SerializeException
     *             XmlException
     * @throws IOException
     *             IOException
     */
    public static <T> T bytesToObject(final InputStream inputStream, final Class<? extends T> type) throws SerializeException, IOException {
        Serializer serializer = getSerializer(type); 
        return bytesToObject(inputStream, type, serializer);
    }
    
    

    /**
     * 入力をオブジェクトに変換する
     * 
     * @param <T>
     *            クラスの型
     * @param content
     *            文字列
     * @param type
     *            Class
     * @return オブジェクト
     * @throws SerializeException
     *             XmlException
     */
    public static <T> T stringToObject(final String content, final Class<? extends T> type) throws SerializeException {
        return bytesToObject(content.getBytes(), type);
    }

}
