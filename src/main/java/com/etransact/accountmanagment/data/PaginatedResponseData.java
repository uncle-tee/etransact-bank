package com.etransact.accountmanagment.data;

import lombok.Data;

@Data
public class PaginatedResponseData<T> {
    private Integer total;
    private T data;
    private Integer perPage;

    public PaginatedResponseData(Integer total, T data, Integer perPage) {
        this.total = total;
        this.data = data;
        this.perPage = perPage;
    }
}
