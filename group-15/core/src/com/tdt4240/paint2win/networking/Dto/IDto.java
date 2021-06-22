package com.tdt4240.paint2win.networking.Dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;


public interface IDto {

    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converts object to json for network transfer.
     * @throws RuntimeException When object can't be converted to JSON.
     * @return Object as JSON-string
     */
    default String toJsonString(){
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting to JSON: ", e);
        }
    }

    /**
     * Parses json and creates object.
     * @param json JSON-string to be parsed.
     * @param dtoTypeClass Class of object that's sent.
     * @throws RuntimeException When object JSON can't be parsed.
     * @return Object parsed.
     */
    static <DtoType extends IDto> DtoType fromJsonString(String json, Class<DtoType> dtoTypeClass){
        try {
            return objectMapper.readValue(json, dtoTypeClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON to Object: ", e);
        }
    }

    /**
     * Parses json objects in and array and creates a list of objects.
     * @param json JSON-string to be parsed.
     * @param dtoTypeClass Class of object that's sent.
     * @throws RuntimeException When object JSON can't be parsed.
     * @return Object parsed.
     */
    static <DtoType extends IDto> List<DtoType> fromArryWithJsonString(String json, Class<DtoType> dtoTypeClass){
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, dtoTypeClass));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON to Object: ", e);
        }
    }
}
