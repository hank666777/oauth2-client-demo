package com.hank.oauth2clientdemo.config;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTypeAdapter extends TypeAdapter<LocalDateTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        out.value(value.format(formatter));
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        String dateTime = in.nextString();
        return LocalDateTime.parse(dateTime, formatter);
    }
}
