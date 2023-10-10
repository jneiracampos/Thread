import java.util.Random;

public class Producto {
    private String nombre;
    private boolean entregado;
    private int id;

    // Lista de nombres de productos posibles
    private static final String[] nombresProductos = {
        "Lavadora", "Refrigerador", "Horno microondas", "Licuadora", "Aspiradora",
        "Cafetera", "Tostadora", "Batidora", "Secadora de ropa", "Plancha",
        "Robot aspirador", "Exprimidor de jugo", "Freidora de aire", "Cocina de inducción",
        "Dispensador de agua", "Robot de cocina", "Horno eléctrico", "Hervidor eléctrico",
        "Robot de limpieza", "Aire acondicionado"
    };

    // Constructor que asigna un nombre aleatorio al producto
    public Producto(int id) {
        Random random = new Random();
        int randomIndex = random.nextInt(nombresProductos.length);
        this.nombre = nombresProductos[randomIndex];
        this.entregado = false;
        this.id = id;
    }

    // Método para obtener el nombre del producto
    public String getNombre() {
        return nombre;
    }

    // Método para obtener el estado de entrega del producto
    public boolean getEntregado() {
        return entregado;
    }

    public String getId(){
        return Integer.toString(id);
    }

    // Método para cambiar el estado de entrega del producto
    public synchronized void setEntregado() {
        this.entregado = true;
        notifyAll();
    }

    public synchronized void isProductoEntregado() {
        while (!entregado) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
    }
}
