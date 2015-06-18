/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package automateix.vista;

import databasemanagement.Conector;
import java.awt.Dimension;

import java.sql.*;  
import java.text.SimpleDateFormat;
import java.util.*; 
import javax.swing.*;  

/**
 *
 * @author David
 */
public class TablaExaminarCliente {
    private Conector con = null;
    private ResultSet rs = null;
    private JTable tabla = null;
    
    public TablaExaminarCliente(String data)
    {
        con = new Conector();
        if(con.Conectar() != 1)
        {
            rs = con.GetVisitas(data);
        }
        
        if( rs != null )
        {
            try{
                ResultSetMetaData md = rs.getMetaData();
                
                int numColumns = md.getColumnCount();

                Vector columns = new Vector(numColumns);

                for(int i=2; i<=numColumns; i++)
                    columns.add(md.getColumnName(i));
                
                Vector datos = new Vector();
                Vector fila; 
                
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                while(rs.next())
                {
                    fila = new Vector(numColumns);
                    for(int i=2; i<=numColumns; i++)
                    {
                        if(i!=2)
                            fila.add(rs.getString(i));
                        else fila.add(format.format(rs.getTimestamp(i)));
                        
                    }
                    datos.add(fila);
                }
                con.Cerrar();
                
                
                tabla = new JTable(datos, columns){
                    @Override
                    public boolean isCellEditable(int row, int column){
                        return false;
                    }
                };
                tabla.setPreferredScrollableViewportSize(new Dimension(300, 100));
                tabla.setFillsViewportHeight(true);
                tabla.setVisible(true);
                tabla.validate();
                
            }
            catch(SQLException sqle){  
                  
                System.out.println(sqle);  
                sqle.printStackTrace();  
             }        
        }
    }
    
    public JTable getTable()
    {
        return tabla;
    }
}
