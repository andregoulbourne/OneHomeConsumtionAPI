package com.andre.service;

import com.andre.service.functional.*;

public interface WriteOutService extends WriteAllReport, WriteFilteredReport, Write14DaysOldReport, WriteSoldReport, WriteFilteredAnalysisInput {
    // This interface combines all the write operations for reports and analysis inputs.
    // It extends multiple interfaces to provide a comprehensive set of methods for writing out data.
    // Each method in the extended interfaces must be implemented by any class that implements WriteOutService.
}