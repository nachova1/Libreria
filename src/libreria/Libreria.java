/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package libreria;

import libreria.servicios.ServicioGral;

/**
 *
 * @author Admin
 */
public class Libreria {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ServicioGral servicio = new ServicioGral();
        servicio.menu();
        
        /*Para arreglar el quilombo q me aparece el principio lei q recomendaron agregar esto al xml:
        <property name="eclipselink.target-database" value="MySQL"/>
        */
    }
    
}
