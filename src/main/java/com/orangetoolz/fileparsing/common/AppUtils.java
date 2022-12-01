package com.orangetoolz.fileparsing.common;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AppUtils {

    public static boolean phoneNoValidate(String phoneNumber) {
        return Pattern.compile(Constants.PHONE_REGEX).matcher(phoneNumber).matches();
    }

    public static boolean emailValidate(String email) {
        return Pattern.compile(Constants.EMAIL_REGEX).matcher(email).matches();
    }

    public static <T> List<List<T>> partitionData(List<T> data, int chunkSize) {
        return IntStream.range(0, data.size())
                .filter(i -> i % chunkSize == 0)
                .mapToObj(i -> data.subList(i, Math.min(i + chunkSize, data.size())))
                .collect(Collectors.toList());
    }
}
