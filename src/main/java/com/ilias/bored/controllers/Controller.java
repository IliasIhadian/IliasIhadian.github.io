package com.ilias.bored.controllers;

import com.ilias.bored.models.LocationStats;
import com.ilias.bored.services.CoronaVirusDataService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/corona")
    public String corona(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        int totalCases = allStats.stream().mapToInt(k -> k.getLatestTotalCases()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("allStats", totalCases);
        return "home";
    }
    @GetMapping("/bio")
    public String bio(Model model) {

        return "bio";
    }


    @GetMapping("/")
    public String home(Model model) {

        return "main";
    }
}
