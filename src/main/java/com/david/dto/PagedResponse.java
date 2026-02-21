package com.david.dto;

import java.util.List;

public record PagedResponse<T>(
    List<T> content,
    int page,
    int size,
    long totalElements,
    int totalPages,
    boolean first,
    boolean last
) {
    public static <T> PagedResponse<T> of(List<T> content, int page, int size, long total) {
        int totalPages = (int) Math.ceil((double) total / size);
        return new PagedResponse<>(
            content,
            page,
            size,
            total,
            totalPages,
            page == 0,
            page >= totalPages - 1
        );
    }
}
