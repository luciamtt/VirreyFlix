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

import com.virreyFlix.model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class Ejercicios {

    @Test
    public void insertarUsuarioYPerfilTest() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Usuario usuario = new Usuario(0, "Juan Pérez", "juan.perez@example.com");
            session.save(usuario);

            Perfil perfil = new Perfil("Perfil Juan", usuario, 30);
            perfil.setU(usuario);
            session.save(perfil);

            Usuario usuarioGuardado = session.get(Usuario.class, usuario.getId());
            Perfil perfilGuardado = session.get(Perfil.class, perfil.getId());

            assertNotNull(usuarioGuardado);
            assertEquals("Juan Pérez", usuarioGuardado.getNombre());
            assertEquals("juan.perez@example.com", usuarioGuardado.getEmail());

            assertNotNull(perfilGuardado);
            assertEquals("Perfil Juan", perfilGuardado.getNombre());
            assertEquals(30, perfilGuardado.getEdad());
            assertEquals(usuarioGuardado.getId(), perfilGuardado.getU().getId());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    @Test
    public void insertarGeneroYSerieTest() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Genero genero = new Genero("Comedia");
            session.save(genero);

            Serie serie = new Serie(13, genero, Collections.emptyList(), "The Funny Show");
            session.save(serie);

            Genero generoGuardado = session.get(Genero.class, genero.getId());
            Serie serieGuardada = session.get(Serie.class, serie.getId());

            assertNotNull(generoGuardado);
            assertEquals("Comedia", generoGuardado.getNombre());

            assertNotNull(serieGuardada);
            assertEquals("The Funny Show", serieGuardada.getTitulo());
            assertEquals(generoGuardado.getId(), serieGuardada.getGenero().getId());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    @Test
    public void insertarEpisodioTest() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Genero genero = new Genero("Drama");
            session.save(genero);

            Serie serie = new Serie(16, genero, Collections.emptyList(), "The Drama Series");
            session.save(serie);

            Episodio episodio = new Episodio("Pilot Episode", 45);
            episodio.setSerie(serie);
            session.save(episodio);

            Episodio episodioGuardado = session.get(Episodio.class, episodio.getId());

            assertNotNull(episodioGuardado);
            assertEquals("Pilot Episode", episodioGuardado.getTitulo());
            assertEquals(45, episodioGuardado.getDuracion());
            assertEquals(serie.getId(), episodioGuardado.getSerie().getId());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    @Test
    public void insertarHistorialTest() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Usuario usuario = new Usuario(0, "Maria Lopez", "maria.lopez@example.com");
            session.save(usuario);

            Perfil perfil = new Perfil("Perfil Maria", usuario, 28);
            perfil.setU(usuario);
            session.save(perfil);

            Genero genero = new Genero("Acción");
            session.save(genero);

            Serie serie = new Serie(18, genero, Collections.emptyList(), "Action Series");
            session.save(serie);

            Episodio episodio = new Episodio("Episode 1", 50);
            episodio.setSerie(serie);
            session.save(episodio);

            Historial historial = new Historial(LocalDateTime.now());
            historial.setPerfil(perfil);
            historial.setEpisodio(episodio);
            session.save(historial);

            Historial historialGuardado = session.get(Historial.class, historial.getId());

            assertNotNull(historialGuardado);
            assertEquals(perfil.getId(), historialGuardado.getPerfil().getId());
            assertEquals(episodio.getId(), historialGuardado.getEpisodio().getId());
            assertNotNull(historialGuardado.getFechaReproduccion());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }
}


