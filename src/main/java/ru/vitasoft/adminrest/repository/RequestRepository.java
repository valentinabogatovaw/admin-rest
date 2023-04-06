package ru.vitasoft.adminrest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.vitasoft.adminrest.entity.Request;
import ru.vitasoft.adminrest.entity.Status;
import ru.vitasoft.adminrest.entity.User;

@Repository
public interface RequestRepository extends PagingAndSortingRepository<Request, Long> {

    Page<Request> findByStatus(Status status, PageRequest pageRequest);

    Page<Request> findByUserNameContaining(String name, PageRequest pageRequest);
    Page<Request> findByStatusAndUserNameContaining(Status sent, String name, PageRequest createDate);
}
