package com.example.demo.service;

import com.example.demo.dto.PeopleDto;
import com.example.demo.mapper.PeopleMapper;
import com.example.demo.model.People;
import com.example.demo.repository.PeopleRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PeopleService {

    @Autowired
    CacheManager cacheManager;

    @Autowired
    PeopleRepository peopleRepository;

    private PeopleMapper peopleMapper = Mappers.getMapper(PeopleMapper.class);

    @Cacheable(
            cacheNames = "findAllCache",
            unless = "#result == null"
    )
    public List<PeopleDto> findAll() {
        return peopleMapper.peopleListToPeopleDtoList(peopleRepository.findAll());
    }

    @Caching(put = {
            @CachePut(value = "findAllCache"),
            @CachePut(value = "findByIdCache", key = "#peopleDto.id")
    })
    public PeopleDto updatePeople(PeopleDto peopleDto) {
        log.info("Update: Updating cache with name: findAllCache and findByIdCache");
        return peopleMapper.peopleToPeopleDto(peopleRepository.save(peopleMapper.peopleDtoToPeople(peopleDto)));
    }

    @CacheEvict(cacheNames = "findAllCache")
    public PeopleDto insertPeople(PeopleDto peopleDto) {
        log.info("Insert: Flushing cache with name: findAllCache");
        return peopleMapper.peopleToPeopleDto(peopleRepository.save(peopleMapper.peopleDtoToPeople(peopleDto)));
    }

    @Cacheable(
            cacheNames = "findByIdCache",
            key = "#id",
            unless = "#result == null"
    )
    public PeopleDto findById(Long id) {
        Optional<People> findPeople = peopleRepository.findById(id);
        if(findPeople.isPresent()) {
            return peopleMapper.peopleToPeopleDto(findPeople.get());
        } else {
            log.error("People not found");
            return null;
        }
    }

    public void flushCache() {
        for (String cacheName : cacheManager.getCacheNames()) {
            cacheManager.getCache(cacheName).clear();
            log.info("Flushing cache with name: " + cacheName);
        }
    }
}
