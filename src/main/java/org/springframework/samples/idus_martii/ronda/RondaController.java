package org.springframework.samples.idus_martii.ronda;

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
@RequestMapping("/rondas")
public class RondaController {

    private final String  RONDAS_LISTING_VIEW="/rondas/rondasList";
    private final String RONDAS_FORM="/rondas/createOrUpdateRondaForm";
    private RondaService service;

    @Autowired
    public RondaController(RondaService service){
        this.service=service;
    }

    @Transactional(readOnly = true)
    @GetMapping("/")
    public ModelAndView showRondas(){
        ModelAndView result=new ModelAndView(RONDAS_LISTING_VIEW);
        result.addObject("rondas", service.getRondas());
        return result;
    }
    @Transactional()
    @GetMapping("/{id}/delete")
    public ModelAndView deleteRonda(@PathVariable int id){
        service.deleteRondaById(id);        
        ModelAndView result=showRondas();
        result.addObject("message", "La ronda ha sido eliminada correctamente");
        return result;
    }
    @Transactional(readOnly = true)
    @GetMapping("/{id}/edit")
    public ModelAndView editRonda(@PathVariable int id){
        Ronda ronda=service.getById(id);        
        ModelAndView result=new ModelAndView(RONDAS_FORM);
        result.addObject("ronda", ronda);
        return result;
    }
    
    @Transactional
    @PostMapping("/{id}/edit")
    public ModelAndView saveRonda(@PathVariable int id,Ronda ronda){
        Ronda rondaToBeUpdated=service.getById(id);
        BeanUtils.copyProperties(ronda,rondaToBeUpdated,"id");
        service.save(rondaToBeUpdated);
        ModelAndView result=showRondas();
        result.addObject("message", "La ronda se ha actualizado correctamente");
        return result;   
    }
    
    @Transactional(readOnly = true)
    @GetMapping("/new")
    public ModelAndView createRonda(){
        Ronda ronda=new Ronda();
        ModelAndView result=new ModelAndView(RONDAS_FORM);
        result.addObject("ronda", ronda);
        return result;
    }
    
    @Transactional
    @PostMapping("/new")
    public ModelAndView saveNewRonda(Ronda ronda, BindingResult br){
        service.save(ronda);
        ModelAndView result=showRondas();
        result.addObject("message", "La ronda ha sido creada correctamente");
        return result;
    }
}