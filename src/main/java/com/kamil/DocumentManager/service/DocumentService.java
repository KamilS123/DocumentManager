package com.kamil.DocumentManager.service;

import com.kamil.DocumentManager.models.Document;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import java.io.FileNotFoundException;
@Repository
public interface DocumentService {
    Document getFile(Long fileId) throws FileNotFoundException;
}
