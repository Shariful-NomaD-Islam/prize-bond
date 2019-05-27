package com.nomad.prizebond.core;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity(name = "app_user")
@Cacheable
public class AppUser extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "appUserSequence", sequenceName = "app_user_pk_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appUserSequence")
    @Column(name = "pk_id")
    public Integer id;

    @Column(name = "user_name", length = 100, unique = true, nullable = false)
    public String userName;

    @Column(name = "password", length = 100, nullable = false)
    public String password;

    @Column(name = "create_date_time", nullable = false)
    public Timestamp createDateTime;

    @Column(name = "status", length = 100, nullable = false)
    public String status;

    @Column(name = "session", length = 100)
    public String session;

    @Column(name = "last_login")
    public Timestamp lastLogin;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Set<Bond> bonds;

    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name = "bondv2ref", referencedColumnName = "pk_id")
    private List<Bondv2> bondv2s;
    
    /*
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_ref", referencedColumnName = "pk_id")
    private List<Bond> bonds;
    */

    public AppUser() {
    }

    public AppUser(String userName, String password, String status) {
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.createDateTime = new Timestamp(System.currentTimeMillis());
    }

}
