# RotinApp — Rotinas, Metas e Progresso

Aplicativo Android desenvolvido em Kotlin com Jetpack Compose para a disciplina de Desenvolvimento de Aplicativos Android, da Universidade Positivo.

O RotinApp tem como objetivo auxiliar os usuários na organização de suas rotinas, no acompanhamento de metas e na visualização do progresso pessoal.

## Demonstração

[Assistir à demonstração do aplicativo](https://drive.google.com/file/d/1ZJ0KFRyBLc90iXeIkImVp8Kb0NQnIrcw/view?usp=sharing)

## Funcionalidades

- **Rotinas:** cadastro e visualização de rotinas e atividades.
- **Metas:** criação, acompanhamento, conclusão e exclusão de metas.
- **Progresso:** visualização do nível, da experiência e das métricas de evolução.
- **Histórico:** acompanhamento de informações relacionadas às atividades e metas concluídas.
- **Perfil:** área destinada às informações do usuário.
- **Navegação:** acesso às principais funcionalidades por meio de uma barra de navegação.

## Tecnologias utilizadas

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- ViewModel
- LazyColumn
- MutableStateListOf
- Android Studio
- Git e GitHub

## Arquitetura e gerenciamento de dados

O aplicativo utiliza `NavHost` e `NavController` para gerenciar a navegação entre as telas. As rotas são organizadas em uma estrutura específica, facilitando a manutenção e a organização do código.

O gerenciamento de estado é realizado com recursos do Jetpack Compose e, em telas específicas, com `ViewModel`. Os dados são mantidos em memória durante a execução do aplicativo, sem persistência após seu encerramento.

## Como executar o projeto

### Pré-requisitos

- Android Studio
- JDK compatível com o projeto
- Android SDK
- Emulador Android ou dispositivo físico

### Instalação

1. Clone o repositório:

   ```bash
   git clone URL_DO_REPOSITORIO
   ```

2. Abra o projeto no Android Studio.
3. Aguarde a sincronização das dependências do Gradle.
4. Inicie um emulador ou conecte um dispositivo Android.
5. Execute o projeto utilizando a opção **Run**.

Substitua `URL_DO_REPOSITORIO` pelo endereço HTTPS do repositório no GitHub.

## Documentação

[Visualizar a documentação e o progresso do projeto](https://docs.google.com/presentation/d/1iY6JRYjjSx3Rd6ML8kH7899-WqSeXBDa_Q5x_Lc1g-s/edit?hl=pt-BR&slide=id.h3a2c9153f717c9d4_0_3#slide=id.h3a2c9153f717c9d4_0_3)

## Contexto acadêmico

**Trabalho 3 — MAF (Mínimo Aplicativo Funcional)**

Disciplina: Desenvolvimento de Aplicativos Android

Instituição: Universidade Positivo

Projeto desenvolvido para fins acadêmicos, com foco na aplicação prática dos conceitos de desenvolvimento Android e na evolução de uma interface visual para um aplicativo funcional.

---

**RotinApp** — Organize suas rotinas, acompanhe suas metas e visualize seu progresso.
