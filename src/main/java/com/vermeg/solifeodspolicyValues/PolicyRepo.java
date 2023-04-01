package com.vermeg.solifeodspolicyValues;

import com.vermeg.solifeodspolicyValues.dtos.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PolicyRepo extends JpaRepository<Policy,Long> {
}





