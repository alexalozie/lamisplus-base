package org.lamisplus.modules.base.controller.apierror;


import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
public class RecordExistException extends EntityNotFoundException {


    public RecordExistException(Class clazz, String... searchParamsMap) {
        super(clazz, searchParamsMap);
    }

    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return StringUtils.capitalize(entity) +
                " already exist " +
                searchParams;
    }

}
