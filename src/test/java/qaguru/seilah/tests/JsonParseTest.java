package qaguru.seilah.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qaguru.seilah.models.Users;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonParseTest {
    private ClassLoader classLoader = JsonParseTest.class.getClassLoader();

    private String firstUserIdExpected = "BRVMC2Q7AGNP0ZRL";
    private String secondUserIdExpected = "5R3MYXUEDI8LQVEZ";

    @DisplayName("Check user ids")
    @Test
    public void checkUserIdsHaveCorrectValues() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream = classLoader.getResourceAsStream("users.json");
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);) {

            Users users = objectMapper.readValue(inputStreamReader, Users.class);

            assertEquals(firstUserIdExpected,
                    users.getUsers().get(0).getId());
            assertEquals(secondUserIdExpected,
                    users.getUsers().get(1).getId());
        }
    }
}