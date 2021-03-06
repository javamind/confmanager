package com.ninjamind.confman.repository;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import com.ninjamind.confman.ConfmanApplication;
import com.ninjamind.confman.config.PersistenceConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test of {@link ApplicationVersionRepository}
 *
 * @author Guillaume EHRET
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {PersistenceConfig.class})
public class ApplicationVersionRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ApplicationVersionRepository applicationtVersionRepository;

    @Before
    public void setUp(){
        Operation operation =
                sequenceOf(
                        CommonOperations.DELETE_ALL,
                        CommonOperations.INSERT_ENVIRONMENT,
                        CommonOperations.INSERT_SOFTWARE_SUITE,
                        CommonOperations.INSERT_APP,
                        CommonOperations.INSERT_VERSION
                );
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetup.launch();
    }


    @Test
    public void shouldFindOneApplicationVersion() {
        assertThat(applicationtVersionRepository.findByIdApp(1L)).hasSize(1).extracting("code").containsExactly("1.0.0");
    }

    @Test
    public void shouldNotFindApplicationVersionByCodeWhenCodeIsNull() {
        assertThat(applicationtVersionRepository.findByCode(null, null)).isNull();
    }

    @Test
    public void shouldFindApplicationVersionByCode() {
        assertThat(applicationtVersionRepository.findByCode("CFM", "1.0.0").getLabel()).isEqualTo("app version");
    }
}
