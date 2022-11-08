package com.example.springboot_test.controllers;

import static org.springframework.http.HttpStatus.*;

import com.example.springboot_test.models.Cuenta;
import com.example.springboot_test.models.TransaccionDTO;
import com.example.springboot_test.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Cuenta detalle(@PathVariable(name = "id") Long id){
        return cuentaService.findById(id);
    }

    @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestBody TransaccionDTO dto){
        cuentaService.transferir(dto.getCuentaOrigenId(),dto.getCuentaDestinoId(),dto.getMonto(),dto.getBancoId());
        Map<String,Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status",OK);
        response.put("mensaje","Transferencia realizada con exito!");
        response.put("transaccion",dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    @ResponseStatus(OK)
    public List<Cuenta> listar(){
        return cuentaService.findAll();
    }

    @PostMapping()
    @ResponseStatus(CREATED)
    public Cuenta guardar(@RequestBody Cuenta cuenta){
        return cuentaService.save(cuenta);
    }
}
