package boardgame;

public class Tabuleiro {
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		if(linhas < 1 || colunas < 1) {
			throw new ExcecoesDoTabuleiro("Erro na criação do tabuleiro: no minimo uma linha e uma coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}
	public int getLinhas() {
		return linhas;
	}
	
	public int getColunas() {
		return colunas;
	}
	
	public Peca peca(int linha, int coluna) {
		if(!posicaoExistente(linha, coluna)) {
			throw new ExcecoesDoTabuleiro("Posição não existe no tabuleiro!");
		}
		return pecas[linha][coluna];
	}
	
	public Peca peca(Position position) {
		if(!posicaoExistente(position)) {
			throw new ExcecoesDoTabuleiro("Posição não existe no tabuleiro!");
		}
		return pecas[position.getLinha()][position.getColuna()];
	}
	
	public void lugarDaPeca(Peca peca, Position position) {
		if(verificandoPeca(position)) {
			throw new ExcecoesDoTabuleiro("Já existe uma peça nessa posição! " + position);
		}
		pecas[position.getLinha()][position.getColuna()] = peca;
		peca.positon = position;
	}
	
	private boolean posicaoExistente(int linha, int coluna) {
		return linha >=0 && linha < linhas && coluna >=0 && coluna < colunas;
	}
	
	public boolean posicaoExistente(Position position) {
		return posicaoExistente(position.getLinha(), position.getColuna());
	}
	
	public boolean verificandoPeca(Position position) {
		if(!posicaoExistente(position)) {
			throw new ExcecoesDoTabuleiro("Posição não existe no tabuleiro! " + position);
		}
		return peca(position) != null;
	}
}
