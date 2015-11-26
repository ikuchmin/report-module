package ru.osslabs.modules.report.reflections;

import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ikuchmin on 25.11.15.
 */
public class TypeReferenceTest {

    public static class SimpleClass {
        private List<String> genericField;
        private String simpleString;
    }

    @Test
    public void testParametrizedType() throws Exception {
        TypeReference<List<String>> tr = new TypeReference<List<String>>() {
        };

        assertEquals(((ParameterizedType)tr.getType()).getActualTypeArguments()[0], String.class);
        assertEquals(((ParameterizedType)tr.getType()).getRawType(), List.class);
    }

    @Test
    public void testParametrizedTypeFabricType() throws Exception {
        TypeReference<?> tr = TypeReference.referenceFabric(SimpleClass.class.getDeclaredField("genericField").getGenericType());

        assertEquals(((ParameterizedType)tr.getType()).getActualTypeArguments()[0], String.class);
        assertEquals(((ParameterizedType)tr.getType()).getRawType(), List.class);
    }

    @Test
    public void testSimpleTypeFabricClass() throws Exception {
        TypeReference<String> tr = TypeReference.referenceFabric(String.class);
        assertEquals(tr.getType(), String.class);
    }


    @Test
    public void testSimpleObject() throws Exception {
        TypeReference<SimpleClass> tr = new TypeReference<SimpleClass>() {
        };

        assertEquals(tr.getType(), SimpleClass.class);
    }

    @Test
    public void testSimpleObjectFabricClass() throws Exception {
        TypeReference<SimpleClass> tr = TypeReference.referenceFabric(SimpleClass.class);
        assertEquals(tr.getType(), SimpleClass.class);
    }

    @Test
    public void testPrimitiveClass() throws Exception {
        TypeReference<Integer> tr = new TypeReference<Integer>() {
        };

        assertEquals(tr.getType(), Integer.class);
    }


    @Test
    public void testPrimitiveTypeFabricType() throws Exception {
        TypeReference<?> tr = TypeReference.referenceFabric(SimpleClass.class.getDeclaredField("simpleString").getGenericType());

        assertEquals(tr.getType(), String.class);
    }

}