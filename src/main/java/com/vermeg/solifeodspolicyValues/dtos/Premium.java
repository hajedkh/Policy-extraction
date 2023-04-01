package com.vermeg.solifeodspolicyValues.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.ValueGenerationType;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Premium {

    @Id
    private Long premiumId;

    private float premiumValue;

    @Enumerated(EnumType.STRING)
    private PremiumStatus premiumStatus;




}
