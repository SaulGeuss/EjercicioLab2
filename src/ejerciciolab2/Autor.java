/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejerciciolab2;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author adiseño.2015
 */

@Entity 

public class Autor {
    @Id
    
    public int idAutor;
    public String Nombre;

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    @Override
    public String toString(){
        return "IdAutor: " + idAutor + " ,Autor: " + Nombre; 
    }
    
    
}