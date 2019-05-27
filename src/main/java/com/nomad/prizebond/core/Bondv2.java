package com.nomad.prizebond.core;

import java.sql.Timestamp;
import java.util.List;

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

@Entity(name = "bondv2")
@Cacheable
public class Bondv2 extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "bondv2Sequence", sequenceName = "bondv2_pk_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bondv2Sequence")
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

 
    public Bondv2() {
    }

    public Bondv2(String prefix, String number, String status, String owner) {
        this.prefix = prefix;
        this.number = number;
        this.status = status;
        this.createDateTime = new Timestamp(System.currentTimeMillis());
        this.owner = owner;
    }
}
