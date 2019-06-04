

package mundowumpus;

import java.util.ArrayList;
import static mundowumpus.Busca.TEM_OBSTACULO;

public class Poco {
    private final int qtd_Linha;
    private final int qtd_Colun;
    public Poco() {
        System.out.println(Busca.matrizAprendizagem.length);
        this.qtd_Linha = Busca.matrizAprendizagem.length;
        this.qtd_Colun = Busca.matrizAprendizagem[0].length;
    }
    
    public void pre_EliminacaoDeE_Poco(Posicao atual) {
        ArrayList<Posicao> arrayy;
        Posicao teste = new Posicao(atual.linha, atual.coluna);
        if (atual.getLinha() - 1 >= 0) {

            arrayy = Busca.matrizAprendizagem[atual.linha - 1][atual.coluna].getTalvezPoco();
            if (arrayy.contains(new Posicao(atual.linha - 1, atual.coluna))) {
                //Posicao atual está marcado como pode haver Poco
                System.out.println("Posicao nao tem Poco");
                removePossibilidadePoco(arrayy, new Posicao(atual.linha - 1, atual.coluna));
                //Acho que essa parte daqeui pra baixo não precisa
                arrayy.remove(new Posicao(atual.linha - 1, atual.coluna));
                if (arrayy.size() == 1) {
                    Posicao posicaoObst = arrayy.get(0);
                    Busca.matrizAprendizagem[posicaoObst.linha][posicaoObst.coluna].setPoco(TEM_OBSTACULO);
                }
            }
            Busca.matrizAprendizagem[atual.linha - 1][atual.coluna].setPoco(Busca.NAO_TEM_OBSTACULO);
        }
        if (atual.getLinha() + 1 < this.qtd_Linha) {

            arrayy = Busca.matrizAprendizagem[atual.linha + 1][atual.coluna].getTalvezPoco();
            // arrayy.add(new Posicao(atual.linha + 1, atual.coluna));
            if (arrayy.contains(new Posicao(atual.linha + 1, atual.coluna))) {
                System.out.println("Posicao nao tem Poco");
                removePossibilidadePoco(arrayy, new Posicao(atual.linha + 1, atual.coluna));
                arrayy.remove(new Posicao(atual.linha + 1, atual.coluna));
                if (arrayy.size() == 1) {
                    Posicao posicaoObst = arrayy.get(0);
                    Busca.matrizAprendizagem[posicaoObst.linha][posicaoObst.coluna].setPoco(TEM_OBSTACULO);
                }
            }
            Busca.matrizAprendizagem[atual.linha + 1][atual.coluna].setPoco(Busca.NAO_TEM_OBSTACULO);
        }
        if (atual.getColuna() - 1 >= 0) {

            arrayy = Busca.matrizAprendizagem[atual.linha][atual.coluna - 1].getTalvezPoco();
            if (arrayy.contains(new Posicao(atual.linha, atual.coluna - 1))) {
                System.out.println("Posicao nao tem Poco");
                removePossibilidadePoco(arrayy, new Posicao(atual.linha - 1, atual.coluna));
                arrayy.remove(new Posicao(atual.linha, atual.coluna - 1));
                if (arrayy.size() == 1) {
                    Posicao posicaoObst = arrayy.get(0);
                    Busca.matrizAprendizagem[posicaoObst.linha][posicaoObst.coluna].setPoco(TEM_OBSTACULO);
                }
            }
            Busca.matrizAprendizagem[atual.linha][atual.coluna - 1].setPoco(Busca.NAO_TEM_OBSTACULO);
        }
        if (atual.getColuna() + 1 < this.qtd_Colun) {

            arrayy = Busca.matrizAprendizagem[atual.linha][atual.coluna + 1].getTalvezPoco();
            if (arrayy.contains(new Posicao(atual.linha, atual.coluna + 1))) {
                System.out.println("Posicao nao tem Poco");
                removePossibilidadePoco(arrayy, new Posicao(atual.linha + 1, atual.coluna));
                arrayy.remove(new Posicao(atual.linha, atual.coluna + 1));
                if (arrayy.size() == 1) {
                    Posicao posicaoObst = arrayy.get(0);
                    Busca.matrizAprendizagem[posicaoObst.linha][posicaoObst.coluna].setPoco(TEM_OBSTACULO);
                }
            }
            Busca.matrizAprendizagem[atual.linha][atual.coluna + 1].setPoco(Busca.NAO_TEM_OBSTACULO);
        }
    }

    private void removePossibilidadePoco(ArrayList<Posicao> arrayPosic, Posicao posicaoRemover) {
        Posicao[] aux = new Posicao[arrayPosic.size()];
        int i = 0;
        for (Posicao obj : arrayPosic) {
            aux[i] = new Posicao(obj.linha, obj.coluna);
            i++;
        }

        for (int j = 0; j < arrayPosic.size(); j++) {
            Busca.matrizAprendizagem[aux[j].linha][aux[j].coluna].getTalvezPoco().remove(posicaoRemover);
            System.out.println("removeu em: " + aux[j].linha + "," + aux[j].coluna);
        }

    }

    public void ProbabilidadePocoAdjacentes(Posicao atual) {
        ArrayList<Posicao> arrayPossibilidades = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            //Precisa do Loop para poder salvar o Array em todas as posições onde pode haver Poco

            if (atual.getLinha() - 1 >= 0) {
                //adicionar ne setTalvezObstaculo no index 0 = posição a cima
                if (i != 1) {
                    arrayPossibilidades.add(new Posicao(atual.linha - 1, atual.coluna));
                }
                Busca.matrizAprendizagem[atual.linha - 1][atual.coluna].setTalvezPoco(arrayPossibilidades);

            }//else{
//            arrayPossibilidades[0] = ;
//        }
            if (atual.getLinha() + 1 < this.qtd_Linha) {
                //adicionar ne setTalvezObstaculo no index 1 = posição a baixo
                if (i != 1) {
                    arrayPossibilidades.add(new Posicao(atual.linha + 1, atual.coluna));
                }
                Busca.matrizAprendizagem[atual.linha + 1][atual.coluna].setTalvezPoco(arrayPossibilidades);

            }
            if (atual.getColuna() - 1 >= 0) {
                //adicionar ne setTalvezObstaculo no index 2 = posição a esquerda
                if (i != 1) {
                    arrayPossibilidades.add(new Posicao(atual.linha, atual.coluna - 1));
                }
                Busca.matrizAprendizagem[atual.linha][atual.coluna - 1].setTalvezPoco(arrayPossibilidades);

            }
            if (atual.getColuna() + 1 < this.qtd_Colun) {
                //adicionar ne setTalvezObstaculo no index 3 = posição a esquerda
                if (i != 1) {
                    arrayPossibilidades.add(new Posicao(atual.linha, atual.coluna + 1));
                }
                Busca.matrizAprendizagem[atual.linha][atual.coluna + 1].setTalvezPoco(arrayPossibilidades);
            }

        }

        pos_EliminacaoDeE_Poco(arrayPossibilidades);

    }

    private ArrayList<Posicao> pos_EliminacaoDeE_Poco(ArrayList<Posicao> newLista) {
        ArrayList<Posicao> posRemover = new ArrayList<>();
        //Se não tiver obstaculo retira a posição da lista, se tiver ou não saber se tem, deixa na lista
        for (Posicao obj : newLista) {
            if (Busca.matrizAprendizagem[obj.linha][obj.coluna].getPoco()== Busca.NAO_TEM_OBSTACULO) {
                //Não tem Poco
                //newLista.remove(obj);
                System.out.println("Comfirmação que não tem Poco : " + obj.linha + "," + obj.coluna);
                posRemover.add(obj);

            }

        }
        newLista.removeAll(posRemover);
        if (newLista.size() == 1) {
            System.out.println("Existe Poco certeza : " + newLista.get(0).linha + "," + newLista.get(0).coluna);
            Busca.matrizAprendizagem[newLista.get(0).linha][newLista.get(0).coluna].setPoco(TEM_OBSTACULO);
        }
        return newLista;

    }
}
