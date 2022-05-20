import java.util.ArrayList;
import java.util.HashMap;
/**
 * Ultima modificacion: 19/05/2022
 * 
 * Clase Matriz 
 * @file Grafo.java
 */

class Matriz {
	private ArrayList<ArrayList<Float>> valores;
	private int tam = 0;
 
	/**
	 * Establece un valor
	 * @param i 
	 * @param j 
	 * @param e 
	 */
	public void set(int i, int j, float e) {
		i--;
		j--;
		ArrayList<Float> temp = valores.get(i);
		temp.set(j, e);
		valores.set(i, temp);
	}
	
	/**
	 * Obtiene un valor
	 * @param i 
	 * @param j 
	 * @return 
	 */
	public Float get(int i, int j) {
		i--;
		j--;
		return valores.get(i).get(j);
	}

	/**
	 * Constructor de la matriz (zero = false)
	 * @param size tamanio
	 * @param zero true o false
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
			valores.add(temp);
		}
		this.tam = size;
	}
	
	/**
	 * Añade una columna y fila a la matriz
	 * Se mueve hacia arriba
	 */
	public void up() {
		tam = tam + 1;
		for (ArrayList<Float> arr : valores) {
			arr.add(INF);
		}
		ArrayList<Float> temp = new ArrayList<Float>();
		for (int i = 0; i < tam; i++) {
			temp.add(INF);
		}
		valores.add(temp);
	}
	
	/**
	 * Genera un array de valores max
	 * @return array con valores max
	 */
	public ArrayList<Float> max() {
		ArrayList<Float> maxArr = new ArrayList<Float>();
		for (int i=0; i<tam; i++) {
			float max = nINF;
			for (ArrayList<Float> arr : valores) {
				if (max < arr.get(i)) {
					max = arr.get(i);
				}
			}
			maxArr.add(max);
		}
		return maxArr;
	}
	
	/**
	 * Constructor de la matriz
	 */
	Matriz(){
		valores = new ArrayList<ArrayList<Float>>();
	}

	/**
	 * Genera el indice del max
	 * @return índice
	 */
	public int min() {
		ArrayList<Float> maxArr = max();
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
		Matriz temp = new Matriz(tam, false);
		for (int i=1; i<=tam; i++) {
			for (int j=1; j<=tam; j++) {
				temp.set(i,  j, get(i, j));
			}
		}
		return temp;
	}
	
	/**
	 * Elimina una fila y columna
	 * @param i a eliminar
	 */
	public void deleteRowCol(int i) {
		valores.remove(i);
		for (ArrayList<Float> arr : valores) {
			arr.remove(i);
		}
	}

    final float INF = Float.POSITIVE_INFINITY;
	final float nINF = Float.NEGATIVE_INFINITY;
}
/**
 * Ultima modificacion: 19/05/2022
 * 
 * Clase Grafo 
 * @file Grafo.java
 */
public class Grafo {
	private Matriz matr;
	private HashMap<String, Integer> nombre_to_id = new HashMap<>();
	private HashMap<Integer, String> id_to_nombre = new HashMap<>();

	/**
	 * Constructor grafo
	 */
	Grafo(){
		matr = new Matriz();
	}

	/**
	 * Agrega una arista
	 * @param from desde donde
	 * @param to hacia donde
	 * @param dist distancia
	 */
	public void addEdge(String origen, String destino, float dist) {
		addNode(origen);
		addNode(destino);
		int from = nombre_to_id.get(origen);
		int to = nombre_to_id.get(destino);
		if (matr.get(from, to) > dist) {
			matr.set(from, to, dist);
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
		return matr.get(nombre_to_id.get(origen), nombre_to_id.get(destino));
	}
	
	/**
	 * Agrega un nodo
	 * @param valor
	 */
	public void addNode(String nombre) {
		if (!nombre_to_id.containsKey(nombre)) {
			size++;
			nombre_to_id.put(nombre, size);
			id_to_nombre.put(size, nombre);
			matr.up();
			addEdge(nombre, nombre, 0);
			modified = true;
		}
	}

	/**
	 * Algo floyd
	 * @return matriz de costo y matriz de rutas
	 */
	public void algFloyd() {
		cost = matr.copy();
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
	public String centro() {
		if (modified) {
			algFloyd();
		}
		int id = cost.min();
		String centr = id_to_nombre.get(id);
		if (centr == null) {
			return "No encontrado";
		}
		return centr;
	}
	
	/**
	 * Indica rutas más cortas
	 * @param from desde donde
	 * @param to hacia donde
	 * @return ruta más corta
	 */
	public String corto(String origen, String destino) {
		int from = nombre_to_id.get(origen);
		int to = nombre_to_id.get(destino);
		
		if (modified) {
			algFloyd();
		}
		if (cost.get(from, to) == cost.INF) {
			return "No existe entre " + origen + " y " + destino;
		}
		String txt = "Distancia: " + cost.get(from, to).toString() + "\n";
		return txt + ruta(from, to, origen + "->") + destino;
	}	
	private Matriz cost;
	private Matriz paths;
	/**
	 * Reconstruye la ruta más corta
	 * @param i origen
	 * @param j destino
	 * @param txt
	 * @return ruta
	 */
	private String ruta(int i, int j, String txt) {
		if (paths.get(i, j) != 0) {
			txt = ruta(i, (int) paths.get(i, j).floatValue(), txt);
			txt += id_to_nombre.get((int) paths.get(i, j).floatValue()) + "->";
			txt = ruta((int) paths.get(i, j).floatValue(), j, txt);
			return txt;
		}
		return txt;
	}
	
	/**
	 * Elimina un nodo
	 * @param valor
	 */
	public void deleteNode(String nombre) {
		int id = nombre_to_id.get(nombre);
		matr.deleteRowCol(id);
		size--;
	}
	
	/**
	 * Elimina una arista
	 * @param origen
	 * @param destino
	 */
	public void deleteEdge(String origen, String destino) {
		int from = nombre_to_id.get(origen);
		int to = nombre_to_id.get(destino);
		matr.set(from, to, matr.INF);
	}
	
	/**
	 * Pasa el grafo a string
	 */
	public String toString() {
		String txt = "";
		for (int i=1; i<size+1; i++) {
			txt += id_to_nombre.get(i) + ", ";
		}
		return txt.substring(0, txt.length() - 2) + "\n" + matr.toString();
	}
	
	private int size = 0;
	private boolean modified = false;

	/**
	 * Regresa las rutas
	 * @return matriz
	 */
	public Matriz getRutas() {
		return paths;
	}
	
	/**
	 * Regresa los costos
	 * @return matriz
	 */
	public Matriz getCost() {
		return cost;
	}
}


