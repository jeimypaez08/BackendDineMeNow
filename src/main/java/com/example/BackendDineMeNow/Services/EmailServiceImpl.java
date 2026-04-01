package com.example.BackendDineMeNow.Services;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;


@Service
public class EmailServiceImpl implements EmailService { 

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String mailFrom;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    //enviar credenciales temporales al restaurante
    @Override
    public void enviarCredenciales(String correoDestino, String nombreRestaurante, String passwordTemporal){
        String asunto = "Bienvenido a DineMeNow - Credenciales de Acceso";
        String cuerpo = construirCorreoCredenciales(nombreRestaurante, correoDestino, passwordTemporal); 
        enviarCorreoHtml(correoDestino, asunto, cuerpo); 
    }

    //enviar notificación de cambio de estado al restaurante
    @Override
    public void enviarNotificacionEstado(String correoDestino, String nombreRestaurante, String nuevoEstado){
        String asunto = "DineMeNow - Actualizacion del estado de tu restaurante";
        String cuerpo = construirCorreoEstado(nombreRestaurante, nuevoEstado);
        enviarCorreoHtml(correoDestino, asunto, cuerpo);
    }

    //enviar confirmación de recibido de solicitud o mensaje al restaurante
    @Override
    public void enviarConfirmacionRecibido(String correoDestino, String nombreRestaurante){
        String asunto = "DineMeNow - Solicitud de registro recibida";
        String cuerpo = construirCorreoConfirmacionRecibido(nombreRestaurante);
        enviarCorreoHtml(correoDestino, asunto, cuerpo);
    }

    // envio de correo genérico
    private void enviarCorreoHtml(String correoDestino, String asunto, String cuerpoHtml){
        // Implementación para enviar un correo electrónico utilizando JavaMailSender
        // Aquí se puede construir un MimeMessage con el asunto y cuerpo en formato HTML y enviarlo a correoDestino

        try{
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setFrom(mailFrom);
            helper.setTo(correoDestino);
            helper.setSubject(asunto);
            helper.setText(cuerpoHtml, true);

            mailSender.send(mensaje);
        }catch(MessagingException e){
            throw new RuntimeException("Error al enviar correo a: " + correoDestino, e);
        }
    }

    //construcción del cuerpo del correo para las credenciales temporales
    private String construirCorreoCredenciales(String nombreRestaurante, String correoDestino, String passwordTemporal){
        return """
        <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 32px; border: 1px solid #e0e0e0; border-radius: 8px;">
          <h2 style="color: #D84315;">¡Bienvenido a DineMeNow, %s!</h2>
          <p>Tu restaurante ha sido registrado exitosamente y está en estado <strong>Activo</strong>.</p>
          <p>Ya puedes acceder a tu panel con las siguientes credenciales temporales:</p>

          <div style="background: #f5f5f5; padding: 16px; border-radius: 6px; margin: 24px 0;">
            <p style="margin: 0;"><strong>Correo:</strong> %s</p>
            <p style="margin: 8px 0 0;"><strong>Contraseña temporal:</strong> %s</p>
          </div>

          <p style="color: #e53935;"><strong>⚠️ Al iniciar sesión por primera vez se te pedirá cambiar esta contraseña.</strong></p>
          <p>Si no realizaste este registro, ignora este correo.</p>

          <hr style="margin: 32px 0; border: none; border-top: 1px solid #e0e0e0;">
          <p style="font-size: 12px; color: #9e9e9e;">DineMeNow — Reservas de restaurantes en Bogotá</p>
        </div>
        """.formatted(nombreRestaurante, correoDestino, passwordTemporal);
    }

    private String construirCorreoEstado(String nombreRestaurante, String nuevoEstado){
        String mensaje = switch(nuevoEstado){
             case "ACTIVO"     -> "¡Tu restaurante ha sido <strong>activado</strong>! Ya es visible para los usuarios.";
             case "INACTIVO"   -> "Tu restaurante ha sido marcado como <strong>inactivo</strong> temporalmente.";
             case "SUSPENDIDO" -> "Tu restaurante ha sido <strong>suspendido</strong>. Contáctanos para más información.";
             default           -> "El estado de tu restaurante ha sido actualizado a <strong>" + nuevoEstado + "</strong>.";
        };

        return """
        <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 32px; border: 1px solid #e0e0e0; border-radius: 8px;">
          <h2 style="color: #D84315;">Actualización de estado — %s</h2>
          <p>%s</p>
          <p>Si tienes dudas, responde este correo o contáctanos desde la plataforma.</p>

          <hr style="margin: 32px 0; border: none; border-top: 1px solid #e0e0e0;">
          <p style="font-size: 12px; color: #9e9e9e;">DineMeNow — Reservas de restaurantes en Bogotá</p>
        </div>
        """.formatted(nombreRestaurante, mensaje);
    }

    //construcción del cuerpo del correo para la confirmación de recibido de solicitud o mensaje
    private String construirCorreoConfirmacionRecibido(String nombreRestaurante){
        return """
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 32px; border: 1px solid #e0e0e0; border-radius: 8px;">
                <h2 style="color: #D84315">¡Hola, %s!</h2>
                <p>Hemos recibido tu solicitud de registro en <strong>DineMeNow<strong>.</p>
                <p>Nuestro equipo está revisando la información de tu restaurante. Este proceso puede tomar hasta <strong>48 horas hábiles</strong>.</p>
                <p>Te notificaremos por este correo cuando tu solicitud sea aprobada o si necesitamos informacion adicional.</p>
                <hr style="margin: 32px 0; border: none; border-top: 1px solid #e0e0e0;">
                <p style="font-size: 12px; color: #9e9e9e;">DineMeNow — Reservas de restaurantes en Bogotá</p>
                 </div>
        """.formatted(nombreRestaurante);
    }



}
