package com.bolota.syssentinel.Entities.SystemEntities;
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
}
