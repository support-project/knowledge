package org.support.project.ormapping.conv;

import java.sql.ResultSet;

import org.support.project.ormapping.conv.impl.ObjectToDatabaseConvDefaultImpl;

public class ObjectToDatabaseConvFactory {
	
	public static ObjectToDatabaseConv getObjectToDatabaseConv(ResultSet rs) {
		return new ObjectToDatabaseConvDefaultImpl();
	}
	
	
}
