package me.gogosing.component.impl;

import me.gogosing.component.GenerateIdComponent;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class GenerateIdComponentImpl implements GenerateIdComponent {

    private final int albumIdLength = 14;

    private final int songIdLength = 14;

    @Override
    public String albumId() {
        return generateId("FA", "yyMMdd", albumIdLength);
    }

    @Override
    public String songId() {
        return generateId("FS", "yyMMdd", songIdLength);
    }

    /**
     * 고유 식별자 생성.
     * @param prefix 식별자 접두사.
     * @param dateFormat 식별자 본문에 포함될 날짜 형식.
     * @param length 식별자 생성 길이.
     * @return 생성된 고유 식별자.
     */
    private String generateId(String prefix, String dateFormat, int length) {
        var currentDate = "";
        if (dateFormat != null) {
            currentDate = ZonedDateTime.now()
                .format(DateTimeFormatter.ofPattern(dateFormat));
        }

        var suffixLength = length - (prefix.length() + currentDate.length());

        var randomString = RandomStringUtils
            .randomAlphanumeric(suffixLength).toUpperCase();

        return new StringBuilder(prefix)
                .append(currentDate).append(randomString).toString();
    }
}
