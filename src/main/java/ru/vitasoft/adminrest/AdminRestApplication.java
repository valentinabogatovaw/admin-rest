package ru.vitasoft.adminrest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.vitasoft.adminrest.entity.Request;
import ru.vitasoft.adminrest.entity.Role;
import ru.vitasoft.adminrest.entity.Status;
import ru.vitasoft.adminrest.entity.User;
import ru.vitasoft.adminrest.repository.RequestRepository;
import ru.vitasoft.adminrest.repository.UserRepository;

import java.sql.Date;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class AdminRestApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(AdminRestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
