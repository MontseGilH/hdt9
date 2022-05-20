/**
 * Ultima modificacion: 19/05/2022
 * 
 * Clase test de los grafos 
 * @file TestGrafo.java
 * @author Montserrat Gil
 */
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestGrafo {
	/**
	 * Evalua crear un grafo, agregar y eliminar nodos y agregar y eliminar relaciones
	 */
	@Test
	void testCrearGrafo()  {
		Grafo graf = new Grafo();
		graf.addNode("prueba1");
		graf.addNode("prueba2");
		graf.addNode("prueba3");
		graf.addEdge("prueba1", "prueba2", 3);
		graf.addEdge("prueba1", "prueba3", 6);
		graf.addEdge("prueba3", "prueba2", 8);
		graf.addEdge("prueba2", "prueba1", 1);
		assertEquals(graf.getEdge("prueba3", "prueba2"),8.0);
		graf.deleteEdge("prueba3", "prueba2");
		assertEquals(graf.corto("prueba3", "prueba2"),"No existe");
	}
	
	/**
	 * Evalua crear un grafo, agregar y eliminar relaciones
	 */
	@Test
	void testFloyd()  {
		Grafo graf = new Grafo();
		graf.addNode("prueba1");
		graf.addNode("prueba2");
		graf.addNode("prueba3");
		graf.addEdge("prueba1", "prueba2", 3);
		graf.addEdge("prueba1", "prueba3", 6);
		graf.addEdge("prueba3", "prueba2", 8);
		graf.addEdge("prueba2", "prueba1", 1);
		assertEquals(graf.centro(),"prueba3");
	}
	
	
}
