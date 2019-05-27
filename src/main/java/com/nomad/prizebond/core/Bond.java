package com.nomad.prizebond.core;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity(name = "bond")
@Cacheable
public class Bond extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "bondSequence", sequenceName = "bond_pk_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bondSequence")
    @Column(name = "pk_id")
    public Integer id;

    @Column(name = "prefix", length = 10, nullable = false)
    public String prefix;

    @Column(name = "number", length = 15, nullable = false)
    public String number;

    @Column(name = "status", length = 25, nullable = false)
    public String status;

    @Column(name = "create_date_time", nullable = false)
    public Timestamp createDateTime;

    @Column(name = "owner", length = 100, nullable = false)
    public String owner;

    @Column(name = "remarks", length = 100, nullable = true)
    public String remarks;

    @ManyToOne
    @JoinColumn(name = "fk_app_user_id", nullable = false)
    public AppUser appUser;

    @OneToMany(mappedBy = "bond", cascade = CascadeType.ALL)
    private Set<RelBondWinner> relBondWinners;

 
    public Bond() {
    }

    public Bond(String prefix, String number, String status, String owner) {
        this.prefix = prefix;
        this.number = number;
        this.status = status;
        this.createDateTime = new Timestamp(System.currentTimeMillis());
        this.owner = owner;
    }
}
