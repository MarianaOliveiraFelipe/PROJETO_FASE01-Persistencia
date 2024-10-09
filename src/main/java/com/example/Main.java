package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main extends Application {
    private static ListarExercicio catalog = new ListarExercicio();
    private static Stage primaryStage;
    private Scene mainScene; 

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        primaryStage.setTitle("Menu de Exercícios");

        // Título
        Label titleLabel = new Label("Controle de Exercícios");
        titleLabel.setId("titleLabel");

        // Descrição
        Label label = new Label("Escolha uma opção:");
        label.setId("descriptionLabel");

        // Botões
        Button btnInserir = createButton("Novo Exercício");
        btnInserir.setOnAction(e -> showInserirExercicioScene());

        Button btnMostrarQuantidade = createButton("Exercícios");
        btnMostrarQuantidade.setOnAction(e -> mostrarQuantidadeExercicios());

        Button btnConverterParaJSONXML = createButton("Converter para JSON e XML");
        btnConverterParaJSONXML.setOnAction(e -> {
            converterParaJSON();
            converterParaXML();
        });

        Button btnCompactarCSV = createButton("Compactar CSV");
        btnCompactarCSV.setOnAction(e -> compactarCSV());

        Button btnMostrarHashSHA256 = createButton("Hash SHA256 do CSV");
        btnMostrarHashSHA256.setOnAction(e -> mostrarHashSHA256());

        Button btnSair = createButton("Sair");
        btnSair.setOnAction(e -> primaryStage.close());

        // Layout dos botões
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(20));

        gridPane.add(btnInserir, 0, 0);
        gridPane.add(btnMostrarQuantidade, 1, 0);
        gridPane.add(btnConverterParaJSONXML, 0, 1);
        gridPane.add(btnCompactarCSV, 1, 1);
        gridPane.add(btnMostrarHashSHA256, 0, 2);
        gridPane.add(btnSair, 1, 2);

        // VBox principal
        VBox vbox = new VBox(30);
        vbox.setId("mainVBox");  
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(titleLabel, label, gridPane);
        vbox.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 20px;");

        mainScene = new Scene(vbox, 600, 400); // Armazena a cena principal

        // CSS externo
        mainScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setId("customButton");
        return button;
    }

    private void showInserirExercicioScene() {
        Label titleLabel = new Label("Inserir Novo Exercício");
        titleLabel.setId("titleLabel");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));

        TextField idField = new TextField();
        idField.setPromptText("ID");
        TextField nomeField = new TextField();
        nomeField.setPromptText("Exercício");

        ComboBox<String> categoriaComboBox = new ComboBox<>();
        categoriaComboBox.setPromptText("Selecione a categoria");
        categoriaComboBox.getItems().addAll(
            "Cardiovascular", "Força", "Flexibilidade", "Equilíbrio",
            "Agilidade", "Mobilidade", "Alongamento", "Funcional",
            "Resistência", "Coordenação"
        );

        ComboBox<String> dificuldadeComboBox = new ComboBox<>();
        dificuldadeComboBox.setPromptText("Selecione a dificuldade");
        dificuldadeComboBox.getItems().addAll("Fácil", "Médio", "Difícil");

        TextField duracaoField = new TextField();
        duracaoField.setPromptText("Duração (minutos)");
        TextField caloriasField = new TextField();
        caloriasField.setPromptText("Calorias");
        TextField descricaoField = new TextField();
        descricaoField.setPromptText("Descrição");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button btnSalvar = createButton("Salvar");
        btnSalvar.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String nome = nomeField.getText();
                String categoria = categoriaComboBox.getValue();
                String dificuldade = dificuldadeComboBox.getValue();
                int duracao = Integer.parseInt(duracaoField.getText());
                int calorias = Integer.parseInt(caloriasField.getText());
                String descricao = descricaoField.getText();

                Exercicio novoExercicio = new Exercicio(id, nome, categoria, dificuldade, duracao, calorias, descricao);
                catalog.adicionarExercicio(novoExercicio);

                System.out.println("Exercício adicionado com sucesso!");
                primaryStage.setScene(mainScene); 
            } catch (NumberFormatException ex) {
                errorLabel.setText("Erro ao converter número: " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                errorLabel.setText(ex.getMessage());
            } catch (IOException ex) {
                errorLabel.setText("Erro ao salvar exercício: " + ex.getMessage());
            }
        });

        Button btnVoltar = createButton("Voltar");
        btnVoltar.setOnAction(e -> primaryStage.setScene(mainScene)); // Retorna à cena principal

        gridPane.add(new Label("ID:"), 0, 0);
        gridPane.add(idField, 1, 0);
        gridPane.add(new Label("Exercício:"), 0, 1);
        gridPane.add(nomeField, 1, 1);
        gridPane.add(new Label("Categoria:"), 0, 2);
        gridPane.add(categoriaComboBox, 1, 2);
        gridPane.add(new Label("Dificuldade:"), 0, 3);
        gridPane.add(dificuldadeComboBox, 1, 3);
        gridPane.add(new Label("Duração (minutos):"), 0, 4);
        gridPane.add(duracaoField, 1, 4);
        gridPane.add(new Label("Calorias:"), 0, 5);
        gridPane.add(caloriasField, 1, 5);
        gridPane.add(new Label("Descrição:"), 0, 6);
        gridPane.add(descricaoField, 1, 6);
        gridPane.add(btnSalvar, 0, 7);
        gridPane.add(btnVoltar, 1, 7);
        gridPane.add(errorLabel, 1, 8);

        VBox vbox = new VBox(20);
        vbox.setId("mainVBox");
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(titleLabel, gridPane);
        vbox.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 20px;");

        Scene inserirExercicioScene = new Scene(vbox, 600, 400);
        inserirExercicioScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        primaryStage.setScene(inserirExercicioScene);
    }

    private static void mostrarQuantidadeExercicios() {
        int quantidade = catalog.getQuantidadeExercicios();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Quantidade de Exercícios");
        alert.setHeaderText(null);
        alert.setContentText(quantidade + " Exercícios");
        alert.showAndWait();
    }

    private static void converterParaJSON() {
        try {
            ConversorJSON.converterParaJSON(catalog.getExercicios());
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Converter para JSON");
            alert.setHeaderText(null);
            alert.setContentText("Arquivo JSON convertido com sucesso!");
            alert.showAndWait();
        } catch (Exception e) { 
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao converter para JSON: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private static void converterParaXML() {
        try {
            ConversorXML.converterParaXML(catalog.getExercicios());
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Converter para XML");
            alert.setHeaderText(null);
            alert.setContentText("Arquivo XML convertido com sucesso!");
            alert.showAndWait();
        } catch (Exception e) { 
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao converter para XML: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private static void compactarCSV() {
        try {
            CompactadorCSV.compactarCSV(catalog.getArquivoCSV());
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Compactar CSV");
            alert.setHeaderText(null);
            alert.setContentText("Arquivo CSV compactado com sucesso!");
            alert.showAndWait();
        } catch (Exception e) { 
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao compactar CSV: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private static void mostrarHashSHA256() {
        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(catalog.getArquivoCSV()));
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(fileBytes);

            StringBuilder hashString = new StringBuilder();
            for (byte b : hashBytes) {
                hashString.append(String.format("%02x", b));
            }
            String hash = hashString.toString();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Hash SHA-256");
            alert.setHeaderText(null);
            alert.setContentText("Hash SHA-256: " + hash);
            alert.showAndWait();
        } catch (IOException | NoSuchAlgorithmException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao calcular o hash SHA-256: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
