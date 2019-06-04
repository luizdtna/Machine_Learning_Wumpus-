package mundowumpus;

import java.util.ArrayList;
import static mundowumpus.Busca.TEM_OBSTACULO;

public class Wumpus {
    private final int qtd_Linha;
    private final int qtd_Colun;
    public Wumpus() {
        this.qtd_Linha = Busca.matrizAprendizagem.length;
        this.qtd_Colun = Busca.matrizAprendizagem[0].length;
    }
    
    public void pre_EliminacaoDeE_Wumpus(Posicao atual) {
        ArrayList<Posicao> arrayy;
        Posicao teste = new Posicao(atual.linha, atual.coluna);
        if (atual.getLinha() - 1 >= 0) {

            arrayy = Busca.matrizAprendizagem[atual.linha - 1][atual.coluna].getTalvezWumpus();
            if (arrayy.contains(new Posicao(atual.linha - 1, atual.coluna))) {
                //Posicao atual está marcada como pode haver wumpus
                removePossibilidadeWumpus(arrayy, new Posicao(atual.linha - 1, atual.coluna));
                //Então é removido essa posição da lista de possibilidade, pois se sabe que não contem Wumpus
                arrayy.remove(new Posicao(atual.linha - 1, atual.coluna));
                if (arrayy.size() == 1) {
                    //Se sobrar somente uma posição no vetor, então e a posição do obstaculo
                    Posicao posicaoObst = arrayy.get(0);
                    //Marca como tem obstaculo
                    Busca.matrizAprendizagem[posicaoObst.linha][posicaoObst.coluna].setWumpus(TEM_OBSTACULO);
                }
            }
            //Marca a posição como não tem obstaculo
            Busca.matrizAprendizagem[atual.linha - 1][atual.coluna].setWumpus(Busca.NAO_TEM_OBSTACULO);
        }
        if (atual.getLinha() + 1 < this.qtd_Linha) {

            arrayy = Busca.matrizAprendizagem[atual.linha + 1][atual.coluna].getTalvezWumpus();
            if (arrayy.contains(new Posicao(atual.linha + 1, atual.coluna))) {
                removePossibilidadeWumpus(arrayy, new Posicao(atual.linha + 1, atual.coluna));
                arrayy.remove(new Posicao(atual.linha + 1, atual.coluna));
                if (arrayy.size() == 1) {
                    Posicao posicaoObst = arrayy.get(0);
                    Busca.matrizAprendizagem[posicaoObst.linha][posicaoObst.coluna].setWumpus(TEM_OBSTACULO);
                }
            }
            Busca.matrizAprendizagem[atual.linha + 1][atual.coluna].setWumpus(Busca.NAO_TEM_OBSTACULO);
        }
        if (atual.getColuna() - 1 >= 0) {

            arrayy = Busca.matrizAprendizagem[atual.linha][atual.coluna - 1].getTalvezWumpus();
            if (arrayy.contains(new Posicao(atual.linha, atual.coluna - 1))) {
                removePossibilidadeWumpus(arrayy, new Posicao(atual.linha - 1, atual.coluna));
                arrayy.remove(new Posicao(atual.linha, atual.coluna - 1));
                if (arrayy.size() == 1) {
                    Posicao posicaoObst = arrayy.get(0);
                    Busca.matrizAprendizagem[posicaoObst.linha][posicaoObst.coluna].setWumpus(TEM_OBSTACULO);
                }
            }
            Busca.matrizAprendizagem[atual.linha][atual.coluna - 1].setWumpus(Busca.NAO_TEM_OBSTACULO);
        }
        if (atual.getColuna() + 1 < this.qtd_Colun) {

            arrayy = Busca.matrizAprendizagem[atual.linha][atual.coluna + 1].getTalvezWumpus();
            if (arrayy.contains(new Posicao(atual.linha, atual.coluna + 1))) {
                removePossibilidadeWumpus(arrayy, new Posicao(atual.linha + 1, atual.coluna));
                arrayy.remove(new Posicao(atual.linha, atual.coluna + 1));
                if (arrayy.size() == 1) {
                    Posicao posicaoObst = arrayy.get(0);
                    Busca.matrizAprendizagem[posicaoObst.linha][posicaoObst.coluna].setWumpus(TEM_OBSTACULO);
                }
            }
            Busca.matrizAprendizagem[atual.linha][atual.coluna + 1].setWumpus(Busca.NAO_TEM_OBSTACULO);
        }
    }

    private void removePossibilidadeWumpus(ArrayList<Posicao> arrayTalvezWumpus, Posicao posicaoRemover) {
        Posicao[] aux = new Posicao[arrayTalvezWumpus.size()];
        int i = 0;
        for (Posicao obj : arrayTalvezWumpus) {
            aux[i] = new Posicao(obj.linha, obj.coluna);
            i++;
        }

        for (int j = 0; j < arrayTalvezWumpus.size(); j++) {
            //remove a posição que descobriu que não tem Wumpus
            Busca.matrizAprendizagem[aux[j].linha][aux[j].coluna].getTalvezWumpus().remove(posicaoRemover);
        }

    }

    public void ProbabilidadeWumpusAdjacentes(Posicao atual) {
        //Vai adicionar a possibilidade de ter Wumpus nas posições adjacentes
        ArrayList<Posicao> posicoesTalvezObstaculo = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            //Precisa do Loop para poder salvar o 'arrayPossibilidades' 
                //em todas as posições onde pode haver Wumpus

            if (atual.getLinha() - 1 >= 0) {
                if (i != 1) {
                    posicoesTalvezObstaculo.add(new Posicao(atual.linha - 1, atual.coluna));
                }
                Busca.matrizAprendizagem[atual.linha - 1][atual.coluna].setTalvezWumpus(posicoesTalvezObstaculo);

            }
            if (atual.getLinha() + 1 < this.qtd_Linha) {
                if (i != 1) {
                    posicoesTalvezObstaculo.add(new Posicao(atual.linha + 1, atual.coluna));
                }
                Busca.matrizAprendizagem[atual.linha + 1][atual.coluna].setTalvezWumpus(posicoesTalvezObstaculo);

            }
            if (atual.getColuna() - 1 >= 0) {
                if (i != 1) {
                    posicoesTalvezObstaculo.add(new Posicao(atual.linha, atual.coluna - 1));
                }
                Busca.matrizAprendizagem[atual.linha][atual.coluna - 1].setTalvezWumpus(posicoesTalvezObstaculo);

            }
            if (atual.getColuna() + 1 < this.qtd_Colun) {
                if (i != 1) {
                    posicoesTalvezObstaculo.add(new Posicao(atual.linha, atual.coluna + 1));
                }
                Busca.matrizAprendizagem[atual.linha][atual.coluna + 1].setTalvezWumpus(posicoesTalvezObstaculo);
            }

        }
        
        pos_EliminacaoDeE_Wumpus(posicoesTalvezObstaculo);

    }

    private ArrayList<Posicao> pos_EliminacaoDeE_Wumpus(ArrayList<Posicao> newArrayTalvezObstaculo) {
        //Vai armazenar as posições que não tem Wumpus
        ArrayList<Posicao> posRemover = new ArrayList<>();
        
        for (Posicao obj : newArrayTalvezObstaculo) {
            //Vai verificar em todas as posições do array 
            if (Busca.matrizAprendizagem[obj.linha][obj.coluna].getWumpus() == Busca.NAO_TEM_OBSTACULO) {
                //Não tem obstaculo, então retira a posição da lista
                posRemover.add(obj);

            }

        }
        //Remove as posições que tem certeza que não há Wumpus
        newArrayTalvezObstaculo.removeAll(posRemover);
        if (newArrayTalvezObstaculo.size() == 1) {
            //Se sobrar 1, quer dizer e a posição do Wumpus
            Busca.matrizAprendizagem[newArrayTalvezObstaculo.get(0).linha][newArrayTalvezObstaculo.get(0).coluna].setWumpus(TEM_OBSTACULO);
        }
        return newArrayTalvezObstaculo;

    }
}
