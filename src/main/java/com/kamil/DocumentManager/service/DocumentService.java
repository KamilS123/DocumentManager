package com.kamil.DocumentManager.service;

import com.kamil.DocumentManager.models.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DocumentService {
    Page<Document>findAllDocumentsPageable(Pageable pageable);
    Document getDocById();
}
