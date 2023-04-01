package com.vermeg.solifeodspolicyValues.dtos;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PolicyActuarialValue implements Serializable {

    @Id
    private Long id;


    private double totalPaiedPremiums;

    private double  benifitsFromInvestment;

    private double totalFeesValue;

    private double surrenderValue;

}
