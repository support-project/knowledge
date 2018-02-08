package org.support.project.web.common;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class ValueConverterTest {

    @Test
    public void testConv() {
        String[] test01 = { "hoge" };
        String result01 = ValueConverter.conv(test01, String.class);
        assertEquals(test01[0], result01);

        String[] test02 = { "1" };
        Integer result02 = ValueConverter.conv(test02, Integer.class);
        assertEquals(new Integer(test02[0]), result02);

        String[] test03 = { "1.5" };
        Double result03 = ValueConverter.conv(test03, Double.class);
        assertEquals(new Double(test03[0]), result03);

        String[] test04 = { "1" };
        Short result04 = ValueConverter.conv(test04, Short.class);
        assertEquals(new Short(test04[0]), result04);

        String[] test05 = { "1" };
        Long result05 = ValueConverter.conv(test05, Long.class);
        assertEquals(new Long(test05[0]), result05);

        String[] test06 = { "1.6" };
        Float result06 = ValueConverter.conv(test06, Float.class);
        assertEquals(new Float(test06[0]), result06);

        String[] test07 = { "true" };
        Boolean result07 = ValueConverter.conv(test07, Boolean.class);
        assertEquals(new Boolean(test07[0]), result07);

        String[] test08 = { "false" };
        Boolean result08 = ValueConverter.conv(test08, Boolean.class);
        assertEquals(new Boolean(test08[0]), result08);
    }

    @Test
    public void testConv2() {
        String[] test01 = { "1" };
        int result01 = ValueConverter.conv(test01, int.class);
        assertTrue(1 == result01);

        String[] test02 = { "1.7" };
        double result02 = ValueConverter.conv(test02, double.class);
        assertTrue(1.7 == result02);

        String[] test04 = { "1" };
        short result04 = ValueConverter.conv(test04, short.class);
        assertTrue(1 == result04);

        String[] test05 = { "1" };
        long result05 = ValueConverter.conv(test05, long.class);
        assertTrue(1 == result05);

        String[] test06 = { "1.6" };
        float result06 = ValueConverter.conv(test06, float.class);
        assertTrue(((float) 1.6) == result06);

        String[] test07 = { "true" };
        boolean result07 = ValueConverter.conv(test07, boolean.class);
        assertTrue(result07);

    }

    @Test
    public void testConv3() {
        String[] test01 = { "hoge1", "hoge2" };
        String[] result01 = ValueConverter.conv(test01, String[].class);
        assertArrayEquals(test01, result01);

        String[] test02 = { "1", "2" };
        Integer[] result02 = ValueConverter.conv(test02, Integer[].class);
        Integer[] check02 = { 1, 2 };
        assertArrayEquals(check02, result02);

        String[] test03 = { "1.3", "2.6" };
        Double[] result03 = ValueConverter.conv(test03, Double[].class);
        Double[] check03 = { 1.3, 2.6 };
        assertArrayEquals(check03, result03);

        String[] test04 = { "true", "false", "true", "false" };
        Boolean[] result04 = ValueConverter.conv(test04, Boolean[].class);
        Boolean[] check04 = { true, false, true, false };
        assertArrayEquals(check04, result04);

        String[] test05 = { "1", "2" };
        int[] result05 = ValueConverter.conv(test05, int[].class);
        int[] check05 = { 1, 2 };
        assertArrayEquals(check05, result05);

        String[] test06 = { "1.6", "2.7" };
        double[] result06 = ValueConverter.conv(test06, double[].class);
        double[] check06 = { 1.6, 2.7 };
        int cnt = 0;
        for (double d : check06) {
            assertTrue(d == result06[cnt]);
            cnt++;
        }
    }

    @Test
    public void testConv4() {
        String[] test01 = { "hoge1", "hoge2" };
        List<String> result01 = ValueConverter.conv(test01, List.class);
        int cnt = 0;
        for (String v : test01) {
            assertEquals(v, result01.get(cnt));
            cnt++;
        }

        // リストの場合、そのリストの中にどんな型を入れれば良いのか分からない(相称型は取得出来ない)
        // この為、Listを使う時は全てStringになってしまうので注意
        String[] test02 = { "1", "2" };
        List<Integer> result02 = ValueConverter.conv(test02, List.class);
        // ↑でIntegerと書いてあるのにも関わらず、Listの中身はStringになる

        cnt = 0;
        for (String v : test02) {
            assertEquals(v, result02.get(cnt));
            cnt++;
        }
    }

}
