package com.example.test_api.repositories;

import com.example.test_api.models.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {
    @Modifying
    void deleteByAttribute_IdIsIn(Collection<Long> ids);

    @Query(value = """
            delete
            from attribute_value_connections avc
            where avc.attribute_value_id in ?1
               or avc.connections_id in ?1
            """, nativeQuery = true)
    @Modifying
    void deleteConnectionsByAttributeValueId(Collection<Long> ids);
}
