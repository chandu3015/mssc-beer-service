package com.chandu.msscbeerservice.web.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;



@JsonTest
class BeerDtoTest extends BaseTest {

    @Autowired
   ObjectMapper objectMapper;

    @Test
    public void serilaizingAndDesrializaingTest()
    {
        BeerDto beerDto = getDto();
        String jsonString="";
        BeerDto beerDto1=null;
        try {
             jsonString = objectMapper.writeValueAsString(beerDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("JSON - "+jsonString);

        try {
             beerDto1 = objectMapper.readValue(jsonString,BeerDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("Object "+beerDto1);
    }




}