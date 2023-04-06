package ru.vitasoft.adminrest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vitasoft.adminrest.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String name);

    List<User> findByNameContaining(String name);
}
