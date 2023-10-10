import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Casillero {

    private ArrayList<Producto> casillero;
    private boolean ultimoProducto = false;

    public Casillero() {
        casillero = new ArrayList<>();
    }

    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy MM dd, HH:mm:ss:SSSS");

    public synchronized void almacenar(Producto producto) {
        while(casillero.size() == 1){
            try {
                System.out.println("CASILLERO || No se puede agregar productos porque el casillero esta lleno || " + LocalDateTime.now().format(dtFormatter));
                wait();
            } catch (InterruptedException e) {}
        }
        casillero.add(producto);
        System.out.println(producto.getNombre() + " (" + producto.getId() + ")" + " || El despachador agrega el producto al casillero || " + LocalDateTime.now().format(dtFormatter));
        notifyAll();
    }

    public synchronized Producto retirar(long id) {
        Producto producto = null;
        while(casillero.size() == 0 && !ultimoProducto){
            try {
                System.out.println("REPARTIDOR || No se puede retirar productos porque no hay productos en el casillero || " + LocalDateTime.now().format(dtFormatter));
                wait();
            } catch (InterruptedException e) {}
        }
        if (casillero.size() > 0) {
            producto = casillero.remove(0);
            System.out.println(producto.getNombre() + " (" + producto.getId() + ")" + " || El repartidor " + id + " retira el producto del casillero || " + LocalDateTime.now().format(dtFormatter));
            notify();
        }
        return producto;
    }

    public synchronized boolean isUltimoProducto() {
        return ultimoProducto;
    }

    public synchronized void setUltimoProducto() {
        this.ultimoProducto = true;
    }

    public synchronized int getCasilleroSize(){
        return casillero.size();
    }
}
