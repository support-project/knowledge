package org.support.project.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StringUtilsTest {

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
    public void testIsEmptyString() {
        assertTrue(StringUtils.isEmpty(null));
        assertTrue(StringUtils.isEmpty(""));

        assertFalse(!StringUtils.isEmpty(null));
        assertFalse(!StringUtils.isEmpty(""));
    }

    @Test
    public void testIsEmptyObject() {
        assertTrue(StringUtils.isEmpty(null));
        assertTrue(!StringUtils.isEmpty(new Object()));
    }

    @Test
    public void testIsNotEmptyString() {
        assertTrue(!StringUtils.isNotEmpty(null));
        assertTrue(!StringUtils.isNotEmpty(""));
        assertFalse(StringUtils.isNotEmpty(null));
        assertFalse(StringUtils.isNotEmpty(""));
    }

    @Test
    public void testIsInteger() {
        assertTrue(StringUtils.isInteger("1"));
        assertFalse(StringUtils.isInteger("1.5"));
    }

    @Test
    public void testIsLong() {
        assertTrue(StringUtils.isLong("1"));
        assertFalse(StringUtils.isLong("1.5"));
    }

    @Test
    public void testIsNumericString() {
        assertTrue(StringUtils.isNumeric("1.32"));
        assertFalse(StringUtils.isNumeric("1.5A"));
    }

    @Test
    public void testZeroPadding() {
        String str = StringUtils.zeroPadding(1, 3);
        assertEquals("001", str);
    }

    @Test
    public void testGetExtension() {
        String ex = StringUtils.getExtension("aaa.txt");
        assertEquals(".txt", ex);
    }

    @Test
    public void testGetFileName() {
        String fileName = StringUtils.getFileName("/data/aaa.txt");
        assertEquals("aaa.txt", fileName);
    }

    @Test
    public void testNextMaxNo() {
        String str = StringUtils.nextMaxNo(10, "1");
        assertEquals("0000000002", str);
    }

    @Test
    public void testAddLength() {
        String str = StringUtils.addLength(1, 3);
        assertEquals("001", str);
    }

    @Test
    public void testNextMaxNoHex() {
        String str = StringUtils.nextMaxNoHex(10, "C");
        assertEquals("000000000D", str);
    }

    @Test
    public void testAddLengthHex() {
        String str = StringUtils.addLengthHex(0x1C, 3);
        assertEquals("01C", str);
    }

    @Test
    public void testDellControlString() {
        String str = StringUtils.dellControlString("123\n456");
        assertEquals("123 456", str);
    }

    @Test
    public void testTrimUni() {
        String str = StringUtils.trimUni(" 123　");
        assertEquals("123", str);
    }

    @Test
    public void testLineTrim() throws IOException {
        String str = StringUtils.lineTrim(" 123\n 456 ");
        assertEquals("123456", str);
    }

    @Test
    public void testGetByteLength() {
        int len = StringUtils.getByteLength("123");
        assertEquals(3, len);
        len = StringUtils.getByteLength("あいう");
        assertEquals(6, len);
    }

    @Test
    public void testFormatNumber() {
        String str = StringUtils.formatNumber((long) 123456);
        assertEquals("123,456", str);
    }

    @Test
    public void testSplitStringString() {
        List<String> strs = StringUtils.splitString("テストです。どうかな？");
        assertEquals("テストです。", strs.get(0));
        assertEquals("どうかな？", strs.get(1));
    }

    @Test
    public void testRandamGen() {
        String str = StringUtils.randamGen(16);
        assertEquals(16, str.length());

    }

    @Test
    public void testConvHankaku() {
        String str = StringUtils.convHankaku("abcd");
        assertEquals("ａｂｃｄ", str);
    }

}
