package org.bootcamp.trashhunter.controllers;

import jdk.internal.org.xml.sax.SAXException;
import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.services.impl.OfferServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


@Controller
public class MapController {

    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;

    @Autowired
    private OfferServiceImpl offerServiceImpl;

    //todo reduce (will be done after merge by Matvey
    @RequestMapping("/map")
    public String map(Model model) throws ParserConfigurationException, SAXException, IOException, org.xml.sax.SAXException, URISyntaxException {
        List<Offer> offerList = offerServiceImpl.getAll();
        model.addAttribute("offerList", offerList);
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        return "map";
    }

    @RequestMapping("/map_geo")
    public String map2(Model model) {
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        return "map_geo";
    }

}
