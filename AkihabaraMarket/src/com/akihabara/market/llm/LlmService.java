package com.akihabara.market.llm;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;
import com.google.gson.*;

public class LlmService {
	private final String apiKey;
	
	public LlmService() {
		this.apiKey = cargarApiKey(); // Carga la clave API desde el archivo de configuración
	}
	
	// Método público para generar un nombre de producto
	public String sugerirNombreProducto(String tipo, String franquicia) {
		String prompt = String.format(
			"Dame únicamente un nombre llamativo y original (sin explicaciones) para un producto otaku del tipo '%s' basado en la franquicia '%s'. Solo responde con el nombre.",
			tipo, franquicia
		);
		
		try {
			HttpClient client = HttpClient.newHttpClient();

			// Construye el mensaje en formato JSON como lo espera la API
			JsonObject message = new JsonObject();
			message.addProperty("role", "user");
			message.addProperty("content", prompt);

			JsonArray messages = new JsonArray();
			messages.add(message);

			JsonObject body = new JsonObject();
			body.addProperty("model", "mistralai/mistral-7b-instruct:free");
			body.add("messages", messages);

			// Prepara la solicitud HTTP con autenticación y tipo de contenido
			HttpRequest request = HttpRequest.newBuilder()
				.uri(new URI("https://openrouter.ai/api/v1/chat/completions"))
				.header("Authorization", "Bearer " + apiKey)
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(body.toString()))
				.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			// Extrae el contenido de la respuesta del modelo
			JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
			String resultado = json
				.getAsJsonArray("choices")
				.get(0)
				.getAsJsonObject()
				.getAsJsonObject("message")
				.get("content")
				.getAsString()
				.trim();

			// Elimina comillas dobles y tipográficas del resultado
			resultado = resultado.replaceAll("[\"“”]", "");

			// Toma solo la primera línea por si viene con explicaciones
			String soloNombre = resultado.split("\n")[0];

			return soloNombre.trim();
		} catch (Exception e) {
			System.out.println("Error al comunicar con OpenRouter: " + e.getMessage());
			return null;
		}
	}
	
	// Carga la clave API desde el archivo config.properties
	private String cargarApiKey() {
		Properties props = new Properties();
		try (FileInputStream fis = new FileInputStream("config.properties")) {
			props.load(fis);
			return props.getProperty("OPENROUTER_API_KEY");
		} catch (IOException e) {
			System.out.println("No se pudo cargar la API Key: " + e.getMessage());
			return null;
		}
	}
}
