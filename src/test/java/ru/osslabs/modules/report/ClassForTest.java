package ru.osslabs.modules.report;

import javaslang.control.Match;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//import org.jooq.lambda.Seq;
//import java.util.stream.Stream;

/**
 * Created by ikuchmin on 03.11.15.
 */
public class ClassForTest {

    @Test
    public void testMatch() throws Exception {
        Object obj = new ArrayList<>();
        Match.of(obj)
                .whenType(List.class).then(() -> "hell")
                .otherwise("bell").forEach(System.out::println);

    }

    private static Locale RUSSIAN = new Locale("ru", "RU");
    @Test
    public void testFormatDate() throws Exception {
        System.out.println(ZonedDateTime.now(ZoneId.of("Europe/Moscow")).format(DateTimeFormatter.ofPattern("dd.MM.uuuu", RUSSIAN)));
    }
}
