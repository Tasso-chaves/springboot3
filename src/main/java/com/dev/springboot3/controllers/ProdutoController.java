package com.dev.springboot3.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dev.springboot3.dtos.ProdutoRecordDto;
import com.dev.springboot3.models.ProdutoModel;
import com.dev.springboot3.repositories.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
public class ProdutoController {
    
    @Autowired
    ProdutoRepository produtoRepository;

    @PostMapping("/produto")
    public ResponseEntity<ProdutoModel> salvarProduto(@RequestBody @Valid ProdutoRecordDto produtoRecordDto){

        var produtoModel = new ProdutoModel();
        BeanUtils.copyProperties(produtoRecordDto, produtoModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produtoModel));
    }

    @GetMapping("/produtos")
    public ResponseEntity<List<ProdutoModel>> listarProdutos(){
        return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findAll());
    }

    @GetMapping("/produto/{id}")
    public ResponseEntity<Object> listarProdutoPorId(@PathVariable(value = "id") UUID id){
        Optional<ProdutoModel> produto0 = produtoRepository.findById(id);
        
        if(produto0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto nao encontrado!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(produto0.get());
    }

    @PutMapping("/produto/{id}")
    public ResponseEntity<Object> atualizarProduto(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProdutoRecordDto produtoRecordDto){

        Optional<ProdutoModel> produto0 = produtoRepository.findById(id);

        if(produto0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto nao encontrado!");
        }

        var produtoModel = produto0.get();

        BeanUtils.copyProperties(produtoRecordDto, produtoModel);
        return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produtoModel));
    }

    @DeleteMapping("/produto/{id}")
    public ResponseEntity<Object> apagarProduto(@PathVariable(value = "id") UUID id){

        Optional<ProdutoModel> produto0 = produtoRepository.findById(id);

        if(produto0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto nao encontrado!");
        }


        produtoRepository.delete(produto0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto excluido com sucesso!");
    }
}
