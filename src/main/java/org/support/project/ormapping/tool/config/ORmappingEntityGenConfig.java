package org.support.project.ormapping.tool.config;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.support.project.common.serialize.Serialize;
import org.support.project.common.serialize.SerializerValue;

/**
 * OR Mapping ToolのEntity自動生成の設定
 * @author Koda
 */
@Serialize(value = SerializerValue.Jaxb)
@XmlRootElement
public class ORmappingEntityGenConfig implements Serializable {
	/** シリアルバージョン */
	private static final long serialVersionUID = 1L;
	/** エンティティを出力するディレクトリ */
	private String entityOutDir;
	/** エンティティのパッケージ名 */
	private String entityPackage;
	/** エンティティのサフィックス */
	private String entitySuffix;
	/**
	 * @return entityOutDir
	 */
	public String getEntityOutDir() {
		return entityOutDir;
	}

	/**
	 * @return entityPackage
	 */
	public String getEntityPackage() {
		return entityPackage;
	}

	/**
	 * @param entityPackage セットする entityPackage
	 */
	public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}

	/**
	 * @return entitySuffix
	 */
	public String getEntitySuffix() {
		return entitySuffix;
	}

	/**
	 * @param entitySuffix セットする entitySuffix
	 */
	public void setEntitySuffix(String entitySuffix) {
		this.entitySuffix = entitySuffix;
	}

	/**
	 * @param entityOutDir セットする entityOutDir
	 */
	public void setEntityOutDir(String entityOutDir) {
		this.entityOutDir = entityOutDir;
	}
	
	
}
