package dados;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import negocio.Usuario;
import java.io.Serializable;
import negocio.Exame;
public class RepositorioUsuario implements Serializable {
	private Usuario[] usuarios;
	private int ultimo;
	
	public RepositorioUsuario(int tamanho) {
		this.usuarios = new Usuario[tamanho];
		this.ultimo = 0;
	}
	
	// quando for para o paciente marcar uma consulta e ele colocar o nome do médico, tem que buscar por nome.
	// o paciente não é obrigado a saber o cpf do médico.
	
	public void cadastrarUsuario(Usuario u) {
		this.usuarios[ultimo] = u;
		this.ultimo += 1;
		if(this.ultimo == this.usuarios.length) {
			this.duplicarLimite();
		}
	}
	
	public Usuario procurar(String id) {
        int i = this.procurarIndice(id);
        Usuario u = null;
        if (i != this.ultimo) {
            u = this.usuarios[i];
        }
        return u;
    }
	

	private int procurarIndice(String id) {
        int i = 0;
        boolean encontrado = false;
        while ((!encontrado) && (i < this.ultimo)) {
            if (id.equals(this.usuarios[i].getId())) {
                encontrado = true;
            } else {
                i = i + 1;
            }
        }
        return i;
    }
	
	public boolean existe(String id) {
        boolean existe = false;
        int indice = this.procurarIndice(id);
        if (indice != this.ultimo) {
            existe = true;
        }
        return existe;
    }
	
	public void remover(String id) {
        int i = this.procurarIndice(id);
        if (i != this.ultimo) {
            this.usuarios[i] = this.usuarios[this.ultimo - 1];
            this.usuarios[this.ultimo - 1] = null;
            this.ultimo = this.ultimo - 1;
        } else {
            // Usuario inexistente, arrumar uma forma de exibir mensagem
        	// de erro.
        }
    }
	
	private void duplicarLimite() {
		if (this.usuarios != null && this.usuarios.length > 0) {
            Usuario[] aux = new Usuario[this.usuarios.length * 2];
            for (int i = 0; i < this.usuarios.length; i++) {
                aux[i] = this.usuarios[i];
            }
            this.usuarios = aux;
        }
		
	}
        
        public void salvarArquivos() throws FileNotFoundException, IOException{
            File arquivo= new File("Usuários.txt");
            FileOutputStream fos = new FileOutputStream(arquivo);
            ObjectOutputStream ous = new ObjectOutputStream(fos);
            ous.writeObject(this.usuarios);
            ous.close();
        }
        
        public void carregarArquivos() throws FileNotFoundException, IOException, ClassNotFoundException{
            File arquivo = new File("Usuários.txt");
            FileInputStream fis = new FileInputStream(arquivo);
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.usuarios = (Usuario[]) ois.readObject();
            ois.close();
        }
}
