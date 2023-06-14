package com.example.lab8.Services;

import com.example.lab8.Entity.Evento;
import com.example.lab8.Entity.TipoTicketEvento;
import com.example.lab8.Repository.EventoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class EventoRest {

    @Autowired
    EventoRepository eventoRepository;

    @GetMapping("/evento")
    public List<Evento> listaEventos(){
        List<Evento> lista = eventoRepository.findAll();
        return lista;
    }

    @GetMapping("/evento/{id}")
    public ResponseEntity<HashMap<String, Object>> obtenerEventoPorId(@PathVariable("id") String idStr) {

        HashMap<String,Object> responseJson = new HashMap<>();
        try {
            int id = Integer.parseInt(idStr);
            Optional<Evento> optEvento = eventoRepository.findById(id);
            if (optEvento.isPresent()) {
                responseJson.put("evento",optEvento.get());
                return ResponseEntity.ok(responseJson);
            } else {
                responseJson.put("msg", "Evento no encontrado");
            }
        } catch (NumberFormatException e) {
            responseJson.put("msg", "el ID debe ser un número entero positivo");
        }
        responseJson.put("result","Falla");
        return  ResponseEntity.badRequest().body(responseJson);
    }


    @PostMapping("/evento")
    public ResponseEntity<HashMap<String, Object>> crearEvento(
            @RequestBody Evento evento,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseJson = new HashMap<>();

        eventoRepository.save(evento);

        if(fetchId){
            responseJson.put("id",evento.getId());
        }
        responseJson.put("estado","creado");

        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionException(HttpServletRequest request){
        HashMap<String,String> responseMap = new HashMap<>();
        if(request.getMethod().equals("POST")){
            responseMap.put("estado","error");
            responseMap.put("msg","Debe enviar un evento");
        }
        return ResponseEntity.badRequest().body(responseMap);
    }

    /* Revisar comentado para poder correr
    @GetMapping("/eventoConTipoDeTicket/{id}")
    public ResponseEntity<HashMap<String, Object>> obtenerEventoConTipoDeTicketPorId(@PathVariable("id") String idStr) {
        HashMap<String, Object> responseJson = new HashMap<>();

        try {
            int id = Integer.parseInt(idStr);
            Optional<Evento> optEvento = eventoRepository.findById(id);
            if (optEvento.isPresent()) {
                Evento evento = optEvento.get();
                List<TipoTicketEvento> tiposTicket = eventot();

                responseJson.put("evento", evento);
                responseJson.put("tiposTicket", tiposTicket);

                return ResponseEntity.ok(responseJson);
            } else {
                responseJson.put("msg", "Evento no encontrado");
            }
        } catch (NumberFormatException e) {
            responseJson.put("msg", "El ID debe ser un número entero positivo");
        } catch (EntityNotFoundException e) {
            responseJson.put("msg", "No se encontró el evento con el ID proporcionado");
        }

        responseJson.put("result", "Falla");
        return ResponseEntity.badRequest().body(responseJson);
    }*/

    @DeleteMapping(value = "/evento/{id}")
    public ResponseEntity<HashMap<String, Object>> borrar(@PathVariable("id") String idStr) {

        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            int id = Integer.parseInt(idStr);
            if (eventoRepository.existsById(id)) {
                eventoRepository.deleteById(id);
                responseMap.put("estado", "borrado exitoso");
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "no se encontró el evento con id: " + id);
                return ResponseEntity.badRequest().body(responseMap);
            }
        } catch (NumberFormatException ex) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "El ID debe ser un número");
            return ResponseEntity.badRequest().body(responseMap);
        }
    }

    @PutMapping("/evento/{id}")
    public ResponseEntity<HashMap<String, Object>> editarEvento(
            @PathVariable("id") int id,
            @RequestBody Evento eventoActualizado
    ) {
        HashMap<String, Object> responseJson = new HashMap<>();

        try {
            Optional<Evento> optEvento = eventoRepository.findById(id);
            if (optEvento.isPresent()) {
                Evento eventoExistente = optEvento.get();

                if (eventoActualizado != null) {
                    if (eventoActualizado.getFecha() != null) {
                        eventoExistente.setFecha(eventoActualizado.getFecha());
                    }
                    if (eventoActualizado.getNombre() != null) {
                        eventoExistente.setNombre(eventoActualizado.getNombre());
                    }
                    if (eventoActualizado.getDescripcion() != null) {
                        eventoExistente.setDescripcion(eventoActualizado.getDescripcion());
                    }
                    if (eventoActualizado.getPathImage() != null) {
                        eventoExistente.setPathImage(eventoActualizado.getPathImage());
                    }

                    eventoRepository.save(eventoExistente);

                    responseJson.put("msg", "Evento actualizado con éxito");
                    responseJson.put("evento", eventoExistente);

                    return ResponseEntity.ok(responseJson);
                } else {
                    responseJson.put("msg", "El objeto evento no puede ser nulo");
                }
            } else {
                responseJson.put("msg", "Evento no encontrado");
            }
        } catch (EntityNotFoundException e) {
            responseJson.put("msg", "No se encontró el evento con el ID proporcionado");
        }

        responseJson.put("result", "Falla");
        return ResponseEntity.badRequest().body(responseJson);
    }












}
