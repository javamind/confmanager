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
 * Test of {@link com.ninjamind.confman.repository.TrackingVersionRepository}
 *
 * @author Guillaume EHRET
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {PersistenceConfig.class})
public class TrackingVersionRepositoryTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private TrackingVersionRepository trackingVersionRepository;

    @Before
    public void setUp() {
        Operation operation =
                sequenceOf(
                        CommonOperations.DELETE_ALL,
                        CommonOperations.INSERT_ENVIRONMENT,
                        CommonOperations.INSERT_SOFTWARE_SUITE,
                        CommonOperations.INSERT_SOFTWARE_SUITE_ENV,
                        CommonOperations.INSERT_APP,
                        CommonOperations.INSERT_INSTANCE,
                        CommonOperations.INSERT_VERSION,
                        CommonOperations.INSERT_PARAMETER,
                        CommonOperations.INSERT_VERSION_TRACKING
                );
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetup.launch();
    }

    @Test
    public void shouldNotFindTrackingVersionByIdAppWhenIdAppIsNull(){
        assertThat(trackingVersionRepository.findByIdApp(null)).isEmpty();
    }

    @Test
    public void shouldFindTrackingVersionByIdApp(){
        assertThat(trackingVersionRepository.findByIdApp(1L)).hasSize(1).extracting("code").contains("1.0.0-track.1");
    }

    @Test
    public void shouldNotFindTrackingVersionByIdAppVersionWhenIdAppIsNull(){
        assertThat(trackingVersionRepository.findByIdAppVersion(null)).isEmpty();
    }

    @Test
    public void shouldFindTrackingVersionByIdAppVersion(){
        assertThat(trackingVersionRepository.findByIdAppVersion(1L)).hasSize(1).extracting("code").contains("1.0.0-track.1");
    }
}
