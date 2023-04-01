package com.vermeg.solifeodspolicyValues.batch;

import com.vermeg.solifeodspolicyValues.dtos.Policy;
import com.vermeg.solifeodspolicyValues.dtos.PolicyActuarialValue;
import org.springframework.batch.item.ItemProcessor;

import java.util.Random;

public class PolicyItemProcessor implements ItemProcessor<Policy, PolicyActuarialValue> {

    @Override
    public PolicyActuarialValue process(Policy item) throws Exception {
        Random rd = new Random();
        return new PolicyActuarialValue(item.getId(),  54);
    }
}
