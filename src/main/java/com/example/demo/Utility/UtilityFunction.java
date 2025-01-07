package com.example.demo.Utility;

import java.lang.reflect.Field;

public class UtilityFunction {
    public static void printToConsoleFieldsAndValues(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        for(Field f : fields) {
            Class t = f.getType();
            Object v = null;
            try {
                v = f.get(o);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            if (t == boolean.class && Boolean.FALSE.equals(v)) {
                // found default value
            } else if (t.isPrimitive() && ((Number) v).doubleValue() == 0) {
                // found default value
            } else if (!t.isPrimitive() && v == null) {
                // found default value
            }

            if (v != null) {
                System.out.println(f.getName() + ": " + v.toString());
            }
        }
    }
}
