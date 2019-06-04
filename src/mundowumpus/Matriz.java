/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mundowumpus;

public class Matriz {

    private Posicao posicao;
    private boolean wumpus;
    private boolean fedor;
    private boolean poco;
    private boolean brisa;
    private boolean brilho;

    public Matriz() {
        this.wumpus = false;
        this.fedor = false;
        this.poco = false;
        this.brisa = false;
        this.brilho = false;
    }

    public Posicao getPosicao() {
        return posicao;
    }

    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
    }

    public boolean isWumpus() {
        return this.wumpus;
    }

    public void setWumpus(boolean wumpus) {
        this.wumpus = wumpus;
    }

    public boolean isFedor() {
        return fedor;
    }

    public void setFedor(boolean fedor) {
        this.fedor = fedor;
    }

    public boolean isPoco() {
        return poco;
    }

    public void setPoco(boolean poco) {
        this.poco = poco;
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
