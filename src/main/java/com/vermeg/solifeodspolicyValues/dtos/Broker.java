package com.vermeg.solifeodspolicyValues.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Broker {

    @Id
    private Long brokerId;

    private String firstName;

    private String lastName;

    private String Address;

    private String phoneNumber;

    private String email;


}
