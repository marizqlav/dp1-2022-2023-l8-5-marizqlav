package org.springframework.samples.petclinic.statistics;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.persistence.Table;

import org.springframework.samples.petclinic.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
//@Table(name = "Achievement")

public class Achievement extends NamedEntity{
	
	 @NotBlank
	private String description;
	private String badgeImage;
	@Min(0)
	private double threshold;
	
	public String getActualDescription(){
        return description.replace("<THRESHOLD>",String.valueOf(threshold));
    }
	
	

}
