/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.guifx;

import excecao.UsuarioNullException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import negocio.Medico;
import negocio.Paciente;
import negocio.Recepcionista;
import negocio.Usuario;
import gui.TelaTextual;
import java.util.logging.Level;
import java.util.logging.Logger;
import negocio.Login;
import negocio.Servidor;

/**
 *
 * @author HENRIQUE.FELIX
 */
public class MainFx extends Application{ 

    private static ArrayList<Scene> cenas = new ArrayList<Scene>();
    private static Stage stage;
    private Servidor s;
  
    
    public void criaCena(String doc) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(doc));
        Scene scene = new Scene(root);
        cenas.add(scene);
    }
    
    public void trocaCena(int i){
        stage.setScene(cenas.get(i));
    }
    
    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        MainFx.stage = stage;
    }
    
    

    @Override
    public void start(Stage stagePrimare) throws Exception{

        stage =  stagePrimare;
        criaCena("TelaDoZero.fxml");//0
        criaCena("FXMLTelaRecepcao.fxml");
        //criaCena("FXMLCadastraUsuarios.fxml");
        stage.setScene(cenas.get(0));
        stage.setTitle("JavaClinical");
        stage.show();
        setStage(stage);
    } 
    
    public void fazerLogin(String id, String senha){
        s = Servidor.getInstance();
        Usuario u = null;
        Login l = new Login(id, senha.hashCode());
        
        switch(l.getId().charAt(0)) {
            case '1':
            try {
                u = s.efetuarLoginRecepcionista(l);
                System.out.println(u);
            } catch (UsuarioNullException ex) {
                // Chamar gui para exceção
            }
            break;
            case '2':
                try {
                u = s.efetuarLoginMedico(l);
            } catch (UsuarioNullException ex) {
                // Chamar gui para exceção
            }
                break;
                case '3':
            try {
                u = s.efetuarLoginPaciente(l);
            } catch (UsuarioNullException ex) {
                // Chamar gui para exceção
            }
            break;
            default:
                break;
        }
    }
    
    public static void main(String [] args){launch(args);}
    
}