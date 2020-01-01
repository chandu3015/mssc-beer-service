package com.chandu.msscbeerservice.service;

import com.chandu.msscbeerservice.domain.Beer;
import com.chandu.msscbeerservice.repositories.BeerRepository;
import com.chandu.msscbeerservice.web.mappers.BeerMapper;
import com.chandu.msscbeerservice.web.model.BeerDto;
import com.chandu.msscbeerservice.web.model.BeerPagedList;
import com.chandu.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public  class BeerServiceImpl implements BeerService {


    BeerMapper beerMapper;

    BeerRepository beerRepository;


    public BeerServiceImpl(BeerMapper beerMapper, BeerRepository beerRepository)
    {
        this.beerMapper=beerMapper;
        this.beerRepository=beerRepository;
    }



    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerDto getBeerById(UUID beerId, boolean showInventoryOnHand) {
        System.out.println("not from cache...");

        BeerDto beerDto=null;
       if(showInventoryOnHand)
           beerDto= beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
       else
           beerDto=beerMapper.beerToBeerDtoHideInventoryOnHand(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));

       return beerDto;
    }

    @Cacheable(cacheNames = "beerUpcCache", key = "#upc")
    @Override
    public BeerDto getBeerByUpc(Long upc) {

       Beer beer = beerRepository.findByUpc(upc);
        System.out.println("Beer upc "+upc+" is of beer "+beer);
        BeerDto beerDto = beerMapper.beerToBeerDto(beer);
        return beerDto;
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



    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerPagedList listBeerService(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, boolean showInventoryOnHand) {
        System.out.println("not from cache...");
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;


        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            //search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search beer_service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);

        }

        if(showInventoryOnHand)
        {
            beerPagedList = new BeerPagedList(
                    beerPage.stream().map(beerMapper::beerToBeerDto).collect(Collectors.toList())
                    ,PageRequest.of(beerPage.getPageable().getPageNumber(),beerPage.getPageable().getPageSize()),beerPage.getSize());

        }
        else
        {
            beerPagedList = new BeerPagedList(
                    beerPage.stream().map(beerMapper::beerToBeerDtoHideInventoryOnHand).collect(Collectors.toList())
                    ,PageRequest.of(beerPage.getPageable().getPageNumber(),beerPage.getPageable().getPageSize()),beerPage.getSize());

        }

        return beerPagedList;
    }


}
