package com.snidd.spring_security_roles.service;

import com.snidd.spring_security_roles.dto.AuthorityDTO;
import com.snidd.spring_security_roles.dto.RoleDTO;
import com.snidd.spring_security_roles.dto.UserDTO;
import com.snidd.spring_security_roles.entity.Authority;
import com.snidd.spring_security_roles.entity.Role;
import com.snidd.spring_security_roles.entity.User;
import com.snidd.spring_security_roles.model.AuthenticatedUser;
import com.snidd.spring_security_roles.repository.AuthorityRepository;
import com.snidd.spring_security_roles.repository.RoleRepository;
import com.snidd.spring_security_roles.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Service to handle user logic.
 */
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;


    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository,
                       AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authorityRepository = authorityRepository;
    }


    /**
     * Create User entity to be stored.
     * Encode/hash password.
     * @param userDTO user dto.
     * @return saved user.
     */
    public User register(UserDTO userDTO) {
        System.out.println("Request body: " +  userDTO);

        User u = new User();
        u.setEmail(userDTO.getEmail());
        u.setUsername(userDTO.getUsername());
        u.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Create and set roles to user & authorities to roles.
        // Lambda: for every role, from .getRoles() create role for user,
        // and for every role set authority.
        Set<Role> roles = userDTO.getRoles().stream().map(roleDTO -> {

            // Check in DB if a user has a role,
            // otherwise create the role for the user.
            // Create single role.
            Role role = getOrCreateRole(roleDTO);

            // A role can have multiple authorities.
            Set<Authority> authorities = getAuthoritiesFromRequest(roleDTO);

            // Set authorities to this particular role.
            role.setAuthorities(authorities);

            return role;
        }).collect(Collectors.toSet());

        // set roles including its authorities.
        u.setRoles(roles);

        return userRepository.save(u);
    }


    /**
     * Check in DB if a user has a role, if not: create the role,
     * otherwise fetch the role from the DB.
     * @param roleDTO roleDTO request.
     * @return already existing role or just created role.
     */
    private Role getOrCreateRole(RoleDTO roleDTO) {
        return roleRepository.findByName(roleDTO.getName()).orElseGet(() ->{
            Role role = new Role();
            role.setName(roleDTO.getName());
            return roleRepository.save(role);
        });
    }


    /**
     * Get all the authorities from RoleDTO to be set to a user's role.
     * Then call getOrCreateAuthority(authorityDTO).
     * If the authority exist in DB the authority is returned, else it is created and stored.
     * @param roleDTO roleDTO request.
     * @return Set<Authority> set to user's role.
     */
    private Set<Authority> getAuthoritiesFromRequest(RoleDTO roleDTO) {
        return roleDTO.getAuthorities().stream().map(authorityDTO -> {
            return getOrCreateAuthority(authorityDTO);
        }).collect(Collectors.toSet());
    }


    /**
     * Check in DB if authority exist, else create authority.
     * @param authorityDTO authorityDTO request.
     * @return Authority authority.
     */
    private Authority getOrCreateAuthority(AuthorityDTO authorityDTO) {
        return authorityRepository.findByName(authorityDTO.getName()).orElseGet(() -> {
            Authority authority = new Authority();
            authority.setName(authorityDTO.getName());
            return authorityRepository.save(authority);
        });
    }


    /**
     * Collect the user's roles and authorities as GrantedAuthority.
     * Load by the username from store/DB.
     * @param username the username identifying the user whose data is required.
     * @return authenticated user.
     * @throws UsernameNotFoundException e.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByUsername(username);


        // Fetch authorities
//        List<GrantedAuthority> authorities = u.getRoles().stream()
//                .flatMap(role -> role.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getName()))).collect(Collectors.toList());

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role : u.getRoles()) {
            for (Authority authority : role.getAuthorities()) {
                authorities.add(
                        // If only authority is required:
                        // new SimpleGrantedAuthority(authority.getName())
                        // If both role AND authority is required:
                         new SimpleGrantedAuthority(role.getName()+"_"+authority.getName())
                );
            }
        }
        System.out.println("Authorities: " + authorities);


        // Fetch roles
        // ROLE_ prefix for Spring Security to distinguish between roles.
        for (Role role : u.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }

//        authorities.addAll(u.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList()));
        System.out.println("Roles: " + authorities);

        AuthenticatedUser authenticatedUser = new AuthenticatedUser(u, authorities);

        return authenticatedUser;
    }


}





















