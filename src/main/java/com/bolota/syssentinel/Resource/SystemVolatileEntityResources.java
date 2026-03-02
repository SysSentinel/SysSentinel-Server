package com.bolota.syssentinel.Resource;

import com.bolota.syssentinel.Entities.SystemEntities.SystemVolatileEntityPersistent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemVolatileEntityResources extends JpaRepository<SystemVolatileEntityPersistent, Long> {
    Page<SystemVolatileEntityPersistent> findBy(Pageable pageable);
    boolean existsByUUID(String uuid);
    void deleteByUUID(String uuid);
    SystemVolatileEntityPersistent getByUUID(String uuid);
}