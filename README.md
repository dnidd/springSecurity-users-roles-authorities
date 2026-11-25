### Spring security
Implementation of creation and login of multiple users, roles and authorities.


### Roles and authorities

Roles:
- <b>ADMIN:</b> DELETE, READ, WRITE
- <b>USER:</b> READ, WRITE

Roles and authorities are implemented by the same class/object GrantedAuthority.<br>
A user can have multiple roles. <br>
A role can have multiple authorities.<br>

- <b>"/api/user/register"</b><br>
  Open to all.
- <b>"/admin" endpoint:</b><br>
Only to be accessed by admin role (ADMIN) with granted admin authorities.
- <b>"/read" or "/greet" endpoint:</b><br>
open to all logged-in users - no check for any authorities or any roles.<br>
Endpoint is not protected by any authority.
- <b>"/api/greet/update" endpoint:</b><br>
open to role user with write authority ("USER_WRITE_PERM")


<b>Different roles to register/create</b><br>
<b>ADMIN:</b>

```
{
    "username":"name",
    "password":"password",
    "email":"name@mail.com",
    "roles": [
        {
            "name":"ADMIN",
            "authorities": [
                {
                    "name":"READ_PERM"
                },
                {
                    "name":"WRITE_PERM"
                },
                {
                    "name":"DELETE_PERM"
                }
            ]
        }
    ]
}
```

<b>USER:</b><br>
USER not allowed to DELETE.

```
{
    "username":"name",
    "password":"password",
    "email":"name@mail.com",
    "roles": [
        {
            "name":"USER",
            "authorities": [
                {
                    "name":"READ_PERM"
                },
                {
                    "name":"WRITE_PERM"
                }
            ]
        }
    ]
}
```


If both role AND authority is required for an endpoint ("/api/greet/update").

In UserService and loadUserByUsername(String username) when collecting roles:
```
new SimpleGrantedAuthority(role.getName()+"_"+authority.getName())
```
add:
```
role.getName()+"_"+
```
to bind a role and an authority.

Likewise, in SecurityConfig and securityFilterChain(HttpSecurity http)
define authority with a role and underscore: 'USER_'.
```
.requestMatchers("/api/greet/update").hasAuthority("USER_WRITE_PERM")
```


### RoleDTO

The "roles" part represents the RoleDTO.

RoleDTO holds:<br>
String title;<br>
Set<AuthorityDTO> authorities;

AuthorityDTO holds:<br>
String name.

```
{
    "username":"john_doe",
    "password":"password123",
    "email":"john.doe@mail.com",
    "roles": [
        {
            "name":"ADMIN",
            "authorities": [
                {
                    "name":"READ_PERM"
                },
                {
                    "name":"WRITE_PERM"
                },
                {
                    "name":"DELETE_PERM"
                }
            ]
        }
    ]
}
```


## Database connection
MySQL, JPA, hibernate.<br>

<b>Tables:</b><br>
- 'users'
  - user id
  - email
  - encrypted/hashed password 
  - username
- 'roles'<br>
  - role id
  - role name
- 'authorities'
  - authority id
  - authority name
- join table 'user_roles'
  - role_id
  - user_id
- join table 'role_authorities'
  - authority_id
  - role_id


<span style="color:white">––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––</span>

🔴🟠🟡🟢🔵🟣🟤⚫⚪

