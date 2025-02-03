import com.mysql.cj.xdevapi.SessionFactory;
import com.virreyFlix.HibernateUtil;
import com.virreyFlix.model.*;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.lang.module.Configuration;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class Ejercicios {
    Session session = HibernateUtil.getSessionFactory().openSession();

    @Test
    public void ejercicio1() {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n --Menu VirreyFlix ---");
            System.out.println("Seleccione una opcion: ");
            System.out.println("1. Gestionar Usuarios");
            System.out.println("2. Gestionar Perfiles");
            System.out.println("3. Gestionar Series");
            System.out.println("4. Gestionar Episodios");
            System.out.println("5. Gestionar Historial de Reproduccion");
            System.out.println("6. Consultas Avanzadas");
            System.out.println("7. Salir");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> gestionarUsuarios(sc);
                case 2 -> gestionarPerfiles(sc);
                case 3 -> gestionarSeries(sc);
                case 4 -> gestionarEpisodios(sc);
                case 5 -> gestionarHistorial(sc);
                case 6 -> consultasAvanzadas(sc);
                case 7 -> {
                    session.close();
                    System.exit(0);
                }
                default -> System.out.println("Opción inválida.");
            }
        }

    }

    private void consultasAvanzadas(Scanner scanner) {
        System.out.println("\n--- CONSULTAS  ---");
        System.out.println("1. Mostrar todos los usuarios con sus perfiles");
        System.out.println("2. Mostrar todas las series");
        System.out.println("3. Mostrar series según edad");
        System.out.println("4. Mostrar capitulos de una serie");
        System.out.println("5. Mostrar capítulos vistos por un usuario");
        System.out.println("6. Añadir todos los capitulos de una serie al historial de un usuario");
        System.out.println("7. Mostrar series por género con duración media de capítulos");
        System.out.println("8. Mostrar las 5 series más vistas");
        System.out.println("9. Volver al Menú Principal");
        System.out.print("Seleccione una opción: ");


        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1 -> {
                mostrarUsuariosConPerfiles();
            }
            case 2 -> {
                mostrarTodasLasSeries();
            }
            case 3 -> {
                mostrarSeriesPorEdad(scanner);
            }
            case 4 -> {
                mostrarCapitulosDeSerie(scanner);
            }
            case 5 -> {
                mostrarCapitulosVistosPorUsuario(scanner);
            }
            case 6 -> {
                añadirSerieAlHistorial(scanner);
            }
            case 7 -> {
                mostrarSeriesPorGenero(scanner);
            }
            case 8 -> {
                mostrarTop5SeriesMasVistas();
            }
            case 9 -> {
                return;
            }

            default -> System.out.println("Opcion Invalida");
        }
    }
        private void mostrarUsuariosConPerfiles(){
            Session session = HibernateUtil.getSessionFactory().openSession();
            List<Usuario> usuarios = session.createQuery("FROM Usuario", Usuario.class).list();
            for (Usuario usuario : usuarios) {
                Perfil perfil = usuario.getPerfil();
                System.out.println("Usuario: " + usuario.getNombre() + ", Email: " + usuario.getEmail() + ", Perfil: " + (perfil != null ? perfil.getNombre() : "Sin perfil"));
            }
            session.close();
        }

        private void mostrarTodasLasSeries(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Serie> serie = session.createQuery("FROM Serie",Serie.class).list();

        for (Serie series : serie){
            System.out.println("Serie: "+series.getNombre()+ " Genero: "+series.getGenero() +" Clasificacion: "+series.getClasificacion());
        }
        session.close();
        }

        public void mostrarSeriesPorEdad(Scanner scanner){

            System.out.println("Ingrese la edad de la serie: ");
           int edad =  scanner.nextInt();
            Session session = HibernateUtil.getSessionFactory().openSession();
            List<Serie> serie = session.createQuery("FROM Serie WHERE edad = :edad",Serie.class).setParameter("edad",edad).list();

            for (Serie series : serie){
                System.out.println("Serie: "+series.getNombre()+ " Genero: "+series.getGenero() +" Clasificacion: "+series.getClasificacion());
            }
            session.close();
        }
        private void mostrarCapitulosDeSerie(Scanner scanner){
            System.out.println("Ingrese el ID de la serie: ");
            int id = scanner.nextInt();

            Session session = HibernateUtil.getSessionFactory().openSession();
            List<Episodios> episodios = session.createQuery("FROM Serie WHERE id = :id",Episodios.class).setParameter("id",id).list();

            for(Episodios epi :  episodios){
                System.out.println("Nombre : "+epi.getSerie()+ " Capitulos: "+epi.getTitulo() +" Duracion: "+epi.getDuracion());
            }
            session.close();
        }

        private void mostrarCapitulosVistosPorUsuario(Scanner scanner){
        System.out.println("Ingrese el ID del usuario: ");
            int id = scanner.nextInt();
            Session session = HibernateUtil.getSessionFactory().openSession();
            List<Historial> historial = session.createQuery("FROM Historial WHERE perfil_id = :id",Historial.class).setParameter("id",id).list();

            for (Historial hist : historial){
                Episodios epi = hist.getEpisodio();
                Serie serie = epi.getSerie();
                System.out.println("Serie: "+serie.getNombre()+ " Capitulo: "+epi.getTitulo() +" Duracion: "+epi.getDuracion()+ "Fecha: "+hist.getFechaReproduccion());

            }
        }

        private void añadirSerieAlHistorial(Scanner scanner){
            System.out.println("Ingrese el ID del Perfil: ");
            int idPerfil = scanner.nextInt();
            System.out.println("Ingrese el ID de la Serie: ");
            int idSerie = scanner.nextInt();

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction(); //Incio una nueva transaccion en Hibernate para poder insertar o modificar registros de la base de datos
            //para finalizarla hay que cerrarla con un commit

        }



    public void gestionarUsuarios(Scanner scanner){

    }
    public void gestionarPerfiles(Scanner scanner){

    }
    public void gestionarSeries(Scanner scanner){

    }
    public void gestionarEpisodios(Scanner scanner){

    }
    public void gestionarHistorial(Scanner scanner){

    }

}
