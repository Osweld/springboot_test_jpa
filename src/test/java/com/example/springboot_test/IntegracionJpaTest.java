package com.example.springboot_test;

import com.example.springboot_test.models.Cuenta;
import com.example.springboot_test.repository.CuentaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class IntegracionJpaTest {


    @Autowired
    CuentaRepository cuentaRepository;

    @Test
    void testFindById() {
        Optional<Cuenta> cuenta = cuentaRepository.findById(1L);
        assertEquals("Andres",cuenta.orElseThrow().getPersona());
    }

    @Test
    void testFindByPerson() {
        Optional<Cuenta> cuenta = cuentaRepository.findByPersona("Andres");
        assertEquals("Andres",cuenta.orElseThrow().getPersona());
        assertEquals("1000.00",cuenta.orElseThrow().getSaldo().toPlainString());
    }

    @Test
    void testFindByPersonThrowException() {
        Optional<Cuenta> cuenta = cuentaRepository.findByPersona("Rod");
        assertThrows(NoSuchElementException.class, cuenta::orElseThrow);
        assertFalse(cuenta.isPresent());

    }

    @Test
    void testFindAll() {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        assertFalse(cuentas.isEmpty());
        assertEquals(2,cuentas.size());

    }

    @Test
    void testSave() {
        //Given
        Cuenta cuentaPepe = new Cuenta(null, "Pepe",new BigDecimal("3000"));
        Cuenta save = cuentaRepository.save(cuentaPepe);
        //when
       // Cuenta cuenta = cuentaRepository.findByPersona("Pepe").orElseThrow();
        Cuenta cuenta = cuentaRepository.findById(save.getId()).orElseThrow();
//El id puede cambiar y lo controla la base de datos por lo cual es mejor buscarlo por algo que sepamos que lo podemos encontrar
        //then
        assertEquals("Pepe",cuenta.getPersona());
        assertEquals("3000",cuenta.getSaldo().toPlainString());
        assertEquals(3,cuenta.getId());
    }

    @Test
    void testUpdate() {
        //Given
        Cuenta cuentaPepe = new Cuenta(null, "Pepe",new BigDecimal("3000"));
        //when
        Cuenta cuenta = cuentaRepository.save(cuentaPepe);
        //Cuenta cuenta = cuentaRepository.findByPersona("Pepe").orElseThrow();
//El id puede cambiar y lo controla la base de datos por lo cual es mejor buscarlo por algo que sepamos que lo podemos encontrar
        //then
        assertEquals("Pepe",cuenta.getPersona());
        assertEquals("3000",cuenta.getSaldo().toPlainString());


        //update
        cuenta.setSaldo(new BigDecimal("3800"));
        Cuenta cuentaActualizada = cuentaRepository.save(cuenta);

        assertEquals("Pepe",cuentaActualizada.getPersona());
        assertEquals("3800",cuentaActualizada.getSaldo().toPlainString());
    }

    @Test
    void testDelete() {
        Cuenta cuenta = cuentaRepository.findById(2L).orElseThrow();
        assertEquals("Jhon",cuenta.getPersona());
        cuentaRepository.delete(cuenta);


        //Esto es para comprobar que el objeto se elimino ya que nos daria error al momento
        //de volver a llamarlo por que esta eliminado
        assertThrows(NoSuchElementException.class, ()->{
            cuentaRepository.findByPersona("jhon").orElseThrow();
        });
        assertEquals(1,cuentaRepository.findAll().size());

    }
}
