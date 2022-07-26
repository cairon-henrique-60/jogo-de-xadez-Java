package xadrez.pecas;

import boardgame.Position;
import boardgame.Tabuleiro;
import xadrez.Cores;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez{

	public Peao(Tabuleiro tabuleiro, Cores cores) {
		super(tabuleiro, cores);
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Position p = new Position(0, 0);
		
		//lógica para os movimentos das pecas da cor branca
		if (getCores() == Cores.WHITE) {
			p.setValor(position.getLinha()-1, position.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().verificandoPeca(p)){
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValor(position.getLinha()-2, position.getColuna());
			Position p2 = new Position(position.getLinha()-1, position.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().verificandoPeca(p) && getTabuleiro().posicaoExistente(p2) && !getTabuleiro().verificandoPeca(p2) && getContadorDeMovimentos() == 0){
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValor(position.getLinha()-1, position.getColuna()-1);
			if (getTabuleiro().posicaoExistente(p) && verificaSeHaPecaAdversarioNaPosicao(p)){
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValor(position.getLinha()-1, position.getColuna()+1);
			if (getTabuleiro().posicaoExistente(p) && verificaSeHaPecaAdversarioNaPosicao(p)){
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}
		else {
			p.setValor(position.getLinha()+1, position.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().verificandoPeca(p)){
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValor(position.getLinha()+2, position.getColuna());
			Position p2 = new Position(position.getLinha()+1, position.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().verificandoPeca(p) && getTabuleiro().posicaoExistente(p2) && !getTabuleiro().verificandoPeca(p2) && getContadorDeMovimentos() == 0){
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValor(position.getLinha()+1, position.getColuna()-1);
			if (getTabuleiro().posicaoExistente(p) && verificaSeHaPecaAdversarioNaPosicao(p)){
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValor(position.getLinha()+1, position.getColuna()+1);
			if (getTabuleiro().posicaoExistente(p) && verificaSeHaPecaAdversarioNaPosicao(p)){
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}
		
		return null;
	}

}
