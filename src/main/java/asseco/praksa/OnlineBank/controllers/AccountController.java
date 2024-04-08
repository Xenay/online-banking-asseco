package asseco.praksa.OnlineBank.controllers;

import asseco.praksa.OnlineBank.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.InputStream;

@RestController
public class AccountController {

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/api/account")
    public ResponseEntity<Account> getAccountDetails() throws Exception {
        // Load the JSON file from the classpath
        InputStream inputStream = resourceLoader.getResource("classpath:accountDataSample.json").getInputStream();

        // Deserialize JSON file to Account object
        ObjectMapper objectMapper = new ObjectMapper();
        Account account = objectMapper.readValue(inputStream, Account.class);

        return ResponseEntity.ok(account);
    }
}