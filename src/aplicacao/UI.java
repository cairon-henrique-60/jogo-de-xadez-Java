package aplicacao;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Cores;
import xadrez.PartidaDeXadez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;

public class UI {
//Cores dos textos
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
//Cores do  fundo
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	//funaco para limpar o terminal a cada jogadad
	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void limpandoTerminal() {
		 System.out.print("\033[H\033[2J");
		 System.out.flush();
	} 
	
	public static PosicaoXadrez LerPosicaoDoXadrez(Scanner sc) {
		try {
			String s = sc.nextLine();
			char coluna = s.charAt(0);
			//recotando o String apartir da posição 1 e covertendo para inteiro
			int linha = Integer.parseInt(s.substring(1));
			return new PosicaoXadrez(coluna, linha);
		}
		catch (RuntimeException e) {
			throw new InputMismatchException("Erro lendo pisição de xadrez. Valores válidos são de a1 até h8");
		}
	}
	
	public static void printXabrez(PartidaDeXadez partidaDeXadez, List<PecaDeXadrez> captura) {
		printPartida(partidaDeXadez.getPecas());
		System.out.println();
		printaAsPecasCapturadas(captura);
		System.out.println();
		System.out.println("Turno: " + partidaDeXadez.getTurno());
		if (!partidaDeXadez.getCheckMate()) {
			System.out.println("Aguardando jogador: " + partidaDeXadez.getJogadorAtual());
			if (partidaDeXadez.getCheck()) {
				System.out.println("CHECK!");
			}
		}
		else {
			System.out.println("CHECKMATE!");
			System.out.println("Vencedor: " + partidaDeXadez.getJogadorAtual());
		}
	}
	
	public static void printPartida(PecaDeXadrez[][] pecas) {
		for (int i=0; i<pecas.length; i++) {
			System.out.print((8-i) + " ");
			for (int j=0; j<pecas.length; j++) {
				printPeca(pecas[i][j], false);
			}
			System.out.println();
		}
		System.out.print("  a b c d e f g h");
	}
	
	public static void printPartida(PecaDeXadrez[][] pecas, boolean[][] movimentosPossiveis) {
		for (int i=0; i<pecas.length; i++) {
			System.out.print((8-i) + " ");
			for (int j=0; j<pecas.length; j++) {
				printPeca(pecas[i][j], movimentosPossiveis[i][j]);
			}
			System.out.println();
		}
		System.out.print("  a b c d e f g h");
	}
	
	private static void printPeca(PecaDeXadrez peca, boolean background) {
		if (background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (peca == null) {
			System.out.print("-" + ANSI_RESET);
		}
		else {
			 if (peca.getCores() == Cores.WHITE) {
	                System.out.print(ANSI_WHITE + peca + ANSI_RESET);
	            }
	            else {
	                System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
	            }
		}
		System.out.print(" ");
	}
	
	
	
	//metodo para imprimir a lista de pecas capituradas
	private static void printaAsPecasCapturadas(List<PecaDeXadrez> captura) {
		List<PecaDeXadrez> white = captura.stream().filter(x -> x.getCores() == Cores.WHITE).collect(Collectors.toList());
		List<PecaDeXadrez> black = captura.stream().filter(x -> x.getCores() == Cores.BLACK).collect(Collectors.toList());
		System.out.println("Peças capturadas:");
		System.out.print("White: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(white.toArray()));
		System.out.print(ANSI_RESET);
		System.out.print("Black: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(black.toArray()));
		System.out.print(ANSI_RESET);
	}
}
