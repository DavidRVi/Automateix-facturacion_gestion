/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package automateix;
import java.util.Date;

/**
 *
 * @author David
 */
public class Cliente {
    
    private String nombre;
    private String apellidos;
    private String DNI;
    private String direccion;
    
    private int telefono;
    
    public Cliente(String unNombre, String unApellido,String unDNI, String unadireccion)
    {
        nombre = unNombre;
        apellidos = unApellido;
        DNI = unDNI;
        direccion = unadireccion;
        
    }
    
    //Constructor de mentira, borrar para aplicacion final
    public Cliente(String unNombre, String unApellido)
    {
        nombre = unNombre;
        apellidos = unApellido;
    }
    
    public Cliente(String unNombre, String unApellido,String unDNI, String unadireccion, int untelefono)
    {
        nombre = unNombre;
        apellidos = unApellido;
        DNI = unDNI;
        direccion = unadireccion;
        telefono = untelefono;
        
    }
    
    public String getNombre()
    {
        return nombre;
    }
    
    public String getApellidos()
    {
        return apellidos;
    }
    
    public String getDNI()
    {
        return DNI;
    }
    
    public String getDireccion()
    {
        return direccion;
    }
    
    public int getTelefono()
    {
        return telefono;
    }
    
    public void setNombre(String unNombre)
    {
        nombre = unNombre;
    }
    
    public void setApellidos(String unApellido)
    {
        apellidos = unApellido;
    }
    
    public void setDNI(String unDNI)
    {
        DNI = unDNI;
    }
    
    public void setDireccion(String unadireccion)
    {
        direccion = unadireccion;
    }
    
    public void setTelefono(int untelefono)
    {
        telefono = untelefono;
    }
}
