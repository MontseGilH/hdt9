/**
 * Ultima modificacion: 19/05/2022
 * 
 * Clase Main
 * @file Main.java
 * @author Montserrat Gil
 */
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //obtener grado de archivo
        Grafo graf = new Grafo();
        Files f = new Files();
        Scanner scan = new Scanner(System.in);
        try{
            graf = f.ciudades("guategrafo.txt");
        } catch (Exception e){
            System.out.print(e.getMessage());
        }

        String opcion = "0";

        while (!opcion.equalsIgnoreCase("5")){
            System.out.println("\nIngrese la opcion a realizar:\n1.Mostrar ruta mas corta de una ciudad a otra\n2. Centro del grafo\n3. Agregar interrupcion de trafico\n4. Establecer nueva conexion\n5.Salir");
            opcion = scan.nextLine();
            if (opcion.equalsIgnoreCase("1")){
                //mostrar ruta mas corta de una ciudad a otra
            }else if (opcion.equalsIgnoreCase("2")){
                //centro del grafo
            }else if (opcion.equalsIgnoreCase("3")){
                //interrupcion de transito
            }else if (opcion.equalsIgnoreCase("4")){
                //nueva conexion
            }else if (opcion.equalsIgnoreCase("5")){
                //salir
            } else {
                System.out.println("Ingrese una opcion valida");
            }
        }

        scan.close();
    }
}
