package examen;

import java.util.Iterator;

public class Main {

	public static void main(String[] args) {
		
		ListaEnlazada<String> lista = new ListaEnlazada<>();
		
		lista.add("Pepe");
		lista.add("Marcos");
		lista.add("María");
		lista.add("Juanito");
		lista.add("Luisa");

		Iterator<String> it = lista.iterator();
		
		int cnt = 1;
		
		System.out.println("Primera iteración: ");
		System.out.println("Tamaño: " + lista.size());
		
		while (it.hasNext()) {
			System.out.println(it.next());
			if (cnt++ == lista.size() - 1) {
				it.remove();
			}
		}
		
		System.out.println("Segunda iteración (después de borrar): ");
		System.out.println("Tamaño: " + lista.size());
		
		it = lista.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		
	}

}
