package com.chandu.msscbeerservice.web.controller;


import com.chandu.msscbeerservice.service.BeerService;
import com.chandu.msscbeerservice.web.model.BeerDto;
import com.chandu.msscbeerservice.web.model.BeerPagedList;
import com.chandu.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    private static final Integer  DEFAULT_PAGE_NUMBER=0;
    private static final Integer  DEFAULT_PAGE_SIZE=25;


    @Autowired
    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping (produces={"application/json"})
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                  @RequestParam(value = "beerName", required = false) String beerName,
                                                  @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle,
                                                   @RequestParam(value = "showInventoryOnHand", required = false) boolean showInventoryOnHand)

    {


        if (pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPagedList beerPagedList= beerService.listBeerService(beerName,beerStyle, PageRequest.of(pageNumber,pageSize),showInventoryOnHand);

        return new ResponseEntity<BeerPagedList>(beerPagedList,HttpStatus.OK);


    }




    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId,
                                               @RequestParam(value = "showInventoryOnHand", required = false) boolean showInventoryOnHand)
    {


        return new ResponseEntity<>(beerService.getBeerById(beerId,showInventoryOnHand),HttpStatus.OK);
        //return new ResponseEntity<>(BeerDto.builder().build(),HttpStatus.OK);
    }


    @GetMapping("/upc/{upc}")
    public ResponseEntity<BeerDto> getBeerByUpc(@PathVariable("upc") Long upc)
    {


        return new ResponseEntity<>(beerService.getBeerByUpc(upc),HttpStatus.OK);
        //return new ResponseEntity<>(BeerDto.builder().build(),HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<BeerDto> saveNewBeer(@Validated @RequestBody BeerDto beerDto)
    {
        return new ResponseEntity<>(beerService.saveNewBeer(beerDto),HttpStatus.CREATED);
        //return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<BeerDto> updateBeerById(@Validated @RequestBody BeerDto beerDto, @PathVariable("beerId") UUID beerId)
    {
          return new ResponseEntity<>(beerService.updateBeerById(beerDto,beerId),HttpStatus.NO_CONTENT);
        //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
