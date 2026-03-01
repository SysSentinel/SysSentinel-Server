package com.bolota.syssentinel.Entities.SystemEntities;
import com.bolota.syssentinel.Entities.DTOs.SystemEntityDTO;
import com.bolota.syssentinel.Entities.DTOs.SystemVolatileEntityDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class SystemVolatileEntity {
    private String UUID;
    private HashMap<String, Double> internetCurrentUsage;
    private HashMap<String, String> internetAdapters;
    private ArrayList<SystemProcessEntity> systemProcessEntities;
    public SystemVolatileEntity(SystemVolatileEntityDTO sveDto) throws JsonProcessingException {
        this.UUID = sveDto.getUUID();
        this.internetAdapters = new ObjectMapper().readValue(sveDto.getInternetAdapters(), HashMap.class);
        this.internetCurrentUsage = new ObjectMapper().readValue(sveDto.getInternetCurrentUsage(), HashMap.class);
        this.systemProcessEntities = new ObjectMapper().readValue(sveDto.getSystemProcessEntities(), ArrayList.class);
    }
}
