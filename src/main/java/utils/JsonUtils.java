package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static String RestultMapToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
