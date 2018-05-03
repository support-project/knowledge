package org.support.project.ormapping.conv.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.support.project.ormapping.conv.impl.ObjectToDatabaseConvDefaultImpl;

public class ObjectToDatabaseConvDefaultImplTest {

	@Test
	public void testGetPropertyToClumnName() {
		String test = "userRoleTest";
		
		ObjectToDatabaseConvDefaultImpl convDefaultImpl = new ObjectToDatabaseConvDefaultImpl();
		
		String column = convDefaultImpl.getPropertyToClumnName(test);
		
		assertEquals("USER_ROLE_TEST", column);
	}

}
