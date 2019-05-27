package com.nomad.prizebond.core;

import java.sql.Timestamp;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity(name = "rel_bond_winner")
@Cacheable
public class RelBondWinner extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "relBondWinnerSequence", sequenceName = "rel_bond_winner_pk_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "relBondWinnerSequence")
    @Column(name = "pk_id")
    public Integer id;

    @Column(name = "status", length = 25, nullable = false)
    public String status;

    @Column(name = "create_date_time", nullable = false)
    public Timestamp createDateTime;

    @Column(name = "remarks", length = 100, nullable = true)
    public String remarks;

    @ManyToOne
    @JoinColumn(name = "fk_winner_id", nullable = false)
    public Winner winner;

    @ManyToOne
    @JoinColumn(name = "fk_bond_id", nullable = false)
    public Bond bond;


    public RelBondWinner() {
    }

    public RelBondWinner(String status, String remarks, Winner winner, Bond bond) {
        this.status = status;
        this.createDateTime = new Timestamp(System.currentTimeMillis());
        this.remarks = remarks;
        this.winner = winner;
        this.bond = bond;
    }
}
