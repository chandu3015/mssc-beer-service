package com.chandu.msscbeerservice.web.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ActiveProfiles;



@ActiveProfiles("kebab")
@JsonTest
public class BeerDtoKebab extends BaseTest {

    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void keebabaTest() {
        BeerDto beerDto = getDto();
        String json=" ";

        try {
             json = objectMapper.writeValueAsString(beerDto);
            System.out.println("marshelling "+json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        try {
            BeerDto beerDto1 = objectMapper.readValue(json,BeerDto.class);
            System.out.println("unmarshelling "+beerDto1);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }
}
