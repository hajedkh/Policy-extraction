package com.vermeg.solifeodspolicyValues.dtos;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Policy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "policy_sequence2")
    private  Long id;


    private String value;

    private Date startDate;

    private Date endDate;

    @Enumerated(EnumType.STRING)
    private PolicyType policyType;

    @Enumerated(EnumType.STRING)
    private PolicyStatus policyStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    private  Broker broker;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Premium> premiums;

    @OneToMany(cascade = CascadeType.ALL)
    private  List<Fee> fees;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Investment> investments;




}
