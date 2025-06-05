package com.akihabara.market.dao;
import com.akihabara.market.model.ProductoOtaku;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private DatabaseConnection dbConnection;

    // Inicializa la conexión a la base de datos cuando se crea la instancia de ProductoDAO
    public ProductoDAO() {
        dbConnection = new DatabaseConnection();
    }

    // Inserta un nuevo producto en la base de datos usando PreparedStatement para evitar inyección SQL
    public void agregarProducto(ProductoOtaku producto) {
        String consulta = "INSERT INTO producto(nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";

        // Establece conexión y prepara la consulta dentro de try-with-resources para cerrar recursos automáticamente
        try (Connection con = dbConnection.getConexion();
             PreparedStatement pstmt = con.prepareStatement(consulta)) {

            // Asigna los valores del objeto producto a los parámetros de la consulta SQL
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getCategoria());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getStock());

            // Ejecuta la inserción
            pstmt.executeUpdate();
            System.out.println("Producto agregado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al agregar producto: " + e.getMessage());
        }
    }

    // Recupera un producto específico por su ID
    public ProductoOtaku obtenerProductoPorId(int id) {
        String consulta = "SELECT * FROM producto WHERE id = ?";

        try (Connection con = dbConnection.getConexion();
             PreparedStatement pstmt = con.prepareStatement(consulta)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            // Si se encuentra el producto, crea y devuelve el objeto ProductoOtaku con los datos recuperados
            if (rs.next()) {
                return new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener producto: " + e.getMessage());
        }
        return null;  // Retorna null si no encuentra producto o hay error
    }

    // Recupera todos los productos almacenados en la base de datos
    public List<ProductoOtaku> obtenerTodosLosProductos() {
        List<ProductoOtaku> productos = new ArrayList<>();
        String consulta = "SELECT * FROM producto";

        try (Connection con = dbConnection.getConexion();
             PreparedStatement pstmt = con.prepareStatement(consulta);
             ResultSet rs = pstmt.executeQuery()) {

            // Itera sobre los resultados, crea objetos ProductoOtaku y los añade a la lista
            while (rs.next()) {
                productos.add(new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener productos: " + e.getMessage());
        }
        return productos;
    }

    // Actualiza los datos de un producto existente identificándolo por ID
    public boolean actualizarProducto(ProductoOtaku producto) {
        String consulta = "UPDATE producto SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id = ?";

        try (Connection con = dbConnection.getConexion();
             PreparedStatement pstmt = con.prepareStatement(consulta)) {

            // Asigna los nuevos valores y el ID del producto que se quiere actualizar
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getCategoria());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getStock());
            pstmt.setInt(5, producto.getId());

            // Ejecuta el update y devuelve true si se modificó alguna fila
            int filas = pstmt.executeUpdate();
            System.out.println("Producto actualizado con éxito");
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
        }
        return false; // Retorna false si hubo un error o no se actualizó ninguna fila
    }

    // Elimina un producto a partir de su ID
    public boolean eliminarProducto(int id) {
        String consulta = "DELETE FROM producto WHERE id = ?";

        try (Connection con = dbConnection.getConexion();
             PreparedStatement pstmt = con.prepareStatement(consulta)) {

            pstmt.setInt(1, id);

            // Ejecuta la eliminación y verifica si se eliminó al menos una fila
            int filas = pstmt.executeUpdate();
            System.out.println("Producto eliminado con éxito");
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
        }
        return false;
    }

    // Busca productos cuyo nombre contenga el texto dado, usando LIKE para búsqueda parcial
    public List<ProductoOtaku> buscarProductosPorNombre(String nombre) {
        List<ProductoOtaku> productos = new ArrayList<>();
        String consulta = "SELECT * FROM producto WHERE nombre LIKE ?";

        try (Connection con = dbConnection.getConexion();
             PreparedStatement pstmt = con.prepareStatement(consulta)) {

            // El comodín % permite buscar nombres que contengan el texto en cualquier posición
            pstmt.setString(1, "%" + nombre + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                productos.add(new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar productos por nombre '" + nombre + "': " + e.getMessage());
        }
        return productos;
    }

    // Busca productos por categoría con búsqueda parcial similar al nombre
    public List<ProductoOtaku> buscarProductoPorCategoria(String categoria) {
        List<ProductoOtaku> productos = new ArrayList<>();
        String consulta = "SELECT * FROM producto WHERE categoria LIKE ?";

        try (Connection con = dbConnection.getConexion();
             PreparedStatement pstmt = con.prepareStatement(consulta)) {

            pstmt.setString(1, "%" + categoria + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                productos.add(new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar productos por categoría '" + categoria + "': " + e.getMessage());
        }
        return productos;
    }
}
