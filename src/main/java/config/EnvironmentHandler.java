package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class EnvironmentHandler {
    private static final Logger LOGGER = Logger.getLogger(EnvironmentHandler.class);

    private static final String ENVIRONMENT_PARAM = "environment";

    private static EnvironmentHandler instance;
    private static EnvironmentModel environmentDto;

    private EnvironmentHandler(EnvironmentType environmentType) {
        String currentEnvParam = System.getProperty(ENVIRONMENT_PARAM);
        EnvironmentType environment;
        if (currentEnvParam != null) {
            environment = convertEnvironmentStringToEnvironmentType(currentEnvParam);
        } else {
            if (environmentType != null) {
                environment = environmentType;
            } else {
                //Default environment is DEV
                environment = EnvironmentType.DEV;
            }
        }
        LOGGER.info("******** INITIALIZING TEST ENVIRONMENT FOR [" + environment.toString() + "] ********");
        environmentDto = convertYAMLFileToEnvironmentModel(environment.getUrl());
    }

    public static EnvironmentHandler getInstance(EnvironmentType environmentType) {
        if (instance == null) {
            instance = new EnvironmentHandler(environmentType);
        }
        return instance;
    }

    public static EnvironmentHandler getInstance() {
        if (instance == null) {
            instance = new EnvironmentHandler(null);
        }
        return instance;
    }

    public EnvironmentModel getEnvironment() {
        return environmentDto;
    }


    private EnvironmentType convertEnvironmentStringToEnvironmentType(String s) {
        EnvironmentType environmentType = EnvironmentType.DEV;
        try {
            environmentType = EnvironmentType.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            LOGGER.error("The environment [" + s + "] doesn't exists. Please input the correct one!" + e);
        }
        return environmentType;
    }

    private EnvironmentModel convertYAMLFileToEnvironmentModel(String environmentPath) {
        ObjectMapper Mapper = new ObjectMapper(new YAMLFactory());
        try {
            return Mapper.readValue(new File(environmentPath), EnvironmentModel.class);
        } catch (IOException e) {
            LOGGER.error("Mapping YAML file with object with error: ", e);
        }
        return null;
    }

}
