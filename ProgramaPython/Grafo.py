"""
Programa implementando grafos
Algoritmos y estructuras de datos
Programado por: Montserrat Gil
Fecha 19/05/2022
"""

import networkx as nx
G = nx.DiGraph()

with open("guategrafo.txt") as file_object:
    for line in file_object:
        lista = line.split()
        G.add_edge(lista[0],lista[1],weight=int(lista[2]))

fin = False
while not fin:
    opcion = input("\nIngrese la opcion a realizar:\n1. Mostrar ruta mas corta de una ciudad a otra\n2. Centro del grafo\n3. Agregar interrupcion de trafico\n4. Establecer nueva conexion\n5. Salir")
    if (opcion=="1"):
        origen = input("Ingresar la ciudad de origen")
        destino = input("Ingresar la ciudad de destino")
        
            
    elif (opcion =="2"):
        regresar=0
    elif (opcion =="3"):
        origen = input("Ingresar la ciudad de origen")
        destino = input("Ingresar la ciudad de destino")
        G.remove_edge(origen,destino)
        print("\nAccion realizada")
    elif (opcion =="4"):
        origen = input("Ingresar la ciudad de origen")
        destino = input("Ingresar la ciudad de destino")
        distancia = int(input("Ingresar la ciudad distancia"))
        G.add_edge(origen,destino,distancia)
        
        print("\nAccion realizada")
    elif (opcion =="5"):
        print("Saliendo del programa")
        fin = True
    else:
        print("Seleccionar opcion valida")