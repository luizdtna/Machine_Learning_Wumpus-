
package mundowumpus;

import java.util.ArrayList;


public class MatrizAprendizagem {

    private Posicao posicao;
    // false == : posição inexplorada | true == caminhou pra ela
    private boolean explorada;
    // int == 0: posição inexplorada | int == 1: Não tem obstaculo | int == 2: Tem obstaculo
    private int wumpus;
    private int poco;
    private ArrayList<Posicao> talvezWumpus;
    private boolean fedor;
    private ArrayList<Posicao> talvezPoco;
    private boolean brisa;
    private boolean brilho;
    //O peso vai ajudar a definir se a posição ja foi muito explorada
    private int peso;

    public MatrizAprendizagem(Posicao newPosicao) {
     //inicia com 0 porque não foi explorado
     this.wumpus = 0;
     this.poco = 0;
     this.talvezWumpus = new ArrayList<>();
     this.talvezPoco = new ArrayList<>();
     this.posicao = new Posicao(newPosicao.linha, newPosicao.coluna);
    }

    public int getPeso() {
        return peso;
    }

    public void setSomaPeso(int peso) {
        //Soma o peso
        this.peso += peso;
    }
    
    
    public Posicao getPosicao() {
        return posicao;
    }

    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
    }

    public boolean isExplorada() {
        return explorada;
    }

    public void setExplorada(boolean explorada) {
        this.explorada = explorada;
    }
    
    public int getWumpus() {
        return wumpus;
    }

    public void setWumpus(int wumpus) {
        this.wumpus = wumpus;
    }

    public int getPoco() {
        return this.poco;
    }

    public void setPoco(int poco) {
        this.poco = poco;
    }

    public ArrayList<Posicao> getTalvezWumpus() {
        return talvezWumpus;
    }

    public void setTalvezWumpus(ArrayList<Posicao> newAtual) {
        this.talvezWumpus = newAtual;
    }

    public boolean isFedor() {
        return fedor;
    }

    public void setFedor(boolean fedor) {
        this.fedor = fedor;
    }

    public ArrayList<Posicao> getTalvezPoco() {
        return talvezPoco;
    }

    public void setTalvezPoco(ArrayList<Posicao> newAtual){
        this.talvezPoco = newAtual;
    }

    public boolean isBrisa() {
        return brisa;
    }

    public void setBrisa(boolean brisa) {
        this.brisa = brisa;
    }

    public boolean isBrilho() {
        return brilho;
    }

    public void setBrilho(boolean brilho) {
        this.brilho = brilho;
    }
    
}
