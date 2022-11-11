package org.springframework.samples.idus_martii.partida;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/partidas")
public class PartidaController {
	 private final String  PARTIDAS_LISTING_VIEW="/partidas/partidasList";
    PartidaService partidaService;

    @Autowired
    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }
    
    @Transactional(readOnly = true)
    @GetMapping("/")
    public ModelAndView showPartidas(HttpServletResponse response){
    	response.addHeader("Refresh", "5");
        ModelAndView result=new ModelAndView(PARTIDAS_LISTING_VIEW);
        result.addObject("partidas", partidaService.getPartidas());
        return result;
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

    @GetMapping(value = "/create/{partidaId}")
    public ModelAndView GetPartidaGeneral(@PathVariable("partidaId") Integer partidaId) {
    	ModelAndView result=new ModelAndView("/partidas/tablero");
        result.addObject("partida", partidaService.findPartida(partidaId));
        return result;
    }
    
    
}
