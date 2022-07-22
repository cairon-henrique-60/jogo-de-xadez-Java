package xadrez;

import boardgame.Peca;
import boardgame.Tabuleiro;

public abstract class PecaDeXadrez extends Peca{
	private Cores cores;

	public PecaDeXadrez(Tabuleiro tabuleiro, Cores cores) {
		super(tabuleiro);
		this.cores = cores;
	}

	public Cores getCores() {
		return cores;
	}

	
}
