package model;

/**
 * Created by Investigación2 on 09/12/2014.
 */
public class Escuelas {
    private int icono;
    private String nombre;
    private int id;

    public Escuelas(int icono, String nombre, int id) {
        this.icono = icono;
        this.nombre = nombre;
        this.id = id;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
