/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejerciciolab2;

/**
 *
 * @author adiseño.2015
 */
public class Libro {
    public int idLibro=0;
    public String Nombre="";
    public String Serie="";
    public String FechaIng;
    public String FechaSal;
    public String Editorial ="";

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

    public String getFechaSal() {
        return FechaSal;
    }

    public void setFechaSal(String FechaSal) {
        this.FechaSal = FechaSal;
    }

    public String getEditorial() {
        return Editorial;
    }

    public void setEditorial(String Editorial) {
        this.Editorial = Editorial;
    }
    
}
