
package actividad2sistemagestionbiblioteca;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Actividad2SistemaGestionBiblioteca {

    
    public static void main(String[] args) {
        
        ArrayList<String[]> libros = new ArrayList<>();
        LinkedList<String[]> usuarios = new LinkedList<>();
        Stack<String[]> librosPrestados = new Stack<>();
        Queue<String[]> colaEspera = new LinkedList<>();
        
        Scanner entrada = new Scanner (System.in);
        
        int opcion;
        do{
            System.out.println("=========================================");
            System.out.println("    SISTEMA DE GESTION DE BIBLIOTECAS    ");
            System.out.println("   HECHO POR TANIA MELISSA CALVO GARCES  ");
            System.out.println("=========================================");
            System.out.println("1. Agregar Libro");
            System.out.println("2. Registrar usuario");
            System.out.println("3. Prestar libro");
            System.out.println("4. Devolver libro");
            System.out.println("5. Mostrar libros disponibles");
            System.out.println("6. Mostrar usuarios registrados");
            System.out.println("7. Salir");
            System.out.println("Seleccione una Opcion: ");
            
            while(!entrada.hasNextInt()){
                System.out.println("Error: Ingrese un numero valido!!");
                entrada.next();
                System.out.println("Seleccione una opcion: ");
            }
            
            opcion = entrada.nextInt();
            entrada.nextLine();
            switch(opcion){
                case 1:
                    System.out.println("Ingrese el ID del libro (unico)");
                    String idLibro = entrada.nextLine();
                    boolean idDuplicado= false;
                    for(String[] libro: libros){
                        if(libro[0].equals(idLibro)){
                            idDuplicado = true;
                            break;
                        }
                    }
                    if(idDuplicado){
                        System.out.println("Error: El id del libro ya existe");
                    }else{
                        System.out.println("Ingrese el nombre del libro");
                        String nombreLibro = entrada.nextLine();
                        System.out.println("Ingrese el autor del libro");
                        String autorLibro = entrada.nextLine();
                        libros.add(new String[]{idLibro, nombreLibro, autorLibro});
                        System.out.println("Libro agregado con exito");
                    }
                    
                                        
                    break;
                case 2:
                    System.out.println("Ingrese la cedula del usuario (solo numero): ");
                    
                    while(!entrada.hasNextInt()){
                        System.out.println("Error: Ingrese un numero valido!!");
                        entrada.next();
                        System.out.println("Ingrese la cedula del usuario (solo con numeros) ");
                    }
                    int cedulaUsuario = entrada.nextInt();
                    entrada.nextLine();
                    System.out.println("Ingrese el nombre del usuario: ");
                    String nombreUsuario = entrada.nextLine();
                    System.out.println("Ingrese los apellidos del usuario: ");
                    String apellidosUsuario = entrada.nextLine();
                    
                    boolean cedulaDuplicado= false;
                    for(String[] usuario: usuarios){
                        if(usuario[0].equals(String.valueOf(cedulaUsuario))){
                            cedulaDuplicado = true;
                            break;
                        }
                    }
                    if(cedulaDuplicado){
                        System.out.println("Error: El usuario ya existe");
                    }else{
                        usuarios.add(new String[]{String.valueOf(cedulaUsuario), nombreUsuario, apellidosUsuario});
                        System.out.println("Usuario registrado con exito");
                    }
                    
                    break;
                case 3:
                    System.out.println("Ingrese el Id del libro que desea prestar: ");
                    String idPrestar = entrada.nextLine();
                    System.out.println("Ingrese la cedula del usuario que presta el libro: ");
                    while(!entrada.hasNextInt()){
                        System.out.println("Error: Ingrese un numero valido!!");
                        entrada.next();
                        System.out.println("Ingrese la cedula del usuario (solo con numeros) ");
                    }
                    int cedulaPrestar = entrada.nextInt();
                    entrada.nextLine();
                    
                    boolean usuarioRegistrado = false;
                    for(String[] usuario: usuarios){
                        if(usuario[0].equals(String.valueOf(cedulaPrestar))){
                            usuarioRegistrado = true;
                            break;
                        }
                    }
                    if(!usuarioRegistrado){
                        System.out.println("Error el usuario con cedula "+cedulaPrestar +"No esta registrado, No se puede prestar el libro");
                    }else{                    
                        boolean libroEncontrado= false;
                        for(String[] libro: libros){
                            if(libro[0].equals(idPrestar)){
                                librosPrestados.push(new String[]{idPrestar,libro[1], libro[2],String.valueOf(cedulaPrestar)});
                                libros.remove(libro);
                                libroEncontrado = true;
                                System.out.println("Libro prestado con exito");
                                break;
                            }
                        }
                        if(!libroEncontrado){
                            System.out.println("Libro no disponible. desea agregar a la cola de espera? (si/no)");
                            String respuesta = entrada.nextLine();
                            if(respuesta.equalsIgnoreCase("si")){
                                colaEspera.add(new String[]{idPrestar,String.valueOf(cedulaPrestar) });
                                System.out.println("agregado a la cola de espera");
                            }
                        }
                    }
                    break;
                case 4:
                    if(!librosPrestados.isEmpty()){
                        String[] libroDevuelto = librosPrestados.pop();
                        libros.add(new String[]{libroDevuelto[0], libroDevuelto[1], libroDevuelto[2]});
                        System.out.println("Libro devuelto exitosamente");                        
                    }
                    if(!colaEspera.isEmpty()){
                        String[] proximosEnCola = colaEspera.poll();
                        System.out.println("El usuario con cedula"+proximosEnCola[1] + "esta en cola y ahora prestara el libro con ID : "+ proximosEnCola[0]);
                        librosPrestados.push(proximosEnCola);
                    }else{
                        System.out.println("No hay libros prestados");
                    }
                    
                    break;
                case 5:
                    if(libros.isEmpty()){
                        System.out.println("No hay libros disponibles");
                    }else{
                        System.out.println(" ----  Libros Disponibles  ----");
                        System.out.printf("%-10s %-30s %-20s%n", "ID", "Nombre", "Autor");
                        for(String[] libro: libros){
                            System.out.printf("%-10s %-30s %-20s%n", libro[0], libro[1], libro[2]);
                        }
                    }
                    break;
                case 6:
                    if(usuarios.isEmpty()){
                        System.out.println("No hay usuarios disponibles");
                    }else{
                        System.out.println(" ----  Usuarios Disponibles  ----");
                        System.out.printf("%-10s %-15s %-20s%n", "Cedula", "Nombre", "Apellidos");
                        for(String[] usuario: usuarios){
                            System.out.printf("%-10s %-15s %-20s%n", usuario[0], usuario[1], usuario[2]);
                        }
                    }
                    break;
                case 7:
                    System.out.println("Vuelva pronto");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo");
                    break;
                case 8:
                    if (librosPrestados.isEmpty()) {
                        System.out.println("No hay historial de préstamos.");
                    } else {
                        System.out.println("---- HISTORIAL DE LIBROS PRESTADOS ----");
                        for (String[] prestado : librosPrestados) {
                            System.out.println("ID: " + prestado[0]
                                    + " | Nombre: " + prestado[1]
                                    + " | Autor: " + prestado[2]
                                    + " | Cédula usuario: " + prestado[3]);
                        }
                    }
                    break;
                case 9:
                    System.out.println("Ingrese el nombre del libro a buscar:");
                    String nombreBuscar = entrada.nextLine();
                    boolean encontrado = false;

                    for (String[] libro : libros) {
                        if (libro[1].equalsIgnoreCase(nombreBuscar)) {
                            System.out.println("Libro encontrado:");
                            System.out.println("ID: " + libro[0] + " | Autor: " + libro[2]);
                            encontrado = true;
                            break;
                        }
                    }

                    if (!encontrado) {
                        System.out.println("El libro no está disponible.");
                    }
                    break;
                case 10:
                    if (librosPrestados.isEmpty()) {
                    System.out.println("No hay libros prestados actualmente.");
                } else {
                    System.out.println("---- LIBROS PRESTADOS ACTUALMENTE ----");
                    for (String[] prestado : librosPrestados) {
                    System.out.println("ID: " + prestado[0]
                                    + " | Nombre: " + prestado[1]
                                    + " | Autor: " + prestado[2]
                                    + " | Cédula usuario: " + prestado[3]);
                        }
                    }
                    break;
                case 11:
                    System.out.println("Ingrese la cédula del usuario a buscar:");

                while (!entrada.hasNextInt()) {
                    System.out.println("Error: Ingrese un número válido:");
                    entrada.next();
                }

                    int cedBuscar = entrada.nextInt();
                    entrada.nextLine();

                    boolean usuarioEncontrado = false;

                    for (String[] usuario : usuarios) {
                    if (usuario[0].equals(String.valueOf(cedBuscar))) {
                    System.out.println("Usuario encontrado:");
                    System.out.println("Cédula: " + usuario[0]
                                    + " | Nombre: " + usuario[1]
                                    + " | Apellidos: " + usuario[2]);
                    usuarioEncontrado = true;
                    break;
                }
            }

                if (!usuarioEncontrado) {
                    System.out.println("No existe un usuario con esa cédula.");
                }
                    break;
            }
        }while (opcion != 7);
               
    }
    
}

