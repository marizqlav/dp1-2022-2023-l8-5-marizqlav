package org.springframework.samples.idus_martii.statistics;

import org.springframework.beans.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.jugador.JugadorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

@Controller
@RequestMapping("/statistics/achievements")
public class AchievementController {
	
	 private final String MY_ACHIEVEMENTS_LISTING_VIEW="/achievements/MyAchievementListing";
	 private final String ACHIEVEMENTS_LISTING_VIEW="/achievements/AchievementListing";
	 private final String ACHIEVEMENTS_FORM="/achievements/createOrUpdateAchievementForm";
	 private final String ACHIEVEMENTS_JUGADOR="/achievements/AchievementsJugador";
	 
	    private AchievementService achievementService;
	    private JugadorService jSrevice;

	    @Autowired
	    public AchievementController(AchievementService service,JugadorService jSrevice){
	        this.achievementService=service;
	        this.jSrevice=jSrevice;
	    }
	    

	    @Transactional(readOnly = true)
	    @GetMapping("/manageAchievements")
	    public ModelAndView showAchievements(){
	        ModelAndView result=new ModelAndView(ACHIEVEMENTS_LISTING_VIEW);
	        result.addObject("achievements", achievementService.getAchievements());
	        return result;
	    }
	    @Transactional(readOnly = true)
	    @GetMapping("/")
	    public ModelAndView showPlayerAchievements(){
	        ModelAndView result=new ModelAndView(MY_ACHIEVEMENTS_LISTING_VIEW);
	        result.addObject("achievements", achievementService.getAchievements());
	        return result;
	    }
	    
	    
	    @Transactional()
	    @GetMapping("/{id}/delete")
	    public ModelAndView deleteAchievement(@PathVariable int id){
	        achievementService.deleteAchievementById(id);  
	        ModelAndView result=showAchievements();
	        result.addObject("message", "The achievement was delete successfully");
	        return result;
	    }

	    @Transactional(readOnly = true)
	    @GetMapping("/{id}/edit")
	    public ModelAndView editAchievement(@PathVariable int id){
	        Achievement achievement=achievementService.getById(id);        
	        ModelAndView result=new ModelAndView(ACHIEVEMENTS_FORM);
	        result.addObject("achievement", achievement);
	        return result;
	    }

	    @Transactional
	    @PostMapping("/{id}/edit")
	    public ModelAndView saveAchievement(@PathVariable int id,@Valid Achievement achievement, BindingResult br){
	        if(br.hasErrors()){
	            return new ModelAndView(ACHIEVEMENTS_FORM,br.getModel());            
	        }

	        Achievement achievementToBeUpdated=achievementService.getById(id);
	        BeanUtils.copyProperties(achievement,achievementToBeUpdated,"id");
	        achievementService.save(achievementToBeUpdated);
	        ModelAndView result=showAchievements();
	        result.addObject("message", "The achievement was updated successfully");
	        return result; 
	    }

	    @Transactional(readOnly = true)
	    @GetMapping("/new")
	    public ModelAndView createAchievement(){
	        Achievement achievement=new Achievement();
	        ModelAndView result=new ModelAndView(ACHIEVEMENTS_FORM);
	        result.addObject("achievement", achievement);
	        return result;
	    }
	    
	    @Transactional
	    @PostMapping("/new")
	    public ModelAndView saveNewAchievement(@Valid Achievement achievement){
	        achievementService.save(achievement);
	        ModelAndView result=showAchievements();
	        result.addObject("message", "The achievement was created successfully");
	        return result;
	    }
	    
	    
	    @Transactional(readOnly = true)
	    @GetMapping("/jugador/{jugadorId}")
	    public ModelAndView aschievementsPlayer(@PathVariable("jugadorId") int jugadorId){
	    	List<Achievement> achievements = achievementService.achievementsJugador(jSrevice.getJugadorById(jugadorId));
	        ModelAndView result=new ModelAndView(ACHIEVEMENTS_JUGADOR);
	        result.addObject("achievements", achievements);
	        result.addObject("jugador", jugadorId);
	        return result;
	    }
	    
	    
	    
	    

}
