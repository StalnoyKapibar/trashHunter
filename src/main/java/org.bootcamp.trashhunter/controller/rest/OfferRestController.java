package org.bootcamp.trashhunter.controller.rest;

import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.services.impl.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/offer")
public class OfferRestController {

    @Autowired
    private OfferService offerService;

    @GetMapping
    public List<Offer> getMeeting(Map<String, Object> map) {
//        if (map.size()==0){
//            return offerService.getFilterQuery(new HashMap<>());
//        }
//        return offerService.getFilterQuery(map);
        List<Offer> filterQuery = offerService.getFilterQuery(map);
        Offer byId = offerService.getById(1l);
        List<Offer> all = offerService.getAll();
        return all;
    }
}
