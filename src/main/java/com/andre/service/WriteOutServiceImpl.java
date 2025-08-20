package com.andre.service;

import com.andre.model.Property;
import com.andre.utility.Constants;
import com.andre.utility.Filter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;

public class WriteOutServiceImpl implements WriteOutService{
    private static final Logger logger = LogManager.getLogger(WriteOutServiceImpl.class);

    private final Filter filter;
    private final String outputPathDir;

    public WriteOutServiceImpl(String outputPathDir) {
        this(new Filter(), outputPathDir);
    }

    public WriteOutServiceImpl(Filter filter, String outputPathDir){
        this.filter = filter;
        this.outputPathDir = outputPathDir;
    }

    @Override
    public void writeOutAllReport(List<Property> result) {
        logger.info("START generate report type: 'ALL'");
        finishingWriteOutSteps(outputPathDir, result, Constants.ReportType.REPORT_ALL.getValue());
    }

    @Override
    public void writeOutActiveReport(List<Property> result) {
        logger.info("START generate report type: 'ACTIVE'");

        var filterList = result.stream()
                .filter(filter.filter())
                .sorted((a, b) -> b.getPrice().compareTo(a.getPrice()))
                .toList();

        finishingWriteOutSteps(outputPathDir, filterList, Constants.ReportType.REPORT_ACTIVE.getValue());
    }

    @Override
    public void writeOutActiveReport14DaysOldOnMarket(List<Property> result) {
        logger.info("START generate report type: 'Active' 14Days old");

        var filterList = result.stream()
                .filter(filter.filter14Days())
                .sorted((a, b) -> b.getPrice().compareTo(a.getPrice()))
                .toList();

        finishingWriteOutSteps(outputPathDir, filterList, Constants.ReportType.REPORT_ACTIVE_14DAYS.getValue());
    }

    @Override
    public void writeOutActiveSoldReport(List<Property> result) {
        logger.info("START generate report type: 'Sold'");

        List<Property> filterList = result.stream()
                .filter(filter.filterSold())
                .sorted((a, b) -> b.getPrice().compareTo(a.getPrice()))
                .toList();

        finishingWriteOutSteps(outputPathDir, filterList, Constants.ReportType.REPORT_SOLD.getValue());
    }

    @Override
    public void writeOutActiveAnalysisInput(List<Property> result) {
        logger.info("START generate 'Active' analysis input");

        var filterList = result.stream()
                .filter(filter.filter14Days())
                .sorted(Comparator.comparing(Property::getPrice).reversed())
                .toList();

        finishingWriteOutStepsAnalysisInput(outputPathDir, filterList, Constants.ReportType.ACTIVE_ANALYSIS_INPUT.getValue());
    }

}
