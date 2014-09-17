package com.ninjamind.confman.controller.web;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.ninjamind.confman.domain.Environment;
import com.ninjamind.confman.dto.EnvironmentDto;
import com.ninjamind.confman.service.ApplicationFacade;
import com.ninjamind.confman.service.EnvironmentFacade;
import com.ninjamind.confman.service.GenericFacade;
import javafx.application.Application;
import net.codestory.http.annotations.Delete;
import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Post;
import net.codestory.http.annotations.Put;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * {@link }
 *
 * @author Guillaume EHRET
 */
public class EnvironmentWebController {
    @Autowired
    @Qualifier("environmentFacade")
    private EnvironmentFacade environmentFacade;

    @Autowired
    private ApplicationFacade applicationFacade;

    @Get("/environment")
    public List<EnvironmentDto> list() {
        return Lists.transform(environmentFacade.findAll(), env -> new EnvironmentDto(env));
    }

    @Get("/environment/application/:id")
    public List<EnvironmentDto> listByApp(Long id) {
        return Lists.transform(applicationFacade.findEnvironmentByIdApp(id), env -> new EnvironmentDto(env));
    }

    @Get("/environment/:id")
    public EnvironmentDto get(Long id) {
        return new EnvironmentDto(environmentFacade.findOne(id));
    }

    @Put("/environment")
    public EnvironmentDto update(EnvironmentDto env) {
        Preconditions.checkNotNull(env, "Object is required to update it");
        return new EnvironmentDto(environmentFacade.update(env.toEnvironment()));
    }

    @Post("/environment")
    public EnvironmentDto save(EnvironmentDto env) {
        Preconditions.checkNotNull(env, "Object is required to save it");
        return new EnvironmentDto(environmentFacade.create(env.toEnvironment()));
    }

    @Delete("/environment/:id")
    public void delete(Long id) {
        environmentFacade.delete(id);
    }
}