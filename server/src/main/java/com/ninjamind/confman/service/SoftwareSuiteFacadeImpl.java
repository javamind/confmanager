package com.ninjamind.confman.service;

import com.ninjamind.confman.domain.ParameterGroupment;
import com.ninjamind.confman.domain.SoftwareSuite;
import com.ninjamind.confman.domain.SoftwareSuiteEnvironment;
import com.ninjamind.confman.repository.EnvironmentRepository;
import com.ninjamind.confman.repository.SofwareSuiteEnvironmentRepository;
import com.ninjamind.confman.repository.SofwareSuiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * {@link }
 *
 * @author Guillaume EHRET
 */
@Service("softwareSuiteFacade")
@Transactional
public class SoftwareSuiteFacadeImpl implements SoftwareSuiteFacade{
    @Autowired
    private SofwareSuiteRepository softwareSuiteRepository;

    @Autowired
    private EnvironmentRepository environmentRepository;

    @Autowired
    private SofwareSuiteEnvironmentRepository softwareSuiteEnvironmentRepository;

    @Override
    public SofwareSuiteRepository getRepository() {
        return softwareSuiteRepository;
    }

    @Override
    public Class<SoftwareSuite> getClassEntity() {
        return SoftwareSuite.class;
    }


    @Override
    public List<SoftwareSuiteEnvironment> findSoftwareSuiteEnvironmentByIdSoft(Long id) {
        return softwareSuiteEnvironmentRepository.findByIdSoft(id);
    }

    @Override
    public SoftwareSuite update(SoftwareSuite softwareSuite, Set<SoftwareSuiteEnvironment> suiteEnvironmentSet) {
        //we delete all the linked
        softwareSuiteEnvironmentRepository.delete(findSoftwareSuiteEnvironmentByIdSoft(softwareSuite.getId()));
        softwareSuite.clearSoftwareSuiteEnvironments();
        updateTracability(softwareSuite);
        SoftwareSuite suiteSaved = getRepository().save(softwareSuite);

        if(suiteEnvironmentSet!=null && !suiteEnvironmentSet.isEmpty()) {
            //Attach the objects to the session
            for(SoftwareSuiteEnvironment softwareSuiteEnvironment : suiteEnvironmentSet){
                softwareSuiteEnvironmentRepository.save(
                        new SoftwareSuiteEnvironment(
                                suiteSaved,
                                environmentRepository.findOne(softwareSuiteEnvironment.getId().getEnvironment().getId())
                                .setActive(true)
                ));
                softwareSuiteEnvironment.setId(null);
            }
        }
        return suiteSaved;
    }

    @Override
    public SoftwareSuite findByCode(SoftwareSuite entity) {
        return getRepository().findByCode(entity.getCode());
    }
}
