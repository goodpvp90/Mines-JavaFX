package mines;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MyController {

	int w, m, h;

	@FXML
	private btns btnsmat[][];
	Mines minesweeper = new Mines(10, 10, 10);
	private MediaPlayer mediaPlayer;
    Media media = new Media(getClass().getResource("Clown.mp3").toString());
    
    
	@FXML
	private GridPane gridPane;

	@FXML
	private TextField height;

	@FXML
	private TextField mines;

	@FXML
	private Button reset;

	@FXML
	private TextField width;

	public void setGrid(GridPane g) {
		gridPane = g;
	}

	@FXML
	public void reset(ActionEvent event) {
	    mediaPlayer = new MediaPlayer(media);
	    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
	    mediaPlayer.play();
		h = Integer.parseInt(height.getText());
		w = Integer.parseInt(width.getText());
		m = Integer.parseInt(mines.getText());
		minesweeper = new Mines(h, w, m);
		gridPane.getChildren().clear();
		btnsmat = new btns[h][w];
		for (int row = 0; row < h; row++) {
			for (int col = 0; col < w; col++) {
				btnsmat[row][col] = new btns(".", row, col);
				btnsmat[row][col].setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						btns button = (btns) event.getSource();
						if (event.getButton() == MouseButton.PRIMARY) {
							// Handle left-click event
							if (minesweeper.open(button.row, button.col)) {
								// Update the buttons based on the game state
								for (int i = 0; i < h; i++)
									for (int j = 0; j < w; j++) 						
										btnsmat[i][j].setText(minesweeper.get(i, j));
								if (minesweeper.isDone())
									windowPopUp(); // Show popup when the game is won
							} else {
								// Handle the case when a mine is clicked
								minesweeper.setShowAll(true);
								for (int i = 0; i < h; i++)
									for (int j = 0; j < w; j++)
										btnsmat[i][j].setText(minesweeper.get(i, j));
							}
						} else if (event.getButton() == MouseButton.SECONDARY) {
							// Handle right-click event
							minesweeper.toggleFlag(button.row, button.col);
							if(minesweeper.get(button.row, button.col).equals("F"))
								btnsmat[button.row][button.col].setStyle("-fx-text-fill: red;");
							else
								btnsmat[button.row][button.col].setStyle("-fx-text-fill: black;");
							btnsmat[button.row][button.col].setText(minesweeper.get(button.row, button.col));
						}

					}
				});
				btnsmat[row][col].setPrefSize(50, 50);
				gridPane.add(btnsmat[row][col], col, row);
			}
		}
	}

	private class btns extends Button {
		protected int row, col;

		public btns(String s, int row, int col) {
			super(s);
			this.row = row;
			this.col = col;
		}
	}

	private void windowPopUp() {
		mediaPlayer.stop();
		HBox secondRoot = new HBox();
		secondRoot.setStyle("-fx-alignment: center;");
		Label winMessage = new Label("OMG You have just won!");
		winMessage.autosize();
		winMessage.setStyle("-fx-font-size: 30px;" + "-fx-text-fill: #333333;"
				+ "-fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );"
				+ "-fx-text-alignment: center;");
		winMessage.setMaxWidth(Double.MAX_VALUE);
		winMessage.setAlignment(Pos.CENTER);
		secondRoot.getChildren().addAll(winMessage);
		Scene scene = new Scene(secondRoot, 400, 200);
		Stage secondStage = new Stage();
		secondStage.setScene(scene);
		secondStage.show();
	}
}
