package application;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CardGameController {

	@FXML
	private Button confirm;
	@FXML
	private TextField length;
	@FXML
	private TextField width;
	
	public void confirm() {
		try {
			Stage newStage = new Stage();
			newStage.setTitle("CardGame");
			BorderPane bp = new BorderPane();
			VBox vb = new VBox();
			bp.setCenter(vb);
			
			int len = Integer.valueOf(length.getText());
			int wid = Integer.valueOf(width.getText());
			
			if(len > 20 || wid > 15 || len <=0 || wid <= 0) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("无法生成棋盘！");
				if(len <=0 || wid <= 0)
					alert.setContentText("长宽不能为负");
				else
					alert.setContentText("长度不能超过20，宽度不能超过15");
				alert.show();
				length.setText("");
				width.setText("");
				return;
			}
			
			Button[][] bt = new Button[wid][len];
			HBox[] hb = new HBox[wid];
			for(int i = 0;i < wid;i++) {
				hb[i] = new HBox();
				hb[i].setAlignment(Pos.CENTER);
				for(int j = 0;j < len;j++) {
					bt[i][j] = new Button();
					final int i0 = i;
					final int j0 = j;
					bt[i0][j0].setPrefSize(50, 50);
					if(Math.random() < 0.2)	//随机生成
						bt[i0][j0].setStyle("-fx-background-color:black");
					else
						bt[i0][j0].setStyle("-fx-background-color:silver");
					
					bt[i0][j0].setOnAction(event -> {
						turnOver(bt[i0][j0]);
						if(i0 > 0) 
							turnOver(bt[i0-1][j0]);
						if(i0 < wid-1)
							turnOver(bt[i0+1][j0]);
						if(j0 > 0)
							turnOver(bt[i0][j0-1]);
						if(j0 < len -1)
							turnOver(bt[i0][j0+1]);
						
						if(isWin(bt)) {
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setHeaderText("YOU WIN!");
							alert.setContentText("恭喜!");
							alert.show();
						}
					});
					hb[i].getChildren().add(bt[i0][j0]);
				}
			}
			for(int i = 0;i < wid;i++) {
				vb.getChildren().add(hb[i]);
			}
			Scene se = new Scene(bp,50*len,50*wid);
			newStage.setScene(se);
			newStage.show();
		} catch(NumberFormatException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("无法生成棋盘！");
			alert.setContentText("长宽必须为正整数");
			alert.show();
			length.setText("");
			width.setText("");
		}
	}
	
	private void turnOver(Button bt) {	//翻牌
		if(bt.getStyle().equals("-fx-background-color:black"))
			bt.setStyle("-fx-background-color:silver");
		else
			bt.setStyle("-fx-background-color:black");
	}
	
	private boolean isWin(Button[][] bt) {//判断输赢
		String st = bt[0][0].getStyle();
		for(Button e[]:bt) {
			for(Button e1:e) {
				if(!e1.getStyle().equals(st))
					return false;
			}
		}
		return true;
	}
	
	
}
