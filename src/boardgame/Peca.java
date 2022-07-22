package boardgame;

public abstract class Peca {
	protected Position position;
	private Tabuleiro tabuleiro;
	
	/*
	 * OBS: a posicao sera passado como valor nulo
	 */	
	
	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		position = null;
	}

	protected Position getPositon() {
		return position;
	}
	
	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	public abstract boolean[][] movimentosPossiveis();
	
	//hook metods(metodos de gancho)é um metodo que faz um gancho com a sub classe
	public boolean possibilidadesDeMovimentos(Position position) {
		return movimentosPossiveis()[position.getLinha()][position.getColuna()];
	}
	
	public boolean verificaSeHaUmPossivelMovimento() {
		boolean[][] mat = movimentosPossiveis();
		for(int i=0; i<mat.length; i++) {
			for(int j=0; j<mat.length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}

}
