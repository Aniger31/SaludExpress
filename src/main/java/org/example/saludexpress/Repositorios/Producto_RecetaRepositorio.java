package org.example.saludexpress.Repositorios;

import org.example.saludexpress.Modelo_Entidades.Producto_Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Producto_RecetaRepositorio extends JpaRepository<Producto_Receta, Integer> {
    // Buscar todos los productos por id de receta
    List<Producto_Receta> findByRecetaIdReceta(Integer idReceta);

    // Buscar todos los productos por id de producto
    List<Producto_Receta> findByProductoIdProducto(Integer idProducto);
}
