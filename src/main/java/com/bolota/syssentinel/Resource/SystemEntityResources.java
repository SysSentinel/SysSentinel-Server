package com.bolota.syssentinel.Resource;

import com.bolota.syssentinel.Entities.SystemEntities.SystemEntityPersistent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemEntityResources extends JpaRepository<SystemEntityPersistent, Long> {
    Page<SystemEntityPersistent> findBy(Pageable pageable);
    Page<SystemEntityPersistent> findByUUID(String uuid, Pageable pageable);
    SystemEntityPersistent getByUUID(String uuid);
    boolean existsByUUID(String uuid);
    void deleteByUUID(String uuid);
}
