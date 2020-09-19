package com.etransact.accountmanagment.data;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
public class TransferRequest {


    @NotNull
    @NotEmpty
    private String to;
    @NotNull
    private BigInteger amount;
}
