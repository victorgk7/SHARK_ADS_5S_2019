package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Pesquisa;

public class PesquisaDAO {

    public List<Pesquisa> listarTodos() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Query query = null;
        List<Pesquisa> pesquisas = null;

        try {
            emf = Persistence.createEntityManagerFactory("WebCensoPU");

            em = emf.createEntityManager();
            query = em.createNamedQuery("Pesquisa.listarTodos");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        pesquisas = query.getResultList();
        return pesquisas;
    }
}
