package com.akihabara.market.model;

public class ProductoOtaku {
	private int id;
	private String nombre;
	private String categoria;
	private double precio;
	private int stock;
	
	//Constructor vacio
	public ProductoOtaku(){
	}
	
    // Constructor completo (para obtener desde la BD)
	public ProductoOtaku(int id, String nombre, String categoria, double precio, int stock) {
		this.id =id;
		this.nombre=nombre;
		this.categoria=categoria;
		this.precio=precio;
		this.stock=stock;
	}
	
    // Constructor SIN id (para insertar en la BD)
    public ProductoOtaku(String nombre, String categoria, double precio, int stock) {
		this.nombre=nombre;
		this.categoria=categoria;
		this.precio=precio;
		this.stock=stock;
    }
	
	//Getters y Setters
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id=id;
	}
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria=categoria;
	}

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    //Metodo toString sobreescrito
    public String toString() {
        return "------------------------------" +
        		"\nProductoOtaku:" +
        		"\nID = " + id +
                "\nNombre = " + nombre +
                "\nCategoría = " + categoria +
                "\nPrecio = " + precio +
                "\nStock = " + stock;
    }
}
