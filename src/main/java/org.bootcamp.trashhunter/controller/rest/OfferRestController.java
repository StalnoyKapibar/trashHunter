package org.bootcamp.trashhunter.controller.rest;

import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.services.impl.OfferServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/offer")
public class OfferRestController {

    @Autowired
    private OfferServiceImpl offerServiceImpl;

    @PostMapping
    public List<Offer> getMeeting(@RequestBody Map<String, Object> map) {
        if (map.size() == 0) {
            return offerServiceImpl.getAll();
        }
        return offerServiceImpl.getFilterQuery(map);
    }

    @GetMapping
    public List<Offer> getMeeting() {
        return offerServiceImpl.getAll();
    }
}
