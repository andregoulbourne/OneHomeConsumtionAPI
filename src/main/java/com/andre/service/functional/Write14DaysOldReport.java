package com.andre.service.functional;

import com.andre.model.Property;
import com.andre.service.functional.common.WritePropertyAnalysis;

import java.util.List;

public interface Write14DaysOldReport extends WritePropertyAnalysis {

    void writeOutActiveReport14DaysOldOnMarket(List<Property> result);

}
