package net.ninjadev.ninjautils.common.config.adapter;

import com.google.gson.*;
import net.ninjadev.ninjautils.common.feature.Feature;
import net.ninjadev.ninjautils.common.util.SharedConstants;

import java.lang.reflect.Type;

public class FeatureAdapter implements JsonSerializer<Feature>, JsonDeserializer<Feature> {

    @Override
    public JsonElement serialize(Feature feature, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject output = new JsonObject();
        output.add("name", new JsonPrimitive(feature.getName()));
        output.add("type", new JsonPrimitive(feature.getClass().getSimpleName()));
        output.add("properties", context.serialize(feature, feature.getClass()));
        return output;
    }

    @Override
    public Feature deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        String type = jsonObject.get("type").getAsString();
        JsonElement properties = jsonObject.get("properties");
        try {
            return context.deserialize(properties, Class.forName("net.ninjadev.ninjautils.feature." + type));
        } catch (ClassNotFoundException ex) {
            SharedConstants.LOG.error("Unable to deserialize Feature: {}", name);
        }
        return null;
    }
}
