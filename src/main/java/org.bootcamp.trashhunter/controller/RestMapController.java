package org.bootcamp.trashhunter.controller;

import org.bootcamp.trashhunter.models.dto.OfferDto;
import org.bootcamp.trashhunter.services.impl.OfferService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RestMapController {

    private final OfferService offerService;

    public RestMapController(OfferService offerService) {
        this.offerService = offerService;
    }

    //todo
    @GetMapping("/offer/coordinates")
    public List<OfferDto> geoCoordinates() {
        return offerService.getAll().stream().map(OfferDto::getDtoIdCoordTrash).collect(Collectors.toList());
    }

    @GetMapping("/offer/{id}")
    public OfferDto getOfferById(@PathVariable("id") long id) {
        return OfferDto.getDto(offerService.getById(id));
    }


}
