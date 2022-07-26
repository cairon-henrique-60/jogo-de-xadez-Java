package xadrez.pecas;

import boardgame.Position;
import boardgame.Tabuleiro;
import xadrez.Cores;
import xadrez.PartidaDeXadez;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez{
	
	private PartidaDeXadez partidaDeXadez;

	public Peao(Tabuleiro tabuleiro, Cores cores, PartidaDeXadez partidaDeXadez) {
		super(tabuleiro, cores);
		this.partidaDeXadez = partidaDeXadez;
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
			
			//#jogada especial en passant pecas brancas
			if (position.getLinha() == 3) {
				Position esquerda = new Position(position.getLinha(), position.getColuna()-1);
				if (getTabuleiro().posicaoExistente(esquerda) && verificaSeHaPecaAdversarioNaPosicao(esquerda) && getTabuleiro().peca(esquerda) == partidaDeXadez.getEnPassantVuneravel()) {
					mat[esquerda.getLinha()-1][esquerda.getColuna()] = true;
				}
				
				Position direita = new Position(position.getLinha(), position.getColuna()+1);
				if (getTabuleiro().posicaoExistente(direita) && verificaSeHaPecaAdversarioNaPosicao(direita) && getTabuleiro().peca(direita) == partidaDeXadez.getEnPassantVuneravel()) {
					mat[direita.getLinha()-1][direita.getColuna()] = true;
				}
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
			
			//#jogada especial en passant pecas pretas
			if (position.getLinha() == 4) {
				Position esquerda = new Position(position.getLinha(), position.getColuna()-1);
				if (getTabuleiro().posicaoExistente(esquerda) && verificaSeHaPecaAdversarioNaPosicao(esquerda) && getTabuleiro().peca(esquerda) == partidaDeXadez.getEnPassantVuneravel()) {
					mat[esquerda.getLinha()+1][esquerda.getColuna()] = true;
				}
				
				Position direita = new Position(position.getLinha(), position.getColuna()+1);
				if (getTabuleiro().posicaoExistente(direita) && verificaSeHaPecaAdversarioNaPosicao(direita) && getTabuleiro().peca(direita) == partidaDeXadez.getEnPassantVuneravel()) {
					mat[direita.getLinha()+1][direita.getColuna()] = true;
				}
			}
			
		}
		
		return mat;
	}
	
	@Override
	public String toString() {
		return "p";
	}

}
