package com.ninjamind.confman.service;

import com.github.zafarkhaja.semver.GrammarException;
import com.github.zafarkhaja.semver.Version;
import com.github.zafarkhaja.semver.util.UnexpectedElementTypeException;
import com.ninjamind.confman.domain.Application;
import com.ninjamind.confman.domain.ApplicationVersion;
import com.ninjamind.confman.exception.FoundException;
import com.ninjamind.confman.exception.NotFoundException;
import com.ninjamind.confman.repository.ApplicationVersionRepository;
import com.ninjamind.confman.repository.ApplicationtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link }
 *
 * @author Guillaume EHRET
 */
@Service("applicationVersionFacade")
@Transactional
public class ApplicationVersionFacadeImpl implements ApplicationVersionFacade {
    @Autowired
    private ApplicationVersionRepository applicationVersionRepository;
    @Autowired
    private ApplicationtRepository applicationtRepository;

    @Override
    public ApplicationVersionRepository getRepository() {
        return applicationVersionRepository;
    }

    @Override
    public Class<ApplicationVersion> getClassEntity() {
        return ApplicationVersion.class;
    }

    @Override
    public boolean checkVersionNumber(String number) {
        try{
            Version.valueOf(number);
            return true;
        }
        catch (NullPointerException | UnexpectedElementTypeException | GrammarException e){
            return false;
        }
    }

    @Override
    public void saveVersionToApplication(String codeApp, String codeVersion, String labelVersion, boolean creation) {
        //Is the application exist? If not it's an error
        Application application = NotFoundException.notFoundIfNull(applicationtRepository.findByCode(codeApp));

        //Is the parameter exist ?
        ApplicationVersion version = applicationVersionRepository.findByCode(codeApp, codeVersion);

        if(creation){
            //If we want to create a new one we throw an exception if parameter exist
            FoundException.foundExceptionIfNotNullAndActive(version);
            version = new ApplicationVersion().setCode(codeVersion).setApplication(application);
        }

        version.setLabel(labelVersion) ;
        applicationVersionRepository.save(version);
    }


    @Override
    public ApplicationVersion findByCode(ApplicationVersion entity) {
        return applicationVersionRepository.findByCode(entity.getApplication().getCode(), entity.getCode());
    }

}
