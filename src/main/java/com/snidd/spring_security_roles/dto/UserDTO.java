package com.snidd.spring_security_roles.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Incoming object mapping to the entity User to be stored in DB.
 * A user can have multiple roles.
 * A Role also holds a collection of authorities.
 */

@Getter
@Setter
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private Set<RoleDTO> roles;
}



// The "roles" part represents the RoleDTO.

// RoleDTO holds:
// String title;
// Set<AuthorityDTO> authorities;

// AuthorityDTO holds:
// String name.

//{
//    "username":"john_doe",
//    "password":"password123",
//    "email":"john.doe@mail.com",
//    "roles": [
//        {
//            "name":"ADMIN",
//            "authorities":[
//                {
//                    "name":"READ_PERM"
//                },
//                {
//                    "name":"WRITE_PERM"
//                },
//                {
//                "name":"DELETE_PERM"
//                }
//            ]
//        }
//    ]
//}