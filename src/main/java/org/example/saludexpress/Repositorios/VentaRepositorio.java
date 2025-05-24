package org.example.saludexpress.Repositorios;

import org.example.saludexpress.Modelo_Entidades.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentaRepositorio extends JpaRepository<Venta, Integer> {
    // Buscar ventas por cliente
    List<Venta> findByClienteIdCliente(Integer idCliente);

    // Buscar ventas por empleado
    List<Venta> findByEmpleadoIdEmpleado(Integer idEmpleado);

    // Buscar ventas dentro de un rango de fechas
    List<Venta> findByFechaVentaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    // Buscar ventas por metodo de pago
    List<Venta> findByMetodoPago(Venta.MetodoPago metodoPago);
}
