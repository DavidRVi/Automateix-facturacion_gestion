/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package automateix.vista;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author David
 */
class ButtonRenderer extends JButton implements TableCellRenderer {

    private String content;
  public ButtonRenderer(String text) {
    setOpaque(true);
    content = text;
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
    if (isSelected) {
      //setForeground(table.getSelectionForeground());
      //setBackground(table.getSelectionBackground());
    } else {
      setForeground(table.getForeground());
      if(content == "+1")
        setBackground(Color.GREEN);
      else if(content =="-1")
          setBackground(Color.CYAN);
      else setBackground(Color.RED);
    }
    setText(content);
    
    return this;
  }
}
