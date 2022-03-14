package com.ilias.bored.controllers;

import com.ilias.bored.models.LocationStats;
import com.ilias.bored.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/corona")
    public String corona(Model model) {
        List<LocationStats> AllStats = coronaVirusDataService.getAllStats();
        int totalCases = AllStats.stream().mapToInt(k -> k.getLatestTotalCases()).sum();
        model.addAttribute("locationStats", AllStats);
        model.addAttribute("allStats", totalCases);
        return "home";
    }


    @GetMapping("/")
    public String home(Model model) {

        return "main";
    }
}
