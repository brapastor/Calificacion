package model;

/**
 * Created by BRAPASTOR on 05/12/2014.
 */
public class ItemGrid {
    private String titulo;
    private int imagen;

    public ItemGrid(String titulo, int imagen) {
        this.titulo = titulo;
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getImagen() {
        return imagen;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
