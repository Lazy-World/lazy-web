package com.lady.messenger.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {
     public static Map<String, String> getErrorMap(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> fieldErrorMapCollector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        return bindingResult.getFieldErrors().stream().collect(fieldErrorMapCollector);
    }

    public static <T> Iterable<T> reverseList(Iterable<T> iterable) {
        LinkedList<T> list = new LinkedList<>();
        for (T item : iterable) {
            list.addFirst(item);
        }
        return list;
    }

}
