package xadrez;

import boardgame.Position;

public class PosicaoXadrez {
	private char coluna;
	private int linha;
	
	public PosicaoXadrez(char coluna, int linha) {
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new ExcecoesXadrez("Erro instanciando PosicaoXadrez. Valores validos de a1 há h8");
		}
		this.coluna = coluna;
		this.linha = linha;
	}
	public char getColuna() {
		return coluna;
	}
	
	public int getLinha() {
		return linha;
	}
	
	protected Position toPosition() {
		return new Position(8 - linha, coluna - 'a');
	}
	
	protected static PosicaoXadrez haPosicao(Position position) {
		return new PosicaoXadrez((char)('a' + position.getColuna()), 8 - position.getLinha());
	}
	
	@Override
	public String toString() {
		return "" + coluna + linha;
	}
}
