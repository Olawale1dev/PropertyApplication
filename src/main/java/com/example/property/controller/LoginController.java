package com.example.property.controller;


import com.example.property.dto.LoginRequestDto;
import com.example.property.dto.LoginResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    @PostMapping
    public ResponseEntity<LoginResponseDTO> performLogin(@Valid @RequestBody LoginRequestDto loginRequestDTO){
        return ResponseEntity.ok(new LoginResponseDTO("Success !"));
    }
}
