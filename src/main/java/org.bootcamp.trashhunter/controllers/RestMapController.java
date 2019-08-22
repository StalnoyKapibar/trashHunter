package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.models.dto.OfferDto;
import org.bootcamp.trashhunter.services.impl.OfferServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RestMapController {

    private final OfferServiceImpl offerServiceImpl;

    public RestMapController(OfferServiceImpl offerServiceImpl) {
        this.offerServiceImpl = offerServiceImpl;
    }

    //todo (will be done after Matvey merge)
    @GetMapping("/offer/coordinates")
    public List<OfferDto> geoCoordinates() {
        return offerServiceImpl.getAll().stream().map(OfferDto::getDtoIdCoordTrash).collect(Collectors.toList());
    }

    @GetMapping("/offer/{id}")
    public OfferDto getOfferById(@PathVariable("id") long id) {
        return OfferDto.getDto(offerServiceImpl.getById(id));
    }


}
