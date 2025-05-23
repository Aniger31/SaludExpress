package org.example.saludexpress.Repositorios;

import org.example.saludexpress.Modelo_Entidades.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface EmpleadoRepositorio  extends JpaRepository<Empleado, Integer> {
    // Buscar empleados por nombre exacto
    List<Empleado> findByNombre(String nombre);

    // Buscar empleados cuyo nombre contenga un texto (ignore mayúsculas/minúsculas)
    List<Empleado> findByNombreIgnoreCaseContaining(String nombre);

    // Buscar por apellido paterno
    List<Empleado> findByAPaterno(String aPaterno);

    // Buscar por apellido materno
    List<Empleado> findByAMaterno(String aMaterno);

    // Buscar empleados que nacieron después de una fecha dada
    List<Empleado> findByFechaNacimientoAfter(Date fecha);

    // Buscar empleados que nacieron antes de una fecha dada
    List<Empleado> findByFechaNacimientoBefore(Date fecha);

    // Buscar empleados por rango de fechas de contratación
    List<Empleado> findByFechaContratacionBetween(Date inicio, Date fin);

    // Buscar empleados por código postal exacto
    List<Empleado> findByCodigoPostal(String codigoPostal);

    // Buscar empleados por colonia (zona o barrio)
    List<Empleado> findByColonia(String colonia);

    // Buscar empleados por sucursal (relación ManyToOne)
    List<Empleado> findBySucursalIdSucursal(Integer idSucursal);

    // Buscar empleados por puesto (relación ManyToOne)
    List<Empleado> findByPuestoIdPuesto(Integer idPuesto);

    // Buscar empleados por estado (relación ManyToOne)
    List<Empleado> findByEstadoIdEstado(Integer idEstado);

    // Buscar empleados por municipio (relación ManyToOne)
    List<Empleado> findByMunicipioIdMunicipio(Integer idMunicipio);

    // Buscar empleados por teléfono exacto
    List<Empleado> findByTelefono(String telefono);

    // Buscar empleados por correo exacto
    List<Empleado> findByCorreo(String correo);
}
