package org.lamisplus.modules.base.base.repository;

import org.lamisplus.modules.base.base.domiain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
}
