/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejerciciolab2;


import javax.persistence.EntityManagerFactory;
import javax.persistence.*;
import ejerciciolab2.Autor;
import ejerciciolab2.Libro;
import controlador.AutorJpaController;
import controlador.LibroJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import modelos.ModeloTablaUsuario;

/**
 *
 * @author adiseño.2015
 */
public class EjercicioLab2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EjercicioLab2PU");
        EntityManager em = emf.createEntityManager();
        
        AutorJpaController miControladorAutor = new AutorJpaController(emf);
        LibroJpaController miControladorLibro = new LibroJpaController(emf);
        
        
        
        
    }
    
}
