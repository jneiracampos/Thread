import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Repartidor extends Thread {
    private Casillero casillero;
    private CountDownLatch latch;
    private long id;

    public Repartidor(Casillero casillero, CountDownLatch latch, long id){
        this.casillero = casillero;
        this.latch = latch;
        this.id = id;
    }

    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy MM dd, HH:mm:ss:SSSS");
    
    @Override
    public void run(){
        while (!casillero.isUltimoProducto() || casillero.getCasilleroSize() != 0) {
            Producto producto = casillero.retirar(this.id);
            if (producto != null) {
                synchronized (this) {
                    T.productosRepartidos++;
                }
                try {
                    System.out.println(producto.getNombre() + " (" +  producto.getId() + ")" + " || Repartidor " + this.id + " entregando el producto || " + LocalDateTime.now().format(dtFormatter) );
                    Random random = new Random();
                    int randomIndex = random.nextInt(3,11);
                    Thread.sleep(randomIndex*1000);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                System.out.println(producto.getNombre() + " (" + producto.getId() + ")" + " || El repartidor " + this.id + " entrega el producto al cliente || " + LocalDateTime.now().format(dtFormatter));
                producto.setEntregado();
            }
        }
        System.out.println("SISTEMA: El repartidor " + this.id + " terminó con sus tareas asignadas || " + LocalDateTime.now().format(dtFormatter));
        latch.countDown();
    }
}
