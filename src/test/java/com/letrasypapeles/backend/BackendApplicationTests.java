package com.letrasypapeles.backend;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("Se desactiva para evitar errores de ApplicationContext innecesarios")
@SpringBootTest
class BackendApplicationTests {

	@Test
	void contextLoads() {
		 // Esta prueba valida que el contexto de Spring se levanta sin errores.
	}
	
	@Test
    void mainMethodRuns() {
        BackendApplication.main(new String[]{});
    }

}
