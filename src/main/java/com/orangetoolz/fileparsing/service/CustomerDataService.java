package com.orangetoolz.fileparsing.service;

import com.orangetoolz.fileparsing.common.AppUtils;
import com.orangetoolz.fileparsing.common.FileProperty;
import com.orangetoolz.fileparsing.common.ThreadService;
import com.orangetoolz.fileparsing.dao.entity.InvalidCustomerEntity;
import com.orangetoolz.fileparsing.dao.entity.ValidCustomerEntity;
import com.orangetoolz.fileparsing.dao.repository.InvalidCustomerRepository;
import com.orangetoolz.fileparsing.dao.repository.ValidCustomerRepository;
import com.orangetoolz.fileparsing.dto.CustomerDTO;
import com.orangetoolz.fileparsing.task.DataExportTask;
import com.orangetoolz.fileparsing.task.InvalidDataStoreTask;
import com.orangetoolz.fileparsing.task.ValidDataStoreTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerDataService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ValidCustomerRepository validCustomerRepository;

    private final InvalidCustomerRepository invalidCustomerRepository;

    private final ReaderService readerService;

    private final FileProperty fileProperty;

    private final ThreadService threadService;

    public CustomerDataService(ValidCustomerRepository validCustomerRepository, InvalidCustomerRepository invalidCustomerRepository,
                               ReaderService readerService, FileProperty fileProperty, ThreadService threadService) {
        this.validCustomerRepository = validCustomerRepository;
        this.invalidCustomerRepository = invalidCustomerRepository;
        this.readerService = readerService;
        this.fileProperty = fileProperty;
        this.threadService = threadService;
    }

    public void importData() throws IOException {

        List<CustomerDTO> customers = readerService.readDataFromFile();

        Map<Boolean, List<CustomerDTO>> data = customers.stream()
                .collect(Collectors.partitioningBy(c ->
                        (AppUtils.emailValidate(c.getEmail())
                                && AppUtils.phoneNoValidate(c.getMobileNo()))));

        List<CustomerDTO> validData = removeDuplicate(data.get(Boolean.TRUE));
        List<CustomerDTO> invalidData = removeDuplicate(data.get(Boolean.FALSE));

        storeValidCustomer(validData);
        storeInvalidCustomer(invalidData);
    }

    public void exportData() {
        exportValidData();
        exportInvalidData();
    }

    private void exportValidData() {

        List<CustomerDTO> customers = new ArrayList<>();
        validCustomerRepository.findAll().forEach(i -> customers.add(new CustomerDTO(i)));

        List<List<CustomerDTO>> partitions = AppUtils.partitionData(customers, fileProperty.getFileWriteChunkSize());

        for (int i = 0; i < partitions.size(); i++) {

            String fileName = fileProperty.getOutputFileLocation() + "valid_customer_" + (i + 1) + ".csv";

            logger.info("Valid customer data export stared for: {} and data size : {}", fileName, partitions.get(i).size());

            threadService.addTask(new DataExportTask(partitions.get(i), fileName))
                    .thenAcceptAsync(status -> logger.info("Valid customer data export ended for: {}, status: {}", fileName, status));
        }

    }

    private void exportInvalidData() {

        List<CustomerDTO> customers = new ArrayList<>();
        invalidCustomerRepository.findAll().forEach(i -> customers.add(new CustomerDTO(i)));

        List<List<CustomerDTO>> partitions = AppUtils.partitionData(customers, fileProperty.getFileWriteChunkSize());

        for (int i = 0; i < partitions.size(); i++) {

            String fileName = fileProperty.getOutputFileLocation() + "invalid_customer_" + (i + 1) + ".csv";

            logger.info("Invalid customer data export stared for: {} and data size : {}", fileName, partitions.get(i).size());

            threadService.addTask(new DataExportTask(partitions.get(i), fileName))
                    .thenAcceptAsync(status -> logger.info("Invalid customer data export ended for: {}, status: {}", fileName, status));
        }
    }

    public void storeValidCustomer(List<CustomerDTO> nonDuplicateValidUserList) {

        List<ValidCustomerEntity> validCustomerEntityList = new ArrayList<>();

        nonDuplicateValidUserList.forEach(userInfo -> {
            ValidCustomerEntity validUser = new ValidCustomerEntity(userInfo);
            validCustomerEntityList.add(validUser);
        });

        List<List<ValidCustomerEntity>> partitions = AppUtils.partitionData(validCustomerEntityList, fileProperty.getDbWriteChunkSize());

        for (int i = 0; i < partitions.size(); i++) {

            String taskName = "ValidDataDbSaveTask_" + (i + 1);

            logger.info("Valid customer data save to db stared for: {} and data size : {}", taskName, partitions.get(i).size());

            threadService.addTask(new ValidDataStoreTask(partitions.get(i), validCustomerRepository))
                    .thenAcceptAsync(status -> logger.info("Valid customer data save to db ended for: {}, status: {}", taskName, status));
        }
    }

    public void storeInvalidCustomer(List<CustomerDTO> nonDuplicateInvalidUserList) {

        List<InvalidCustomerEntity> invalidCustomerEntityList = new ArrayList<>();

        nonDuplicateInvalidUserList.forEach(userInfo -> {
            InvalidCustomerEntity invalidCustomer = new InvalidCustomerEntity(userInfo);
            invalidCustomerEntityList.add(invalidCustomer);
        });

        List<List<InvalidCustomerEntity>> partitions = AppUtils.partitionData(invalidCustomerEntityList, fileProperty.getDbWriteChunkSize());

        for (int i = 0; i < partitions.size(); i++) {

            String taskName = "InvalidDataDbSaveTask_" + (i + 1);

            logger.info("Invalid customer data save to db stared for: {} and data size : {}", taskName, partitions.get(i).size());

            threadService.addTask(new InvalidDataStoreTask(partitions.get(i), invalidCustomerRepository))
                    .thenAcceptAsync(status -> logger.info("Invalid customer data save to db ended for: {}, status: {}", taskName, status));
        }
    }

    private List<CustomerDTO> removeDuplicate(List<CustomerDTO> data) {
        Set<String> emailSet = new HashSet<>();
        Set<String> mobileSet = new HashSet<>();

        return data.stream()
                .filter(d -> emailSet.add(d.getEmail()))
                .filter(d -> mobileSet.add(d.getMobileNo()))
                .collect(Collectors.toList());
    }
}
