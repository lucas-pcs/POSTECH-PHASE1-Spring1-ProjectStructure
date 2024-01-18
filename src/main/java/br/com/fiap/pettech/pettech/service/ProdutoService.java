package br.com.fiap.pettech.pettech.service;

import br.com.fiap.pettech.pettech.controller.exception.ControllerNotFoundException;
import br.com.fiap.pettech.pettech.dto.ProdutoDTO;
import br.com.fiap.pettech.pettech.entities.Produto;
import br.com.fiap.pettech.pettech.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Collection<ProdutoDTO> findAll(){
        // map Collection<Produto> from JPA to Collection<ProdutoDTO> using stream.map() function
        // and after transforms the stream into a List
        Collection<Produto> produtoCollection = produtoRepository.findAll();
        return produtoCollection.stream().map(this::toProdutoDTO).collect(Collectors.toList());
    }

    public ProdutoDTO findById(UUID id){
        return toProdutoDTO(produtoRepository.findById(id).orElseThrow(()-> new ControllerNotFoundException("Produto não encontrado")));
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO){
        return toProdutoDTO(produtoRepository.save(toProduto(produtoDTO)));
    }


    public ProdutoDTO update(UUID id, ProdutoDTO produtoDTO){
        try {
            // obtém o objeto a ser atualizado
            Produto buscaProduto = produtoRepository.getReferenceById(id); // OBS: o método getOne(UUID id) consegue fazer o update do produto com apenas uma querySQL
            // fazendo com que o JPA crie uma instancia do produto e caso ele exista, atualiza, caso contrário, retorna um erro

            // atualiza ele caso exista
            buscaProduto.setDescricao(produtoDTO.descricao());
            buscaProduto.setNome(produtoDTO.nome());
            buscaProduto.setPreco(produtoDTO.preco());
            buscaProduto.setUrlDaImagem(produtoDTO.urlDaImagem());

            // e salva
            return toProdutoDTO(produtoRepository.save(buscaProduto));
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

    // a mapper to transform the entity to DTO
    private ProdutoDTO toProdutoDTO(Produto produto){
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getUrlDaImagem()
        );
    }

    // a mapper to transform the DTO to entity
    private Produto toProduto(ProdutoDTO produtoDTO){
        return new Produto(
                produtoDTO.id(),
                produtoDTO.nome(),
                produtoDTO.descricao(),
                produtoDTO.preco(),
                produtoDTO.urlDaImagem()
        );
    }

}
