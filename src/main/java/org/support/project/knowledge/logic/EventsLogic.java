package org.support.project.knowledge.logic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.EventsDao;
import org.support.project.knowledge.entity.EventsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.web.bean.LoginedUser;

@DI(instance = Instance.Singleton)
public class EventsLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(EventsLogic.class);
    
    public static EventsLogic get() {
        return Container.getComp(EventsLogic.class);
    }

    private void logging(Calendar calendar) {
        if (LOG.isDebugEnabled()) {
            StringBuilder builder = new  StringBuilder();
            builder.append(calendar.getTimeInMillis()).append(" ");
            builder.append(calendar.get(Calendar.YEAR)).append("/")
                .append(calendar.get(Calendar.MONTH) + 1).append("/")
                .append(calendar.get(Calendar.DATE)).append(" ");
            builder.append(calendar.get(Calendar.HOUR)).append(":")
                .append(calendar.get(Calendar.MINUTE)).append(":")
                .append(calendar.get(Calendar.SECOND)).append(".")
                .append(calendar.get(Calendar.MILLISECOND)).append(" ");
            builder.append(calendar.getTimeZone().getDisplayName());
            LOG.debug(builder.toString());
        }
    }
    
    public List<EventsEntity> eventList(String date, String timezone, LoginedUser loginedUser) throws ParseException {
        DateFormat monthformat = new SimpleDateFormat("yyyyMM");
        Date d = monthformat.parse(date);
        TimeZone tz = TimeZone.getTimeZone(timezone);
        Calendar start = Calendar.getInstance(tz);
        Calendar end = Calendar.getInstance(tz);
        
        TimeZone gmt = TimeZone.getTimeZone("GMT");
        Calendar in = Calendar.getInstance(gmt);
        in.setTime(d);
        
        start.set(in.get(Calendar.YEAR), in.get(Calendar.MONTH), 1, 0, 0, 0);
        start.set(Calendar.MILLISECOND, 0);
        end.set(in.get(Calendar.YEAR), in.get(Calendar.MONTH), in.getActualMaximum(Calendar.DATE), 23, 59, 59);
        end.set(Calendar.MILLISECOND, 999);
        
        logging(in);
        logging(start);
        logging(end);
        
        return EventsDao.get().selectAccessAbleEvents(start, end, loginedUser);
    }

    public List<KnowledgesEntity> eventKnowledgeList(String date, String timezone, LoginedUser loginedUser) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date d = format.parse(date);
        TimeZone tz = TimeZone.getTimeZone(timezone);
        Calendar start = Calendar.getInstance(tz);
        start.setTime(d);
        start.set(Calendar.HOUR, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        return EventsDao.get().selectAccessAbleEvents(start, loginedUser, 50, 0);
    }

}
