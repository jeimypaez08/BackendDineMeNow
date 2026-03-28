package com.example.BackendDineMeNow.Services;

import com.example.BackendDineMeNow.Dtos.LoginRequestDto;
import com.example.BackendDineMeNow.Dtos.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto loginDto);

}
