package org.example.saludexpress.Repositorios;

import org.example.saludexpress.Modelo_Entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long> {

    // Buscar productos por nombre (coincidencia parcial, ignorando mayúsculas/minúsculas)
    List<Producto> findByNombreProductoContainingIgnoreCase(String nombreProducto);

    // Buscar productos por departamento
    List<Producto> findByDepartamentoIdDepartamento(Integer idDepartamento);

    // Buscar productos por proveedor
    List<Producto> findByProveedoresIdProveedor(Integer idProveedor);

    // Buscar productos por marca
    List<Producto> findByMarcaIdMarca(Integer idMarca);
}
