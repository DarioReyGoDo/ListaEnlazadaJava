package examen;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ListaEnlazada<E> implements List<E> {

	private int numElementos;
	
	// Apuntador al primer y al último nodo.
	private Nodo<E> primero;
	private Nodo<E> ultimo;

	public ListaEnlazada() {
		// Inicializar el número de elementos a cero.
		this.numElementos = 0;
	}
	
	@Override
	public int size() {
		return this.numElementos;
	}

	@Override
	public boolean isEmpty() {
		return this.numElementos == 0;
	}

	@Override
	public boolean contains(Object o) {
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		return new ListaEnlazadaIterator();
	}

	@Override
	public Object[] toArray() {
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}

	@Override
	public boolean add(E e) {
		// Generamos un nodo que contenga el elemento introducido.
		Nodo<E> nuevo = new Nodo<>(e);
		
		// Si la lista está vacía, el nodo pasará a ser el primero y el último.
		if (this.isEmpty()) {
			this.primero = this.ultimo = nuevo;
		}
		// Si no está vacía.
		else {
			// El último elemento apuntará como elemento siguiente al nuevo elemento.
			this.ultimo.setSiguiente(nuevo);
			// El último elemento de la lista pasará a ser el nuevo elemento.
			this.ultimo = nuevo;
		}
		
		// Aumentamos en 1 el número de elementos.
		this.numElementos++;
		
		return true;
	}

	@Override
	public boolean remove(Object o) {
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}

	@Override
	public void clear() {
		
	}

	@Override
	public E get(int index) {
		return null;
	}

	@Override
	public E set(int index, E element) {
		return null;
	}

	@Override
	public void add(int index, E element) {
		
	}

	@Override
	public E remove(int index) {
		return null;
	}

	@Override
	public int indexOf(Object o) {
		return 0;
	}

	@Override
	public int lastIndexOf(Object o) {
		return 0;
	}

	@Override
	public ListIterator<E> listIterator() {
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return null;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return null;
	}
	
	protected class ListaEnlazadaIterator implements Iterator<E> {

		private Nodo<E> anteriorDevuelto;
		private Nodo<E> ultimoDevuelto;
		private Nodo<E> siguienteADevolver;
		// Esta variable será el índice de iteración, por llamarlo así.
		private int objetosDevueltos;
		
		public ListaEnlazadaIterator() {
			this.objetosDevueltos = 0;
		}
		
		@Override
		public boolean hasNext() {
			/*
			 *  ListaEnlazada.this hace referencia al objeto ListaEnlazada que envuelve a este iterador en concreto.
			 *  En este caso, al no haber conflicto de nombres con la clase interna, no sería necesario y se podría obviar.
			 */
			return this.objetosDevueltos < ListaEnlazada.this.numElementos;
		}

		@Override
		public E next() {
			// Comprobamos si se puede devolver el siguiente elemento. De no se posible, se devuelve un error.
			if (!this.hasNext()) {
				throw new NoSuchElementException("No hay siguiente elemento en la lista.");
			}
			
			// La primera vez que devolvemos un objeto.
			if (objetosDevueltos == 0) {
				// Nuestro elemento devuelto será el primer elemento de la lista.
				this.ultimoDevuelto = ListaEnlazada.this.primero;
				// Almacenamos el elemento siguiente para poder seguir iterando después de eliminar un elemento.
				this.siguienteADevolver = this.ultimoDevuelto.getSiguiente();
			}
			// Si no es la primera vez que devolvemos un elemento.
			else {
				// Almaceno el último que saqué como el anterior.
				this.anteriorDevuelto = this.ultimoDevuelto;
				// El elemento actual pasa a ser el que anteriormente era el siguiente.
				this.ultimoDevuelto = this.siguienteADevolver;
				// El elemento siguiente, será, lógicamente, el siguiente del actual.
				this.siguienteADevolver = this.ultimoDevuelto.getSiguiente();
			}
			
//			if (this.ultimoDevuelto == null) {
//				throw new NoSuchElementException("No hay elementos en la lista.");
//			}
			
			// Aumentamos el índice en 1.
			this.objetosDevueltos++;
			
			// Devolvemos el valor del nodo.
			return this.ultimoDevuelto.getElemento();
		} 
		
		@Override
		// Según la documentación que leí, remove no debería romper el cursor virtual del iterador, se debe mantener la posición por la que se estaba iterando.
		public void remove() {
			// Comprobamos que el método se haya después de llamar a next y que sólo pueda llamarse una única vez por cada next.
			if (this.ultimoDevuelto == null) {
				throw new IllegalStateException("El método sólo puede llamarse una vez por cada invocación de next.");
			}
			
			// Si el elemento es el último.
			if (this.siguienteADevolver == null) {
				if (this.anteriorDevuelto != null) {
					// Lo eliminamos del registro del elemento anterior.
					this.anteriorDevuelto.setSiguiente(null);
				}
				// Si, además de ser el último, es el primero.
				else {
					// Colocamos primero y último en la lista a null.
					ListaEnlazada.this.primero = ListaEnlazada.this.ultimo = null;
				}
				// Pasamos último devuelto a null.
				this.ultimoDevuelto = null;
				// Reducimos el índice en 1.
				this.objetosDevueltos--;
				// Reducimos el tamaño de la lista en 1.
				ListaEnlazada.this.numElementos--;
			}
			// Si el elemento no es el último.
			else {
				if (this.anteriorDevuelto != null) {
					// Hacemos que su elemento anterior apunte al elemento siguiente.
					this.anteriorDevuelto.setSiguiente(this.siguienteADevolver);
				}
				// Si el elemento es el primero.
				else {
					// Hacemos que el primer elemento de la lista sea su elemento siguiente.
					ListaEnlazada.this.primero = this.siguienteADevolver;
				}
				// Pasamos último devuelto a null.
				this.ultimoDevuelto = null;
				// Reducimos el índice en 1.
				this.objetosDevueltos--;
				// Reducimos el tamaño de la lista en 1.
				ListaEnlazada.this.numElementos--;
			}
			
		}
		
	}
	
}
