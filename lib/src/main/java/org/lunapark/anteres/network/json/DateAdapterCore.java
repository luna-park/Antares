package org.lunapark.anteres.network.json;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateAdapterCore {

    private final String pattern;
    private Locale locale;

    public DateAdapterCore(String pattern) {
        this.pattern = pattern;
    }

    private Locale getLocale() {
        if (locale == null)
            locale = new Locale("ru");
        return locale;
    }

    public String write(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, getLocale());
        return simpleDateFormat.format(date);
    }

    public Date read(String dateString) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, getLocale());
            return simpleDateFormat.parse(dateString);
        } catch (Exception ignored) {
            return null;
        }
    }
}
