package listaEnlazada;

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
	public boolean add(E e) {

		Nodo<E> nuevo = new Nodo<>(e);
		
		if (this.isEmpty()) {
			this.primero = this.ultimo = nuevo;
		}
		else {
			this.ultimo.setSiguiente(nuevo);
			nuevo.setAnterior(this.ultimo);
			this.ultimo = nuevo;
		}
		
		this.numElementos++;
		
		return true;
	}

	@Override
	public void add(int index, E e) {
		
		checkValidIndex(index);
		
		Nodo<E> newNode = new Nodo<>(e);	
		Nodo<E> currentNode;
		
		if (index < this.numElementos / 2) {
			currentNode = this.primero;
			for (int i = 0; i < index; i++) {
				currentNode.getSiguiente();
			}
		}
		else {
			currentNode = this.ultimo;
			for (int i = this.numElementos - 1; i > index; i--) {
				currentNode.getAnterior();
			}
		}
		
		Nodo<E> tempSig = currentNode;
		Nodo<E> tempAnt = currentNode.getAnterior();
		
		tempSig.setAnterior(newNode);
		tempAnt.setSiguiente(newNode);
		newNode.setSiguiente(tempSig);
		newNode.setAnterior(tempAnt);
		
		this.numElementos++;
		
	}

	@Override
	public boolean addAll(Collection<? extends E> arg0) {
		// TOD Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends E> arg1) {
		// TOD Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TOD Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object arg0) {
		// TOD Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TOD Auto-generated method stub
		return false;
	}

	@Override
	public E get(int index) {
		
		checkValidIndex(index);
		
		Nodo<E> element = null;
		
		/*
		 * Para mejorar la eficiencia se empieza a recorrer desde el principio
		 * o desde el final dependiendo de a qué extremo se acerque más el índice
		 * dado.
		 */
		if (index < this.numElementos / 2) {
			element = this.primero;
			for (int i = 0; i < index; i++) {
				element.getSiguiente();
			}
		}
		else {
			element = this.ultimo;
			for (int i = this.numElementos - 1; i > index; i--) {
				element.getAnterior();
			}
		}
		
		return element.getElemento();
	}

	public void checkValidIndex(int index) {
		if (index < 0 || index >= this.numElementos) {
			throw new IndexOutOfBoundsException("El índice a buscar debe ser válido.");
		}
	}
	
	@Override
	public int indexOf(Object o) {
		Nodo<E> element = this.primero;
		int cnt = 0;
		int index = -1;
		
		// Forma de recorrer con un for.
//		for (Nodo<E> x = this.primero; x != null && indice == -1; x = x.getSiguiente()) {
//			
//		}
		
		while (element != null && index == -1) {
			
			if (o == null && element.getElemento() == null || element.getElemento().equals(o)) {
				index = cnt;
			}
			
			element = element.getSiguiente();
			cnt++;
		}
		
		return index;
	}

	@Override
	public boolean isEmpty() {
		return this.numElementos == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new ListaEnlazadaIterator();
	}

	@Override
	public int lastIndexOf(Object arg0) {
		// TOD Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<E> listIterator() {
		// TOD Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int arg0) {
		// TOD Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object arg0) {
		// TOD Auto-generated method stub
		return false;
	}

	@Override
	public E remove(int arg0) {
		// TOD Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TOD Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TOD Auto-generated method stub
		return false;
	}

	@Override
	public E set(int arg0, E arg1) {
		// TOD Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		return this.numElementos;
	}

	@Override
	public List<E> subList(int arg0, int arg1) {
		// TOD Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TOD Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TOD Auto-generated method stub
		return null;
	}
	
	protected class ListaEnlazadaIterator implements Iterator<E> {

		private Nodo<E> ultimoDevuelto;
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
			
			// Esta comprobación es equivalente a la realizada más abajo.
//			if (this.objetosDevueltos >= ListaEnlazada.this.numElementos) {
//				throw new NoSuchElementException();
//			}
			
			if (this.ultimoDevuelto == null) {
				this.ultimoDevuelto = ListaEnlazada.this.primero;
			}
			else {
				this.ultimoDevuelto = this.ultimoDevuelto.getSiguiente();
			}
			
			if (this.ultimoDevuelto == null) {
				throw new NoSuchElementException();
			}
			
			// Vamos a devolver otro objeto más.
			this.objetosDevueltos++;
			
			return this.ultimoDevuelto.getElemento();
		}
		
	}
	
}
