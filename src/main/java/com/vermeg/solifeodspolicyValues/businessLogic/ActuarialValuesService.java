package com.vermeg.solifeodspolicyValues.businessLogic;


import com.vermeg.solifeodspolicyValues.dtos.*;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ActuarialValuesService {


    public float getTotalPaiedPremiums(Policy policy){
        float payedPremimums = policy.getPremiums().stream().filter(premium -> premium.getPremiumStatus() == PremiumStatus.PAYED)
                .map(Premium::getPremiumValue)
                .reduce(Float::sum).get();
        return payedPremimums;
    }

    public float getTotalFeesValue(Policy policy){
        float totalFees = policy.getFees().stream().map(Fee::getFeeAmount).reduce(Float::sum).get();
        return totalFees;
    }


    public double getBenifitsFromInvestment(Policy policy){
        return  new Random().nextFloat() * 0.5;
    }

    public double getSurrenderValue(Policy policy){
        return Double.parseDouble("0");
    }



}
