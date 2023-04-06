package ru.vitasoft.adminrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.vitasoft.adminrest.entity.Request;
import ru.vitasoft.adminrest.entity.Status;
import ru.vitasoft.adminrest.repository.RequestRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class OperatorController {
    @Autowired
    RequestRepository requestRepository;

    @PutMapping(path = "/operator/request/{id}")
    public ResponseEntity<Request> acceptRequest(@PathVariable Long id,
                                                 @RequestParam Status status){
        Optional<Request> saved = requestRepository.findById(id);
        if (saved.isPresent() && saved.get().getStatus() == Status.DRAFT) {
            Request requestSaved = saved.get();
            requestSaved.setStatus(status);
            requestRepository.save(requestSaved);
            return new ResponseEntity<>(requestSaved, HttpStatus.I_AM_A_TEAPOT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/operator/requests")
    public List<Request> getAllRequests(@RequestParam(defaultValue = "0") int pageNumber,
                                          @RequestParam(defaultValue = "5") int pageSize,
                                          @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction){
        return requestRepository.findByStatus(Status.SENT, PageRequest.of(pageNumber, pageSize, Sort.by(direction,"createDate"))).getContent();
    }

    @GetMapping(path = "/operator/user/{name}")
    public List<Request> getUsersRequests(@PathVariable String name,
                                          @RequestParam(defaultValue = "0") int pageNumber,
                                          @RequestParam(defaultValue = "5") int pageSize,
                                          @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction){
        return requestRepository.findByStatusAndUserNameContaining(Status.SENT, name, PageRequest.of(pageNumber, pageSize, Sort.by(direction,"createDate"))).getContent();
    }
}