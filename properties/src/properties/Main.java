package properties;
import java.io.IOException;
import java.util.Properties;
import java.io.FileInputStream;

public class Main {
	public static void main(String[] args) {
	      Properties props = new Properties();
	        try {
	            props.load(new FileInputStream("config.properties"));
	            String fondo = props.getProperty("COLOR_FONDO");
	            String texto = props.getProperty("COLOR_TEXTO");
	            String fuente = props.getProperty("FUENTE");
	            System.out.println("CONFIGURACIÓN DEL SISTEMA");
	            System.out.println("Color de fondo: " + fondo);
	            System.out.println("Color de texto: " + texto);
	            System.out.println("Tipo de fuente: " + fuente);
	        } catch (IOException e) {
	            System.out.println("No se ha podido leer el archivo de configuración");
	        }
	}
}
