package com.kamil.DocumentManager.repository;

import com.kamil.DocumentManager.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DocumentRepository extends JpaRepository<Document,Long> {

    @Modifying
    @Transactional(readOnly = false)
    @Query("update Document d set d.document_name=:docName where d.document_name=5")
    void updateDocument(@Param("docName")String docName);

}
