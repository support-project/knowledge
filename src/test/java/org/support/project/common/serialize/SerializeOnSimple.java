package org.support.project.common.serialize;

@Serialize(value = SerializerValue.Simple, serializeOutputType = SerializeOutputType.XML)
public class SerializeOnSimple {

    private String str;
    private Integer num;

    /**
     * @return the str
     */
    public String getStr() {
        return str;
    }

    /**
     * @param str
     *            the str to set
     */
    public void setStr(String str) {
        this.str = str;
    }

    /**
     * @return the num
     */
    public Integer getNum() {
        return num;
    }

    /**
     * @param num
     *            the num to set
     */
    public void setNum(Integer num) {
        this.num = num;
    }

}
