package mainView;

import engine.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.*;
import java.util.List;

public class Controller {

    @FXML TextArea resultTA;
    @FXML ChoiceBox styleCB;
    @FXML ComboBox lineCB;
    @FXML TextField countTF;
    @FXML Button  createButton;
    @FXML Button save;
    @FXML Button useNewButton;
    @FXML Button useDefaultButton;


    //styleCB items 参数
    private  String[] styleCBItems = {"五言","七言"};
    //lineCB items 参数
    private String[] lineCBItems = {"2","4","8","12"};

    //处理引擎
    private SimpleCreator creator;
    private Segmentation segmentation;

    private int col = 5;   //记录诗句列数
    private int line = 4;       //记录诗句行数
    private int count = 1;      //记录一次生成的诗句数量

    public void initialize(){
        creator = new SimpleCreator();

        //设置 styleCB
        styleCB.getItems().addAll(styleCBItems);
        styleCB.getSelectionModel().select(0);
        styleCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String type = styleCBItems[((Integer)newValue).intValue()];
                switch(type){
                    case "五言": col = 5; break;
                    case "七言": col = 7; break;
                }
            }
        });
        //设置 lineCB
        lineCB.getItems().addAll(lineCBItems);
        lineCB.getSelectionModel().select(1);
        lineCB.setOnAction((Event e)->{
            String temp = lineCB.getSelectionModel().getSelectedItem().toString();
            if(temp.matches("[0-9]{1,}")){
                line = Integer.parseInt(temp);
            }
        });


        //设置createButton
        createButton.setOnAction((ActionEvent e)->{
           //验证countTF是否为数字并对其进行容错
            String countStr = countTF.getText();
            if(countStr.matches("[0-9]+]")){
                count = Integer.parseInt(countStr);
            }
            //产生诗句并将其答应在TextArea上
            for(int i=0;i<count;i++){
                List<String> poerty = creator.createPoerty(line,(col-3)/2+1);
                resultTA.appendText(SimpleCreator.formatPoerty(poerty));
            }
        });


        //设置save动作：保存文件
        save.setOnAction((ActionEvent e)->{
            FileChooser chooser = new FileChooser();
            chooser.setTitle("选择保存路径");
            chooser.setInitialFileName("poerty");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".txt","*.txt"));
           File file = chooser.showSaveDialog(Main.getStage());
            //保存文件
            if(file == null)
                return;
            try {
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                String[] lines = resultTA.getText().split("\n");
                for(int i=0;i<lines.length;i++)
                    out.println(lines[i]);
                out.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            //默认自动打开文件
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(file);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });


        //设置Setting动作：更换原始素材
        useNewButton.setOnAction((ActionEvent e)->{
            //打开文件选择器，并重新解析原始素材
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择原始素材文件");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".txt","*.txt"));
            File file = fileChooser.showOpenDialog(Main.getStage());
            if(file == null)
                return;
            segmentation = new Segmentation(file.getAbsolutePath());
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.setHeaderText("以更换默认原始素材，语料库解析完毕");
            information.showAndWait();
        });

        useDefaultButton.setOnAction((ActionEvent e)->{
            //使用默认素材解析
            segmentation = new Segmentation(Segmentation.getDefalutSrcFile());
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.setHeaderText("以更换默认原始素材，语料库解析完毕");
            information.showAndWait();
        });

    }
}
