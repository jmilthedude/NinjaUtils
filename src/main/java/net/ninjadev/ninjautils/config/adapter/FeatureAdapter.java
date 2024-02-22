package net.ninjadev.ninjautils.config.adapter;

import com.google.gson.*;
import net.ninjadev.ninjautils.NinjaUtils;
import net.ninjadev.ninjautils.feature.*;

import java.lang.reflect.Type;

public class FeatureAdapter implements JsonSerializer<Feature>, JsonDeserializer<Feature> {

//    @Override
//    public void write(JsonWriter out, Feature feature) throws IOException {
//        if (feature == null) {
//            out.nullValue();
//            return;
//        }
//
//        out.beginObject();
//
//        out.name("name");
//        out.value(feature.getName());
//        out.name("enabled");
//        out.value(feature.isEnabled());
//
//        feature.writeJson(out);
//
//        out.endObject();
//    }
//
//    @Override
//    public Feature read(JsonReader in) throws IOException {
//
//        if (in.peek() == JsonToken.NULL) {
//            in.nextNull();
//            return null;
//        }
//
//        in.beginObject();
//        String name = "";
//        boolean enabled = false;
//        Optional<Feature> feature = Optional.empty();
//        while (in.peek() == JsonToken.NAME) {
//            String property = in.nextName();
//
//            if (property.equalsIgnoreCase("name")) {
//                name = in.nextString();
//            }
//            if (property.equalsIgnoreCase("enabled")) {
//                enabled = in.nextBoolean();
//            }
//        }
//
//        if (feature.isEmpty()) {
//            feature = ModFeatures.create(name, enabled, in);
//        }
//
//        in.endObject();
//        return feature.orElse(null);
//    }

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
            NinjaUtils.LOG.error("Unable to deserialize Feature: {}", name);
        }
        return null;
    }
}
