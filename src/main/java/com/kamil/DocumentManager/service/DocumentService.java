package com.kamil.DocumentManager.service;

import com.kamil.DocumentManager.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

   public void updateName(Long id) {
   }
}
