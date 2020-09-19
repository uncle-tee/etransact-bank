package com.etransact.accountmanagment.domain.entity;

import com.etransact.accountmanagment.domain.enumeration.GenericStatusConstant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigInteger;

@Entity
@Getter
@Setter
public class Account extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private BigInteger balanceInMinorUnit;

    @Column(nullable = false)
    private BigInteger minBalance;

    public Account() {
        super(GenericStatusConstant.ACTIVE);
    }

    public Account(String name, String number) {
        super(GenericStatusConstant.ACTIVE);
        this.name = name;
        this.number = number;
        this.balanceInMinorUnit = BigInteger.ZERO;
        this.minBalance = BigInteger.ZERO;
    }
}
