package org.bootcamp.trashhunter.controller;

import jdk.internal.org.xml.sax.SAXException;
import org.bootcamp.trashhunter.embedded.Coordinates;
import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.services.impl.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.net.URL;
import java.util.List;


@Controller
public class MapController {

    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;

    @Autowired
    private OfferService offerService;

    @RequestMapping("/map")
    public String map(Model model) throws ParserConfigurationException, SAXException, IOException, org.xml.sax.SAXException, URISyntaxException {
        List<Offer> offerList = offerService.getAll();

        model.addAttribute("offerList", offerList);
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);

        return "map";
    }

    @RequestMapping("/map_geo")
    public String map2(Model model) {
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        return "map_geo";

    }

    @RequestMapping("/offer/coordinates")
    @ResponseBody
    public List<Coordinates> geoCoordinates(Model model) {
        return Arrays.asList(
                new Coordinates(60.111732, 30.267616),
                new Coordinates(60.111533, 30.265730),
                new Coordinates(60.107563, 30.268029)

        );

    }


}
