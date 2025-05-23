package org.example.saludexpress.Repositorios;

import org.example.saludexpress.Modelo_Entidades.Estado;
import org.example.saludexpress.Modelo_Entidades.Farmacia;
import org.example.saludexpress.Modelo_Entidades.Municipio;
import org.example.saludexpress.Modelo_Entidades.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SucursalRepositorio extends JpaRepository<Sucursal, Integer> {
    // Buscar por colonia
    List<Sucursal> findByColonia(String colonia);

    // Buscar por código postal
    List<Sucursal> findByCodigoPostal(String codigoPostal);

    // Buscar por municipio
    List<Sucursal> findByMunicipio(Municipio municipio);

    // Buscar por estado
    List<Sucursal> findByEstado(Estado estado);

    // Buscar por farmacia
    List<Sucursal> findByFarmacia(Farmacia farmacia);

    // Buscar por teléfono
    List<Sucursal> findByTelefono(String telefono);

    // Buscar por calle
    List<Sucursal> findByCalle(String calle);

    // Buscar por número de calle
    List<Sucursal> findByNumero(Integer numero);
}
