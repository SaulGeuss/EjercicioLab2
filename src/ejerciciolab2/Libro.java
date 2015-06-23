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

@Entity                  //Entity en una tabla, le estoy diciendo a java que lo trate como una tabla
//@Table(name = "Libro")
public class Libro implements Serializable{
   
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int idLibro;
    
//    @Column(name="nombre")
    public String Nombre;
    
//    @Column(name="serie")
    public String Serie;
    
//    @Column(name="fechaIngreso")
    public String FechaIng;
    
//    @Column(name="fechaPublicacion")
    public String FechaPubli;
    
//    @Column(name="editorial")
    public String Editorial;
    
    @ManyToOne
    public Autor IdAutor;
    
    
    
    public Libro(){
    }

    public Libro(String Nombre, String Serie, String FechaIng, String FechaPubli, String Editorial ){
//        this.idLibro = idLibro;
        this.Nombre = Nombre;
        this.Serie =  Serie;
        this.FechaIng =  FechaIng;
        this.FechaPubli = FechaPubli;
        this.Editorial = Editorial;
    }

    public Autor getIdAutor() {
        return IdAutor;
    }

    public void setIdAutor(Autor IdAutor) {
        this.IdAutor = IdAutor;
    }
    
    
    
    
//    public Set<Autor> getAutores() {
//        return autores;
//    }
//
//    public void setAutores(Set<Autor> autores) {
//        this.autores = autores;
//    }
    
    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getSerie() {
        return Serie;
    }

    public void setSerie(String Serie) {
        this.Serie = Serie;
    }

    public String getFechaIng() {
        return FechaIng;
    }

    public void setFechaIng(String FechaIng) {
        this.FechaIng = FechaIng;
    }

    public String getFechaPubli() {
        return FechaPubli;
    }

    public void setFechaPubli(String FechaPubli) {
        this.FechaPubli = FechaPubli;
    }
    
    public String getEditorial() {
        return Editorial;
    }

    public void setEditorial(String Editorial) {
        this.Editorial = Editorial;
    }
    
    @Override
    public String toString(){
        return "IdLibro: " + idLibro + " ,Nombre: " + Nombre + " ,Serie: " + Serie + " ,Fecha ingreso: " + FechaIng + " , Fecha publicacion: " + FechaPubli + " , Editorial: " + Editorial; 
    }
}
