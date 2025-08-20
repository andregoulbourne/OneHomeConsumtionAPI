package com.andre.service.functional.common;

import com.andre.model.Property;
import com.andre.writer.CSVUtility;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;

public interface WritePropertyAnalysis {
     Logger logger = LogManager.getLogger(WritePropertyAnalysis.class);

     default void finishingWriteOutSteps(String outputPathDir, List<Property> filterList, String path) {
        filterList.forEach(logger::info);

        if(StringUtils.isBlank(outputPathDir.replace(File.separator, "")))
            CSVUtility.writeOutListOfProperties("./src/main/resources/output/"+path, filterList);
        else
            CSVUtility.writeOutListOfProperties(outputPathDir+path, filterList);
    }
}
