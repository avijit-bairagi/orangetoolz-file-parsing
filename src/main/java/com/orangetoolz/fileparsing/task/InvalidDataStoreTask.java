package com.orangetoolz.fileparsing.task;

import com.orangetoolz.fileparsing.common.TaskStatus;
import com.orangetoolz.fileparsing.dao.entity.InvalidCustomerEntity;
import com.orangetoolz.fileparsing.dao.repository.InvalidCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.function.Supplier;

public class InvalidDataStoreTask implements Supplier<TaskStatus> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final List<InvalidCustomerEntity> customers;

    private final InvalidCustomerRepository repository;

    public InvalidDataStoreTask(List<InvalidCustomerEntity> customers, InvalidCustomerRepository repository) {
        this.customers = customers;
        this.repository = repository;
    }

    @Override
    public TaskStatus get() {

        try {
            repository.saveAll(customers);
        } catch (DataIntegrityViolationException e) {
            logger.error("Duplicate data found.", e);
            return TaskStatus.FAILED;
        } catch (Exception e) {
            logger.error("Exception occurred while saving data to db.", e);
            return TaskStatus.FAILED;
        }
        return TaskStatus.SUCCESS;
    }
}
