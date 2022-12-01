package com.orangetoolz.fileparsing.dao.repository;

import com.orangetoolz.fileparsing.dao.entity.ValidCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidCustomerRepository extends JpaRepository<ValidCustomerEntity, Integer> {
}
