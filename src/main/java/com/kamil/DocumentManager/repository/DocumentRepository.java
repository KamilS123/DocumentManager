package com.kamil.DocumentManager.repository;

import com.kamil.DocumentManager.models.Document;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends CrudRepository<Document,Long> {
}
