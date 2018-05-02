package org.support.project.common.serialize;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.exception.SerializeException;
import org.support.project.common.util.PropertyUtil;

public class SerializeUtilsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testJaxb() throws SerializeException, IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		SerializeOnJaxb obj1 = new SerializeOnJaxb();
		obj1.setStr("hoge");
		obj1.setNum(100);
		SerializeUtils.writeObject(obj1, outputStream);
		SerializeOnJaxb obj2 = SerializeUtils.bytesToObject(outputStream.toByteArray(), SerializeOnJaxb.class);
		assertTrue(PropertyUtil.equalsProperty(obj1, obj2));
		outputStream.close();
		
		String str = SerializeUtils.objectToString(obj1);
		obj2 = SerializeUtils.stringToObject(str, SerializeOnJaxb.class);
		assertTrue(PropertyUtil.equalsProperty(obj1, obj2));
	}

	@Test
	public void testSimple() throws SerializeException, IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		SerializeOnSimple obj1 = new SerializeOnSimple();
		obj1.setStr("hoge");
		obj1.setNum(100);
		SerializeUtils.writeObject(obj1, outputStream);
		SerializeOnSimple obj2 = SerializeUtils.bytesToObject(outputStream.toByteArray(), SerializeOnSimple.class);
		assertTrue(PropertyUtil.equalsProperty(obj1, obj2));
		outputStream.close();
		
		String str = SerializeUtils.objectToString(obj1);
		obj2 = SerializeUtils.stringToObject(str, SerializeOnSimple.class);
		assertTrue(PropertyUtil.equalsProperty(obj1, obj2));
	}

	@Test
	public void testJSONIC() throws SerializeException, IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		SerializeOnJSONIC obj1 = new SerializeOnJSONIC();
		obj1.setStr("hoge");
		obj1.setNum(100);
		SerializeUtils.writeObject(obj1, outputStream);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		SerializeOnJSONIC obj2 = SerializeUtils.bytesToObject(inputStream, SerializeOnJSONIC.class);
		assertTrue(PropertyUtil.equalsProperty(obj1, obj2));
		outputStream.close();
		
		String str = SerializeUtils.objectToString(obj1);
		obj2 = SerializeUtils.stringToObject(str, SerializeOnJSONIC.class);
		assertTrue(PropertyUtil.equalsProperty(obj1, obj2));
	}
	
	@Test
	public void testSerializable() throws SerializeException, IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		SerializeOnSerializable obj1 = new SerializeOnSerializable();
		obj1.setStr("hoge");
		obj1.setNum(100);
		SerializeUtils.writeObject(obj1, outputStream);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		SerializeOnSerializable obj2 = SerializeUtils.bytesToObject(inputStream, SerializeOnSerializable.class);
		assertTrue(PropertyUtil.equalsProperty(obj1, obj2));
		outputStream.close();
	}

	@Test
	public void testDefault() throws SerializeException, IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		SerializeOnDefault obj1 = new SerializeOnDefault();
		obj1.setStr("hoge");
		obj1.setNum(100);
		SerializeUtils.writeObject(obj1, outputStream);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		SerializeOnDefault obj2 = SerializeUtils.bytesToObject(inputStream, SerializeOnDefault.class);
		assertTrue(PropertyUtil.equalsProperty(obj1, obj2));
		outputStream.close();
	}
	

}
