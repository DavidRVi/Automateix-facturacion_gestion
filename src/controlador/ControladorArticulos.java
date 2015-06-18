/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;
import automateix.vista.GestionPrecios;
import databasemanagement.Conector;
import javax.swing.JOptionPane;
/**
 *
 * @author David
 */
public class ControladorArticulos {
    
    private GestionPrecios ventana = null;
    private Conector con = null;
    
    public ControladorArticulos(){
        
    }
    
    public void lanzarUI(){
        ventana = new GestionPrecios(this);
        ventana.startit(this);
    }    
    
    public void modificarArticuloBD(String nombre, float precio, String oldname) {
        con = new Conector();
        con.Conectar();
        con.modificarArticulo(nombre,precio, oldname);
        con.Cerrar();
        ventana.actualizarTabla();
    }
    
    public void eliminarArticuloBD(String nombre){
        con = new Conector();
        con.Conectar();
        con.eliminarArticulo(nombre);
        con.Cerrar();
    }
}
