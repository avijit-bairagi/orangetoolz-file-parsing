package com.orangetoolz.fileparsing;

import com.orangetoolz.fileparsing.service.CustomerDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class FileParsingApplicationTests {

    @Autowired
    private CustomerDataService customerDataService;

    @Test
    void importData() throws IOException {
        //customerDataService.importData();
    }

    @Test
    void exportData() {
        //customerDataService.exportData();
    }
}
