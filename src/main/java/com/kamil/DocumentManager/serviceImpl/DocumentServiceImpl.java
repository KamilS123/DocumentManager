package com.kamil.DocumentManager.serviceImpl;

import com.kamil.DocumentManager.models.Document;
import com.kamil.DocumentManager.repository.DocumentRepository;
import com.kamil.DocumentManager.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class DocumentServiceImpl implements DocumentService {

    @Autowired
    DocumentRepository documentRepository;

    @Override
    public Page<Document>findAllDocumentsPageable(Pageable pageable) {
        Page<Document>documentPageLit = documentRepository.findAll(pageable);
        return documentPageLit;
    }
}
