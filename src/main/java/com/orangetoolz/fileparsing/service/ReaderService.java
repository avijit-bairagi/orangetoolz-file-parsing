package com.orangetoolz.fileparsing.service;


import com.orangetoolz.fileparsing.common.FileProperty;
import com.orangetoolz.fileparsing.dto.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ReaderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FileProperty fileProperty;

    public ReaderService(FileProperty fileProperty) {
        this.fileProperty = fileProperty;
    }

    public List<CustomerDTO> readDataFromFile() throws IOException {

        List<CustomerDTO> customers = new ArrayList<>();

        Iterator<String> itr;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileProperty.getInputFileLocation())));
        String strLine;
        ArrayList<String> list = new ArrayList<>();
        while ((strLine = br.readLine()) != null) {
            list.add(strLine);
        }

        logger.info("Total line: {}", list.size());

        for (itr = list.iterator(); itr.hasNext(); ) {
            String str = itr.next();
            String[] splitSt = str.split(",");
            CustomerDTO customerDto = new CustomerDTO();

            for (int i = 0; i < splitSt.length; i++) {

                customerDto.setFirstName(splitSt[0]);
                customerDto.setLastName(splitSt[1]);
                customerDto.setAddress(splitSt[2]);
                customerDto.setPost(splitSt[3]);
                customerDto.setPostCode(splitSt[4]);
                customerDto.setMobileNo(splitSt[5]);
                customerDto.setEmail(splitSt[6]);

                if (splitSt.length > 7) {
                    customerDto.setIp(splitSt[7]);
                }
            }

            customers.add(customerDto);
        }

        return customers;
    }
}
