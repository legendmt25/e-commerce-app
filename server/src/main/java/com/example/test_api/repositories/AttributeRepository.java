package com.example.test_api.repositories;

import com.example.test_api.models.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    List<Attribute> findByProduct_Id(Long id);

    @Modifying
    @Query(value = """
            delete
            from attribute_value_connections avc
            where avc.attribute_value_id in (select av.id from attribute_value av where av.attribute_id = ?1)
               or avc.connections_id in (select av.id from attribute_value av where av.attribute_id = ?1)
            """, nativeQuery = true)
    void deleteAllConnectionsByAttributeId(Long id);

}
