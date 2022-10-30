package com.vermeg.solifeodspolicyValues.batch;

import com.vermeg.solifeodspolicyValues.models.Policy;
import com.vermeg.solifeodspolicyValues.models.PolicyActuarialValue;
import org.springframework.batch.item.ItemProcessor;

import java.util.Random;

public class PolicyItemProcessor implements ItemProcessor<Policy, PolicyActuarialValue> {

    @Override
    public PolicyActuarialValue process(Policy item) throws Exception {
        Random rd = new Random();
        return new PolicyActuarialValue(item.getId()+String.valueOf(rd.nextInt()), Integer.parseInt(item.getValue())+ 54);
    }
}
