/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mundowumpus;


public class Posicao {
    int linha, coluna;
    
    public Posicao(int row, int col){
        this.linha = row;
        this.coluna = col;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }
      
    @Override
    public boolean equals(Object ob) {
        //Compara se é a mesma posição
        Posicao posicaoComparar = (Posicao) ob;
        return (this.linha == posicaoComparar.linha)&&(this.coluna == posicaoComparar.coluna);
    }
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.linha;
        hash = 83 * hash + this.coluna;
        return hash;
    }
            
        
    
    
}
