package com.example.property.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author sotiamiyu
 * Saheed Tiamiyu Oluwatosin
 */

@Component
public class InternalPropertyFile {
    private static Logger log = LoggerFactory.getLogger(InternalPropertyFile.class);
    public static Properties properties(){
        Properties propsin = null;
        try{
            propsin = new InternalPropertyFile().readPropertiesFile();
        }catch (IOException ex){
            log.error("Error occured when getting props application.properties:{}", ex);
        }catch (Exception exp){
            log.error("Other Error occured when getting props application.properties:{}", exp);
        }
        return propsin;
    }

    public static Properties properties(String str){
        Properties propsin = null;
        try{
            propsin = new InternalPropertyFile().readPropertiesFile(str);
        }catch (IOException ex){
            log.error("Error occured when getting props application.properties:{}", ex);
        }catch (Exception exp){
            log.error("Other Error occured when getting props application.properties:{}", exp);
        }
        return propsin;
    }
    public Properties readPropertiesFile() throws IOException {
        FileInputStream fis = null;
        Properties prop = null;

        try {
            InputStream fileInputStream = getClass()
                    .getClassLoader().getResourceAsStream("application.properties");
            prop = new Properties();
            prop.load(fileInputStream);
        } catch( IOException ioe ){
            log.error("Error occured: because application.properties could not be read", ioe);
            ioe.printStackTrace();
        }
        return prop;
    }

    public Properties readPropertiesFile(String fileName) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;

        try {
            InputStream fileInputStream = getClass()
                    .getClassLoader().getResourceAsStream(fileName);
            prop = new Properties();
            prop.load(fileInputStream);
        } catch(FileNotFoundException fnfe) {
            log.error("Error occured application.properties file was not found:", fnfe);
            fnfe.printStackTrace();
        } catch(IOException ioe) {
            log.error("Error occured because application.properties could not be read:", ioe);
            ioe.printStackTrace();
        }
        return prop;
    }

}
