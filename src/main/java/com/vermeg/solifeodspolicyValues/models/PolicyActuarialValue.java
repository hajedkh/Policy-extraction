package com.vermeg.solifeodspolicyValues.models;


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
    private String id;


    private int value;

}
