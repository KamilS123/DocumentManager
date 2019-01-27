package com.kamil.DocumentManager.repository;

import com.kamil.DocumentManager.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
}
