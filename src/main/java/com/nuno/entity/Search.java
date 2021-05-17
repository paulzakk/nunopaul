package com.nuno.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Search {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long sid;

    String keyword;

    public Search() {
    }

    public Search(Long id, String keyword) {
        this.sid = id;
        this.keyword = keyword;
    }

    public Long getId() {
        return sid;
    }

    public void setId(Long id) {
        this.sid = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public Search(String query) {
        this.keyword = query;
    }

    public String toString() {
        return "sid: " + getId() +
                ", keyword: " + getKeyword();
    }

}
