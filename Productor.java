import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Productor extends Thread {
    private int cont = 0;
    private int cantidadProductos;
    private Bodega bodega;
    private long id;

    public Productor(int cantidadProductos, Bodega bodega, long id){
        this.cantidadProductos = cantidadProductos;
        this.bodega = bodega;
        this.id = id;
    }

    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy MM dd, HH:mm:ss:SSSS");

    @Override
    public void run(){
        while (cont < cantidadProductos){
            Random random = new Random();
            int idProd = random.nextInt();
            if (idProd < 0){
                idProd = idProd * (-1);
            }
            Producto producto = new Producto(idProd);     
            bodega.almacenar(producto, this.id);
            cont++;
            producto.isProductoEntregado();
        }
        System.out.println("SISTEMA || El productor " + this.id +  " terminó con sus tareas asignadas || " +  LocalDateTime.now().format(dtFormatter));
    }
}
