package com.akihabara.market.controller;

import com.akihabara.market.model.ProductoOtaku;
import com.akihabara.market.view.InventarioGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class GuiController {
    private final InventarioGUI vista;
    private final ProductoController productoController;

    public GuiController(InventarioGUI vista) {
        this.vista = vista;
        this.productoController = new ProductoController();
        inicializarEventos();  // Vincula los eventos de la interfaz con métodos del controlador
        cargarProductosEnTabla();  // Al iniciar, muestra todos los productos cargados desde el modelo
    }

    private void inicializarEventos() {
        // Cada botón tiene asignado un listener que ejecuta su función correspondiente
        // Al hacer click en agregar, actualizar, eliminar o limpiar, se llaman estos métodos
        vista.getBtnAgregar().addActionListener(e -> agregarProducto());
        vista.getBtnActualizar().addActionListener(e -> actualizarProducto());
        vista.getBtnEliminar().addActionListener(e -> eliminarProducto());
        vista.getBtnLimpiar().addActionListener(e -> limpiarFormulario());

        // Listener para cuando el usuario selecciona una fila en la tabla,
        // se carga el producto seleccionado en el formulario para su edición
        vista.getTablaProductos().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cargarProductoSeleccionado();
            }
        });
    }

    // Agrega un producto nuevo usando los datos ingresados en el formulario
    private void agregarProducto() {
        try {
            // Lee y procesa los datos ingresados en los campos de texto
            String nombre = vista.getTxtNombre().getText().trim();
            String categoria = vista.getTxtCategoria().getText().trim();
            double precio = Double.parseDouble(vista.getTxtPrecio().getText());
            int stock = Integer.parseInt(vista.getTxtStock().getText());

            // Llama al controlador de productos para agregarlo, actualiza la tabla y limpia formulario si tiene éxito
            if (productoController.agregarProducto(nombre, categoria, precio, stock)) {
                cargarProductosEnTabla();
                limpiarFormulario();
                mostrarMensaje("Producto agregado correctamente.");
            } else {
                mostrarError("No se pudo agregar el producto.");
            }
        } catch (Exception e) {
            // Captura errores como formato incorrecto o campos vacíos
            mostrarError("Error al agregar producto: " + e.getMessage());
        }
    }

    // Actualiza los datos del producto seleccionado en la tabla
    private void actualizarProducto() {
        try {
            int fila = vista.getTablaProductos().getSelectedRow();
            if (fila == -1) {
                mostrarError("Seleccione un producto para actualizar.");  // Asegura que haya selección antes de actualizar
                return;
            }

            // Obtiene el id y los datos modificados para actualizar en el modelo
            int id = Integer.parseInt(vista.getTxtId().getText());
            String nombre = vista.getTxtNombre().getText().trim();
            String categoria = vista.getTxtCategoria().getText().trim();
            double precio = Double.parseDouble(vista.getTxtPrecio().getText());
            int stock = Integer.parseInt(vista.getTxtStock().getText());

            // Actualiza el producto en la base de datos, refresca la tabla y limpia el formulario si tiene éxito
            if (productoController.actualizarProducto(id, nombre, categoria, precio, stock)) {
                cargarProductosEnTabla();
                limpiarFormulario();
                mostrarMensaje("Producto actualizado correctamente.");
            } else {
                mostrarError("No se pudo actualizar el producto.");
            }
        } catch (Exception e) {
            mostrarError("Error al actualizar producto: " + e.getMessage());
        }
    }

    // Elimina el producto seleccionado tras pedir confirmación
    private void eliminarProducto() {
        int fila = vista.getTablaProductos().getSelectedRow();
        if (fila == -1) {
            mostrarError("Seleccione un producto para eliminar.");
            return;
        }

        // Extrae el ID del producto de la fila seleccionada
        int id = Integer.parseInt(vista.getTablaProductos().getValueAt(fila, 0).toString());

        // Muestra diálogo para confirmar antes de eliminar
        int confirm = JOptionPane.showConfirmDialog(vista, "¿Estás seguro de eliminar el producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Si confirma, llama al controlador para eliminar y actualiza vista
            if (productoController.eliminarProducto(id)) {
                cargarProductosEnTabla();
                limpiarFormulario();
                mostrarMensaje("Producto eliminado correctamente.");
            } else {
                mostrarError("No se pudo eliminar el producto.");
            }
        }
    }

    // Carga todos los productos desde el modelo y actualiza la tabla de la vista
    private void cargarProductosEnTabla() {
        List<ProductoOtaku> productos = productoController.listarProductos();
        DefaultTableModel modelo = (DefaultTableModel) vista.getTablaProductos().getModel();
        modelo.setRowCount(0);  // Limpia todos los datos existentes en la tabla

        // Agrega cada producto como una nueva fila en la tabla
        for (ProductoOtaku p : productos) {
            modelo.addRow(new Object[]{
                p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock()
            });
        }
    }

    // Cuando se selecciona una fila, carga los datos de ese producto en los campos para edición
    private void cargarProductoSeleccionado() {
        int fila = vista.getTablaProductos().getSelectedRow();
        if (fila != -1) {
            vista.getTxtId().setText(vista.getTablaProductos().getValueAt(fila, 0).toString());
            vista.getTxtNombre().setText(vista.getTablaProductos().getValueAt(fila, 1).toString());
            vista.getTxtCategoria().setText(vista.getTablaProductos().getValueAt(fila, 2).toString());
            vista.getTxtPrecio().setText(vista.getTablaProductos().getValueAt(fila, 3).toString());
            vista.getTxtStock().setText(vista.getTablaProductos().getValueAt(fila, 4).toString());
        }
    }

    // Limpia el formulario y deselecciona cualquier fila en la tabla
    private void limpiarFormulario() {
        vista.getTxtId().setText("");
        vista.getTxtNombre().setText("");
        vista.getTxtCategoria().setText("");
        vista.getTxtPrecio().setText("");
        vista.getTxtStock().setText("");
        vista.getTablaProductos().clearSelection();
    }

    // Mensajes emergentes para informar al usuario
    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String error) {
        JOptionPane.showMessageDialog(vista, error, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

