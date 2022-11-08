package com.example.springboot_test.services;

import com.example.springboot_test.models.Cuenta;

import java.math.BigDecimal;
import java.util.List;

public interface CuentaService {

    List<Cuenta>  findAll();
    Cuenta findById(Long id);
    int revisarTotalTransferencias(Long bancoId);
    Cuenta save(Cuenta cuenta);
    BigDecimal revisarSaldo(Long cuentaId);
    void transferir(Long numCuentaOrigen,Long numCuentaDestino,BigDecimal monto,Long bancoId);
}
