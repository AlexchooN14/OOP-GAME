package map;
import javafx.scene.layout.Pane;
import sample.*;

import java.util.ArrayList;

public class Grid {

    Pane layer;

    Cell[][] mapCell;//Creating cell array

    public ArrayList<Cell> mapArraylist=new ArrayList<>();

    public Grid(){

        mapCell = new Cell [(int) Settings.ROW_CELL_COUNT] [(int)Settings.COLUMN_CELL_COUNT];
        for(int i = 0; i < mapCell.length; i++){
            for(int j = 0; j < mapCell[i].length; j++){
                mapCell[i][j]=new Cell(i*64,j*64,64,64,1);
            }
        }
    }

    public Grid(int[][] newMap){
        mapCell = new Cell [(int) Settings.ROW_CELL_COUNT] [(int)Settings.COLUMN_CELL_COUNT];

        for(int i=0;i<mapCell.length;i++){
            for(int j=0;j<mapCell[i].length;j++){

                switch (newMap[j][i]){
                    case 0:
                        mapCell[i][j]=new Cell(i*64,j*64,64,64,0);
                        break;
                    case 1:
                        mapCell[i][j]=new Cell(i*64,j*64,64,64,1);
                }
            }
        }
    }

    public void Draw(Pane layer){
        this.layer=layer;

        for(int i = 0 ; i < mapCell.length; i++){
            for(int j = 0; j < mapCell[i].length; j++){

                Cell cell=mapCell[i][j];
                mapArraylist.add(cell);

            }
        }
        for(int i = 0; i < mapArraylist.size(); i++){
            layer.getChildren().add(mapArraylist.get(i).getRect());
        }

    }
}
