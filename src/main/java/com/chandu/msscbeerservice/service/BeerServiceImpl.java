package com.chandu.msscbeerservice.service;

import com.chandu.msscbeerservice.domain.Beer;
import com.chandu.msscbeerservice.repositories.BeerRepository;
import com.chandu.msscbeerservice.web.mappers.BeerMapper;
import com.chandu.msscbeerservice.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BeerServiceImpl implements BeerService {

    @Autowired
    BeerMapper beerMapper;
    @Autowired
    BeerRepository beerRepository;

    BeerServiceImpl(BeerMapper beerMapper, BeerRepository beerRepository)
    {
        this.beerMapper=beerMapper;
        this.beerRepository=beerRepository;
    }


    @Override
    public BeerDto getBeerById(UUID beerId) {
        return beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.BeerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeerById(BeerDto beerDto, UUID beerId) {

        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beerDto.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }
}
