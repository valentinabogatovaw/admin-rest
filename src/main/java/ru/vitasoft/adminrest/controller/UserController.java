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
import ru.vitasoft.adminrest.entity.User;
import ru.vitasoft.adminrest.repository.RequestRepository;
import ru.vitasoft.adminrest.repository.UserRepository;

import java.util.List;
import java.util.Optional;
@RestController
public class UserController {
    @Autowired
    RequestRepository requestRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "/user/request/{id}")
    public ResponseEntity<Request> getRequest(@PathVariable Long id){
        Optional<Request> request = requestRepository.findById(id);
        if (request.isPresent()){
            return new ResponseEntity<Request>(request.get(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/user/request")
    public ResponseEntity<Request> createRequest(@RequestBody Request request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        User saved = userRepository.findByName(user.getUsername());
        request.setStatus(Status.DRAFT);
        request.setUser(saved);
        Request inDB = requestRepository.save(request);
        return new ResponseEntity<>(inDB, HttpStatus.CREATED);
    }

    @PutMapping(path = "/user/request/{id}")
    public ResponseEntity<Request> editDraftRequest(@RequestBody Request request, @PathVariable Long id){
        Optional<Request> saved = requestRepository.findById(id);
        if (saved.isPresent() && saved.get().getStatus() == Status.DRAFT) {
            Request requestSaved = saved.get();
            requestSaved.setStatus(Status.DRAFT);
            requestSaved.setText(request.getText());
            requestRepository.save(requestSaved);
            return new ResponseEntity<>(requestSaved, HttpStatus.I_AM_A_TEAPOT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/user/requests/")
    public List<Request> getUsersRequests(@RequestParam(defaultValue = "0") int pageNumber,
                                          @RequestParam(defaultValue = "5") int pageSize,
                                          @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        User saved = userRepository.findByName(user.getUsername());
        return requestRepository.findByUserNameContaining(saved.getName(), PageRequest.of(pageNumber, pageSize, Sort.by(direction,"createDate"))).getContent();
    }

    @PutMapping(path = "/user/request/draft/{id}")
    public ResponseEntity<Request> sendRequest(@PathVariable Long id){
        Optional<Request> saved = requestRepository.findById(id);
        if (saved.isPresent() && saved.get().getStatus() == Status.DRAFT) {
            Request requestSaved = saved.get();
            requestSaved.setStatus(Status.SENT);
            requestRepository.save(requestSaved);
            return new ResponseEntity<>(requestSaved, HttpStatus.I_AM_A_TEAPOT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
