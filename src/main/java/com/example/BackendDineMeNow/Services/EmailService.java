package com.example.BackendDineMeNow.Services;

public interface EmailService {

    void enviarCredenciales(String correoDestino, String nombreRestaurante, String passwordTemporal);// Método para enviar credenciales temporales al restaurante

    void enviarNotificacionEstado(String correoDestino, String nombreRestaurante, String nuevoEstado);// Método para enviar notificación de cambio de estado al restaurante

    void enviarConfirmacionRecibido(String correoDestino, String nombreRestaurante); // Método para enviar confirmación de recibido de solicitud o mensaje al restaurante

}
