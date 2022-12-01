package com.orangetoolz.fileparsing.task;

import com.orangetoolz.fileparsing.common.TaskStatus;
import com.orangetoolz.fileparsing.dto.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

public class DataExportTask implements Supplier<TaskStatus> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final List<CustomerDTO> validCustomerEntityList;

    private final String fileName;

    public DataExportTask(List<CustomerDTO> validCustomerEntityList, String fileName) {
        this.validCustomerEntityList = validCustomerEntityList;
        this.fileName = fileName;

    }

    @Override
    public TaskStatus get() {
        try {
            writeDataToFile();
        } catch (IOException e) {
            logger.info("Exception occurred while write data to file.", e);
            return TaskStatus.FAILED;
        } catch (Exception e) {
            logger.error("Exception occurred while writing data to file.", e);
            return TaskStatus.FAILED;
        }
        return TaskStatus.SUCCESS;
    }

    private void writeDataToFile() throws IOException {

        FileWriter writer = new FileWriter(fileName);

        try (BufferedWriter bwr = new BufferedWriter(writer)) {

            writeHeader(bwr);

            for (CustomerDTO customer : validCustomerEntityList) {

                bwr.write(customer.getId().toString());
                bwr.write(",");

                bwr.write(customer.getFirstName() == null ? "" : customer.getFirstName());
                bwr.write(",");

                bwr.write(customer.getLastName() == null ? "" : customer.getLastName());
                bwr.write(",");

                bwr.write(customer.getAddress() == null ? "" : customer.getAddress());
                bwr.write(",");

                bwr.write(customer.getPost() == null ? "" : customer.getPost());
                bwr.write(",");

                bwr.write(customer.getPostCode() == null ? "" : customer.getPostCode());
                bwr.write(",");

                bwr.write(customer.getMobileNo() == null ? "" : customer.getMobileNo());
                bwr.write(",");

                bwr.write(customer.getEmail() == null ? "" : customer.getEmail());
                bwr.write(",");

                bwr.write(customer.getIp() == null ? "" : customer.getIp());

                bwr.newLine();
            }

            bwr.close();

            logger.info("File: {} write successfully.", fileName);
        }
    }

    private void writeHeader(BufferedWriter bwr) throws IOException {
        bwr.write("ID,");
        bwr.write("First Name,");
        bwr.write("Last Name,");
        bwr.write("Address,");
        bwr.write("Post,");
        bwr.write("Post Code,");
        bwr.write("Mobile No,");
        bwr.write("Email,");
        bwr.write("IP");
        bwr.newLine();
    }
}
