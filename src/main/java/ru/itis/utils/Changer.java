package ru.itis.utils;

import ru.itis.models.ProjectManager;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Changer {

    public static void dataChange(ProjectManager pm, String s){
        String[] changes = s.split(" {3}");
        Arrays.stream(changes).map(change -> change.split(" ")).forEachOrdered(chars -> {
            try {
                Field field = pm.getClass().getDeclaredField(chars[0]);
                field.setAccessible(true);
                char sign = chars[1].charAt(0);
                if (sign == '+') {
                    field.set(pm, (Long) field.get(pm) + Long.parseLong(chars[2]));
                }
                if (sign == '-') {
                    field.set(pm, (Long) field.get(pm) - Long.parseLong(chars[2]));
                }
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                throw new IllegalArgumentException(
                        "can't find the field with name: " + chars[0]);
            }
        });
    }
}
