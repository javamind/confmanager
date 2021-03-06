package com.ninjamind.confman.web.app;

import com.google.common.base.Preconditions;
import com.ninjamind.confman.domain.PaginatedList;
import com.ninjamind.confman.domain.ParameterValue;
import com.ninjamind.confman.dto.PaginatedListDto;
import com.ninjamind.confman.dto.ParameterValueDto;
import com.ninjamind.confman.dto.ParameterValueFilterDto;
import com.ninjamind.confman.service.ParameterValueFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Rest API for {@link com.ninjamind.confman.domain.ParameterValue}
 *
 * @author Guillaume EHRET
 */
@RestController
@RequestMapping(value = "/app/parametervalue")
public class ParameterValueController {

    @Autowired
    @Qualifier("parameterValueFacade")
    private ParameterValueFacade parameterValueFacade;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public PaginatedListDto<ParameterValueDto> search(@RequestBody ParameterValueFilterDto criteria) {
        Preconditions.checkNotNull(criteria);

        PaginatedList<ParameterValue> parameterValues =
                parameterValueFacade.filter(
                        criteria.getPage(),
                        criteria.getNbEltPerPage(),
                        criteria.toParameterValueSearchBuilder());

        //The DOs are transformed in DTOs
        return
                new PaginatedListDto(
                        parameterValues.getCompleteSize(),
                        parameterValues.getCurrentPage(),
                        parameterValues.getNbElementByPage(),
                        parameterValues.stream().map(parameter -> new ParameterValueDto(parameter)).collect(Collectors.toList())
                );

    }

    @RequestMapping("/{id}")
    public ParameterValueDto get(@PathVariable Long id) {
        return new ParameterValueDto(parameterValueFacade.findOne(id));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void update(@RequestBody List<LinkedHashMap> parameters) {
        Preconditions.checkNotNull(parameters, "List is required to update it");
        parameterValueFacade.update(
                parameters
                        .stream()
                        .filter(p -> !(Boolean) p.get("toDelete"))
                        .map(p -> ParameterValueDto.toParameterValue(p))
                        .collect(Collectors.toList())
        );
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<ParameterValueDto> save(@RequestBody Long idTrackingVersionDto) {
        Preconditions.checkNotNull(idTrackingVersionDto, "The id version is required to create the value parameters");

        return  parameterValueFacade.create(idTrackingVersionDto).stream().map(parameterValue -> new ParameterValueDto(parameterValue)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        parameterValueFacade.delete(id);
    }
}

