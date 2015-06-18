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
import automateix.vista.ExaminarCliente;

/**
 *
 * @author David
 */
public class ControladorExaminarClientes {
    private Conector con = null;
    private Cliente cli = null;
    
    public ControladorExaminarClientes(String DNI)
    {
            con = new Conector();
            con.Conectar();
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
    
    public void LanzarUI()
    {
        ExaminarCliente ventana = new ExaminarCliente(cli);
        
        ventana.startit(cli);
        
    }
    
}
