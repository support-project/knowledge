package org.support.project.knowledge.logic;

import java.util.TimeZone;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

@DI(instance = Instance.Singleton)
public class TimeZoneLogic {
    public static TimeZoneLogic get() {
        return Container.getComp(TimeZoneLogic.class);
    }

    public boolean exist(String timezone) {
        String[] zones = TimeZone.getAvailableIDs();
        boolean exist = false;
        for (int i = 0; i < zones.length; i++) {
            String zone = zones[i];
            if (timezone.equals(zone)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

}
