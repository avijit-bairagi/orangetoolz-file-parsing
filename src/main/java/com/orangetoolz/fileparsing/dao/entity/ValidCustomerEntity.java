package com.orangetoolz.fileparsing.dao.entity;

import com.orangetoolz.fileparsing.dto.CustomerDTO;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "VALID_CUSTOMER_INFO")
public class ValidCustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_generator")
    @SequenceGenerator(name = "t_generator", sequenceName = "TABLE_SEQUENCE", initialValue = 1)
    private Integer id;

    private String firstName;

    private String lastName;

    private String address;

    private String post;

    private String postCode;
    @Column(unique = true)

    private String mobileNo;
    @Column(unique = true)

    private String email;

    private String ip;

    public ValidCustomerEntity(CustomerDTO customerDto) {
        this.firstName = customerDto.getFirstName();
        this.lastName = customerDto.getLastName();
        this.address = customerDto.getAddress();
        this.post = customerDto.getPost();
        this.postCode = customerDto.getPostCode();
        this.mobileNo = customerDto.getMobileNo();
        this.email = customerDto.getEmail();
        this.ip = customerDto.getIp();
    }

    public ValidCustomerEntity() {

    }
}
