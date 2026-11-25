package com.snidd.spring_security_roles.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;


/**
 * RoleDTO to be mapped and stored in DB.
 * A user can hold multiple roles.
 * A role can hold multiple authorities.
 */
@Getter
@Setter
public class RoleDTO {
    private String name;
    private Set<AuthorityDTO> authorities;
}
