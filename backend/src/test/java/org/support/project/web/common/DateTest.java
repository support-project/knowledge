package org.support.project.web.common;

import java.text.DateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.util.DateUtils;
import org.support.project.ormapping.tool.dao.InitializeDao;
import org.support.project.web.dao.FunctionsDao;
import org.support.project.web.entity.FunctionsEntity;

public class DateTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Webの初期化
        InitializeDao dao = InitializeDao.get();
        // 全テーブル削除
        org.support.project.web.dao.gen.DatabaseControlDao dao2 = new org.support.project.web.dao.gen.DatabaseControlDao();
        dao2.dropAllTable();
        dao.initializeDatabase("/org/support/project/web/database/ddl.sql");
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

    // @Test
    public void test() {
        // Locale.setDefault(Locale.US);
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.getDefault());
        TimeZone zone = TimeZone.getTimeZone("GMT" + (-540 / 60));
        dateFormat.setTimeZone(zone);

        System.out.println("java.util.Date:" + DateUtils.now());
        System.out.println("java.util.Date:getTime():" + DateUtils.now().getTime());
        System.out.println(dateFormat.format(DateUtils.now()));

        FunctionsEntity functionsEntity = new FunctionsEntity();
        functionsEntity.setFunctionKey("test");
        functionsEntity.setDescription("test");
        FunctionsDao dao = FunctionsDao.get();
        dao.save(functionsEntity);

        System.out.println(functionsEntity.getUpdateDatetime());
        System.out.println(functionsEntity.getUpdateDatetime().getTime());

        Date check = DateUtils.now();
        check.setTime(functionsEntity.getUpdateDatetime().getTime());

        System.out.println("2 java.util.Date:" + check);
        System.out.println("2 java.util.Date:getTime():" + check.getTime());
        System.out.println(dateFormat.format(check));
    }

    @Test
    public void testJava8() {
        System.out.println("testJava8");

        // ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
        // -540 / 60
        TimeZone zone = TimeZone.getTimeZone("GMT+09:00");
        // TimeZone zone = TimeZone.getTimeZone("Asia/Tokyo");
        System.out.println(zone.getID());

        // ZoneId zoneId = ZoneId.of("America/Los_Angeles");
        ZoneId zoneId = ZoneId.of(zone.getID());

        ZonedDateTime dateTime = ZonedDateTime.now(zoneId);
        // ZonedDateTime dateTime = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSSxxxxx VV");
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");

        System.out.println(dateTime.format(formatter));
        System.out.println(DateUtils.now().getTime());

        FunctionsEntity functionsEntity = new FunctionsEntity();
        functionsEntity.setFunctionKey("test");
        functionsEntity.setDescription("test");
        FunctionsDao dao = FunctionsDao.get();
        dao.save(functionsEntity);

        System.out.println(functionsEntity.getUpdateDatetime());
        System.out.println(functionsEntity.getUpdateDatetime().getTime());

        ZonedDateTime ztime = ZonedDateTime.ofInstant(functionsEntity.getUpdateDatetime().toInstant(), zoneId);
        System.out.println(ztime.format(formatter));
        System.out.println(functionsEntity.getUpdateDatetime().getTime());

        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.JAPAN);
        TimeZone z = dateFormat.getTimeZone();
        System.out.println(z.getID());
        System.out.println();

        // long millis = ztime.toInstant().toEpochMilli();
        System.out.println(dateFormat.format(Date.from(ztime.toInstant())));

    }

}
