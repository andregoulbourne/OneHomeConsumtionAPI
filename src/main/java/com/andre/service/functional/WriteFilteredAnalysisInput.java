package com.andre.service.functional;

import com.andre.model.Property;
import com.andre.service.functional.common.WriteAnalysisInput;

import java.util.List;

@FunctionalInterface
public interface WriteFilteredAnalysisInput extends WriteAnalysisInput {
    void writeOutActiveAnalysisInput(List<Property> result);
}
