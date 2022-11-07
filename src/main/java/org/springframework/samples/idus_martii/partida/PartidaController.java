package org.springframework.samples.idus_martii.partida;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/partida/{userId}")
public class PartidaController {
    
    PartidaService partidaService;

    @Autowired
    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @GetMapping(value = "/create")
    public ModelAndView CrearPartidaForm() {
        return null; //TODO Mario y Pablo
    }

    @PostMapping(value = "/create")
    public void CrearPartida() {
        //TODO Mario y Pablo
        //Redirect a /partida/{userId}/{partidaId}
    }
    
    @PostMapping(value = "/{partidaId}/iniciar")
    public void IniciarPartida(@PathVariable("partidaId") Integer partidaId) {
        partidaService.IniciarPartida(partidaId);
    }

    @GetMapping(value = "/{partidaId}")
    public ModelAndView GetPartidaGeneral(@PathVariable("partidaId") Integer partidaId) {
        return null;
    }
}
