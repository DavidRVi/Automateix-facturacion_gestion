/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automateix.vista;

import automateix.Articulo;
import automateix.Factura;
import controlador.ControladorFacturacion;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @version 1.0 11/09/98
 * @author david
 */
public class JButtonTableExample extends JPanel {

  private JTable table;
  DefaultTableModel dm;
  Factura listaArticulos;
  ControladorFacturacion cf;
  Object[] columnNames;
  Object[][] rowData;
  VentanaFacturacion parent;
  public JButtonTableExample(ControladorFacturacion controller, VentanaFacturacion window) {
    //super("JButtonTable Example");
      parent = window;
    columnNames = new Object[] { "Nombre", "Precio", "Cantidad", "Añadir", "Quitar", "Eliminar"  };
    rowData = new Object[0][0];
    cf = controller;
    listaArticulos = new Factura();
    dm = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column){ return column>2;
}
    };
   
    dm.setDataVector(null, columnNames);
    

    
    table = new JTable(dm);
    
    //table.getColumn("Button").setCellRenderer(new ButtonRenderer());
    //table.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));
    //JScrollPane scroll = new JScrollPane(table);
    //add(scroll);
    //setSize(400, 100);
    //setVisible(true);
  }

  public JTable getTable(){
      return table;
  }

  void addArticulo(String art) {
        Articulo article = cf.getArticulo(art);
        listaArticulos.addArticulo(article);
        updateTable();
        this.repaint();
        
        
  }

    public void updateTable() {
        dm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){ return column > 2;
}
        };
        
        rowData = new Object[listaArticulos.getSize()][3];
        for(int i=0; i<listaArticulos.getSize(); i++)
        {
            rowData[i][0] = listaArticulos.getArticulo(i).getNombre();
            rowData[i][1] = listaArticulos.getArticulo(i).getPrecio();
            rowData[i][2] = listaArticulos.getArticulo(i).getCantidad();
        }
        
        dm.setDataVector(rowData, columnNames);
        
        table = new JTable(dm);
        
        table.getColumn("Añadir").setCellRenderer(new ButtonRenderer("+1"));
        table.getColumn("Añadir").setCellEditor(new ButtonEditor(new JCheckBox(), this));
        table.getColumn("Quitar").setCellRenderer(new ButtonRenderer("-1"));
        table.getColumn("Quitar").setCellEditor(new ButtonEditor(new JCheckBox(), this));
        table.getColumn("Eliminar").setCellRenderer(new ButtonRenderer("X"));
        table.getColumn("Eliminar").setCellEditor(new ButtonEditor(new JCheckBox(), this));
        parent.updateTable();
        
            
    }
    
    public Factura getFactura(){
        return listaArticulos;
    }
    
}