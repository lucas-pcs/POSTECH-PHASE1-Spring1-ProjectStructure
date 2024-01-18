package br.com.fiap.pettech.pettech.service;

import br.com.fiap.pettech.pettech.controller.exception.ControllerNotFoundException;
import br.com.fiap.pettech.pettech.entities.Produto;
import br.com.fiap.pettech.pettech.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Collection<Produto> findAll(){
        return produtoRepository.findAll();
    }

    public Produto findById(UUID id){
        return produtoRepository.findById(id).orElseThrow(()-> new ControllerNotFoundException("Produto não encontrado"));
    }

    public Produto save(Produto produto){
        return produtoRepository.save(produto);
    }


    public Produto update(UUID id, Produto produto){
        try {
            // obtém o objeto a ser atualizado
            Produto buscaProduto = produtoRepository.getOne(id); // OBS: o método getOne(UUID id) consegue fazer o update do produto com apenas uma querySQL
            // fazendo com que o JPA crie uma instancia do produto e caso ele exista, atualiza, caso contrário, retorna um erro

            // atualiza ele caso exista
            buscaProduto.setDescricao(produto.getDescricao());
            buscaProduto.setNome(produto.getNome());
            buscaProduto.setPreco(produto.getPreco());
            buscaProduto.setUrlDaImagem(produto.getUrlDaImagem());

            // e salva
            return produtoRepository.save(buscaProduto);
        } catch (EntityNotFoundException e){
            throw new ControllerNotFoundException("Produto não encontrado para update");
        }
    }

    public void delete(UUID id){
        try{
            produtoRepository.deleteById(id);
        } catch (EntityNotFoundException e){
            throw new ControllerNotFoundException("Produto não encontrado para delete");
        }
    }
}
