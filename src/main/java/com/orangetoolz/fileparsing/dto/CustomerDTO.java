package com.orangetoolz.fileparsing.dto;

import com.orangetoolz.fileparsing.dao.entity.InvalidCustomerEntity;
import com.orangetoolz.fileparsing.dao.entity.ValidCustomerEntity;
import lombok.Data;

@Data
public class CustomerDTO {

    private Integer id;

    private String firstName;

    private String lastName;

    private String address;

    private String post;

    private String postCode;

    private String mobileNo;

    private String email;

    private String ip;

    public CustomerDTO() {
    }

    public CustomerDTO(ValidCustomerEntity customerDto) {
        this.id = customerDto.getId();
        this.firstName = customerDto.getFirstName();
        this.lastName = customerDto.getLastName();
        this.address = customerDto.getAddress();
        this.post = customerDto.getPost();
        this.postCode = customerDto.getPostCode();
        this.mobileNo = customerDto.getMobileNo();
        this.email = customerDto.getEmail();
        this.ip = customerDto.getIp();
    }

    public CustomerDTO(InvalidCustomerEntity customerDto) {
        this.id = customerDto.getId();
        this.firstName = customerDto.getFirstName();
        this.lastName = customerDto.getLastName();
        this.address = customerDto.getAddress();
        this.post = customerDto.getPost();
        this.postCode = customerDto.getPostCode();
        this.mobileNo = customerDto.getMobileNo();
        this.email = customerDto.getEmail();
        this.ip = customerDto.getIp();
    }
}
