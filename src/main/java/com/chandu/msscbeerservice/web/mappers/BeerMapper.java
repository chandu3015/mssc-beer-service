package com.chandu.msscbeerservice.web.mappers;

import com.chandu.msscbeerservice.domain.Beer;
import com.chandu.msscbeerservice.web.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses={DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);
    Beer BeerDtoToBeer(BeerDto beerDto);

    BeerDto beerToBeerDtoHideInventoryOnHand(Beer beer);
}
