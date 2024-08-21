package br.com.loto.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "accounts")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    private String username;

    private boolean active;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "account", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnoreProperties("account")
    @Fetch(FetchMode.SUBSELECT)
    private List<AccountContact> contacts;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "account", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnoreProperties("account")
    @Fetch(FetchMode.SUBSELECT)
    private List<AccountPassword> passwords;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "account", fetch = FetchType.EAGER, orphanRemoval = true)
    @Setter(value = AccessLevel.NONE)
    private List<AccountPermission> permissions;

}
