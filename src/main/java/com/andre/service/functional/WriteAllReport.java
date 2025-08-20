package com.andre.service.functional;

import com.andre.model.Property;
import com.andre.service.functional.common.WritePropertyAnalysis;

import java.util.List;

@FunctionalInterface
public interface WriteAllReport extends WritePropertyAnalysis {
    void writeOutAllReport(List<Property> result);
}
