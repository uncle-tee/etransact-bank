package com.etransact.accountmanagment.domain.entity;

import com.etransact.accountmanagment.domain.enumeration.GenericStatusConstant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigInteger;

@Entity
@Getter
@Setter
@Builder
public class Transaction extends BaseEntity {
    @ManyToOne
    @JoinColumn
    private Account account;


    @Column(nullable = false)
    private String reference;

    @Column()
    private BigInteger debit;

    @Column()
    private BigInteger credit;

    @Column()
    private String description;

    @Column(nullable = false)
    private BigInteger balance;

    public Transaction() {
        super(GenericStatusConstant.ACTIVE);
    }

    public Transaction(Account account, String reference, BigInteger debit, BigInteger credit, String description, BigInteger balance) {
        super(GenericStatusConstant.ACTIVE);
        this.account = account;
        this.reference = reference;
        this.debit = debit;
        this.credit = credit;
        this.description = description;
        this.balance = balance;
    }
}
