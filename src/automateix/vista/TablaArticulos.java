/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package automateix.vista;

import databasemanagement.Conector;
import java.awt.Dimension;

import java.sql.*;  
import java.util.*; 
import javax.swing.*;  
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author David
 */
public class TablaArticulos {
    private Conector con = null;
    private ResultSet rs = null;
    private JTable tabla = null;
    
    public TablaArticulos()
    {
        con = new Conector();
        if(con.Conectar() != 1)
        {
            rs = con.GetArticulos();
        }
        
        if( rs != null )
        {
            try{
                
                ResultSetMetaData md = rs.getMetaData();
                
                int numColumns = md.getColumnCount();

                Vector columns = new Vector(numColumns);

                for(int i=1; i<=numColumns; i++)
                    columns.add(md.getColumnName(i));
                
                Vector datos = new Vector();
                Vector fila; 
                
                
                while(rs.next())
                {
                    fila = new Vector(numColumns);
                    for(int i=1; i<=numColumns; i++)
                    {
                        fila.add(rs.getString(i));
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