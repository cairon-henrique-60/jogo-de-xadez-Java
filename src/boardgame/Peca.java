package boardgame;

public class Peca {
	protected Position position;
	private Tabuleiro tabuleiro;
	
	/*
	 * OBS: a posicao sera passado como valor nulo
	 */	
	
	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	protected Position getPositon() {
		return position;
	}
}
