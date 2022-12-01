package com.orangetoolz.fileparsing.controller;


import com.orangetoolz.fileparsing.service.CustomerDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class CustomerController {

    private final CustomerDataService customerDataService;

    public CustomerController(CustomerDataService customerDataService) {
        this.customerDataService = customerDataService;
    }

    @GetMapping("/import-data")
    public void importData() throws IOException {
        customerDataService.importData();

    }

    @GetMapping("/export-data")
    public void exportData() {
        customerDataService.exportData();
    }
}
