package me.gogosing.persistence.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA boolean 과 Y/N 을 변환해 주는 Converter.
 */
@Converter
public class BooleanToCharConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "Y".equals(dbData);
    }
}
