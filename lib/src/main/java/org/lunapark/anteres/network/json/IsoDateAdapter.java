package org.lunapark.anteres.network.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;

public class IsoDateAdapter extends TypeAdapter<Date> {
    private final DateAdapterCore dateAdapterCore = new DateAdapterCore("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(dateAdapterCore.write(value));
        }
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        return dateAdapterCore.read(in.nextString());
    }
}
