package com.ninjamind.confman.web;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.ninjamind.confman.domain.Application;
import com.ninjamind.confman.dto.ApplicationDto;
import com.ninjamind.confman.service.ApplicationFacade;
import com.ninjamind.confman.service.GenericFacade;
import net.codestory.http.annotations.Delete;
import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Post;
import net.codestory.http.annotations.Put;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link }
 *
 * @author EHRET_G
 */
public class ApplicationController {
    @Autowired
    @Qualifier("applicationFacade")
    private ApplicationFacade<Application, Long> genericFacade;

    @Get("/application")
    public List<ApplicationDto> list() {
        return Lists.transform(genericFacade.findAll(), env -> new ApplicationDto(env));
    }

    @Get("/application/:id")
    public ApplicationDto get(Long id) {
        Application app = genericFacade.findOneWthDependencies(id);
        return new ApplicationDto(
                app,
                app.getApplicationVersions().stream().collect(Collectors.toList()),
                app.getInstances().stream().collect(Collectors.toList()),
                app.getParameters().stream().collect(Collectors.toList()));
    }

    @Put("/application")
    public ApplicationDto update(ApplicationDto env) {
        Preconditions.checkNotNull(env, "Object is required to update it");
        return new ApplicationDto(genericFacade.save(env.toApplication()));
    }

    @Post("/application")
    public ApplicationDto save(ApplicationDto env) {
        Preconditions.checkNotNull(env, "Object is required to save it");
        return new ApplicationDto(genericFacade.save(env.toApplication()));
    }

    @Delete("/application/:id")
    public void delete(Long id) {
        genericFacade.delete(id);
    }
}
