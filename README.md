# 🎧 Projeto Java - Aplicativo de Músicas

Este é um projeto de aplicativo de músicas desenvolvido em Java com interface gráfica utilizando `Swing` e persistência de dados em um banco PostgreSQL. O sistema permite que os usuários façam login ou cadastro, curtam músicas, visualizem o histórico de busca e gerenciem playlists.

## 🛠️ Funcionalidades

### 🔐 Tela de Entrada (Login/Cadastro)
- Permite ao usuário se **logar com um nome de usuário existente e senha** ou **criar uma nova conta**.
- Após o login, o usuário é redirecionado para a tela principal (menu).

### 🏠 Tela Menu Principal
- Exibe os seguintes botões de navegação:
  - **Músicas Curtidas**: lista todas as músicas que o usuário curtiu.
  - **Histórico**: mostra as últimas 10 músicas buscadas.
  - **Playlists**: permite visualizar, criar e gerenciar playlists.
- Campo de **pesquisa** onde o usuário pode buscar músicas cadastradas no banco de dados, puxando por um filtro que o usuário pode escolher (nome, genero e artista).
- Resultado da busca permite:
  - Visualizar o nome, o gênero e o artista da música.
  - Adicionar a música às músicas curtidas.

### ❤️ Tela de Músicas Curtidas
- Exibe todas as músicas que o usuário marcou como curtidas.

### 🕒 Tela de Histórico
- Exibe as **10 últimas músicas** buscadas pelo usuário, ordenadas da mais recente para a mais antiga.

### 🎵 Tela de Playlists
- Lista todas as playlists criadas pelo usuário.
- Ao clicar duas vezes em uma playlist (modo playlist), é exibida uma janela com todas as músicas contidas nela.
- Nesta janela é possível:
  - **Excluir a playlist** com confirmação.
  - Caso a playlist esteja vazia, o usuário será notificado.
- Ao clicar duas vezes em uma música (modo músicas), é possível adicioná-la a uma das playlists existentes.

## 🧱 Tecnologias Utilizadas

- **Java SE 8+**
- **Swing** (Interface gráfica)
- **PostgreSQL** (Banco de dados relacional)

## 🔌 Banco de Dados

Certifique-se de ter o PostgreSQL rodando e que a tabela `playlists` (entre outras) esteja criada corretamente. Evite usar aspas ou letras maiúsculas ao criar nomes de tabelas para evitar erros de case-sensitive.

Exemplo básico para a tabela `playlists`:

```sql
CREATE TABLE playlists (
    id_playlist SERIAL PRIMARY KEY,
    nome_playlist VARCHAR(255),
    user_id VARCHAR(255),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

LINK PARA VÍDEO: https://youtu.be/eG4VZLsgr3w
LINK PARA GITHUB: https://github.com/lesgoooou/Projeto-JAVA.git