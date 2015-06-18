/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;
import automateix.Cliente;
import databasemanagement.Conector;
import java.sql.ResultSet;
import java.sql.SQLException;
import automateix.vista.ModificarCliente;
import automateix.vista.TablaClientes;


/**
 *
 * @author David
 */
public class ControladorModificarCliente {
    private Conector con = null;
    private Cliente cli = null;
    private TablaClientes table = null;
    
    public ControladorModificarCliente(String DNI, TablaClientes parent)
    {
        con = new Conector();
            con.Conectar();
            table = parent;
            ResultSet rs = con.GetCliente(DNI);
            String nombre = null;
            String apellidos = null;
            String direccion = null;
            int telefono = 0;
            
            try{    
                if(rs.next())
                {
                    nombre = rs.getString("Nombre");
                    apellidos = rs.getString("Apellidos");
                    direccion = rs.getString("Direccion");
                    telefono = rs.getInt("Telefono");
                }
            }
            catch(SQLException sqle){  
                  
                System.out.println(sqle); 
                
             }
            con.Cerrar();
            
            cli = new Cliente(nombre, apellidos, DNI, direccion, telefono);
    }
    
    public void modificarCliente(String DNI, String nombre, String apellidos, String direccion, String telefono)
    {
        
        con = new Conector();
        con.Conectar();
        con.modificarCliente(DNI, nombre, apellidos, direccion, Integer.valueOf(telefono), cli.getDNI());
        table.Llenartabla(null);
        cli.setDNI(DNI);
        con.Cerrar();
    }
    
    public void LanzarUI()
    {
        ModificarCliente ventana = new ModificarCliente(cli, this);
        
        ventana.startit(cli, this);
    }
}
