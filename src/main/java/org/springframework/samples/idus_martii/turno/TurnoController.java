package org.springframework.samples.idus_martii.turno;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/turnos")
public class TurnoController {

    private final String  TURNOS_LISTING_VIEW="/turnos/turnosList";
    private final String TURNOS_FORM="/turnos/createOrUpdateTurnoForm";
    private TurnoService service;

    @Autowired
    public TurnoController(TurnoService service){
        this.service=service;
    }

    @Transactional(readOnly = true)
    @GetMapping("/")
    public ModelAndView showTurnos(){
        ModelAndView result=new ModelAndView(TURNOS_LISTING_VIEW);
        result.addObject("turnos", service.getTurnos());
        return result;
    }
    @Transactional()
    @GetMapping("/{id}/delete")
    public ModelAndView deleteTurno(@PathVariable int id){
        service.deleteTurnoById(id);        
        ModelAndView result=showTurnos();
        result.addObject("message", "El turno se ha eliminado correctamente");
        return result;
    }
    @Transactional(readOnly = true)
    @GetMapping("/{id}/edit")
    public ModelAndView editTurno(@PathVariable int id){
        Turno turno=service.getById(id);        
        ModelAndView result=new ModelAndView(TURNOS_FORM);
        result.addObject("turno", turno);
        return result;
    }
    
    @Transactional
    @PostMapping("/{id}/edit")
    public ModelAndView saveTurno(@PathVariable int id,Turno turno){
        Turno turnoToBeUpdated=service.getById(id);
        BeanUtils.copyProperties(turno,turnoToBeUpdated,"id");
        service.save(turnoToBeUpdated);
        ModelAndView result=showTurnos();
        result.addObject("message", "El turno se ha actualizado correctamente");
        return result;   
    }
    
    @Transactional(readOnly = true)
    @GetMapping("/new")
    public ModelAndView createTurno(){
        Turno turno= new Turno();
        ModelAndView result=new ModelAndView(TURNOS_FORM);
        result.addObject("turno", turno);
        return result;
    }
    
    @Transactional
    @PostMapping("/new")
    public ModelAndView saveNewTurno(Turno turno, BindingResult br){
        service.save(turno);
        ModelAndView result=showTurnos();
        result.addObject("message", "El turno se ha creado correctamente");
        return result;
    }
}