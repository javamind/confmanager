package com.ninjamind.confman.service;

import com.ninjamind.confman.domain.Application;
import com.ninjamind.confman.domain.ApplicationVersion;
import com.ninjamind.confman.domain.Instance;
import com.ninjamind.confman.domain.Parameter;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

/**
 * {@link }
 *
 * @author EHRET_G
 */
public interface ApplicationFacade<T, ID extends Serializable> extends GenericFacade<T, ID> {

    /**
     * @see com.ninjamind.confman.repository.ApplicationtRepository#findApplicationVersionByIdApp(Long)
     * @param id
     * @return
     */
    List<ApplicationVersion> findApplicationVersionByIdApp( Long id);

    /**
     * @see com.ninjamind.confman.repository.ApplicationtRepository#findParameterByIdApp(Long)
     * @param id
     * @return
     */
    List<Parameter> findParameterByIdApp( Long id);

    /**
     * @see com.ninjamind.confman.repository.ApplicationtRepository#findInstanceByIdApp(Long)
     * @param id
     * @return
     */
    List<Instance> findInstanceByIdApp( Long id);

    /**
     * @see com.ninjamind.confman.repository.ApplicationtRepository#findOneWthDependencies(Long)
     * @param id
     * @return
     */
    Application findOneWthDependencies(Long id);
}
