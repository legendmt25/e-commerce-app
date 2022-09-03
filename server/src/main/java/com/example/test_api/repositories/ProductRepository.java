package com.example.test_api.repositories;

import com.example.test_api.models.Product;
import com.example.test_api.models.projections.MinMaxPriceProjection;
import com.example.test_api.models.projections.ProductWithPriceProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = """
            select pr.*
            from (select p.id          as id,
                         p.title       as title,
                         min(av.value) as minPrice,
                         max(av.value) as maxPrice
                   from product p
                        inner join attribute a on p.id = a.product_id
                        inner join attribute_value av on a.id = av.attribute_id
                where upper(a.title) = upper('Цена')
                group by p.id) pr
                where not (cast(pr.maxPrice as bigint) < ?1 or cast(pr.minPrice as bigint) > ?2)
                order by ?#{#pageable}
            """
            , nativeQuery = true)
    Page<ProductWithPriceProjection> findAllWithPrice(@Param("low") Long low, @Param("high") Long high, Pageable pageable);

    @Query("""
            select min(av.value) as minPrice,
                   max(av.value) as maxPrice
            from Product p
            inner join p.attributes a
            inner join a.values av
            where upper(a.title) = upper('Цена')
            """)
    MinMaxPriceProjection findMinAndMaxPrice();

}