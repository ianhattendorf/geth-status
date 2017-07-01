package com.ianhattendorf.geth.gethstatus.web;

import com.ianhattendorf.geth.gethstatus.domain.GethStatus;
import com.ianhattendorf.geth.gethstatus.service.GethService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final GethService gethService;

    @Autowired
    public IndexController(GethService gethService) {
        this.gethService = gethService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        GethStatus gethStatus = new GethStatus();
        gethStatus.setPeerCount(gethService.getPeerCount());
        model.addAttribute("gethStatus", gethStatus);
        return "index";
    }
}
