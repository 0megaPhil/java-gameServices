package com.firmys.gameservices.common;

import java.time.format.DateTimeFormatter;

public class Formatters {
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
