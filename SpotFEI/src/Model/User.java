package Model;


public class User {
    private String usuario, senha, conf_senha;

    public User(String usuario, String senha, String conf_senha) {
        this.usuario = usuario;
        this.senha = senha;
        this.conf_senha = conf_senha;
    }
    
    public String getUsuario(){
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public String getSenha(){
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getConf_senha(){
        return conf_senha;
    }
    public void setConf_senha(String conf_senha) {
        this.conf_senha = conf_senha;
    }
    
    
    public String toString() {
        return "Usuario{" + "usuario=" + usuario + ", senha=" + senha + '}';
    }
    public boolean validarUsuario(String usuario, String senha) {
        return usuario.equals("admin") && senha.equals("1234");
    } 
}
