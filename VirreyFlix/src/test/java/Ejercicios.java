import com.mysql.cj.xdevapi.SessionFactory;
import com.virreyFlix.HibernateUtil;
import com.virreyFlix.model.*;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.lang.module.Configuration;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
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

    private void mostrarUsuariosConPerfiles() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Usuario> usuarios = session.createQuery("FROM Usuario", Usuario.class).list();
        for (Usuario usuario : usuarios) {
            Perfil perfil = usuario.getPerfil();
            System.out.println("Usuario: " + usuario.getNombre() + ", Email: " + usuario.getEmail() + ", Perfil: " + (perfil != null ? perfil.getNombre() : "Sin perfil"));
        }
        session.close();
    }

    private void mostrarTodasLasSeries() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Serie> serie = session.createQuery("FROM Serie", Serie.class).list();

        for (Serie series : serie) {
            System.out.println("Serie: " + series.getNombre() + " Genero: " + series.getGenero() + " Clasificacion: " + series.getClasificacion());
        }
        session.close();
    }

    public void mostrarSeriesPorEdad(Scanner scanner) {

        System.out.println("Ingrese la edad de la serie: ");
        int edad = scanner.nextInt();
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Serie> serie = session.createQuery("FROM Serie WHERE edad = :edad", Serie.class).setParameter("edad", edad).list();

        for (Serie series : serie) {
            System.out.println("Serie: " + series.getNombre() + " Genero: " + series.getGenero() + " Clasificacion: " + series.getClasificacion());
        }
        session.close();
    }

    private void mostrarCapitulosDeSerie(Scanner scanner) {
        System.out.println("Ingrese el ID de la serie: ");
        int id = scanner.nextInt();

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Episodios> episodios = session.createQuery("FROM Serie WHERE id = :id", Episodios.class).setParameter("id", id).list();

        for (Episodios epi : episodios) {
            System.out.println("Nombre : " + epi.getSerie() + " Capitulos: " + epi.getTitulo() + " Duracion: " + epi.getDuracion());
        }
        session.close();
    }

    private void mostrarCapitulosVistosPorUsuario(Scanner scanner) {
        System.out.println("Ingrese el ID del usuario: ");
        int id = scanner.nextInt();
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Historial> historial = session.createQuery("FROM Historial WHERE perfil_id = :id", Historial.class).setParameter("id", id).list();

        for (Historial hist : historial) {
            Episodios epi = hist.getEpisodio();
            Serie serie = epi.getSerie();
            System.out.println("Serie: " + serie.getNombre() + " Capitulo: " + epi.getTitulo() + " Duracion: " + epi.getDuracion() + "Fecha: " + hist.getFechaReproduccion());

        }
    }

    private void añadirSerieAlHistorial(Scanner scanner) {
        System.out.println("Ingrese el ID del Perfil: ");
        int idPerfil = scanner.nextInt();
        System.out.println("Ingrese el ID de la Serie: ");
        int idSerie = scanner.nextInt();
        LocalDateTime fechaHoy = LocalDateTime.now();

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction(); //Incio una nueva transaccion en Hibernate para poder insertar o modificar registros de la base de datos
        //para finalizarla hay que cerrarla con un commit


        List<Episodios> episodios = session.createQuery("FROM Episodio WHERE serie.id = :serieId", Episodios.class)
                .setParameter("serieId", idSerie)
                .list();
        for (Episodios episodio : episodios) {
            Historial historialExistente = session.createQuery("FROM Historial WHERE perfil.id = :perfilId AND episodio.id = :episodioId", Historial.class)
                    .setParameter("perfilId", idPerfil)
                    .setParameter("episodioId", episodio.getId())
                    .uniqueResult();

            if (historialExistente != null) {
                historialExistente.setFechaReproduccion(fechaHoy);
                session.update(historialExistente);
            } else {
                Historial nuevoHistorial = new Historial();
                nuevoHistorial.setPerfil(session.get(Perfil.class, idPerfil));
                nuevoHistorial.setEpisodio(episodio);
                nuevoHistorial.setFechaReproduccion(fechaHoy);
                session.save(nuevoHistorial);
            }
        }
        session.getTransaction().commit();
        session.close();
        System.out.println("Historial actualizado");

    }

    private void mostrarSeriesPorGenero(Scanner scanner){
        System.out.println("Ingresa el genero: ");
        String genero = scanner.nextLine();
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> resultados = session.createQuery(
                        "SELECT s.titulo, AVG(e.duracion) FROM Serie s " +
                                "JOIN s.generos g JOIN s.episodios e " +
                                "WHERE g.nombre = :genero GROUP BY s.id", Object[].class)
                .setParameter("genero", genero)
                .list();

        for (Object[] resultado : resultados) {
            System.out.println("Serie: " + resultado[0] + ", Duración Media: " + resultado[1] + " minutos");
        }
        session.close();
    }

    private void mostrarTop5SeriesMasVistas(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> resultados = session.createQuery(
                        "SELECT s.titulo, COUNT(h.id) FROM Serie s " +
                                "JOIN s.episodios e JOIN e.historial h " +
                                "GROUP BY s.id ORDER BY COUNT(h.id) DESC", Object[].class)
                .setMaxResults(5)
                .list();
    }


    public void gestionarUsuarios(Scanner scanner) {
        while (true) {
            System.out.println("\n--- GESTIONAR USUARIOS ---");
            System.out.print("Seleccione una opcion: ");
            System.out.println("1. Agregar Usuario");
            System.out.println("2. Modificar Usuario");
            System.out.println("3. Eliminar Usuario");
            System.out.println("4. Mostrar Usuario");
            System.out.println("5. Volver al Menu Principal");
            scanner.nextLine();


            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1 -> agregarUsuario(scanner);
                case 2 -> modificarUsuario(scanner);
                case 3 -> eliminarUsuario(scanner);
                case 4 -> mostrarUsuario(scanner);
                case 5 -> {
                    return;
                }
                default -> System.out.println("No existe esa opcion");
            }
        }
    }
    private void agregarUsuario(Scanner scanner) {
        System.out.print("Ingrese el nombre del usuario: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el email del usuario: ");
        String email = scanner.nextLine();

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(usuario);
        session.getTransaction().commit();
        session.close();

        System.out.println("Usuario agregado");
    }
    private void modificarUsuario(Scanner scanner) {
        System.out.print("Ingrese el ID del usuario para modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Usuario usuario = session.get(Usuario.class, id);

        if (usuario != null) {
            System.out.print("Ingrese el nuevo nombre: ");
            usuario.setNombre(scanner.nextLine());
            System.out.print("Ingrese el nuevo email: ");
            usuario.setEmail(scanner.nextLine());

            session.beginTransaction();
            session.update(usuario);
            session.getTransaction().commit();
            System.out.println("Usuario modificado");
        } else {
            System.out.println("Usuario no encontrado");
        }
        session.close();
    }


    public void gestionarPerfiles(Scanner scanner) {

    }

    public void gestionarSeries(Scanner scanner) {

    }

    public void gestionarEpisodios(Scanner scanner) {

    }

    public void gestionarHistorial(Scanner scanner) {

    }
}

