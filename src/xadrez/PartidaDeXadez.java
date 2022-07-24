package xadrez;

import java.util.ArrayList;
import java.util.List;

import boardgame.Peca;
import boardgame.Position;
import boardgame.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadez {
	private int turno;
	private Cores jogadorAtual;
	private Tabuleiro tabuleiro;
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	
	public PartidaDeXadez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cores.WHITE;
		iniciarPartida();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cores getJogadorAtual() {
		return jogadorAtual;
	}
	
	public PecaDeXadrez[][] getPecas(){
		PecaDeXadrez[][] mat = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoDePatida) {
		Position position = posicaoDePatida.toPosition();
		validandoPosicao(position);
		return tabuleiro.peca(position).movimentosPossiveis();
	}
	
	public PecaDeXadrez performaceParaMover(PosicaoXadrez posicaoDeOrigem, PosicaoXadrez posicaoDeDestino) {
		Position partida = posicaoDeOrigem.toPosition();
		Position destino = posicaoDeDestino.toPosition();
		validandoPosicao(partida);
		validandoPosicaoDeDestino(partida, destino);
		Peca capturaPeca = Mover(partida, destino);
		proximaPartida();
		return (PecaDeXadrez)capturaPeca;
	}
	
	private Peca Mover(Position partida, Position destino) {
		Peca p = tabuleiro.removerPeca(partida);
		Peca capturaPeca = tabuleiro.removerPeca(destino);
		tabuleiro.lugarDaPeca(p, destino);
		//condicional para testar se o movimento capturou alguma peça adverssária
		if (capturaPeca != null) {
			pecasNoTabuleiro.remove(capturaPeca);
			pecasCapturadas.add(capturaPeca);
		}
		
		return  capturaPeca;
	}
	
	private void validandoPosicao(Position position) {
		if (!tabuleiro.verificandoPeca(position)) {
			throw new ExcecoesXadrez("Não existe peca na posicao de origem");
		}
		if (jogadorAtual != ((PecaDeXadrez)tabuleiro.peca(position)).getCores()) {
			throw new ExcecoesXadrez("A peca escolhida não é sua");
		}
		if (!tabuleiro.peca(position).verificaSeHaUmPossivelMovimento()) {
			throw new ExcecoesXadrez("Não existe movimentos possiveis para a peca escolhida");
		}
	}
	
	private void validandoPosicaoDeDestino(Position partida, Position destino) {
		if (!tabuleiro.peca(partida).possibilidadesDeMovimentos(destino)) {
			throw new ExcecoesXadrez("A peca escolhida não pode se mover para a posicão de destino");
		}
	}
	
	private void proximaPartida() {
		turno++;
		jogadorAtual = (jogadorAtual == Cores.WHITE) ? Cores.BLACK : Cores.WHITE;
	}
	
	private void colocandoNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.lugarDaPeca(peca, new PosicaoXadrez(coluna, linha).toPosition());
		pecasNoTabuleiro.add(peca);
	}
	
	private void iniciarPartida() {
		colocandoNovaPeca('c', 1, new Torre(tabuleiro, Cores.WHITE));
        colocandoNovaPeca('c', 2, new Torre(tabuleiro, Cores.WHITE));
        colocandoNovaPeca('d', 2, new Torre(tabuleiro, Cores.WHITE));
        colocandoNovaPeca('e', 2, new Torre(tabuleiro, Cores.WHITE));
        colocandoNovaPeca('e', 1, new Torre(tabuleiro, Cores.WHITE));
        colocandoNovaPeca('d', 1, new Rei(tabuleiro, Cores.WHITE));

        colocandoNovaPeca('c', 7, new Torre(tabuleiro, Cores.BLACK));
        colocandoNovaPeca('c', 8, new Torre(tabuleiro, Cores.BLACK));
        colocandoNovaPeca('d', 7, new Torre(tabuleiro, Cores.BLACK));
        colocandoNovaPeca('e', 7, new Torre(tabuleiro, Cores.BLACK));
        colocandoNovaPeca('e', 8, new Torre(tabuleiro, Cores.BLACK));
        colocandoNovaPeca('d', 8, new Rei(tabuleiro, Cores.BLACK));
		}
}
