package com.bolota.syssentinel.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SysSentinelService {
    public static String genJSON(Object o){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.writeValueAsString(o);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
