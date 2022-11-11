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
        //TODO Mario y Pablo
        return null;
    }

    @PostMapping(value = "/create")
    public ModelAndView CrearPartida() {
        //TODO Mario y Pablo
        return null;
    }
    
    @GetMapping(value = "/{partidaId}/iniciar")
    public ModelAndView IniciarPartida(@PathVariable("partidaId") Integer partidaId) {
        partidaService.IniciarPartida(partidaId);
        return new ModelAndView("redirect:/partida/{userId}/{partidaId}");
    }

    @GetMapping(value = "/{partidaId}")
    public ModelAndView GetPartidaGeneral(@PathVariable("partidaId") Integer partidaId) {
        return null;
    }
}
