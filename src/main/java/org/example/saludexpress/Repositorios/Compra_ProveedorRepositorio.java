package org.example.saludexpress.Repositorios;

import org.example.saludexpress.Modelo_Entidades.Compra_Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface Compra_ProveedorRepositorio extends JpaRepository<Compra_Proveedor, Integer> {
    // Buscar compras por proveedor
    List<Compra_Proveedor> findByProveedorIdProveedor(Integer idProveedor);

    // Buscar compras por sucursal
    List<Compra_Proveedor> findBySucursalIdSucursal(Integer idSucursal);

    // Buscar compras por fecha de compra (exacta)
    List<Compra_Proveedor> findByFechaCompra(LocalDateTime fechaCompra);

    // Buscar compras dentro de un rango de fechas
    List<Compra_Proveedor> findByFechaCompraBetween(LocalDateTime startDate, LocalDateTime endDate);
}
