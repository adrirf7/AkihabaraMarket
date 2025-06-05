package com.akihabara.market.controller;

import com.akihabara.market.dao.ProductoDAO;
import com.akihabara.market.model.ProductoOtaku;

import java.util.List;

public class ProductoController {

    // Instancia del DAO para interactuar con la base de datos
    private final ProductoDAO dao;

    public ProductoController() {
        this.dao = new ProductoDAO();
    }

    // Crea un nuevo producto y lo guarda usando el DAO
    public boolean agregarProducto(String nombre, String categoria, double precio, int stock) {
        try {
            ProductoOtaku producto = new ProductoOtaku(nombre, categoria, precio, stock);
            dao.agregarProducto(producto);
            return true;
        } catch (Exception e) {
            // Se captura cualquier error que pueda ocurrir durante el guardado
            System.err.println("Error en agregarProducto: " + e.getMessage());
            return false;
        }
    }

    // Actualiza los datos de un producto existente identificado por su id
    public boolean actualizarProducto(int id, String nombre, String categoria, double precio, int stock) {
        try {
            // Crea un objeto producto con los nuevos datos incluyendo el id
            ProductoOtaku producto = new ProductoOtaku(id, nombre, categoria, precio, stock);
            // Pide al DAO que actualice el registro en la base de datos
            return dao.actualizarProducto(producto);
        } catch (Exception e) {
            System.err.println("Error en actualizarProducto: " + e.getMessage());
            return false;
        }
    }

    // Elimina un producto según su id
    public boolean eliminarProducto(int id) {
        try {
            return dao.eliminarProducto(id);
        } catch (Exception e) {
            System.err.println("Error en eliminarProducto: " + e.getMessage());
            return false;
        }
    }

    // Obtiene la lista completa de productos desde la base de datos
    public List<ProductoOtaku> listarProductos() {
        return dao.obtenerTodosLosProductos();
    }

    // Busca y devuelve un producto según su id, o null si no existe
    public ProductoOtaku buscarPorId(int id) {
        return dao.obtenerProductoPorId(id);
    }

    // Devuelve una lista de productos cuyo nombre contiene el texto dado (búsqueda LIKE)
    public List<ProductoOtaku> buscarPorNombre(String nombre) {
        return dao.buscarProductosPorNombre(nombre);
    }

    // Devuelve una lista de productos cuya categoría contiene el texto dado (búsqueda LIKE)
    public List<ProductoOtaku> buscarPorCategoria(String categoria) {
        return dao.buscarProductoPorCategoria(categoria);
    }
}
