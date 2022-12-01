package com.orangetoolz.fileparsing.dao.repository;


import com.orangetoolz.fileparsing.dao.entity.InvalidCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidCustomerRepository extends JpaRepository<InvalidCustomerEntity, Integer> {
}
