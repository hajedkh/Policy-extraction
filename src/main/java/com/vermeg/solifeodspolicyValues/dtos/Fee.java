package com.vermeg.solifeodspolicyValues.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Fee {

    @Id
    private Long feeId;


    private String feeLabel;


    @Enumerated(EnumType.STRING)
    private FeeType feetype;


    private float feeAmount;

    private int paymentFrequency;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;




}
