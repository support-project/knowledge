package org.support.project.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DateUtilsTest {

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
    public void testFormatTransferDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, 0, 1, 1, 1, 1);
        calendar.set(Calendar.MILLISECOND, 1);
        
        String str = DateUtils.formatTransferDateTime(calendar.getTime());
        assertEquals("20150101010101001", str);
    }

    @Test
    public void testParseTransferDateTime() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, 0, 1, 1, 1, 1);
        calendar.set(Calendar.MILLISECOND, 1);
        
        Date d = DateUtils.parseTransferDateTime("20150101010101001");
        assertEquals(calendar.getTimeInMillis(), d.getTime());
    }

    @Test
    public void testDifferenceDaysStringString() throws ParseException {
        int d = DateUtils.differenceDays("2015/01/01", "2015/01/05");
        assertEquals(-4, d);
    }

    @Test
    public void testDifferenceDaysDateDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, 0, 1, 1, 1, 1);
        calendar.set(Calendar.MILLISECOND, 1);
        
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2015, 0, 5, 1, 1, 1);
        calendar2.set(Calendar.MILLISECOND, 1);
        
        int d = DateUtils.differenceDays(calendar2.getTime(), calendar.getTime());
        assertEquals(4, d);
    }

    @Test
    public void testGetRoundsDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, 0, 1, 1, 1, 1);
        calendar.set(Calendar.MILLISECOND, 1);
        
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2015, 0, 1, 12, 0, 0);
        calendar2.set(Calendar.MILLISECOND, 0);
        
        Date d = DateUtils.getRoundsDate(calendar.getTime());
        assertEquals(calendar2.getTimeInMillis(), d.getTime());        
    }

}
