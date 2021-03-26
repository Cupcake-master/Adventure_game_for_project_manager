package ru.itis.utils;

import ru.itis.models.ProjectManager;

import java.lang.reflect.Field;

public class Inspector {

    public static boolean checkingThePossibilityOfOptions(ProjectManager pm, String condition) {
        String s = condition.substring(1, condition.length() - 1);
        String[] checkingDates = s.split(" {3}");
        for (String checkDate : checkingDates) {
            String[] dates = checkDate.split(" ");
            try {
                Field field = pm.getClass().getDeclaredField(dates[0]);
                field.setAccessible(true);
                Long l = (Long) field.get(pm);
                Long number = Long.valueOf(dates[2]);
                char sign = dates[1].charAt(0);
                if (sign == '>') {
                    return l > number;
                }
                if (sign == '<') {
                    return l < number;
                }

            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException(
                        "Can't find the field with name: " + dates[0]);
            } catch (IllegalAccessException ex) {
                throw new IllegalArgumentException(
                        "Could not access the variable: "
                );
            }
        }
        return true;
    }
}
