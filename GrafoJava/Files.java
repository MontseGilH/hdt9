import java.io.File;
import java.util.Scanner;
/**
 * Ultima modificacion: 19/05/2022
 * 
 * Clase archivos que controla los archivos
 * @file Archivos.java
 * @author Montserrat Gil
 */

public class Files {
     /**
     * Metodo que obtiene los pacientes de un archivo txt 
     * Utiliza un vectorHeap 
     * @param archivo direccion del archivo
     * @return pac lista de pacientes
     * @throws Exception
     */
    public Grafo ciudades(String archivo) throws Exception{
        Grafo pac =  new Grafo();
        try{
            File file = new File(archivo);
            Scanner s = new Scanner(file);
            while (s.hasNextLine()){
               String[] items = s.nextLine().split(" ");
               pac.addEdge(items[0],items[1],Float.valueOf(items[2]));
            }
            s.close();
        } catch (Exception e){
            throw new Exception ("Error al leer el archivo");
        }
        return pac;
      }
}
