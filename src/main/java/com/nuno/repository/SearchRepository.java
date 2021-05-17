package com.nuno.repository;

import com.nuno.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Search, String> {

    @Query(value = "SELECT keyword AS keyword, count(keyword) AS count FROM SEARCH GROUP BY keyword ORDER BY count DESC, keyword limit 10", nativeQuery = true)
    List<SearchCountRepository> popularSearch();
}
