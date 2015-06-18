/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package automateix;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class Factura {
    
    private List<Articulo> lista_articulos;
    private int numero;
    
    public Factura(){
        lista_articulos = new ArrayList<Articulo>(0);
        
    }
    /**
     * Funcion para añadir articulo a la lista de articulos a añadir a la factura
     * @param art Articulo a añadir
     */
    public void addArticulo(Articulo art)
    {
        lista_articulos.add(art);
    }
    /**
     * 
     * @param index
     * @return Articulo in index
     */
    public Articulo getArticulo(int index)
    {
        return lista_articulos.get(index);
    }
    /**
     *  Funcion para eliminar un articulo de la lista de la compra
     * @param index indice de la lista de articulos que queremos eliminar
     * @return Articulo eliminado
     */
    public Articulo eliminarArticulo(int index)
    {
        return lista_articulos.remove(index);
    }
    /**
     * incrementa en uno la cantidad de articulo en la factura
     * @param index indice del articulo en la lista
     */
    public void increaseArticulo(int index)
    {
        lista_articulos.get(index).increase();
    }
    /**
     * decrementa en uno la cantidad del articulo en la factura, si es <=0 lo elimina de la lista
     * @param index indice del articulo en la lista
     */
    public void decreaseArticulo(int index)
    {
        if(lista_articulos.get(index).decrease())
            eliminarArticulo(index);
    }

    public int getSize() {
        return lista_articulos.size();
    }
    
    public float getPrecioTotal()
    {
        float total = 0;
        for(int i=0; i<lista_articulos.size(); ++i)
            total+= lista_articulos.get(i).getPrecio() * lista_articulos.get(i).getCantidad();
        
        return total;
    }
    
    public int getNumero(){
        return numero;
    }
    
    public void setNumero(int n){
        numero = n;
    }
    
}
