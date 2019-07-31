package com.kamil.DocumentManager.repository;

import com.kamil.DocumentManager.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Modifying
    @Transactional(readOnly = false)
    @Query(value = "update Document d set d.documentName=:docName, d.documentComments=:docCom, d.documentDescription=:docDesc, d.editionDate=:editDate where d.id=:docID")
    void updateDocument(@Param("docName") String docName, @Param("docCom") String docCom, @Param("docDesc") String docDesc, @Param("editDate") LocalDateTime date);

    @Query("select d from Document d where d.documentName=:docName")
    List<Document> findDocByName(@Param("docName") String docName);

    @Query("from Document d where d.user.id=:user_id AND d.documentName=:docName")
    List<Document>findDocNameByLoggedId(@Param("user_id")Long user_id, @Param("docName") String name);

    @Query("from Document d where d.user.id=:user_id")
    List<Document>findByLoggedId(@Param("user_id")Long user_id);
}
