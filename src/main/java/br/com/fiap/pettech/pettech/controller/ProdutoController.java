package br.com.fiap.pettech.pettech.controller;

import br.com.fiap.pettech.pettech.dto.ProdutoDTO;
import br.com.fiap.pettech.pettech.service.ProdutoService;
import br.com.fiap.pettech.pettech.entities.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping
    public ResponseEntity<Collection<ProdutoDTO>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> findById(@PathVariable UUID id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> save(@RequestBody ProdutoDTO produtoDTO) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(service.save(produtoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> update(@PathVariable UUID id, @RequestBody ProdutoDTO produtoDTO){
        return ResponseEntity.ok(service.update(id, produtoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
