package com.povio.exam.flowerspot.controller;

import com.povio.exam.flowerspot.model.Sighting;
import com.povio.exam.flowerspot.repository.SightingRepository;
import com.povio.exam.flowerspot.service.SightingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sightings")
public class SightingController {
    private SightingRepository sightingRepository;
    private SightingService sightingService;

    public SightingController(SightingRepository sightingRepository, SightingService sightingService) {
        this.sightingRepository = sightingRepository;
        this.sightingService = sightingService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> retrieveAll() {
        List<Sighting> sightings = sightingRepository.findAll();
        return ResponseEntity.ok(sightings);
    }

    @RequestMapping(value = "/{sightingId}", method = RequestMethod.GET)
    public ResponseEntity<?> retrieve(@PathVariable Long sightingId) {
        Optional<Sighting> sighting = sightingRepository.findById(sightingId);
        if (!sighting.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sighting.get());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody Sighting sighting) {
        sighting.setId(null);
        sightingService.save(sighting);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{sightingId}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Sighting sighting, @PathVariable Long sightingId) {
        Optional<Sighting> newSighting = sightingRepository.findById(sightingId);
        if (!newSighting.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        sighting.setId(sightingId);
        if (!sightingService.isMySighting(newSighting.get())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        sightingService.save(sighting);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{sightingId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long sightingId) {
        Optional<Sighting> newSighting = sightingRepository.findById(sightingId);
        if (!newSighting.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if (!sightingService.isMySighting(newSighting.get())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        sightingRepository.delete(newSighting.get());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{sightingId}/like", method = RequestMethod.POST)
    public ResponseEntity<?> like(@PathVariable Long sightingId) {
        Optional<Sighting> sighting = sightingRepository.findById(sightingId);
        if (!sighting.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if (sightingService.likedAlready(sighting.get())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        sightingService.like(sighting.get());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{sightingId}/like", method = RequestMethod.DELETE)
    public ResponseEntity<?> removelike(@PathVariable Long sightingId) {
        Optional<Sighting> sighting = sightingRepository.findById(sightingId);
        if (!sighting.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if (!sightingService.likedAlready(sighting.get())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        sightingService.removeLike(sighting.get());
        return ResponseEntity.ok().build();
    }

}
