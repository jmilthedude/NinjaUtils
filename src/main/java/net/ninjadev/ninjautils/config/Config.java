package net.ninjadev.ninjautils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.ninjadev.ninjautils.NinjaUtils;
import net.ninjadev.ninjautils.config.adapter.FeatureAdapter;

import java.io.*;

public abstract class Config {

    private static final Gson GSON = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapterFactory(FeatureAdapter.FACTORY)
            .setPrettyPrinting()
            .create();
    protected File root = new File("config/NinjaUtils/");
    protected String extension = ".json";

    private boolean isDirty = true;

    public void generateConfig() {
        this.reset();

        this.writeConfig();
    }

    private File getConfigFile() {
        return new File(this.root, this.getName() + this.extension);
    }

    public abstract String getName();

    @SuppressWarnings("unchecked")
    public <T extends Config> T readConfig() {
        try {
            return (T) GSON.fromJson(new FileReader(this.getConfigFile()), this.getClass());
        } catch (FileNotFoundException e) {
            this.generateConfig();
        }

        return (T) this;
    }

    protected abstract void reset();

    public void writeConfig() {
        try {
            if (!root.exists() && !root.mkdirs()) return;
            if (!this.getConfigFile().exists() && !this.getConfigFile().createNewFile()) return;
            FileWriter writer = new FileWriter(this.getConfigFile());
            GSON.toJson(this, writer);
            writer.flush();
            writer.close();
            NinjaUtils.LOG.info("Saved " + this.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        if(!this.isDirty) return;
        this.writeConfig();
        this.isDirty = false;
    }

    public void markDirty() {
        this.isDirty = true;
    }

}