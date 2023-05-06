package com.vermeg.solifeodspolicyValues.batch;

import com.vermeg.solifeodspolicyValues.dtos.Policy;
import com.vermeg.solifeodspolicyValues.dtos.PolicyActuarialValue;
import org.springframework.batch.item.ItemProcessor;

import java.util.Random;

public class PolicyItemProcessor implements ItemProcessor<Policy, PolicyActuarialValue> {

    @Override
    public PolicyActuarialValue process(Policy item) throws Exception {
        Random rd = new Random();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("interruption");
        }
        return new PolicyActuarialValue(item.getId()+rd.nextLong(),  Double.parseDouble("22"),  Double.parseDouble("22"),
                Double.parseDouble("22"),  Double.parseDouble("22"));
    }
}
