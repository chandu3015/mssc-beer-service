package com.chandu.msscbeerservice.web.mappers;

import com.chandu.msscbeerservice.domain.Beer;
import com.chandu.msscbeerservice.service.BeerInventory.BeerInventoryService;
import com.chandu.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class BeerMapperDecorator implements  BeerMapper {


    BeerInventoryService beerInventoryService;


    BeerMapper beerMapper;


    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper beerMapper) {
        this.beerMapper = beerMapper;
    }



    @Override
    public BeerDto beerToBeerDto(Beer beer) {
      BeerDto beerDto = beerMapper.beerToBeerDto(beer);

      Integer quantityOnHand = beerInventoryService.getOnHandInventory(beer.getId());
      beerDto.setQuantityOnHand(quantityOnHand);

        return beerDto;
    }

    @Override
    public Beer BeerDtoToBeer(BeerDto beerDto) {
        return beerMapper.BeerDtoToBeer(beerDto);
    }

    @Override
    public BeerDto beerToBeerDtoHideInventoryOnHand(Beer beer)
    {
        return beerMapper.beerToBeerDto(beer);

    }
}
