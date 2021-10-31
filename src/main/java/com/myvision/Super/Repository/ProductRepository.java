package com.myvision.Super.Repository;

import com.myvision.Super.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String nameImage);

    public Page<Product> findByDesignContains(@Param(value = "mc") String mc, Pageable pageable);

    public List<Product> findBycategory(@Param(value = "categ") String category);
    public Page<Product> findBycategoryContains(@Param(value="mc") String category,Pageable pageable) ;


}
