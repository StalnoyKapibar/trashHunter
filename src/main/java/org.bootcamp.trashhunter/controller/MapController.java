package org.bootcamp.trashhunter.controller;

import jdk.internal.org.xml.sax.SAXException;
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


@Controller
public class MapController {

    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;


    @RequestMapping("/map")
    public String map(Model model) throws ParserConfigurationException, SAXException, IOException, org.xml.sax.SAXException, URISyntaxException {
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        return "map";

    }


}
