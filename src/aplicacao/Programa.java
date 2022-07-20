package aplicacao;

import xadrez.PartidaDeXadez;

public class Programa {

	public static void main(String[] args) {
		PartidaDeXadez partidaDeXadez = new PartidaDeXadez();
		UI.printPartida(partidaDeXadez.getPecas());
	}

}
