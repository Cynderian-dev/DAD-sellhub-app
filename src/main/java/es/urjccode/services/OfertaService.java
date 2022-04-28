package es.urjccode.services;

import es.urjccode.models.OfertaModel;
import es.urjccode.repositories.OfertaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfertaService {

    @Autowired
    private OfertaRepo ofertaRepo;

    @Cacheable("ofertas")
    public List<OfertaModel> obtenerOferta(){
        List<OfertaModel> ofertas = ofertaRepo.findByFechaCierreNullOrderByPrecio();
        return ofertas;
    }
}
