# GymPlanner

Aplicativo Android para **controle de treinos em academia**, desenvolvido como projeto de Dispositivos Móveis (UEMG).

## Funcionalidades
Tela de Login com autenticação local.  
Tela principal com listagem de treinos (RecyclerView).  
Cadastro de treinos e exercícios com validação de campos.  
Edição e exclusão de treinos.  
Banco de dados local (SQLite/Room) com duas tabelas relacionadas:
- **Treinos** (nome, objetivo)
- **Exercícios** (nome, carga, repetições, id do treino)

## Tecnologias Utilizadas
- **Kotlin**
- **Android Studio**
- **Room (SQLite)**
- **ViewBinding**
- **Jetpack Components (Navigation, Lifecycle)**

## Como rodar
Clone o repositório:
```bash
git clone https://github.com/seu-usuario/gymplanner.git
