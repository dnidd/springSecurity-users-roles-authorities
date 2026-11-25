package com.snidd.spring_security_roles.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * AuthorityDTO to bes mapped and stored in DB.
 * A role can have multiple authorities.
 * A user can have multiple roles.
 */
@Getter
@Setter
public class AuthorityDTO {
    private String name;
}
