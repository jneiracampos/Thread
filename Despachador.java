import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Despachador extends Thread {

    private int cont = 0;
    private int cantidadProductosDespachar;
    private Bodega bodega;
    private Casillero casillero;

    public Despachador(int cantidadProductosDespachar, Bodega bodega, Casillero casillero) {
        this.cantidadProductosDespachar = cantidadProductosDespachar;
        this.bodega = bodega;
        this.casillero = casillero;
    }

    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy MM dd, HH:mm:ss:SSSS");

    public int getCantidadProductosDespachar() {
        return cantidadProductosDespachar;
    }
    
    @Override
    public void run(){ 
        while (cont < cantidadProductosDespachar - 1) {
            Producto producto = bodega.retirar();
            casillero.almacenar(producto);
            cont++;
        }
        Producto producto = bodega.retirar();
        casillero.almacenar(producto);
        casillero.setUltimoProducto();
        System.out.println("SISTEMA || El despachador terminó con sus tareas asignadas || " + LocalDateTime.now().format(dtFormatter));
    }
}
