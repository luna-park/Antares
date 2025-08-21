package org.lunapark.anteres.network.json;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericListJsonHandler<T> implements JsonHandler<List<T>> {

    private final Gson gson;
    private final String rootName;
    private final Class<T> typeClass;

    public GenericListJsonHandler(Gson gson, String rootName, Class<T> typeClass) {
        this.gson = gson;
        this.rootName = rootName;
        this.typeClass = typeClass;
    }

    @Override
    public List<T> handle(String json) {
        List<T> elements = new ArrayList<>();
        if (TextUtils.isEmpty(json) || json.equals("null")) return elements;

        JsonParser jsonParser = new JsonParser();
        JsonElement rootElement = jsonParser.parse(json);
        JsonElement element = rootElement.getAsJsonObject().get(rootName);

        if (element != null) {
            if (element.isJsonArray()) {
                T[] array = gson.fromJson(element, TypeToken.getArray(typeClass).getType());
                elements.addAll(Arrays.asList(array));
            } else {
                elements.add(gson.fromJson(element, typeClass));
            }
        }

        return elements;
    }
}