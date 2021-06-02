package dev.array21.reactioner.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;

import org.yaml.snakeyaml.Yaml;

import com.google.gson.Gson;

import dev.array21.classvalidator.ClassValidator;
import dev.array21.classvalidator.Pair;
import dev.array21.reactioner.Reactioner;

public class Config {

	private static final Gson GSON = new Gson();
	private static final Yaml YAML = new Yaml();
	
	private static final String CONTAINER_CONFIG_PATH = "/config/";
	
	public ConfigManifest loadConfig(boolean container) {
		if(container) {
			return loadConfig(CONTAINER_CONFIG_PATH);
		} else {
			File jarFile;
			
			try {
				jarFile = new File(Config.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			} catch (URISyntaxException e) {
				//Unreachable
				return null;
			}
			
			return loadConfig(jarFile.getAbsolutePath());
		}
	}
	
	private ConfigManifest loadConfig(String filepath) {
		File f = new File(filepath, "config.yml");
		if(!f.exists()) {
			f.getParentFile().mkdirs();
			
			try {
				saveResource("config.yml", f);
			} catch(IOException e) {
				Reactioner.logWarn("Failed to save config file from jar: " + e.getMessage());
				System.exit(1);
			}
		}
		
		Object yaml;
		try {
			yaml = YAML.load(new FileInputStream(f));
		} catch(FileNotFoundException e) {
			//Unreachable
			return null;
		}
		
		String json = GSON.toJson(yaml, java.util.LinkedHashMap.class);
		ConfigManifest manifest = GSON.fromJson(json, ConfigManifest.class);
		
		Pair<Boolean, String> validationResult = ClassValidator.validateType(manifest);
		if(validationResult.getA() == null) {
			throw new RuntimeException(validationResult.getB());
		}
		
		if(!validationResult.getA() ) {
			Reactioner.logWarn("Invalid config: " + validationResult.getB());
			System.exit(1);
		}
		
		return manifest;
	}
	
	private void saveResource(String sourcePath, File destFile) throws IOException {
		InputStream resource = this.getClass().getClassLoader().getResourceAsStream(sourcePath);
		if(resource == null) {
			throw new RuntimeException(String.format("Cannot find resource in jar at path '%s'", sourcePath));
		}
		
		Files.copy(resource, destFile.toPath());
	}
}
