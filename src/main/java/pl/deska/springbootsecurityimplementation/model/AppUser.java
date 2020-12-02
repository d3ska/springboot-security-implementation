package pl.deska.springbootsecurityimplementation.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.deska.springbootsecurityimplementation.model.Role;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "app_users")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private Role role;
    private boolean isEnabled;
    private boolean isConfirmedByAdmin;

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AppUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean isEnabled() {
            return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isConfirmedByAdmin() {
        return isConfirmedByAdmin;
    }

    public void setConfirmedByAdmin(boolean confirmedByAdmin) {
        isConfirmedByAdmin = confirmedByAdmin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if(role == Role.ADMIN && isConfirmedByAdmin)
         authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        else
         authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

}
