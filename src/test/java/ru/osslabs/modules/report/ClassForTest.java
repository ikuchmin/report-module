package ru.osslabs.modules.report;

import javaslang.collection.Stream;
import javaslang.control.Option;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.Test;
import ru.osslabs.modules.report.decorators.BetweenDateReport;
import ru.osslabs.modules.report.decorators.DestinationOutputStreamReport;
import ru.osslabs.modules.report.decorators.DestinationPathReport;
import ru.osslabs.modules.report.decorators.SourceFututeHSSFWorkBookReport;
import ru.osslabs.modules.report.factories.sed.ddunaev.SecondReportToOutputStream;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

import static ru.osslabs.modules.report.reflections.ObjectUtils.cast;

//import org.jooq.lambda.Seq;
//import java.util.stream.Stream;

/**
 * Created by ikuchmin on 03.11.15.
 */
public class ClassForTest {
    public static class ATT {
        void dispatch(Object obj) {
            System.out.println("Object");
        }
    }

    public static class TT extends ATT {

        void dispatch(String obj) {
            System.out.println("String");
        }

        void dispatch(Integer obj) {
            System.out.println("Integer");
        }

        void dispatch(TT obj) {
            System.out.println("TT");
        }
    }

    public static class TTS extends TT {

    }

    @Test
    public void testDispathcingOnType() throws Exception {
//        List<?> lst = null;
//        Class<?> cl = List.class;
//        Class<? extends List<?>> cll = cl;
        new TT().dispatch((Object) new TTS());
        new TT().dispatch("hell");
        new TT().dispatch(42);
    }

    class AZ {
        List<String> str;
    }

    public static void method(List<?> lst) {
        System.out.println(((ParameterizedType)lst.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0]);
    }
    @Test
    public void testReflection() throws Exception {
        method(new ArrayList<AZ>());
//        Option<Type> expectedClass =
//        Stream.ofAll(AZ.class.getDeclaredFields())
//                .flatMap((f) -> f.getType().getGenericInterfaces())
//                .forEach(System.out::println);


//        Type[] genericInterfaces = type.getClass().getGenericInterfaces();
//                Stream.ofAll([1])
//                .forEach(System.out::println);
//                .map((in) -> (ParameterizedType) in)
//                .filter((in) -> in.getRawType() == List.class)
//                .map((in) -> in.getActualTypeArguments()[1])
//                .headOption();
//
//        System.out.println(genericInterfaces);
    }
}
