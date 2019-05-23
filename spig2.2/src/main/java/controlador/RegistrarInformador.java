/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import auxiliares.FileToStringer;
import modelo.InformadorDAO;
import modelo.Comentario;
import modelo.Informador;
import auxiliares.SendEmail;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;


import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author n-droid
 */

@ManagedBean
public class RegistrarInformador {
    private String nombre;
    private String correo;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void agregaInformador(){
        Random rn = new Random();
        Informador i = new Informador();
        i.setNombre(nombre.toLowerCase());
        i.setCorreo(correo.toLowerCase());
        String contrasenia = nombre.toLowerCase() + Integer.toString(rn.nextInt() % 1000);
        i.setContrasenia(contrasenia);
        InformadorDAO udb = new InformadorDAO();
        try {
            udb.save(i);
            FileToStringer fts = new FileToStringer();
            String html_string = fts.readFile("informador_mail.html");
            html_string = html_string.replace("[[nombre]]",nombre);
            html_string = html_string.replace("[[password]]",contrasenia);
            SendEmail.send(correo,
                    "Bienvenido a SPIG",
                    html_string);
            Mensajes.info("Registrado");
        } catch (Exception ex) {
            Mensajes.error("Ya existe ese correo");
        }
    }
}
