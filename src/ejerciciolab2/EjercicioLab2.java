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
//        
        AutorJpaController miControladorAutor = new AutorJpaController(emf);
        LibroJpaController miControladorLibro = new LibroJpaController(emf);
        
//        Libro miLibro = new Libro(1, "Marca", "13", "22/06/2015", "13/06/1990", "Acme");
//        Libro miLibro2 = new Libro(2, "Pulling", "4", "22/06/2015", "04/06/1989", "Acme");
//        
//        Autor miAutor = new Autor(1, "Saul Geussepe");
//        Autor miAutor2 = new Autor(2, "Rodolfo Guillermo");
//        Autor miAutor3 = new Autor(3, "Keila Geanina");
//        
//        miLibro.getAutores().add(miAutor);
//        miLibro.getAutores().add(miAutor2);
//        miLibro2.getAutores().add(miAutor3);
//        
//        miAutor.getLibros().add(miLibro);
//        miAutor2.getLibros().add(miLibro);
//        miAutor3.getLibros().add(miLibro2);
//        
//        try{
//            miControladorLibro.create(miLibro);
//            miControladorLibro.create(miLibro2);
//            miControladorAutor.create(miAutor);
//            miControladorAutor.create(miAutor2);
//            miControladorAutor.create(miAutor3);
//        }catch (PersistenceException ex){
//            Logger.getLogger(EjercicioLab2.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
//            Logger.getLogger(EjercicioLab2.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
         emf.close();
        
    }
    
}
