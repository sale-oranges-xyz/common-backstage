package com.github.geng.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiPage<T> {

    private int totalPages;
    private long totalElements;

    private int pageSize;
    private List<T> dataLists;

}
