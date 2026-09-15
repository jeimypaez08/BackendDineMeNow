package com.example.BackendDineMeNow.Services;

import com.example.BackendDineMeNow.Dtos.CambiarPasswordRecuperacionDto;
import com.example.BackendDineMeNow.Dtos.LoginRequestDto;
import com.example.BackendDineMeNow.Dtos.LoginResponseDto;
import com.example.BackendDineMeNow.Dtos.ResetTokenResponseDto;
import com.example.BackendDineMeNow.Dtos.SolicitarRecuperacionDto;
import com.example.BackendDineMeNow.Dtos.VerificarCodigoRecuperacionDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto loginDto);
    void solicitarRecuperacion(SolicitarRecuperacionDto dto);

    ResetTokenResponseDto verificarCodigoRecuperacion(VerificarCodigoRecuperacionDto dto);

    void cambiarPasswordConToken(String resetToken, CambiarPasswordRecuperacionDto dto);

}
