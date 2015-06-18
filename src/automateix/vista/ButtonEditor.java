/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package automateix.vista;

import automateix.Factura;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author David
 */
class ButtonEditor extends DefaultCellEditor {
  protected JButton button;

  private String label;

  private boolean isPushed;
  private JButtonTableExample parent;

  public ButtonEditor(JCheckBox checkBox, JButtonTableExample parent) {
    super(checkBox);
    button = new JButton();
    this.parent = parent;
    button.setOpaque(true);
    button.addActionListener(new ActionListener() {
        @Override
      public void actionPerformed(ActionEvent e) {
        //fireEditingStopped();
        //doAction();
      }
    });
  }

  @Override
  public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int row, int column) {
    /*if (isSelected) {
      button.setForeground(table.getSelectionForeground());
      button.setBackground(table.getSelectionBackground());
    } else {
      button.setForeground(table.getForeground());
      button.setBackground(table.getBackground());
    }*/
    //label = (value == null) ? "" : value.toString();
    //button.setText(label);
    Factura f = parent.getFactura();
      if(column == 3)
        f.increaseArticulo(row);
      else if (column == 4)
          f.decreaseArticulo(row);
      else if (column == 5)
          f.eliminarArticulo(row);
    isPushed = true;
    parent.updateTable();
    return button;
  }

  private void doAction() {
      
  }
  @Override
  public Object getCellEditorValue() {
    
    isPushed = false;
    return new String(label);
  }

  @Override
  public boolean stopCellEditing() {
    isPushed = false;
    return super.stopCellEditing();
  }

  @Override
  protected void fireEditingStopped() {
    super.fireEditingStopped();
  }
}
