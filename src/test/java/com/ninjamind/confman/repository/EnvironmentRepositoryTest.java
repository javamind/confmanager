package com.ninjamind.confman.repository;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import com.ninjamind.confman.config.PersistenceConfig;
import com.ninjamind.confman.domain.Environment;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * One test to verify database connection
 *
 * @author EHRET_G
 */
@ContextConfiguration(classes = {PersistenceConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class EnvironmentRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private EnvironmentRepository environmentRepository;

    @Before
    public void setUp(){
        Operation operation =
                sequenceOf(
                        CommonOperations.DELETE_ALL,
                        CommonOperations.INSERT_ENVIRONMENT,
                        CommonOperations.INSERT_SOFTWARE_SUITE,
                        CommonOperations.INSERT_APP,
                        CommonOperations.INSERT_SOFTWARE_SUITE_ENV,
                        CommonOperations.INSERT_INSTANCE
                );
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetup.launch();
    }

    @Test
    public void shouldFindOneEnvironment() {
        assertThat(environmentRepository.findOne(1L).getCode()).isEqualTo("dev");
    }

    @Test
    public void shouldFindEnvironmentByIdApp() {
        assertThat(environmentRepository.findEnvironmentByIdApp(1L)).extracting("code").containsExactly("dev");
    }
}
