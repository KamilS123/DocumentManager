package com.kamil.DocumentManager.repository;

import com.kamil.DocumentManager.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    @Modifying
    @Transactional(readOnly = false    )
    @Query("update User u set u.password=:newPassword where u.id=:id")
    void updatePassword(@Param("newPassword") String newPassword, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update User u set u.status=:newStatus where u.id=:id")
    void updateStatus(@Param("newStatus") String newStatus, @Param("id") Long id);
}
