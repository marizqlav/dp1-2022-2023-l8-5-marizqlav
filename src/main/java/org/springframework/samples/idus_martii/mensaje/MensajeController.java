package org.springframework.samples.idus_martii.mensaje;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/mensajes")
public class MensajeController {

  
    private MensajeService mensajeService;

    @Autowired
    public MensajeController(MensajeService mensajeService){
        this.mensajeService=mensajeService;
    }

    
}