package org.example.saludexpress.Repositorios;

import org.example.saludexpress.Modelo_Entidades.Detalle_Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Detalle_VentaRepositorio extends JpaRepository<Detalle_Venta, Integer> {
    // Buscar detalles por id de venta
    List<Detalle_Venta> findByVentaIdVenta(Integer idVenta);

    // Buscar detalles por id de producto
    List<Detalle_Venta> findByProductoIdProducto(Integer idProducto);
}
