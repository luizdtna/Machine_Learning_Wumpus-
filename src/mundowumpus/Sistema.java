package mundowumpus;

import java.awt.Frame;
import view.NewJDialog;

public class Sistema {

    public static void main(String[] args) {

        NewJDialog jD = new NewJDialog(new Frame(""), true);
        jD.setVisible(true);

        int i = NewJDialog.i;
        int j = NewJDialog.j;
        Matriz[][] mat;
        AmbienteMundo ambienteMundo = new AmbienteMundo();
        mat = ambienteMundo.armazenaNaMatriz(new Posicao(NewJDialog.x, NewJDialog.y), i, j, NewJDialog.wmp, NewJDialog.poc);

        Busca busc = new Busca(mat, new Posicao(NewJDialog.x, NewJDialog.y), i, j);
        busc.BuscarOuro();

    }
}
