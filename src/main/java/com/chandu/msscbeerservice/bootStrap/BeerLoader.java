package com.chandu.msscbeerservice.bootStrap;

import com.chandu.msscbeerservice.domain.Beer;
import com.chandu.msscbeerservice.repositories.BeerRepository;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import java.util.UUID;

@Component
public class BeerLoader implements  CommandLineRunner {

    private BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository)
    {
      this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        loadBeerObject();
    }



    public void loadBeerObject()
    {
        if(beerRepository.count()==0)
        {
            beerRepository.save(Beer.builder().beerName("miller")

                    .beerStyle("LARGE")
                    .quantityToBrew(12)
                    .minOnHand(5)

                    .upc(351879l)
                    .build());

            beerRepository.save(Beer.builder().beerName("Coors")

                    .beerStyle("LARGE")
                    .quantityToBrew(12)
                    .minOnHand(5)
                    .upc(351654l)
                    .build());
        }
    }



}
