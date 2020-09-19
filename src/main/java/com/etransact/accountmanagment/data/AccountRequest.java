package com.etransact.accountmanagment.data;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Getter
public class AccountRequest {
    @NotNull
    private String accountName;

    @NotNull
    private BigInteger openBalance;
}
