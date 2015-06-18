/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package automateix;

/**
 *
 * @author David
 */
public class Articulo {
    private String nombre;
    private float precio;
    private int cantidad;
    
    public Articulo(String nombre, float precio)
    {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = 1;
    }
    
    public Articulo(){
    }
    
    public Articulo(String nombre, float precio, int cantidad)
    {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public float getPrecio() {
        return precio;
    }
    
    public int getCantidad(){
        return cantidad;
    }
    
    public void setNombre(String newNombre)
    {
        nombre = newNombre;
    }
    
    public void setPrecio(float newPrecio)
    {
     
        precio = newPrecio;
    }
    
    public void increase()
    {
        cantidad++;
    }
    
    /**
     * Decrementa en uno la cantidad de ese articulo
     * @return false si cantidad <= 0, eliminar de la factura, true otherwise
     */
    public boolean decrease()
    {
        cantidad--;
        if(cantidad >0)
            return false;
        else return true;
    }

    public void setCantidad(int i) {
        cantidad = i;
    }

    public float getPrecioTotal() {
        return precio * cantidad;
    }
}
