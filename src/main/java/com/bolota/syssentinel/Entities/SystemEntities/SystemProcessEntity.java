package com.bolota.syssentinel.Entities.SystemEntities;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class SystemProcessEntity {
    private String  name;
    private int     pid;
    private double  residentMem; // em MBs
    private double  virtualMem;  // em GBs
    private double  cpuLoad;
}
