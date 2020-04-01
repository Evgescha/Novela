package com.novelasgame.novelas.storage;

import java.nio.file.Paths;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    public final String ground = "upload-dir";
    private String location = "upload-dir";

    public String getLocation() {
        return location;
    }

    public String getGround() {
		return ground;
	}

	public void setLocation(String... locArr) {
        this.location = this.ground;
        for (String str : locArr)
            this.location = Paths.get(this.getLocation()).resolve(str).toString();
//        System.err.println(this.getLocation());
    }

}
