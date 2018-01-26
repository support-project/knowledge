package org.support.project.common.statistic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.support.project.common.statistic.Statistics;
import org.support.project.common.util.PropertyUtil;

public class StatisticsTest {

    @Test
    public void testMin() {
        Double[] nums = { 1.00, 2.00, 3.5, 4.00, 5.5, 6.00 };
        Double min = Statistics.min(nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の最小値は " + min);
        assertEquals(new Double(1.00), min);
    }

    @Test
    public void testMax() {
        Double[] nums = { 1.00, 2.00, 3.5, 4.00, 5.5, 6.00 };
        Double max = Statistics.max(nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の最大値は " + max);
        assertEquals(new Double(6.00), max);
    }

    @Test
    public void testSum() {
        Double[] nums = { 1.00, 2.00, 3.5, 4.00, 5.5, 6.00 };
        Double total = Statistics.sum(nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の合計は " + total);
        assertEquals(new Double(22.00), total);
    }

    @Test
    public void testProduct() {
        Double[] nums = { 1.00, 2.00, 3.5, 4.00, 5.5, 6.00 };
        Double total = Statistics.product(nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の積は " + total);
        assertEquals(new Double(924.00), total);
    }

    @Test
    public void testCount() {
        Double[] nums = { 1.00, 2.00, 3.5, 4.00, 5.5, 6.00 };
        Integer total = Statistics.count(nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の件数は " + total);
        assertEquals(new Integer(6), total);
    }

    @Test
    public void testArimean() {
        Double[] nums = { 1.00, 2.00, 3.5, 4.00, 5.5, 6.00 };
        Double average = Statistics.arimean(nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の相加平均は " + average);
        assertEquals(new Double(3.6666666666666665), average);
    }

    @Test
    public void testGeomean() {
        Double[] nums = { 2.00, 4.00, 8.00 };
        Double average = Statistics.geomean(nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の幾何平均は " + average);
        assertEquals(new Double(4.0), average);

        Double[] nums2 = { 2.00, 8.00 };
        Double average2 = Statistics.geomean(nums2);
        System.out.println(PropertyUtil.reflectionToString(nums2) + " の幾何平均は " + average2);
        assertEquals(new Double(4.0), average2);

        Double[] nums3 = { 4.00, 1.00, 1.00 / 32 };
        Double average3 = Statistics.geomean(nums3);
        System.out.println(PropertyUtil.reflectionToString(nums3) + " の幾何平均は " + average3);
        assertEquals(new Double(1.00 / 2.00), average3);
    }

    /**
     * 調和平均が登場する問題の例について。 「行きは時速4km、帰りは時速6kmで歩いた場合、平均時速は何kmですか」 平均時速とは時速の (相加) 平均ではなく、 全体の距離を全体の時間で割ったものですから注意が必要で、
     * 答えは時速4.8kmになります。時速4kmで歩いた時間の方が長いので、 答えは時速5kmよりも遅いのです。この 4.8 という数は、4 と 6 の調和平均です。
     */
    @Test
    public void testHarmean() {
        Double[] nums = { 4.00, 6.00 };
        Double average = Statistics.harmean(nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の調和平均は " + average);
        assertEquals(new Double(4.800000000000001), average);
    }

    @Test
    public void testWeimean() {
        Map<String, Double> weights = new HashMap<>();
        weights.put("数学", 2.00);
        weights.put("国語", 1.00);
        weights.put("英語", 1.00);

        List<Map<String, Double>> nums = new ArrayList<>();

        Map<String, Double> num1 = new HashMap<>();
        num1.put("数学", 70.00);
        num1.put("国語", 80.00);
        num1.put("英語", 60.00);

        Map<String, Double> num2 = new HashMap<>();
        num2.put("数学", 75.00);
        num2.put("国語", 60.00);
        num2.put("英語", 95.00);

        nums.add(num1);
        nums.add(num2);

        Double average = Statistics.weimean(nums, weights);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の加重平均は " + average);
        assertEquals(new Double(73.125), average);
    }

    /*
     * 測定値１，２，３，４，５について。
     * 
     * (1) 平均値＝３、自乗の平均値＝１１より 分散＝自乗の平均値－平均値の自乗より ＝１１－（３×３）＝１１－９＝２
     * 
     * (2) 平方和＝（１×１＋２×２＋３×３＋４×４＋５×５）－５×３×３ ＝１０ 自由度＝５－１＝４ 分散＝平方和÷自由度より ＝１０÷４＝２．５
     * 
     * １は標本分散 ２は不偏分散
     * 
     * 1/nΣ(xi-μ)^2=1/nΣxi^2-2μ/nΣxi+μ^2 =1/nΣxi^2-μ^2 (∵μ=1/nΣxi)
     * 
     * 
     * 1/(n-1)Σ(xi-μ)^2=1/(n-1)Σxi^2-2μ/(n-1)Σxi+nμ^2/(n-1) =1/(n-1)Σxi^2-nμ^2/(n-1) (∵Σxi=nμ)
     * 
     */

    @Test
    public void testVar() {
        Double[] nums = { 100.00, 80.00, 90.00, 30.00, 70.00 };
        Double var = Statistics.var(nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の分散(不偏分散)は " + var);
        assertEquals(new Double(730), var);
    }

    @Test
    public void testStdev() {
        Double[] nums = { 100.00, 80.00, 90.00, 30.00, 70.00 };
        Double var = Statistics.stdev(nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の標準偏差(統計値の標準偏差)は " + var);
        assertEquals(new Double(27.018512172212592), var);
    }

    @Test
    public void testVarp() {
        Double[] nums = { 100.00, 80.00, 90.00, 30.00, 70.00 };
        Double var = Statistics.varp(nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の分散(標本分散)は " + var);
        assertEquals(new Double(584), var);
    }

    @Test
    public void testStdevp() {
        Double[] nums = { 100.00, 80.00, 90.00, 30.00, 70.00 };
        Double var = Statistics.stdevp(nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の標準偏差(母集団の標準偏差)は " + var);
        assertEquals(new Double(24.166091947189145), var);
    }

    @Test
    public void testCovariance() {
        Double[] nums1 = { 85.00, 63.00, 78.00, 75.00, 66.00, 56.00, 89.00, 94.00, 81.00, 71.00 };
        Double[] nums2 = { 90.00, 58.00, 71.00, 78.00, 62.00, 60.00, 95.00, 88.00, 80.00, 75.00 };
        Double covariance = Statistics.covariance(nums1, nums2);
        System.out.println(PropertyUtil.reflectionToString(nums1) + PropertyUtil.reflectionToString(nums2) + " の共分散は " + covariance);
        assertEquals(new Double(129.53999999999996), covariance);
    }

    @Test
    public void testCorrelation() {
        // 国語テスト：85,63,78,75,66,56,89,94,81,71
        // 英語テスト：90,58,71,78,62,60,95,88,80,75
        // 相関は0.9217473

        // -1 ～ -0.7 強い負の相関
        // -0.7 ～ -0.4 かなりの負の相関
        // -0.4 ～ -0.2 やや相関あり
        // -0.2 ～ 0 ほとんど相関なし
        // 0 ～ 0.2 ほとんど相関なし
        // 0.2 ～ 0.4 やや相関あり
        // 0.4 ～ 0.7 かなりの正の相関
        // 0.7 ～ 1 強い正の相関

        Double[] nums1 = { 85.00, 63.00, 78.00, 75.00, 66.00, 56.00, 89.00, 94.00, 81.00, 71.00 };
        Double[] nums2 = { 90.00, 58.00, 71.00, 78.00, 62.00, 60.00, 95.00, 88.00, 80.00, 75.00 };
        Double correlation = Statistics.correlation(nums1, nums2);
        System.out.println(PropertyUtil.reflectionToString(nums1) + PropertyUtil.reflectionToString(nums2) + " の相関は " + correlation);
        assertEquals(new Double(0.921747315216523), correlation);
    }

    @Test
    public void testSkew() {
        // 歪度は正規分布に比べて、どれだけ歪んでいるかを表す。
        // 0は左右対称、正の数は右にゆがみ、負の場合左に歪む
        Double[] nums = { 100.00, 80.00, 90.00, 30.00, 70.00 };
        Double var = Statistics.skew(nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の歪度は " + var);
        assertEquals(new Double(-1.3385038869326558), var);
    }

    @Test
    public void testKurt() {
        // 尖度は正規分布に比べて、どれだけ尖っているかを表す。
        // 0は正規分布と同じ、正の数は鋭角、負の場合平坦になる
        Double[] nums = { 100.00, 80.00, 90.00, 30.00, 70.00 };
        Double var = Statistics.kurt(nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の尖度は " + var);
        assertEquals(new Double(2.021017076374555), var);
    }

    @Test
    public void testRank() {
        Double[] nums = { 240.00, // 4
            175.00, // 6
            260.00, // 3
            320.00, // 1
            180.00, // 5
            270.00 // 2
        };
        Double var = Statistics.rank(nums[0], nums);
        System.out.println(nums[0] + " のランクは " + var);
        assertEquals(new Double(4.00), var);

        var = Statistics.rank(nums[3], nums);
        System.out.println(nums[3] + " のランクは " + var);
        assertEquals(new Double(1.00), var);
    }

    @Test
    public void testMode() {
        Double[] nums = { 100.00, 30.00, 70.00, 80.00, 90.00, 30.00, 70.00, 70.00 };
        Double var = Statistics.mode(nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の最頻値は " + var);
        assertEquals(new Double(70.00), var);
    }

    @Test
    public void testMedian() {
        Double[] nums = { 100.00, 30.00, 70.00, 80.00, 90.00, 30.00, 70.00, 70.00 };
        Double var = Statistics.median(nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の中央値は " + var);
        assertEquals(new Double(70.00), var);
    }

    @Test
    public void testPercentile() {
        Double[] nums = { 100.00, 80.00, 90.00, 30.00, 70.00 };
        Double var = Statistics.percentile(0.5, nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の " + 0.5 + " のパーセンタイル値は " + var);
        assertEquals(new Double(80.00), var);

        var = Statistics.percentile(0.4, nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の " + 0.4 + " のパーセンタイル値は " + var);
        assertEquals(new Double(76.00), var);

        Double[] nums2 = { 100.00, 80.00, 90.00, 30.00, 70.00, 60.00 };
        var = Statistics.percentile(0.5, nums2);
        System.out.println(PropertyUtil.reflectionToString(nums2) + " の " + 0.5 + " のパーセンタイル値は " + var);
        assertEquals(new Double(75.00), var);

        var = Statistics.percentile(0.4, nums2);
        System.out.println(PropertyUtil.reflectionToString(nums2) + " の " + 0.4 + " のパーセンタイル値は " + var);
        assertEquals(new Double(70.00), var);

        var = Statistics.percentile(0.9, nums2);
        System.out.println(PropertyUtil.reflectionToString(nums2) + " の " + 0.9 + " のパーセンタイル値は " + var);
        assertEquals(new Double(95.00), var);
    }

    @Test
    public void testQuartile() {
        Double[] nums = { 100.00, 80.00, 90.00, 30.00, 70.00, 60.00 };
        Double var = Statistics.quartile(1, nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の " + 1 + " の四分位は " + var);
        assertEquals(new Double(62.5), var);

        var = Statistics.quartile(2, nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の " + 2 + " の四分位は " + var);
        assertEquals(new Double(75.00), var);

        var = Statistics.quartile(3, nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の " + 3 + " の四分位は " + var);
        assertEquals(new Double(87.5), var);

        var = Statistics.quartile(4, nums);
        System.out.println(PropertyUtil.reflectionToString(nums) + " の " + 3 + " の四分位は " + var);
        assertEquals(new Double(100.0), var);
    }

}
