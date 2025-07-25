package com.akihabara.market.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class InventarioGUI extends JFrame {
    private JTextField txtId, txtNombre, txtCategoria, txtPrecio, txtStock;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tablaProductos;
    private JButton btnSugerirNombre; //boton para sugerir un nombre con la IA

    public InventarioGUI() {
        setTitle("Inventario Otaku");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Panel para formulario con campos (5 filas, 2 columnas, espacios)
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));

        txtId = new JTextField();
        txtId.setEnabled(false);
        txtNombre = new JTextField();
        txtCategoria = new JTextField();
        txtPrecio = new JTextField();
        txtStock = new JTextField();

        // Crear panel para el campo "Nombre" + botón delante
        JPanel panelNombreConBoton = new JPanel(new BorderLayout(5, 0));
        btnSugerirNombre = new JButton("Sugerir");
        panelNombreConBoton.add(btnSugerirNombre, BorderLayout.WEST);
        panelNombreConBoton.add(txtNombre, BorderLayout.CENTER);
        
        panelFormulario.add(new JLabel("ID:"));
        panelFormulario.add(txtId);
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(panelNombreConBoton);
        panelFormulario.add(new JLabel("Categoría:"));
        panelFormulario.add(txtCategoria);
        panelFormulario.add(new JLabel("Precio:"));
        panelFormulario.add(txtPrecio);
        panelFormulario.add(new JLabel("Stock:"));
        panelFormulario.add(txtStock);

        // Panel para botones con FlowLayout centrado y espacio horizontal entre botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Tabla con modelo vacío inicial
        tablaProductos = new JTable(new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Categoría", "Precio", "Stock"}, 0
        ));
        JScrollPane scrollTabla = new JScrollPane(tablaProductos);

        // Panel contenedor para formulario y botones (NORTE)
        JPanel panelNorte = new JPanel(new BorderLayout(10, 10));
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);
        
        // Agregar padding (margen interno) a panelNorte y scrollTabla
        int padding = 15;
        panelNorte.setBorder(new EmptyBorder(padding, padding, padding, padding));
        scrollTabla.setBorder(new EmptyBorder(padding, padding, padding, padding));

        // Agregar componentes a la ventana principal
        setLayout(new BorderLayout(10, 10));
        add(panelNorte, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
    }

    // Getters para el controlador
    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtCategoria() { return txtCategoria; }
    public JTextField getTxtPrecio() { return txtPrecio; }
    public JTextField getTxtStock() { return txtStock; }
    public JButton getBtnAgregar() { return btnAgregar; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JButton getBtnSugerirNombre() { return btnSugerirNombre; }
    public JTable getTablaProductos() { return tablaProductos; }
}
