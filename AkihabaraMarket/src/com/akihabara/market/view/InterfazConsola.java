package com.akihabara.market.view;

import com.akihabara.market.model.ProductoOtaku;
import com.akihabara.market.util.InputUtils;

import java.util.List;
import java.util.Scanner;

public class InterfazConsola {

    private final Scanner scanner = new Scanner(System.in);

    public void mostrarMenu() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("1. Añadir producto");
        System.out.println("2. Consultar producto por ID");
        System.out.println("3. Listar todos los productos");
        System.out.println("4. Listar productos por nombre");
        System.out.println("5. Listar productos por categoría");
        System.out.println("6. Actualizar producto");
        System.out.println("7. Eliminar producto");
        System.out.println("8. Salir");
    }

    public int leerOpcion() {
        return InputUtils.leerEntero("Selecciona una opción: ");
    }

    public ProductoOtaku leerDatosProducto() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Categoría: ");
        String categoria = scanner.nextLine();

        double precio = InputUtils.leerDouble("Precio: ");
        int stock = InputUtils.leerEntero("Stock: ");

        return new ProductoOtaku(nombre, categoria, precio, stock);
    }

    public int pedirIdProducto() {
        return InputUtils.leerEntero("ID del producto: ");
    }

    public String pedirNombreProducto() {
        System.out.print("Nombre del producto: ");
        return scanner.nextLine();
    }

    public String pedirCategoriaProducto() {
        System.out.print("Categoría del producto: ");
        return scanner.nextLine();
    }

    public void mostrarProducto(ProductoOtaku producto) {
        if (producto != null) {
            System.out.println("Producto encontrado:");
            System.out.println(producto);
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    public void mostrarListaProductos(List<ProductoOtaku> productos) {
        if (productos.isEmpty()) {
            System.out.println("No hay productos para mostrar.");
        } else {
            System.out.println("\n--- Lista de productos ---");
            for (ProductoOtaku p : productos) {
                System.out.println(p);
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
