package examen;

public class Nodo<E> {

	// El elemento que contiene.
	private E elemento;
	// El puntero al siguiente nodo.
	private Nodo<E> siguiente;
	
	public Nodo (E e) {
		this.elemento = e;
	}

	public Nodo<E> getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(Nodo<E> siguiente) {
		this.siguiente = siguiente;
	}

	public E getElemento() {
		return elemento;
	}

	public void setElemento(E elemento) {
		this.elemento = elemento;
	}
	
}
