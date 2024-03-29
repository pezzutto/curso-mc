package com.nelioalves.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nelioalves.cursomc.domain.Produto;
import com.nelioalves.cursomc.dto.ProdutoDTO;
import com.nelioalves.cursomc.resources.utils.ResourceUtils;
import com.nelioalves.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		
		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	// Get não aceita mandar parametros pelo corpo da requisição
	@GetMapping(value="/page")
	public ResponseEntity<Page<ProdutoDTO>> searchPage( @RequestParam(name="nome", defaultValue="") String nome
			                                          , @RequestParam(name="categorias", defaultValue="") String categorias
			                                          , @RequestParam(name="page", defaultValue="0") Integer page
			                                          , @RequestParam(name="linesPerPage", defaultValue="24") Integer linesPerPage
			                                          , @RequestParam(name="orderBy", defaultValue="nome") String orderBy
			                                          , @RequestParam(name="direction", defaultValue="ASC") String direction) {
		
		List<Integer> lstCat = ResourceUtils.decodeIntList(categorias);
		
		Page<Produto> lstProd = service.search(ResourceUtils.decodeParam(nome), lstCat, page, linesPerPage, orderBy, direction);
		
		Page<ProdutoDTO> lstDto = lstProd.map(prod -> new ProdutoDTO(prod));
		return ResponseEntity.ok().body(lstDto);
	}
	
}
