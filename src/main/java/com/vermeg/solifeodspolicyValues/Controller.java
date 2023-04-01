package com.vermeg.solifeodspolicyValues;


import com.vermeg.solifeodspolicyValues.dtos.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {


    @Autowired
    PolicyRepo policyRepo;




    @PostMapping("/createPolicy")
    public ResponseEntity<Policy> createpolicy(@RequestBody Policy policy){

        policyRepo.save(policy);
        return new ResponseEntity<Policy>(HttpStatus.CREATED);
    }

    @GetMapping("/getPolicies")
    public List<Policy> getpolicy(){
        List<Policy> allPolicies = policyRepo.findAll();
        return allPolicies;

    }
}
