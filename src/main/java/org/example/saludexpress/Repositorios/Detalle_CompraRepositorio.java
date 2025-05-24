package org.example.saludexpress.Repositorios;

import org.example.saludexpress.Modelo_Entidades.Detalle_Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Detalle_CompraRepositorio extends JpaRepository<Detalle_Compra, Integer> {
    // Buscar detalles por compra
    List<Detalle_Compra> findByCompraIdCompra(Integer idCompra);

    // Buscar detalles por producto
    List<Detalle_Compra> findByProductoIdProducto(Integer idProducto);
}
