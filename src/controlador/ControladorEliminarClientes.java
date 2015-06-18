/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;

import databasemanagement.Conector;
import java.sql.ResultSet;
import java.sql.SQLException;
import automateix.vista.ExaminarCliente;

/**
 *
 * @author David
 */
public class ControladorEliminarClientes {
    private Conector con = null;
    private String DNI = null;
    
    public ControladorEliminarClientes(String unDNI)
    {
        DNI = unDNI;
    }
    
    public void eliminar(){
        con = new Conector();
        con.Conectar();
        con.EliminarCliente(DNI);
        con.EliminarVisitas(DNI);
        con.Cerrar();
    }
}
