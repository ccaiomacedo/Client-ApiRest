package com.devcaio.client.resources;

import com.devcaio.client.dto.ClientDTO;
import com.devcaio.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RequestMapping("/clients")
@RestController
public class ClientResource {

    @Autowired
    private ClientService service;

    @GetMapping()
    public ResponseEntity<Page<ClientDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                   @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerpage,
                                                   @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                                                   @RequestParam(value = "OrderBy", defaultValue = "name") String orderBy) {

        PageRequest pageRequest = PageRequest.of(page, linesPerpage, Sort.Direction.valueOf(direction), orderBy);
        Page<ClientDTO> list = service.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
        ClientDTO client = service.findById(id);
        return ResponseEntity.ok(client);
    }

    @PostMapping
    public ResponseEntity<ClientDTO> insert(@RequestBody ClientDTO clientDTO) {
        clientDTO = service.insert(clientDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clientDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(clientDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> update(@PathVariable Long id, @RequestBody ClientDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
