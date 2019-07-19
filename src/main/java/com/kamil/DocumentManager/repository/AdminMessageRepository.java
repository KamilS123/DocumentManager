package com.kamil.DocumentManager.repository;

import com.kamil.DocumentManager.models.AdminMessage;
import org.springframework.data.repository.CrudRepository;

public interface AdminMessageRepository extends CrudRepository<AdminMessage,Long> {
}
