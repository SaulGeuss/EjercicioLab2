/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejerciciolab2;

import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

/**
 *
 * @author adiseño.2015
 */

@Entity 

public class Autor implements Serializable {
    
    @Id
//    @Column(name = "IdAutor")
    public int idAutor;
    
//    @Column(name = "nombre")
    public String Nombre;
    
    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy = "autores")
    public Set<Libro> libros = new HashSet();

    
    
    public Autor(){
    }

    public Autor(int idAutor, String Nombre){
        this.idAutor = idAutor;
        this.Nombre = Nombre;
    }
    
    
    public Set<Libro> getLibros() {
        return libros;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }
    
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
