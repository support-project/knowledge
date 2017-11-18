package org.support.project.knowledge.logic.notification.webhook;

import java.util.Iterator;

import org.junit.Assert;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class AssertJson {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(AssertJson.class);
    
    public static void equals(JsonObject expected, JsonObject actual) {
        LOG.debug(expected.toString());
        LOG.debug(actual.toString());
        Assert.assertEquals(expected.keySet().size(), actual.keySet().size());
        Iterator<String> props = expected.keySet().iterator();
        while (props.hasNext()) {
            String prop = (String) props.next();
            JsonElement e = expected.get(prop);
            JsonElement a = actual.get(prop);
            equals(prop, e, a);
        }
    }

    private static void equals(String prop, JsonElement e, JsonElement a) {
        if (e.isJsonPrimitive()) {
            if (a == null || !a.isJsonPrimitive()) {
                Assert.fail("[" + prop + "] " +  e + " != " + a);
            }
            if (prop.equals("insert_date") || prop.equals("update_date")) {
                return;
            }
            JsonPrimitive val1 = e.getAsJsonPrimitive();
            JsonPrimitive val2 = a.getAsJsonPrimitive();
            Assert.assertEquals("[" + prop + "]", val1, val2);
        } else if (e.isJsonObject()) {
            if (a == null || !a.isJsonObject()) {
                Assert.fail("[" + prop + "] " +  e + " != " + a);
            }
            LOG.info("property:" + prop + " is object.");
            equals(e.getAsJsonObject(), a.getAsJsonObject());
        } else if (e.isJsonArray()) {
            if (a == null || !a.isJsonArray()) {
                Assert.fail("[" + prop + "] " +  e + " != " + a);
            }
            JsonArray array1 = e.getAsJsonArray();
            JsonArray array2 = a.getAsJsonArray();
            Assert.assertEquals(array1.size(), array2.size());
            for (int i = 0; i < array1.size(); i++) {
                JsonElement item1 = array1.get(i);
                JsonElement item2 = array2.get(i);
                equals(prop + "(" + i + ")", item1, item2);
            }
        }
    }
    
    
}
