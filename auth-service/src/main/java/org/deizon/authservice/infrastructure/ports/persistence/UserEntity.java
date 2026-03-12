package org.deizon.authservice.infrastructure.ports.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.deizon.authservice.domain.RoleEnum;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Table(name = "tb_user")
@Entity(name = "tb_user")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "cpf", unique = true)
    @CPF(message = "CPF invalid")
    private String cpf;

    @Column(name = "cnpj", unique = true)
    @CNPJ(message = "CNPJ invalid")
    private String cnpj;

    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "Email invalid")
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    public UserEntity(UUID id, String fullName, String cpf, String cnpj, String email, String password, RoleEnum role) {
        this.id = id;
        this.fullName = fullName;
        this.cpf = cpf;
        this.cnpj = cnpj;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserEntity() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == RoleEnum.ADMIN)
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER"),
                    new SimpleGrantedAuthority("ROLE_MERCHANT")
            );
        if (role == RoleEnum.USER)
            return List.of(
                    new SimpleGrantedAuthority("ROLE_USER"),
                    new SimpleGrantedAuthority("ROLE_MERCHANT")
            );
        return List.of(new SimpleGrantedAuthority("ROLE_MERCHANT"));
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
