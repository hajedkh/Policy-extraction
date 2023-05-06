package com.vermeg.solifeodspolicyValues.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    private Long customerId;

    private String firstName;

    private String lastName;

    private String Address;

    private String phoneNumber;

    private String email;

}
