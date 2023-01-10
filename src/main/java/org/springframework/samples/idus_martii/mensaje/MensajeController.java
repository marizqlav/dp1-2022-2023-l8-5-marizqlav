package org.springframework.samples.idus_martii.mensaje;

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
@RequestMapping("/mensajes")
public class MensajeController {

  
    private MensajeService mensajeService;

    @Autowired
    public MensajeController(MensajeService mensajeService){
        this.mensajeService=mensajeService;
    }

    
}