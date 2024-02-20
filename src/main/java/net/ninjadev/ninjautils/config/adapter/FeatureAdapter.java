package net.ninjadev.ninjautils.config.adapter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.ninjadev.ninjautils.feature.Feature;
import net.ninjadev.ninjautils.init.ModFeatures;

import java.io.IOException;
import java.util.Optional;

public class FeatureAdapter extends TypeAdapter<Feature> {

    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            return typeToken.getRawType() == Feature.class ? (TypeAdapter<T>) new FeatureAdapter() : null;
        }
    };

    @Override
    public void write(JsonWriter out, Feature feature) throws IOException {
        if (feature == null) {
            out.nullValue();
            return;
        }

        out.beginObject();

        out.name("name");
        out.value(feature.getName());
        out.name("enabled");
        out.value(feature.isEnabled());

        feature.writeJson(out);

        out.endObject();
    }

    @Override
    public Feature read(JsonReader in) throws IOException {

        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        in.beginObject();
        String name = "";
        boolean enabled = false;
        while (in.peek() == JsonToken.NAME) {
            String property = in.nextName();

            if (property.equalsIgnoreCase("name")) {
                name = in.nextString();
            }
            if (property.equalsIgnoreCase("enabled")) {
                enabled = in.nextBoolean();
            }
        }

        Optional<Feature> featureOptional = ModFeatures.create(name, enabled, in);
        in.endObject();
        return featureOptional.orElse(null);
    }
}
