package com.nomad.prizebond.core;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity(name = "winner")
@Cacheable
public class Winner extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "winnerSequence", sequenceName = "winner_pk_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "winnerSequence")
    @Column(name = "pk_id")
    public Integer id;

    @Column(name = "suffix", length = 10, nullable = false)
    public String suffix;

    @Column(name = "draw_number", nullable = false)
    public int drawNumber;

    @Column(name = "prize_ammount", nullable = false)
    public int prizeAmmount;

    @Column(name = "place", nullable = false)
    public int place;

    @Column(name = "status", length = 20, nullable = false)
    public String status;

    @Column(name = "create_date_time", nullable = false)
    public Timestamp createDateTime;

    @OneToMany(mappedBy = "winner", cascade = CascadeType.ALL)
    private Set<RelBondWinner> relBondWinners;

    public Winner() {
    }

    public Winner(String suffix, int drawNumber, int prizeAmmount, int place, String status) {
        this.suffix = suffix;
        this.drawNumber = drawNumber;
        this.prizeAmmount = prizeAmmount;
        this.place = place;
        this.createDateTime = new Timestamp(System.currentTimeMillis());
        this.status = status;
    }
}
