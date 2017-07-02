package com.ianhattendorf.geth.gethstatus.web;

import com.ianhattendorf.geth.gethstatus.domain.GethStatus;
import com.ianhattendorf.geth.gethstatus.service.GeoService;
import com.ianhattendorf.geth.gethstatus.service.GethService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final GethService gethService;
    private final GeoService geoService;

    @Autowired
    public IndexController(GethService gethService, GeoService geoService) {
        this.gethService = gethService;
        this.geoService = geoService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        GethStatus gethStatus = new GethStatus(gethService);
        model.addAttribute("gethStatus", gethStatus);
        model.addAttribute("geoInfo", geoService.getInfo());
        return "index";
    }
}
