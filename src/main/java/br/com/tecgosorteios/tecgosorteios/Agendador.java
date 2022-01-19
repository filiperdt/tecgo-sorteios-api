package br.com.tecgosorteios.tecgosorteios;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.tecgosorteios.tecgosorteios.repository.SorteioRepository;

@Component
public class Agendador extends TimerTask {
	@Autowired
	private SorteioRepository sorteioRepository;
	
	private LocalDateTime dataAtual;
	private LocalDateTime dataDoSorteio;
	
	String stringDataDoSorteio = "18/01/2022" + " 21:58:00";
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	@Override
	public void run() {
		dataAtual = LocalDateTime.now();
		dataDoSorteio = LocalDateTime.parse(stringDataDoSorteio, formatter);
		
		String str = sorteioRepository.findAll().get(0).equals(dataAtual)? "Mesmo dia" : "Dia diferente";
		
		System.out.println(str);
	}
}
