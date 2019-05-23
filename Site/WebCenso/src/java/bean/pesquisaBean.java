
package bean;


import dao.PesquisaDAO;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import model.Pesquisa;


@ManagedBean(name="pesquisaBean")
@ViewScoped
public class pesquisaBean implements Serializable {
    private final PesquisaDAO pesquisaDao = new PesquisaDAO();;
    private List<Pesquisa> pesquisas;
     
 
 
    @PostConstruct
    public void init() {
        
        try {
            pesquisas = pesquisaDao.listarTodos();
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),e.getMessage()));
            context.getExternalContext().getFlash().setKeepMessages(true);
        }
        
    }
     

    public List<Pesquisa> getPesquisas() {
        return pesquisas;
    }
 
}