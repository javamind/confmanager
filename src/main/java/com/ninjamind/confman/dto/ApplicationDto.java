package com.ninjamind.confman.dto;

import com.ninjamind.confman.domain.Application;

/**
 * {@link com.ninjamind.confman.domain.Application}
 *
 * @author EHRET_G
 */
public class ApplicationDto extends AbstractConfManDto {

    public ApplicationDto() {
        super();
    }

    public ApplicationDto(Application object) {
        super(
                object.getId(),
                object.getCode(),
                object.getLabel(),
                object.getVersion()
        );
    }

    public Application toApplication() {
        return new Application()
                .setId(getId())
                .setCode(getCode())
                .setLabel(getLabel())
                .setVersion(getVersion());
    }
}
