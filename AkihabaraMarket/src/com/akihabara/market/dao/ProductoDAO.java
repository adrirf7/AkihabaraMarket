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

        // try-with-resources asegura cierre automático de recursos (conexión y statement)
        try (Connection con = dbConnection.getConexion();
             PreparedStatement pstmt = con.prepareStatement(consulta)) {

            // Mapea los campos del producto a los parámetros del SQL
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getCategoria());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getStock());

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

            // Solo entra si encuentra coincidencia exacta con el ID
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

            // Crea un objeto por cada fila y lo añade a la lista
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

            // Asegura que el ID del producto se usa para localizarlo y el resto para actualizarlo
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getCategoria());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getStock());
            pstmt.setInt(5, producto.getId());

            int filas = pstmt.executeUpdate();  // filas > 0 indica éxito
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

            int filas = pstmt.executeUpdate();  // Elimina si filas > 0
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

            // % permite coincidencias parciales antes y después del texto
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
