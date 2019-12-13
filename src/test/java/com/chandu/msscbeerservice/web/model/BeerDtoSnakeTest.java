package com.chandu.msscbeerservice.web.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("snake")
@JsonTest
public class BeerDtoSnakeTest extends BaseTest {
    @Autowired
    ObjectMapper objectMapper;


    @Test
    void testSnake()
    {

        BeerDto beerDto= getDto();
        String json="";
        try {
             json = objectMapper.writeValueAsString(beerDto);
            System.out.println("json is "+json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        BeerDto beerDto1=null;
        try {
             beerDto1=objectMapper.readValue(json,BeerDto.class);
             System.out.println(beerDto1);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

}
