/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;

import automateix.Articulo;
import automateix.Cliente;
import automateix.vista.TablaClientes;
import automateix.vista.VentanaFacturacion;
import databasemanagement.Conector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author David
 */
public class ControladorFacturacion {
    private Conector con = null;
    private Cliente cli = null;
    private TablaClientes table = null;
    
    public ControladorFacturacion(String DNI, TablaClientes parent){
        con = new Conector();
        con.Conectar();
        table = parent;
        ResultSet rs = con.GetCliente(DNI);
        String nombre = null;
        String apellidos = null;
        String direccion = null;
            
        try{    
            if(rs.next())
            {
                nombre = rs.getString("Nombre");
                apellidos = rs.getString("Apellidos");
                direccion = rs.getString("Direccion");
            }
        }
        catch(SQLException sqle){  
                  
            System.out.println(sqle); 
                
        }
        con.Cerrar();
            
        cli = new Cliente(nombre, apellidos, DNI, direccion);
    }
    
    public void LanzarUI(){
        VentanaFacturacion ventana = new VentanaFacturacion(cli, this);
        
        ventana.startit(cli, this);
    }

    public int getCount(int year) {
        con = new Conector();
        con.Conectar();
        int count = con.getContadorFacturas(year);
        con.Cerrar();
        return count;
    }
    
    public void addFactura(int year, int value){
        con = new Conector();
        con.Conectar();
        con.addContadorFacturas(year, value);
        con.Cerrar();      
        
    }
    
    public List<String> getAllProducts(){
        con = new Conector();
        con.Conectar();
        ResultSet result = con.GetArticulos();
        List<String> list = new ArrayList<String>();
        try{
            while(result.next())
                list.add(result.getString("nombre"));
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
        }
        con.Cerrar();
        
        return list;
        
        
    }
    
    public Articulo getArticulo(String name)
    {
        con = new Conector();
        con.Conectar();
        Articulo art = con.GetArticulo(name);
        
        con.Cerrar();
        
        return art;
    }

    public int getCurrentIVA() {
        con = new Conector();
        con.Conectar();
        int iva = con.getIVA();
        con.Cerrar();
        return iva;
    }

    public float getCurrentFacturaMinuto() {
        con = new Conector();
        con.Conectar();
        double factura_minuto = con.getTarifaMinuto();
        con.Cerrar();
        return (float) factura_minuto;
    }

    public void insertarVisita(String dni, String string) {
        con = new Conector();
        con.Conectar();
        con.insertarVisita(dni, string);
        con.Cerrar();
    }
}
