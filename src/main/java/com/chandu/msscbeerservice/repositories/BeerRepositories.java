package com.chandu.msscbeerservice.repositories;

import com.chandu.msscbeerservice.domain.Beer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BeerRepositories extends PagingAndSortingRepository<Beer, UUID> {
}
