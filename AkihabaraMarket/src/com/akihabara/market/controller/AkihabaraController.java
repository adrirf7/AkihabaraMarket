package com.akihabara.market.controller;

import com.akihabara.market.dao.ProductoDAO;
import com.akihabara.market.model.ProductoOtaku;
import com.akihabara.market.view.InterfazConsola;

import java.util.List;

public class AkihabaraController {
    private final InterfazConsola vista;
    private final ProductoDAO dao;

    public AkihabaraController() {
        vista = new InterfazConsola();
        dao = new ProductoDAO();
    }

    public void iniciar() {
        int opcion;

        do {
            vista.mostrarMenu();
            opcion = vista.leerOpcion();

            switch (opcion) {
                case 1:
                    dao.agregarProducto(vista.leerDatosProducto());
                    break;

                case 2:
                    vista.mostrarProducto(dao.obtenerProductoPorId(vista.pedirIdProducto()));
                    break;

                case 3:
                    vista.mostrarListaProductos(dao.obtenerTodosLosProductos());
                    break;

                case 4:
                    vista.mostrarListaProductos(dao.buscarProductosPorNombre(vista.pedirNombreProducto()));
                    break;

                case 5:
                    vista.mostrarListaProductos(dao.buscarProductoPorCategoria(vista.pedirCategoriaProducto()));
                    break;

                case 6:
                    ProductoOtaku actualizado = vista.leerDatosProducto();
                    actualizado.setId(vista.pedirIdProducto());
                    dao.actualizarProducto(actualizado);
                    break;

                case 7:
                    dao.eliminarProducto(vista.pedirIdProducto());
                    break;

                case 8:
                    vista.mostrarMensaje("Saliendo del sistema. ¡Gracias!");
                    break;

                default:
                    vista.mostrarMensaje("Opción inválida. Intenta de nuevo.");
            }

        } while (opcion != 8);
    }
}
