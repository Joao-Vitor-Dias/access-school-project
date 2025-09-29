package com.accesses.administrative_system;

import com.accesses.administrative_system.util.TelephoneFormatter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class AdministrativeSystemApplicationTests {

	@Test
	void contextLoads() throws JsonProcessingException {

        String telephone = TelephoneFormatter.telephoneFormatter("(00) 11111-1111");

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("session", "default");
        jsonMap.put("chatId", telephone);
        jsonMap.put("text", "Hi !!!");
        jsonMap.put("reply_to", null);
        jsonMap.put("linkPreview", true);
        jsonMap.put("linkPreviewHighQuality", false);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(jsonMap);

        System.out.println(jsonMap);

	}

}
