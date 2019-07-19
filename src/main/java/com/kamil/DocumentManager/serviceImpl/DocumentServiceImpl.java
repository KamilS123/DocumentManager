package com.kamil.DocumentManager.serviceImpl;

import com.kamil.DocumentManager.models.Document;
import com.kamil.DocumentManager.repository.DocumentRepository;
import com.kamil.DocumentManager.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    DocumentRepository documentRepository;

    @Override
    public Document getFile(Long fileId) throws FileNotFoundException {
        return documentRepository.findById(fileId)
                .orElseThrow(()-> new FileNotFoundException("File not found " + fileId));
    }
}
