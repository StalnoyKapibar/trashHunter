package org.bootcamp.trashhunter.controller;

import jdk.internal.org.xml.sax.SAXException;
import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.services.impl.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
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


}
