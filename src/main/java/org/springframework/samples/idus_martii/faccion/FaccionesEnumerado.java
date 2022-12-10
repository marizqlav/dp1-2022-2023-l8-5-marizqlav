package org.springframework.samples.idus_martii.faccion;

public enum FaccionesEnumerado {
	
	Leal {
		public String toString() {
			return "Leal";
		}
	},
	Traidor {
		public String toString() {
			return "Traidor";
		}
	}, 
	Mercader {
		public String toString() {
			return "Mercader";
		}
	};

}
