package interfaces;

import java.util.List;
import model.Persona;
import model.Usuario;

public interface CRUD {
 
    public List listar();
    public Persona list(int id);
    public boolean add(Persona per);
    public boolean edit(Persona per);
    public boolean eliminar(int id);
    public boolean login(Usuario sr);
}
