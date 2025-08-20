package com.andre.service.functional;

import com.andre.model.Property;
import com.andre.service.functional.common.WritePropertyAnalysis;

import java.util.List;

@FunctionalInterface
public interface WriteFilteredReport extends WritePropertyAnalysis {
    void writeOutActiveReport(List<Property> result);
}
