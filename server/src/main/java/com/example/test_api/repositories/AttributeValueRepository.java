package com.example.test_api.repositories;

import com.example.test_api.models.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {
    void deleteAttributeValuesByAttribute_Id(Long attribute_id);

    @Query(value = """
            delete
            from attribute_value_connections avc
            where avc.attribute_value_id = ?1
               or avc.connections_id = ?1
            """, nativeQuery = true)
    @Modifying
    void deleteConnectionsByAttributeValueId(Long attribute_value_id);
}
