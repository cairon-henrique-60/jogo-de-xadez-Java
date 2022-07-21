package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.ExcecoesXadrez;
import xadrez.PartidaDeXadez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PartidaDeXadez partidaDeXadez = new PartidaDeXadez();
		
		while (true) {
			try {
				UI.limpandoTerminal();
				UI.printPartida(partidaDeXadez.getPecas());
				System.out.println();
				System.out.print("Partida: ");
				PosicaoXadrez partida = UI.LerPosicaoDoXadrez(sc);
				
				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.LerPosicaoDoXadrez(sc);
				
				
				PecaDeXadrez capturaPeca = partidaDeXadez.performaceParaMover(partida, destino);
			}
			catch (ExcecoesXadrez e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
	}

}
