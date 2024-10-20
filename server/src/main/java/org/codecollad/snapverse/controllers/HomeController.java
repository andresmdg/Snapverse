package org.codecollad.snapverse.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public ResponseEntity<?> home () {
        return new ResponseEntity<>("API is running...", HttpStatus.OK);
    }

}
