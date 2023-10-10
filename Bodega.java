import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Bodega {

    private ArrayList<Producto> bodega;
    private int tamanio;

    public Bodega(int tamanio){
        this.tamanio = tamanio;
        bodega = new ArrayList<>();
    }

    
    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy MM dd, HH:mm:ss:SSSS");

    public synchronized void almacenar(Producto producto, long id) {
        while(bodega.size() == tamanio){
            try {
                System.out.println("BODEGA || No se puede agregar productos porque la bodega esta llena || " + LocalDateTime.now().format(dtFormatter));
                wait();
            } catch (InterruptedException e) {}
        }
        bodega.add(producto);
        System.out.println(producto.getNombre() + " (" + producto.getId() + ")" + " || El productor " + id + " agrega el producto a la bodega || " + LocalDateTime.now().format(dtFormatter));
        notify();
    }

    public Producto retirar() {
        Producto producto = null;
        while(bodega.size() == 0){
            try {
                System.out.println("DESPACHADOR || No se puede retirar productos porque no hay productos en la bodega || " + LocalDateTime.now().format(dtFormatter));
                Thread.sleep(10000);
            } catch (Exception e) {}
        }
        synchronized (this){
            producto = (Producto) bodega.remove(0);
            System.out.println(producto.getNombre() + " (" + producto.getId() + ")" + " || El despachador retira el producto de la bodega || " + LocalDateTime.now().format(dtFormatter));
            notify();
        }
        return producto;
    }
}
