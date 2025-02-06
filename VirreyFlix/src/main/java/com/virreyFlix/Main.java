package com.virreyFlix;

import com.virreyFlix.model.*;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Gestionar Usuarios");
            System.out.println("2. Gestionar Perfiles");
            System.out.println("3. Gestionar Series");
            System.out.println("4. Gestionar Episodios");
            System.out.println("5. Gestionar Historial de Reproducción");
            System.out.println("6. Consultas Avanzadas");
            System.out.println("7. Gestionar Géneros");
            System.out.println("8. Salir");

            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> gestionarUsuarios(sc);
                case 2 -> gestionarPerfiles(sc);
                case 3 -> gestionarSeries(sc);
                case 4 -> gestionarEpisodios(sc);
                case 5 -> gestionarHistorial(sc);
                case 6 -> consultasAvanzadas(sc);
                case 7 -> gestionarGeneros(sc);
                case 8 -> System.exit(0);
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    public static void gestionarUsuarios(Scanner scanner) {
        while (true) {
            System.out.println("\n--- GESTIONAR USUARIOS ---");
            System.out.println("1. Insertar Usuario");
            System.out.println("2. Modificar Usuario");
            System.out.println("3. Eliminar Usuario");
            System.out.println("4. Mostrar Usuarios");
            System.out.println("5. Volver al Menú Principal");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> InsertarUsuario(scanner);
                case 2 -> modificarUsuario(scanner);
                case 3 -> eliminarUsuario(scanner);
                case 4 -> mostrarUsuarios();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Opcion no correcta.");
            }
        }
    }

    private static void InsertarUsuario(Scanner scanner) {
        System.out.print("Ingresa el nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingresa el correo: ");
        String correo = scanner.nextLine();

        Usuario usuario = new Usuario(nombre, correo);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(usuario);
        session.getTransaction().commit();
        session.close();

        System.out.println("Usuario agregado");
    }

    private static void modificarUsuario(Scanner scanner) {
        System.out.print("Ingresa el ID del usuario para modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Usuario usuario = session.get(Usuario.class, id);

        if (usuario != null) {
            System.out.print("Ingresa el nuevo nombre del usuario: ");
            usuario.setNombre(scanner.nextLine());
            System.out.print("Ingresa el nuevo correo electronico del usuario: ");
            usuario.setEmail(scanner.nextLine());

            session.beginTransaction();
            session.update(usuario);
            session.getTransaction().commit();
            System.out.println("Usuario modificado ");
        } else {
            System.out.println("Usuario no encontrado");
        }
        session.close();
    }

    private static void eliminarUsuario(Scanner scanner) {
        System.out.print("Ingrese el ID del usuario que desea eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Usuario usuario = session.get(Usuario.class, id);

        if (usuario != null) {
            session.beginTransaction();
            session.delete(usuario);
            session.getTransaction().commit();
            System.out.println("Usuario eliminado");
        } else {
            System.out.println("Usuario no encontrado");
        }
        session.close();
    }

    private static void mostrarUsuarios() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Usuario> usuarios = session.createQuery("FROM Usuario", Usuario.class).list();

        if (!usuarios.isEmpty()) {
            for (Usuario usuario : usuarios) {
                System.out.println("ID: " + usuario.getId() + ", Nombre: " + usuario.getNombre() + ", Correo: " + usuario.getEmail());
            }
        } else {
            System.out.println("No hay usuarios registrados.");
        }
        session.close();
    }

    public static void gestionarGeneros(Scanner scanner) {
        while (true) {
            System.out.println("\n--- GESTIONAR GENEROS ---");
            System.out.println("1. Insertar Genero");
            System.out.println("2. Modificar Genero");
            System.out.println("3. Eliminar Genero");
            System.out.println("4. Mostrar Generos");
            System.out.println("5. Volver al Menu Principal");

            int opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 1 -> insertarGenero(scanner);
                case 2 -> modificarGenero(scanner);
                case 3 -> eliminarGenero(scanner);
                case 4 -> mostrarGeneros();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void insertarGenero(Scanner scanner) {
        System.out.print("Ingresa el nombre del genero: ");
        String nombre = scanner.nextLine();

        Genero genero = new Genero(nombre);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(genero);
        session.getTransaction().commit();
        session.close();

        System.out.println("Genero insertado .");
    }

    private static void modificarGenero(Scanner scanner) {
        System.out.print("Ingresa el ID del genero para modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Genero genero = session.get(Genero.class, id);

        if (genero != null) {
            System.out.print("Ingrese el nuevo nombre del genero: ");
            genero.setNombre(scanner.nextLine());

            session.beginTransaction();
            session.update(genero);
            session.getTransaction().commit();
            System.out.println("Genero modificado");
        } else {
            System.out.println("Genero no encontrado");
        }
        session.close();
    }

    private static void eliminarGenero(Scanner scanner) {
        System.out.print("Ingresa el ID del genero para eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Genero genero = session.get(Genero.class, id);

        if (genero != null) {
            session.beginTransaction();
            session.delete(genero);
            session.getTransaction().commit();
            System.out.println("Genero eliminado");
        } else {
            System.out.println("Genero no encontrado");
        }
        session.close();
    }

    private static void mostrarGeneros() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Genero> generos = session.createQuery("FROM Genero", Genero.class).list();

        if (!generos.isEmpty()) {
            for (Genero genero : generos) {
                System.out.println("ID: " + genero.getId() + ", Nombre: " + genero.getNombre());
            }
        } else {
            System.out.println("No hay generos registrados");
        }
        session.close();
    }


    private static void gestionarPerfiles(Scanner scanner) {

        while (true) {
            System.out.println("\n--- GESTIONAR Perfil ---");
            System.out.println("1. Insertar Perfil");
            System.out.println("2. Modificar Perfil");
            System.out.println("3. Eliminar Perfil");
            System.out.println("4. Mostrar Perfil");
            System.out.println("5. Volver al Menu Principal");

            int opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 1 -> insertarPerfil(scanner);
                case 2 -> modificarPerfil(scanner);
                case 3 -> eliminarPerfil(scanner);
                case 4 -> mostrarPerfiles();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private static void insertarPerfil(Scanner scanner) {
        System.out.print("Ingrese el nombre del perfil: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese el ID del usuario al que pertenece este perfil: ");
        int usuarioId = scanner.nextInt();
        scanner.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Usuario usuario = session.get(Usuario.class, usuarioId);

        if (usuario != null) {
            Perfil perfil = new Perfil();
            session.beginTransaction();
            session.save(perfil);
            session.getTransaction().commit();
            System.out.println("Perfil agregado");
        } else {
            System.out.println("Usuario no encontrado");
        }
        session.close();
    }

    private static void modificarPerfil(Scanner scanner) {
        System.out.print("Ingresa el ID del perfil a modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Perfil perfil = session.get(Perfil.class, id);

        if (perfil != null) {
            System.out.print("Ingresa el nuevo nombre del perfil: ");
            perfil.setNombre(scanner.nextLine());

            session.beginTransaction();
            session.update(perfil);
            session.getTransaction().commit();
            System.out.println("Perfil modificado ");
        } else {
            System.out.println("Perfil no encontrado");
        }
        session.close();
    }

    private static void eliminarPerfil(Scanner scanner) {
        System.out.print("Ingresa el ID del perfil a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Perfil perfil = session.get(Perfil.class, id);

        if (perfil != null) {
            session.beginTransaction();
            session.delete(perfil);
            session.getTransaction().commit();
            System.out.println("Perfil eliminado");
        } else {
            System.out.println("Perfil no encontrado");
        }
        session.close();
    }

    private static void mostrarPerfiles() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Perfil> perfiles = session.createQuery("FROM Perfil", Perfil.class).list();

        if (!perfiles.isEmpty()) {
            for (Perfil perfil : perfiles) {
                System.out.println("Nombre: " + perfil.getNombre() + ",Usuario: " + perfil.getU().getNombre() +" ,Edad" +perfil.getEdad());
            }
        } else {
            System.out.println("No hay perfiles registrados");
        }
        session.close();

    }

    public static void gestionarSeries(Scanner scanner) {
        while (true) {
            System.out.println("\n--- GESTIONAR SERIES ---");
            System.out.println("1. Insertar Serie");
            System.out.println("2. Modificar Serie");
            System.out.println("3. Eliminar Serie");
            System.out.println("4. Mostrar Series");
            System.out.println("5. Volver al Menú Principal");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> insertarSerie(scanner);
                case 2 -> modificarSerie(scanner);
                case 3 -> eliminarSerie(scanner);
                case 4 -> mostrarSeries();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void insertarSerie(Scanner scanner) {
        System.out.print("Ingresa el título de la serie: ");
        String titulo = scanner.nextLine();

        System.out.println("Elige el genero de la serie:");
        List<Genero> generos = obtenerGeneros();
        for (int i = 0; i < generos.size(); i++) {
            System.out.println(i + 1 + ". " + generos.get(i).getNombre());
        }
        int generoOpcion = scanner.nextInt() - 1;
        scanner.nextLine();
        Genero genero = generos.get(generoOpcion);

        System.out.print("Ingresa la calificación de edad de la serie: ");
        int calificacionEdad = scanner.nextInt();
        scanner.nextLine();


        Serie serie = new Serie(calificacionEdad, genero, null, titulo);


        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(serie);
        session.getTransaction().commit();
        session.close();

        System.out.println("Serie agregada ");
    }

    private static void modificarSerie(Scanner scanner) {
        System.out.print("Ingresa el ID de la serie que deseas modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Serie serie = session.get(Serie.class, id);

        if (serie != null) {
            System.out.print("Ingresa el nuevo título de la serie: ");
            serie.setTitulo(scanner.nextLine());

            System.out.println("Elige el nuevo género de la serie:");
            List<Genero> generos = obtenerGeneros();
            for (int i = 0; i < generos.size(); i++) {
                System.out.println(i + 1 + ". " + generos.get(i).getNombre());
            }
            int generoOpcion = scanner.nextInt() - 1;
            scanner.nextLine();
            Genero genero = generos.get(generoOpcion);

            serie.setGenero(genero);

            System.out.print("Ingresa la nueva calificación de edad de la serie: ");
            serie.setCalificacionEdad(scanner.nextInt());
            scanner.nextLine();

            session.beginTransaction();
            session.update(serie);
            session.getTransaction().commit();
            System.out.println("Serie modificada ");
        } else {
            System.out.println("Serie no encontrada");
        }
        session.close();
    }

    private static void eliminarSerie(Scanner scanner) {
        System.out.print("Ingresa el ID de la serie que deseas eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Serie serie = session.get(Serie.class, id);

        if (serie != null) {
            session.beginTransaction();
            session.delete(serie);
            session.getTransaction().commit();
            System.out.println("Serie eliminada");
        } else {
            System.out.println("Serie no encontrada");
        }
        session.close();
    }

    private static void mostrarSeries() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Serie> series = session.createQuery("FROM Serie", Serie.class).list();

        if (!series.isEmpty()) {
            for (Serie serie : series) {
                System.out.println("ID: " + serie.getId() + " ,Título: " + serie.getTitulo() +
                        " ,Genero: " + serie.getGenero().getNombre() + " ,Calificacion Edad: " + serie.getCalificacionEdad());
            }
        } else {
            System.out.println("No hay series registradas");
        }
        session.close();
    }

    private static List<Genero> obtenerGeneros() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Genero> generos = session.createQuery("FROM Genero", Genero.class).list();
        session.close();
        return generos;
    }



    private static void gestionarEpisodios(Scanner scanner) {
        while (true) {
            System.out.println("\n--- GESTIONAR EPISODIOS ---");
            System.out.println("1. Insertar Episodio");
            System.out.println("2. Modificar Episodio");
            System.out.println("3. Eliminar Episodio");
            System.out.println("4. Mostrar Episodios");
            System.out.println("5. Volver al Menú Principal");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> insertarEpisodio(scanner);
                case 2 -> modificarEpisodio(scanner);
                case 3 -> eliminarEpisodio(scanner);
                case 4 -> mostrarEpisodios();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void insertarEpisodio(Scanner scanner) {
        System.out.print("Ingresa el título del episodio: ");
        String titulo = scanner.nextLine();

        System.out.print("Ingresa la duración del episodio (en minutos): ");
        int duracion = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Selecciona la serie a la que pertenece el episodio:");
        List<Serie> series = obtenerSeries();
        if (series.isEmpty()) {
            System.out.println("No hay series disponibles. Crea una serie antes de agregar episodios.");
            return;
        }
        for (int i = 0; i < series.size(); i++) {
            System.out.println((i + 1) + ". " + series.get(i).getTitulo());
        }
        int serieOpcion = scanner.nextInt() - 1;
        scanner.nextLine();
        Serie serie = series.get(serieOpcion);


        Episodio episodio = new Episodio(titulo, duracion);
        episodio.setSerie(serie);


        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(episodio);
        session.getTransaction().commit();
        session.close();

        System.out.println("Episodio agregado exitosamente.");
    }

    private static void modificarEpisodio(Scanner scanner) {
        System.out.print("Ingresa el ID del episodio que deseas modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Episodio episodio = session.get(Episodio.class, id);

        if (episodio != null) {
            System.out.print("Ingresa el nuevo título del episodio: ");
            episodio.setTitulo(scanner.nextLine());

            System.out.print("Ingresa la nueva duración del episodio (en minutos): ");
            episodio.setDuracion(scanner.nextInt());
            scanner.nextLine();

            session.beginTransaction();
            session.update(episodio);
            session.getTransaction().commit();
            System.out.println("Episodio modificado ");
        } else {
            System.out.println("Episodio no encontrado");
        }
        session.close();
    }

    private static void eliminarEpisodio(Scanner scanner) {
        System.out.print("Ingresa el ID del episodio que deseas eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Episodio episodio = session.get(Episodio.class, id);

        if (episodio != null) {
            session.beginTransaction();
            session.delete(episodio);
            session.getTransaction().commit();
            System.out.println("Episodio eliminado");
        } else {
            System.out.println("Episodio no encontrado.");
        }
        session.close();
    }

    private static void mostrarEpisodios() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Episodio> episodios = session.createQuery("FROM Episodio", Episodio.class).list();

        if (!episodios.isEmpty()) {
            for (Episodio episodio : episodios) {
                System.out.println("ID: " + episodio.getId() +
                        ", Título: " + episodio.getTitulo() +
                        ", Duración: " + episodio.getDuracion() + " min" +
                        ", Serie: " + episodio.getSerie().getTitulo());
            }
        } else {
            System.out.println("No hay episodios registrados");
        }
        session.close();
    }

    private static List<Serie> obtenerSeries() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Serie> series = session.createQuery("FROM Serie", Serie.class).list();
        session.close();
        return series;
    }

    private static void gestionarHistorial(Scanner scanner) {
        while (true) {
            System.out.println("\n--- GESTIONAR HISTORIAL  ---");
            System.out.println("1. Insertar Registro");
            System.out.println("2. Modificar Registro");
            System.out.println("3. Eliminar Registro");
            System.out.println("4. Mostrar Historial");
            System.out.println("5. Volver al Menú Principal");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> insertarHistorial(scanner);
                case 2 -> modificarHistorial(scanner);
                case 3 -> eliminarHistorial(scanner);
                case 4 -> mostrarHistorial();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void insertarHistorial(Scanner scanner) {
        System.out.println("Selecciona el perfil para el historial:");
        List<Perfil> perfiles = obtenerPerfiles();
        if (perfiles.isEmpty()) {
            System.out.println("No hay perfiles disponibles. Crea un perfil antes de agregar al historial.");
            return;
        }
        for (int i = 0; i < perfiles.size(); i++) {
            System.out.println((i + 1) + ". " + perfiles.get(i).getNombre());
        }
        int perfilOpcion = scanner.nextInt() - 1;
        scanner.nextLine();
        Perfil perfil = perfiles.get(perfilOpcion);

        System.out.println("Selecciona el episodio reproducido:");
        List<Episodio> episodios = obtenerEpisodios();
        if (episodios.isEmpty()) {
            System.out.println("No hay episodios disponibles. Crea episodios antes de agregar al historial.");
            return;
        }
        for (int i = 0; i < episodios.size(); i++) {
            System.out.println((i + 1) + ". " + episodios.get(i).getTitulo() + " (Serie: " + episodios.get(i).getSerie().getTitulo() + ")");
        }
        int episodioOpcion = scanner.nextInt() - 1;
        scanner.nextLine();
        Episodio episodio = episodios.get(episodioOpcion);

        Historial historial = new Historial();
        historial.setPerfil(perfil);
        historial.setEpisodio(episodio);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(historial);
        session.getTransaction().commit();
        session.close();

        System.out.println("Registro de reproducción agregado");
    }

    private static void modificarHistorial(Scanner scanner) {
        System.out.print("Ingresa el ID del historial que deseas modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Historial historial = session.get(Historial.class, id);

        if (historial != null) {

            System.out.print("Ingresa la nueva fecha de reproducción (yyyy-MM-dd HH:mm): ");
            String fechaStr = scanner.nextLine();
            LocalDateTime nuevaFecha = LocalDateTime.parse(fechaStr.replace(" ", "T"));
            historial.setFechaReproduccion(nuevaFecha);

            session.beginTransaction();
            session.update(historial);
            session.getTransaction().commit();
            System.out.println("Registro de historial modificado");
        } else {
            System.out.println("Registro de historial no encontrado");
        }
        session.close();
    }

    private static void eliminarHistorial(Scanner scanner) {
        System.out.print("Ingresa el ID del historial que deseas eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Historial historial = session.get(Historial.class, id);

        if (historial != null) {
            session.beginTransaction();
            session.delete(historial);
            session.getTransaction().commit();
            System.out.println("Registro de historial eliminado");
        } else {
            System.out.println("Registro de historial no encontrado");
        }
        session.close();
    }

    private static void mostrarHistorial() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Historial> historialList = session.createQuery("FROM Historial", Historial.class).list();

        if (!historialList.isEmpty()) {
            for (Historial historial : historialList) {
                System.out.println("ID: " + historial.getId() +
                        ", Fecha de Reproducción: " + historial.getFechaReproduccion() +
                        ", Perfil: " + historial.getPerfil().getNombre() +
                        ", Episodio: " + historial.getEpisodio().getTitulo());
            }
        } else {
            System.out.println("No hay registros");
        }
        session.close();
    }


    private static List<Perfil> obtenerPerfiles() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Perfil> perfiles = session.createQuery("FROM Perfil", Perfil.class).list();
        session.close();
        return perfiles;
    }

    private static List<Episodio> obtenerEpisodios() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Episodio> episodios = session.createQuery("FROM Episodio", Episodio.class).list();
        session.close();
        return episodios;
    }


    private static void consultasAvanzadas(Scanner scanner) {
        while (true) {
            System.out.println("\n--- CONSULTAS AVANZADAS ---");
            System.out.println("1. Mostrar todos los usuarios con la información de sus perfiles");
            System.out.println("2. Mostrar todas las series");
            System.out.println("3. Mostrar series por edad introducida");
            System.out.println("4. Mostrar capítulos de una serie por ID");
            System.out.println("5. Mostrar capítulos vistos por usuario");
            System.out.println("6. Añadir todos los capítulos de una serie al historial de un usuario");
            System.out.println("7. Mostrar series por género con duración media");
            System.out.println("8. Mostrar las 5 series más vistas del catálogo");
            System.out.println("9. Volver al Menú Principal");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> mostrarUsuariosConPerfiles();
                case 2 -> mostrarTodasLasSeries();
                case 3 -> mostrarSeriesPorEdad(scanner);
                case 4 -> mostrarCapitulosDeSerie(scanner);
                case 5 -> mostrarCapitulosVistosPorUsuario(scanner);
                case 6 -> anadirCapitulosAlHistorial(scanner);
                case 7 -> mostrarSeriesPorGeneroConDuracionMedia(scanner);
                case 8 -> mostrarTop5SeriesMasVistas();
                case 9 -> {
                    return;
                }
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private static void mostrarUsuariosConPerfiles() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> resultados = session.createQuery(
                "SELECT u.nombre, p.nombre FROM Usuario u JOIN u.perfiles p", Object[].class).list();

        if (!resultados.isEmpty()) {
            resultados.forEach(r -> System.out.println("Usuario: " + r[0] + ", Perfil: " + r[1]));
        } else {
            System.out.println("No se encontraron usuarios con perfiles.");
        }
        session.close();
    }

    private static void mostrarTodasLasSeries() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Serie> series = session.createQuery("FROM Serie", Serie.class).list();

        if (!series.isEmpty()) {
            series.forEach(serie -> System.out.println("Serie: " + serie.getTitulo()));
        } else {
            System.out.println("No hay series registradas.");
        }
        session.close();
    }

    private static void mostrarSeriesPorEdad(Scanner scanner) {
        System.out.print("Ingresa la edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Serie> series = session.createQuery(
                        "FROM Serie s WHERE s.edadMinima <= :edad", Serie.class)
                .setParameter("edad", edad)
                .list();

        if (!series.isEmpty()) {
            series.forEach(serie -> System.out.println("Serie: " + serie.getTitulo()));
        } else {
            System.out.println("No hay series disponibles para esta edad.");
        }
        session.close();
    }

    private static void mostrarCapitulosDeSerie(Scanner scanner) {
        System.out.print("Ingresa el ID de la serie: ");
        int idSerie = scanner.nextInt();
        scanner.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Episodio> episodios = session.createQuery(
                        "FROM Episodio e WHERE e.serie.id = :idSerie", Episodio.class)
                .setParameter("idSerie", idSerie)
                .list();

        if (!episodios.isEmpty()) {
            episodios.forEach(e -> System.out.println("Capítulo: " + e.getTitulo()));
        } else {
            System.out.println("No se encontraron capítulos para esta serie.");
        }
        session.close();
    }

    private static void mostrarCapitulosVistosPorUsuario(Scanner scanner) {
        System.out.print("Ingrese el ID del usuario: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> resultados = session.createQuery(
                        "SELECT e.serie.nombre, e.nombre, e.duracion, h.fechaReproduccion FROM Historial h JOIN h.episodio e WHERE h.perfil.usuario.id = :idUsuario", Object[].class)
                .setParameter("idUsuario", idUsuario)
                .list();

        if (!resultados.isEmpty()) {
            resultados.forEach(r -> System.out.println("Serie: " + r[0] + ", Capítulo: " + r[1] + ", Duración: " + r[2] + ", Fecha: " + r[3]));
        } else {
            System.out.println("No se encontraron capítulos vistos para este usuario.");
        }
        session.close();
    }

    private static void anadirCapitulosAlHistorial(Scanner scanner) {
        System.out.print("Ingrese el ID del usuario: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Ingrese el ID de la serie: ");
        int idSerie = scanner.nextInt();
        scanner.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        List<Episodio> episodios = session.createQuery(
                        "FROM Episodio e WHERE e.serie.id = :idSerie", Episodio.class)
                .setParameter("idSerie", idSerie)
                .list();

        for (Episodio episodio : episodios) {
            Historial historial = session.createQuery(
                            "FROM Historial h WHERE h.perfil.usuario.id = :idUsuario AND h.episodio.id = :idEpisodio", Historial.class)
                    .setParameter("idUsuario", idUsuario)
                    .setParameter("idEpisodio", episodio.getId())
                    .uniqueResultOptional()
                    .orElse(new Historial());

            historial.setPerfil(session.get(Perfil.class, idUsuario));
            historial.setEpisodio(episodio);
            historial.setFechaReproduccion(LocalDate.now().atStartOfDay());

            session.saveOrUpdate(historial);
        }

        session.getTransaction().commit();
        session.close();

        System.out.println("Capítulos añadidos al historial exitosamente.");
    }

    private static void mostrarSeriesPorGeneroConDuracionMedia(Scanner scanner) {
        System.out.print("Ingrese el nombre del género: ");
        String genero = scanner.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> resultados = session.createQuery(
                        "SELECT s.nombre, AVG(e.duracion) FROM Serie s JOIN s.episodios e WHERE s.genero.nombre = :genero GROUP BY s.nombre", Object[].class)
                .setParameter("genero", genero)
                .list();

        if (!resultados.isEmpty()) {
            resultados.forEach(r -> System.out.println("Serie: " + r[0] + ", Duración Media: " + r[1] + " minutos"));
        } else {
            System.out.println("No se encontraron series para este género.");
        }
        session.close();
    }

    private static void mostrarTop5SeriesMasVistas() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> resultados = session.createQuery(
                        "SELECT s.nombre, COUNT(h) FROM Historial h JOIN h.episodio.serie s GROUP BY s.nombre ORDER BY COUNT(h) DESC", Object[].class)
                .setMaxResults(5)
                .list();

        if (!resultados.isEmpty()) {
            resultados.forEach(r -> System.out.println("Serie: " + r[0] + ", Vistas: " + r[1]));
        } else {
            System.out.println("No hay series vistas.");
        }
    }


}
