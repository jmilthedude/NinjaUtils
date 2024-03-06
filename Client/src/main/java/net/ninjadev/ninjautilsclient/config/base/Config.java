package net.ninjadev.ninjautilsclient.config.base;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.ninjadev.ninjautilsclient.NinjaUtilsClient;
import net.ninjadev.ninjautilsclient.config.base.adapter.IdentifierAdapter;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Random;

public abstract class Config {

    protected static final Random rand = new Random();

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapterFactory(IdentifierAdapter.FACTORY)
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();
    protected String root = String.format("%s%s%s%s", "config", File.separator, NinjaUtilsClient.MOD_ID, File.separator);
    protected String extension = ".json";

    public void generateConfig() {
        this.reset();

        try {
            this.writeConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getConfigFile() {
        String parent = String.format("%s%s%s", this.root, this.getFolder(), File.separator);
        String child = this.getName() + this.extension;
        return new File(parent, child);
    }

    public abstract String getName();

    public abstract String getFolder();

    @SuppressWarnings("unchecked")
    public <T extends Config> T readConfig() {
        try {
            return GSON.fromJson(new FileReader(this.getConfigFile()), (Type) this.getClass());
        } catch (FileNotFoundException e) {
            this.generateConfig();
        }

        return (T) this;
    }

    protected abstract void reset();

    private void writeConfig() throws IOException {
        File dir = new File(this.root + this.getFolder());
        if (!dir.exists() && !dir.mkdirs()) return;
        if (!this.getConfigFile().exists() && !this.getConfigFile().createNewFile()) return;
        FileWriter writer = new FileWriter(this.getConfigFile());
        GSON.toJson(this, writer);
        writer.flush();
        writer.close();
    }

    public void save() {
        try {
            this.writeConfig();
        } catch (Exception ignored) {
        }
    }

}
