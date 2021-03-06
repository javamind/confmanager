package com.ninjamind.confman.repository;

import com.ninjamind.confman.domain.Environment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository associé au {@link com.ninjamind.confman.domain.Environment}
 *
 * @author Guillaume EHRET
 */
public interface EnvironmentRepository extends ConfmanRepository<Environment, Long> {

    @Query(value = "SELECT e FROM Environment e  left join fetch e.softwareSuiteEnvironments se left join fetch se.id.softwareSuite s left join fetch s.applications a WHERE a.id = :id")
    List<Environment> findByIdApp(@Param("id") Long id);

    Environment findByCode(String code);
}
