package org.springframework.samples.idus_martii.turno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/turnos")
public class TurnoController {

    private final String  TURNOS_LISTING_VIEW="/turnos/turnosList";

    private TurnoService service;

    @Autowired
    public TurnoController(TurnoService service){
        this.service=service;
    }


    @GetMapping("/")
    public ModelAndView showTurnos(){
        ModelAndView result=new ModelAndView(TURNOS_LISTING_VIEW);
        result.addObject("turnos", service.getTurnos());
        return result;
    }
}