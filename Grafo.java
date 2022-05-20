import java.util.ArrayList;
import java.util.HashMap;
/**
 * Ultima modificacion: 19/05/2022
 * 
 * Clase grafo 
 * @file Grafo.java
 */

class Matriz {
	private ArrayList<ArrayList<Float>> data;
	private int size = 0;
	
	/**
	 * Constructor de la matriz
	 */
	Matriz(){
		data = new ArrayList<ArrayList<Float>>();
	}

	/**
	 * Establece un valor
	 * @param i fila
	 * @param j columna
	 * @param e valor
	 */
	public void set(int i, int j, float e) {
		i--;
		j--;
		ArrayList<Float> temp = data.get(i);
		temp.set(j, e);
		data.set(i, temp);
	}
	
	/**
	 * Obtiene el valor de algún espacio
	 * @param i fila
	 * @param j columna
	 * @return valor
	 */
	public Float get(int i, int j) {
		i--;
		j--;
		return data.get(i).get(j);
	}

	/**
	 * Constructor de la matriz (zero = false)
	 * @param size
	 * @param zero
	 */
	Matriz(int size, boolean zero) {
		this();
		for (int i = 0; i < size; i++) {
			ArrayList<Float> temp = new ArrayList<Float>();
			for (int j = 0; j < size; j++) {
				if (zero) {
					temp.add(0f);
				}
				else {
					temp.add(INF);
				}
			}
			data.add(temp);
		}
		this.size = size;
	}
	
	/**
	 * Añade una columna y fila a la matriz
	 */
	public void scale_up() {
		size = size + 1;
		for (ArrayList<Float> arr : data) {
			arr.add(INF);
		}
		ArrayList<Float> temp = new ArrayList<Float>();
		for (int i = 0; i < size; i++) {
			temp.add(INF);
		}
		data.add(temp);
	}
	
	/**
	 * Genera un array de valores máximos por columna
	 * @return array
	 */
	private ArrayList<Float> maxArray() {
		ArrayList<Float> maxArr = new ArrayList<Float>();
		for (int i=0; i<size; i++) {
			float max = nINF;
			for (ArrayList<Float> arr : data) {
				if (max < arr.get(i)) {
					max = arr.get(i);
				}
			}
			maxArr.add(max);
		}
		return maxArr;
	}
	
	/**
	 * Genera el índice mínimo de el maxArray
	 * @return índice
	 */
	public int argmin() {
		ArrayList<Float> maxArr = maxArray();
		float min = INF;
		int id = 0;
		for (int i=0; i<maxArr.size(); i++) {
			if (maxArr.get(i) < min) {
				min = maxArr.get(i);
				id = i + 1;
			}
		}
		return id;
	}
	
	/**
	 * Genera una copia
	 * @return copia
	 */
	public Matriz copy() {
		Matriz temp = new Matriz(size, false);
		for (int i=1; i<=size; i++) {
			for (int j=1; j<=size; j++) {
				temp.set(i,  j, get(i, j));
			}
		}
		return temp;
	}
	

	/**
	 * Elimina una fila y columna
	 * @param i fila y columna
	 */
	public void deleteRowCol(int i) {
		data.remove(i);
		for (ArrayList<Float> arr : data) {
			arr.remove(i);
		}
	}
	
	/**
	 * Genera String con la matriz
	 */
	@Override
	public String toString() {
		String txt = "";
		for (ArrayList<Float> arr : data) {
			txt += "[";
			for (Float f : arr) {
				if (f == INF) {
					txt += "INF,\t";
				} else {
					txt += f.toString() + ",\t";
				}
			}
			txt = txt.substring(0, txt.length() - 2);
			txt += "]\n";
		}
		return txt;
	}

    final float INF = Float.POSITIVE_INFINITY;
	final float nINF = Float.NEGATIVE_INFINITY;
}


public class Grafo {

	
	private Matriz matrix;
	private HashMap<String, Integer> nombre_to_id = new HashMap<>();
	private HashMap<Integer, String> id_to_nombre = new HashMap<>();
	private Matriz cost;
	private Matriz paths;
	private int size = 0;
	private boolean modified = false;
	
	/**
	 * Inicializa grafo vacio
	 */
	Grafo(){
		matrix = new Matriz();
	}
	
	/**
	 * Añade un nodo
	 * @param ciudad
	 */
	public void addNode(String nombre) {
		if (!nombre_to_id.containsKey(nombre)) {
			size++;
			nombre_to_id.put(nombre, size);
			id_to_nombre.put(size, nombre);
			matrix.scale_up();
			addEdge(nombre, nombre, 0);
			modified = true;
		}
	}
	
	/**
	 * Añade un arco o arista
	 * @param origen
	 * @param destino
	 * @param dist distancia
	 */
	public void addEdge(String origen, String destino, float dist) {
		addNode(origen);
		addNode(destino);
		int from = nombre_to_id.get(origen);
		int to = nombre_to_id.get(destino);
		if (matrix.get(from, to) > dist) {
			matrix.set(from, to, dist);
			modified = true;
		}
	}
	
	/**
	 * Indica el arco o arista entre dos nodos
	 * @param origen
	 * @param destino
	 * @return arco o arista
	 */
	public float getEdge(String origen, String destino) {
		addNode(origen);
		addNode(destino);
		return matrix.get(nombre_to_id.get(origen), nombre_to_id.get(destino));
	}
	
	/**
	 * Algoritmo de Floyd para rutas más cortas
	 * @return matriz de costo y matriz de rutas
	 */
	public void Floyd() {
		cost = matrix.copy();
		paths = new Matriz(size, true);
		
		for (int k=1; k<=size; k++) {
			for (int i=1; i<=size; i++) {
				for (int j=1; j<=size; j++) {
					if (cost.get(i, j) > cost.get(i, k) + cost.get(k, j)) {
						cost.set(i, j, cost.get(i, k) + cost.get(k, j));
						if (i != j) {
							paths.set(i, j, k);
						}
					}
				}
			}
		}
		modified = false;
	}
	
	/**
	 * Indica el centro del grafo
	 * @return nodo
	 */
	public String centre() {
		if (modified) {
			Floyd();
		}
		int id = cost.argmin();
		String ciudad = id_to_nombre.get(id);
		if (ciudad == null) {
			return "No tiene centro";
		}
		return "Centro: " + ciudad;
	}
	
	/**
	 * Indica rutas más cortas
	 * @param origen
	 * @param destino
	 * @return ruta más corta
	 */
	public String shortestPath(String origen, String destino) {
		int from = nombre_to_id.get(origen);
		int to = nombre_to_id.get(destino);
		
		if (modified) {
			Floyd();
		}
		
		if (cost.get(from, to) == cost.INF) {
			return "No existe camino entre " + origen + " y " + destino;
		}
		
		String txt = "Distancia: " + cost.get(from, to).toString() + "\n";
		
		return txt + path(from, to, origen + "->") + destino;
		
		
	}
	
	/**
	 * Reconstruye la ruta más corta
	 * @param i origen
	 * @param j destino
	 * @param txt
	 * @return ruta
	 */
	private String path(int i, int j, String txt) {
		if (paths.get(i, j) != 0) {
			txt = path(i, (int) paths.get(i, j).floatValue(), txt);
			txt += id_to_nombre.get((int) paths.get(i, j).floatValue()) + "->";
			txt = path((int) paths.get(i, j).floatValue(), j, txt);
			return txt;
		}
		return txt;
	}
	
	/**
	 * Elimina un nodo
	 * @param ciudad
	 */
	public void deleteNode(String nombre) {
		int id = nombre_to_id.get(nombre);
		matrix.deleteRowCol(id);
		size--;
	}
	
	/**
	 * Elimina un arco o arista
	 * @param origen
	 * @param destino
	 */
	public void deleteEdge(String origen, String destino) {
		int from = nombre_to_id.get(origen);
		int to = nombre_to_id.get(destino);
		matrix.set(from, to, matrix.INF);
	}
	
	/**
	 * Genera un texto con el grafo.
	 */
	public String toString() {
		String txt = "";
		for (int i=1; i<size+1; i++) {
			txt += id_to_nombre.get(i) + ", ";
		}
		return txt.substring(0, txt.length() - 2) + "\n" + matrix.toString();
	}
	
	/**
	 * Indica la matriz de rutas
	 * @return matriz
	 */
	public Matriz getPaths() {
		return paths;
	}
	
	/**
	 * Indica la matriz de costo
	 * @return matriz
	 */
	public Matriz getCost() {
		return cost;
	}
}


