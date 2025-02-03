import com.virreyFlix.HibernateUtil;
import com.virreyFlix.model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class PruebasEntidades {

    @Test
    public  void prueba(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();
        Usuario u = new Usuario(1,"kka","kekekdkdkd@kfkf.es");

        session.persist(u);

        session.close();
    }

    @Test
    public void prueba_serie(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();

        Serie serie = new Serie("Los Simpson","comedia",18);
        Serie serie1 = new Serie("mamamia","drama",12);
        Serie serie2 = new Serie("pocoyo","infaltil",0);
        Serie serie3 = new Serie("lalalal","terror",7);

        Episodios ep1 = new Episodios("Navidad",30);
        Episodios ep2 = new Episodios("Halloween",40);
        Episodios ep3 = new Episodios("Verano",20);

        ep1.setSerie(serie);
        ep1.setSerie(serie);
        ep1.setSerie(serie);

        session.persist(serie);
        session.persist(ep1);
        session.persist(ep2);
        session.persist(ep3);



        tx.commit();
        session.close();
    }

    @Test
    public void consultarSerie(){
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Serie consultada = s2.find(Serie.class, 1);

        Transaction tx = s2.beginTransaction();

        System.out.println("Episodios: "+consultada.getNombre());
        System.out.println("Series: "+consultada.getNombre());
        tx.commit();
        s2.close();
    }

    public void historiales(){
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s2.beginTransaction(LocalDateTime.now().minusDays());

        Historial h = new Historial();
        Historial h2 = new Historial();

        h.setEpisodio(s2.find(Episodios.class,1));
        h.setPerfil(s2.find(Perfil.class,1));

        s2.persist(h);
        s2.persist(h2);

        tx.commit();
        s2.close();
    }

    public void consultarPerfil(){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Perfil p  = s.find(Perfil.class,1);
        System.out.println(p);
        System.out.println(p);
    }
}
