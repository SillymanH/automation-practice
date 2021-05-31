package dataProvider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

public class ConfigFileReader {

    private Properties properties;
    private final String propertyFilePath= "Configs//Configuration.properties";

    public ConfigFileReader(){

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }

    public String getUsername() {

        String username = properties.getProperty("username");
        if(username!= null)
            return username;
        else throw new RuntimeException("username not specified in the Configuration.properties file.");
    }

    public String getPassword() {

        String decodedPassword = decodePassword(properties.getProperty("password"));
        if(decodedPassword!= null)
            return decodedPassword;
        else throw new RuntimeException("password is not specified in the Configuration.properties file.");
    }

    public String decodePassword(String encodedBytes) {

        byte[] decodedBytes = Base64.getDecoder().decode(encodedBytes);
        return new String(decodedBytes);
    }

    public String getURL() {

        String URL = properties.getProperty("url");
        if(URL!= null)
            return URL;
        else throw new RuntimeException("URL is not specified in the Configuration.properties file.");
    }

    public String getDriverPath() {

        String driverPath = properties.getProperty("driverPath");
        if(driverPath!= null)
            return driverPath;
        else throw new RuntimeException("driverPath not specified in the Configuration.properties file.");
    }

}
