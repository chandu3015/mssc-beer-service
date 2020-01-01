package com.chandu.msscbeerservice.service;

import com.chandu.msscbeerservice.web.model.BeerDto;
import com.chandu.msscbeerservice.web.model.BeerPagedList;
import com.chandu.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {

    BeerDto getBeerById(UUID beerId, boolean showInventoryOnHand);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeerById(BeerDto beerDto, UUID beerId);

    BeerPagedList listBeerService(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, boolean showInventoryOnHand);

    BeerDto getBeerByUpc(Long upc);
}
