/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;
import automateix.Cliente;
import automateix.vista.NuevoCliente;
import databasemanagement.Conector;
import javax.swing.JOptionPane;

/**
 *
 * @author David
 */
public class ControladorClientes {
    
    private NuevoCliente ventana;
    private Cliente elcliente = null;
    private Conector con;
    public ControladorClientes(NuevoCliente laVentana)
    {
        ventana = laVentana;
    }
    
    public int crearCliente()
    {
        int error = 0;
        String nombre = null;
        String apellidos = null;
        String DNI = null;
        String direccion = null;
        String telefono = null;
        if(ventana.getNombre().length() != 0)
            nombre = ventana.getNombre();
        else error = 1;
        
        if(ventana.getApellidos().length() != 0 && error != 1)
            apellidos = ventana.getApellidos();
        else error = 1;
        
        if(ventana.getDNI().length() != 0 && error != 1)
            DNI = ventana.getDNI();
        else error = 1;
        
        if (ventana.getDireccion().length()!=0 && error !=1)
            direccion = ventana.getDireccion();
        else error = 1;
        
        if(ventana.getTelefono().length() != 0 && error!=1)
            telefono = ventana.getTelefono();
        else error = 1;
        
        if( error != 1 )
            elcliente = new Cliente(nombre, apellidos, DNI, direccion, Integer.valueOf(telefono));
        
        return error;
    }
    
    public void insertarClienteBD()
    {
        con = new Conector();
        int err = con.Conectar();
        if(err != 0)
        {
            JOptionPane.showMessageDialog(null, "Ha habido un error conectando a la base de datos.", "Error de BD!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        con.InsertarCliente(elcliente.getNombre(), elcliente.getApellidos(), elcliente.getDNI(), elcliente.getDireccion(), elcliente.getTelefono());
        con.insertarVisita(elcliente.getDNI(), "Se insertó el cliente en la BD.");
        con.Cerrar();
       
    }
    
    
    
    
    
}
