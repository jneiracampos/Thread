import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class T {    
    public static int productosRepartidos = 0;

    public static void main(String[] args) {
        
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the number of producers: ");
            int cantidadProductores = scanner.nextInt();

            System.out.print("Enter the number of deliverymen: ");
            int cantidadRepartidores = scanner.nextInt();

            System.out.print("Enter the warehouse size: ");
            int tamanioBodega = scanner.nextInt();

            System.out.print("Enter the total number of products: ");
            int cantidadProductos = scanner.nextInt();

            int enteroProductos = cantidadProductos / cantidadProductores;
            int moduloProductos = cantidadProductos % cantidadProductores;
            DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy MM dd, HH:mm:ss:SSSS");
            Bodega bodega = new Bodega(tamanioBodega);
            Casillero casillero = new Casillero();

            for (int i = 0; i < cantidadProductores - 1; i++) {
                Productor productor = new Productor(enteroProductos, bodega, i);
                productor.start();
            }

            Productor productor = new Productor(enteroProductos+moduloProductos, bodega, cantidadProductores - 1);
            productor.start();
            
            Despachador despachador = new Despachador(cantidadProductos, bodega, casillero);
            despachador.start();

            CountDownLatch latch = new CountDownLatch(cantidadRepartidores);

            for (int i = 0; i < cantidadRepartidores; i++) {
                Repartidor repartidor = new Repartidor(casillero, latch, i);
                repartidor.start();
            }

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("SISTEMA || Se repartieron en total " + productosRepartidos + " productos || " + LocalDateTime.now().format(dtFormatter));
        }

    }
}
