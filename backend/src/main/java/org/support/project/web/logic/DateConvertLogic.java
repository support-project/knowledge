package org.support.project.web.logic;

import java.lang.invoke.MethodHandles;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.support.project.common.config.AppConfig;
import org.support.project.common.config.ConfigLoader;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.util.JspUtil;

public class DateConvertLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * Get instance
     * @return instance
     */
    public static DateConvertLogic get() {
        return Container.getComp(DateConvertLogic.class);
    }

    
    /**
     * 日付の文字列を取得
     * 保存されている値はGMTなので、ブラウザのロケールで変換をかける
     * @param val 日付
     * @param request リクエスト
     * @return 日付の文字列
     */
    public String convertDate(Date val, HttpServletRequest request) {
        Locale locale = HttpUtil.getLocale(request);
        String offset = HttpUtil.getCookie(request, JspUtil.TIME_ZONE_OFFSET);
        return convertDate(val, locale, offset);
    }
    

    /**
     * 日付の文字列を取得
     * 保存されている値はGMTなので、ブラウザのロケールで変換をかける
     * @param val 日付
     * @param locale locale
     * @param offset timezone offset
     * @return 日付の文字列
     */
    public String convertDate(Date val, Locale locale, String offset) {
        // TimeZone zone = dateFormat.getTimeZone();
        TimeZone zone = null;
        // ブラウザからoffsetを取得して補正をかける(dateFormat.getTimeZone()を実行したら、GMTだった。。。）
        if (StringUtils.isEmpty(offset)) {
            AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
            zone = TimeZone.getTimeZone(appConfig.getTime_zone());
        } else {
            if (StringUtils.isInteger(offset)) {
                int off = Integer.parseInt(offset);
                return convertDate(val, locale, off);
            }
        }
        return convertDate(val, locale, zone);
    }

    /**
     * 日付の文字列を取得
     * 保存されている値はGMTなので、ブラウザのロケールで変換をかける
     * @param val 日付
     * @param locale locale
     * @param off timezone offset
     * @return 日付の文字列
     */
    public String convertDate(Date val, Locale locale, int off) {
        off = off / 60;
        TimeZone zone = null;
        // GMT+09:00 が日本
        StringBuilder offsetBuilder = new StringBuilder();
        offsetBuilder.append("GMT");
        if (off <= 0) {
            offsetBuilder.append("+0");
            off = off * -1; // 正の数へ
        } else {
            offsetBuilder.append("-0");
        }
        offsetBuilder.append(off);
        offsetBuilder.append(":00");
        LOG.trace(offsetBuilder.toString());

        zone = TimeZone.getTimeZone(offsetBuilder.toString());
        return convertDate(val, locale, zone);
    }
    
    /**
     * 日付の文字列を取得
     * 保存されている値はGMTなので、ブラウザのロケールで変換をかける
     * @param val 日付
     * @param locale locale
     * @param zone TimeZone
     * @return 日付の文字列
     */
    private String convertDate(Date val, Locale locale, TimeZone zone) {
        // DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, request.getLocale());
        // DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT, request.getLocale());
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT, locale);
        
        if (zone == null) {
            zone = dateFormat.getTimeZone();
        }
        if (LOG.isTraceEnabled()) {
            LOG.trace(zone.getDisplayName());
            LOG.trace(zone.getRawOffset());
            LOG.trace(zone);
        }

        // ZoneId zoneId = ZoneId.of(zone.getID());
        // ZonedDateTime ztime = ZonedDateTime.ofInstant(val.toInstant(), zoneId);
        // ztime.plusSeconds(zone.getRawOffset() / 1000);
        // Date date = Date.from(ztime.toInstant());
        
        StringBuilder builder = new StringBuilder();
        Date conv = DateUtils.now();
        conv.setTime(val.getTime() + zone.getRawOffset());
        builder.append(dateFormat.format(conv));

        return builder.toString();
    }
    
    /**
     * タイムゾーンの取得
     * @param locale locale
     * @param off timezone offset
     * @return TimeZone
     */
    public TimeZone getTimezone(Locale locale, int off) {
        off = off / 60;
        TimeZone zone = null;
        // GMT+09:00 が日本
        StringBuilder offsetBuilder = new StringBuilder();
        offsetBuilder.append("GMT");
        if (off <= 0) {
            offsetBuilder.append("+0");
            off = off * -1; // 正の数へ
        } else {
            offsetBuilder.append("-0");
        }
        offsetBuilder.append(off);
        offsetBuilder.append(":00");
        LOG.trace(offsetBuilder.toString());

        zone = TimeZone.getTimeZone(offsetBuilder.toString());
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT, locale);
        
        if (zone == null) {
            zone = dateFormat.getTimeZone();
        }
        if (LOG.isTraceEnabled()) {
            LOG.trace(zone.getDisplayName());
            LOG.trace(zone.getRawOffset());
            LOG.trace(zone);
        }
        return zone;
    }
    
    
}
