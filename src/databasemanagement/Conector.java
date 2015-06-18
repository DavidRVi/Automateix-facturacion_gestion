/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package databasemanagement;
import automateix.Articulo;
import controlador.ControladorFacturacion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author David
 */
public class Conector {
    private String db;
    private String user;
    private String pw;
    private Connection con = null;
    
    public Conector()
    {
        db = "automateix";
        user = "root";
        pw = "mysqlserver";
    }
    
    public int Conectar(){
        try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		System.out.println("MySQL JDBC Driver not found.");
		e.printStackTrace();
		return 1;
	}
        
        try {
		con = DriverManager.getConnection("jdbc:mysql://localhost:3307/" + db+"?zeroDateTimeBehavior=convertToNull", user, pw);
 
	} catch (SQLException e) {
		System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		return 1;
	}
        if (con != null) {
		//System.out.println("You made it, take control your database now!");
	} else {
		System.out.println("Failed to make connection!");
                return 1;
	}
        return 0;
    }
    
    public void Cerrar(){
        try{
                con.close();
            } catch(SQLException e) {
                System.out.println("Failed closing conection.");
                e.printStackTrace();
                return;
            }
    }
    
    public void InsertarCliente(String nombre, String apellidos, String DNI, String direccion, int telefono)
    {
        PreparedStatement preparedStatement = null;
        String insertTableSQL = "INSERT INTO clientes (DNI,Nombre,Apellidos,Direccion,Telefono) VALUES" +
                                "(?,?,?,?,?)"; 
        
        try{
            preparedStatement = con.prepareStatement(insertTableSQL);
            
            preparedStatement.setString(1, DNI);
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, apellidos);
            preparedStatement.setString(4, direccion);
            preparedStatement.setInt(5, telefono);
            
            preparedStatement.executeUpdate();
            
            preparedStatement.closeOnCompletion();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public ResultSet BuscarClientes(String data)
    {
        ResultSet rs = null;
        
        try{
            Statement request = con.createStatement();
            if(data == null)
                rs = request.executeQuery("SELECT * FROM clientes LIMIT 50");
            else
                rs = request.executeQuery("SELECT * FROM clientes WHERE DNI LIKE '"+ data +"%' OR Nombre LIKE '%"+ data +"%'");
        }
        catch(SQLException sqle){  

            System.out.println(sqle); 
            sqle.printStackTrace();  
        }
        
        
        return rs;
    }
    
    public ResultSet GetCliente(String DNI)
    {
        ResultSet rs = null;
        
        try{
            Statement request = con.createStatement();
            
            if(DNI != null)
                rs = request.executeQuery("SELECT * FROM clientes WHERE DNI='"+ DNI +"'");
            else System.out.println("DNI = NULL");
        }
        catch(SQLException sqle){  

            System.out.println(sqle); 
            sqle.printStackTrace();  
        }
        
        
        return rs;
    }
    
    public void modificarCliente(String DNI, String nombre, String apellidos, String direccion, int telefono, String oldDNI)
    {
        String query = "UPDATE clientes SET DNI='"+DNI+"', "
                + "Nombre='"+nombre+"', Apellidos='"+apellidos+"', Direccion='"+direccion+"', Telefono='"+telefono+"' WHERE DNI='"+oldDNI+"'";
        String visitQuery = "UPDATE visitas SET DNI='"+DNI+"' WHERE DNI ='"+oldDNI+"'";
        
        try{
            Statement request = con.createStatement();
            
            if(DNI!= null)
            {
                request.execute(query);
                request.execute(visitQuery);
            }
            else System.out.println("DNI != NULL");
        }catch(SQLException sqle){
            System.out.println(sqle);
            sqle.printStackTrace();
        }
        
    }
    
    public void EliminarCliente(String DNI)
    {
        String query = "DELETE FROM clientes WHERE DNI='"+DNI+"'";
        try{
            Statement request = con.createStatement();
            
            if(DNI != null)
                request.executeUpdate(query);
            else System.out.println("DNI = NULL");
        }
        catch(SQLException sqle){  

            System.out.println(sqle); 
            sqle.printStackTrace();  
        }
    }
    
    public void EliminarVisitas(String DNI)
    {
        String query = "DELETE FROM visitas WHERE DNI='"+DNI+"'";
        try{
            Statement request = con.createStatement();
            
            if(DNI!= null)
                request.executeUpdate(query);
            else System.out.println("DNI = NULL");
            
        }catch(SQLException sqle){
            System.out.println(sqle);
            sqle.printStackTrace();
        }
    }

    public ResultSet GetVisitas(String data) {
        ResultSet rs = null;
        
        try{
            Statement request = con.createStatement();
            if(data!=null)
                rs = request.executeQuery("SELECT * FROM visitas WHERE DNI ='"+data+"'");
           
        }
        catch(SQLException sqle){  
            System.out.println(sqle); 
            sqle.printStackTrace();  
        }
        
        return rs;
    }
    
    public void insertarVisita(String DNI, String comentario)
    {
        try{
            PreparedStatement query = con.prepareStatement("INSERT INTO visitas(DNI, fecha, comentario) values (?,?,?)");
            
            query.setString(1, DNI);
            
            Calendar cal = Calendar.getInstance();
            java.sql.Timestamp sqlDate = new java.sql.Timestamp(cal.getTimeInMillis());
            query.setTimestamp(2, sqlDate);
            query.setString(3, comentario);
            
            query.executeUpdate();
        }
        catch(SQLException sqle){  
            System.out.println(sqle); 
            sqle.printStackTrace();  
        }
    }

    public ResultSet GetArticulos() {
        ResultSet rs = null;
        
        try{
            Statement request = con.createStatement();
            
            rs = request.executeQuery("SELECT * FROM articulos");
           
        }
        catch(SQLException sqle){  
            System.out.println(sqle); 
            sqle.printStackTrace();  
        }
        
        return rs;
    }
    
    public void insertarArticulo(String nombre, float precio) {
        try{
            PreparedStatement query = con.prepareStatement("INSERT INTO articulos(nombre, precio) values (?,?)");
            
            query.setString(1, nombre);
            query.setFloat(2, precio);
            
            query.executeUpdate();
        }
        catch(SQLException sqle){  
            System.out.println(sqle); 
            sqle.printStackTrace();  
        }
    }
    
    public void modificarArticulo(String nombre, float precio, String oldNombre)
    {
        String query = "UPDATE articulos SET nombre='"+nombre+"', "
                +"precio='"+precio+"' WHERE nombre='"+oldNombre+"'";
        
        try{
            Statement request = con.createStatement();
            
            if(nombre!= null)
            {
                request.execute(query);
            }
            else System.out.println("DNI != NULL");
        }catch(SQLException sqle){
            System.out.println(sqle);
            sqle.printStackTrace();
        }
        
    }
    
    public void eliminarArticulo(String nombre)
    {
        String query = "DELETE FROM articulos WHERE nombre='"+nombre+"'";
        try{
            Statement request = con.createStatement();
            
            if(nombre!= null)
                request.executeUpdate(query);
            else System.out.println("Nombre = NULL");
            
        }catch(SQLException sqle){
            System.out.println(sqle);
            sqle.printStackTrace();
        }
    }

    public int getContadorFacturas(int year) {
        String query = "SELECT * FROM contadorfacturas WHERE year='" + year + "'";
        ResultSet rs;
        try{
            Statement request = con.createStatement();
            
            
            rs = request.executeQuery(query);
            if(rs.next())
                return rs.getInt("count");
            else{
                PreparedStatement queryInsert = con.prepareStatement("INSERT INTO contadorfacturas(year,count) values (?, ?)");
                queryInsert.setInt(1, year);
                queryInsert.setInt(2, 1);
                
                queryInsert.executeUpdate();
                return 1;
                
            }
        } catch(SQLException sqle){
            System.out.println(sqle);
            sqle.printStackTrace();
            return 0;
        }
       
    }

    public Articulo GetArticulo(String name) {
        String query = "SELECT * FROM articulos WHERE nombre='" + name +"'";
        ResultSet result;
        Articulo art = null;
        try {
            Statement request = con.createStatement();
            result = request.executeQuery(query);
            result.next();
            art = new Articulo(result.getString(1), result.getFloat(2));
        } catch (SQLException ex) {
            Logger.getLogger(ControladorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return art;
    }

    public int getIVA() {
        String query = "SELECT * FROM iva";
        ResultSet result;
        int iva = 0;
        
        try {
            Statement request = con.createStatement();
            result = request.executeQuery(query);
            result.next();
            iva = result.getInt("valor");
            
        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return iva;
    }


    public void modificarIVA(int nuevoIVA, int IVA) {
        String query = "UPDATE iva SET valor='"+nuevoIVA+"' WHERE valor='"+IVA+"'";
        try {
            Statement request = con.createStatement();
            request.executeUpdate(query);
           
        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addContadorFacturas(int year, int value) {
        try {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            String query = "UPDATE contadorfacturas SET count='" +value+"' WHERE year='" + year +"'";
            Statement request = con.createStatement();
            request.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public double getTarifaMinuto(){
        ResultSet result;
        double tarifa = 0.0;
        try{
            String query = "SELECT * FROM tarifa_minuto";
            Statement request = con.createStatement();
            result = request.executeQuery(query);
            result.next();
            tarifa = result.getDouble("minuto");
            
        }catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tarifa;
    }
    
    public void setTarifaMinuto(double value, double old_value){
        String query = "UPDATE tarifa_minuto SET minuto='" +value+ "'WHERE minuto='"+old_value+"'";
        try {
            Statement request = con.createStatement();
            request.executeUpdate(query);
           
        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
            
            
}
