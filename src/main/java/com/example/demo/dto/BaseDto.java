package com.example.demo.dto;

import java.util.Objects;

public class BaseDto {

    public int total;

    public int page;

    public int limit;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "BaseDto{" +
                "total=" + total +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseDto)) return false;
        BaseDto baseDto = (BaseDto) o;
        return getTotal() == baseDto.getTotal() && getPage() == baseDto.getPage() && getLimit() == baseDto.getLimit();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTotal(), getPage(), getLimit());
    }
}
