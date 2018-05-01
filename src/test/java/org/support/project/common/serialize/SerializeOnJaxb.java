package org.support.project.common.serialize;

import javax.xml.bind.annotation.XmlRootElement;

@Serialize(value = SerializerValue.Jaxb)
@XmlRootElement
public class SerializeOnJaxb {

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
