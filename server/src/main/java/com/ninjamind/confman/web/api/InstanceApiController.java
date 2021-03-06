package com.ninjamind.confman.web.api;

import com.google.common.base.Preconditions;
import com.ninjamind.confman.domain.Instance;
import com.ninjamind.confman.dto.InstanceConfmanDto;
import com.ninjamind.confman.service.InstanceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This controller is the public API which can be use by script to read or add datas from confman. The
 * param are less restrictive than the controller GUI.
 *
 * @author Guillaume EHRET
 */
@RestController
@RequestMapping(value = "/api/instance")
public class InstanceApiController {

    @Autowired
    private InstanceFacade instanceFacade;

    /**
     * Create a instance in Confman for an application
     * @param confmanDto dto which have to contain application code and param code and label
     */
    @RequestMapping(method = RequestMethod.POST)
    public void addParam(@RequestBody InstanceConfmanDto confmanDto) {
        saveparam(confmanDto, true);
    }

    /**
     * Save or update param
     * @param confmanDto
     * @param creation
     */
    private void saveparam(InstanceConfmanDto confmanDto, boolean creation) {
        Preconditions.checkNotNull(confmanDto, "DTO ConfmanDto is required");
        Preconditions.checkNotNull(confmanDto.getCodeApplication(), "application code is required");
        Preconditions.checkNotNull(confmanDto.getCode(), "instance code is required");
        Preconditions.checkNotNull(confmanDto.getCodeEnvironment(), "environment code is required");
        Preconditions.checkNotNull(confmanDto.getLabel(), "instance label is required");

        instanceFacade.saveInstanceToApplication(
                confmanDto.getCodeApplication(),
                confmanDto.getCode(),
                confmanDto.getCodeEnvironment(),
                confmanDto.getLabel(),
                creation);
    }

    /**
     * Update a instance in Confman for an application
     * @param confmanDto dto which have to contain application code and param code and label
     */
    @RequestMapping(method = RequestMethod.PUT)
    public void updateParam(@RequestBody InstanceConfmanDto confmanDto) {
        saveparam(confmanDto, false);
    }

    /**
     * Read an instance
     * @param codeApp
     * @param codeInstance
     * @param codeEnv
     * @return
     */
    @RequestMapping(value = "/{codeInstance}/app/{codeApp}/env/{codeEnv}")
    public InstanceConfmanDto getInstance(@PathVariable String codeInstance, @PathVariable String codeApp, @PathVariable String codeEnv) {
        Preconditions.checkNotNull(codeApp, "application code is required");
        Preconditions.checkNotNull(codeInstance, "instance code is required");
        Preconditions.checkNotNull(codeInstance, "instance code is required");
        Instance instance = instanceFacade.getRepository().findByCode(codeInstance, codeApp, codeEnv);

        if(instance==null){
            return null;
        }
        return new InstanceConfmanDto()
                .setCode(instance.getCode())
                .setLabel(instance.getLabel())
                .setCodeApplication(codeApp)
                .setCodeEnvironment(codeApp)
                .setId(instance.getId());
    }
}
