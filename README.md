# RotinApp — Rotinas, Metas e Progresso

Aplicativo Android desenvolvido em Kotlin com Jetpack Compose para a disciplina de Desenvolvimento de Aplicativos Android, da Universidade Positivo.

O RotinApp tem como objetivo auxiliar os usuários na organização de suas rotinas, no acompanhamento de metas e na visualização do progresso pessoal.

Este projeto corresponde ao **Trabalho 3 — Persistência de Dados com Room**, continuação do Trabalho 2, com foco na implementação de persistência local utilizando Room, biblioteca do Android Jetpack que fornece uma camada de abstração sobre o SQLite.

## Demonstração

[Assistir à demonstração do aplicativo](https://drive.google.com/file/d/1JuVWiPPGbDRMxu2Hx3Kd-RXzVuXgmgQQ/view?usp=sharing)

A demonstração apresenta o aplicativo e suas funcionalidades. Recomenda-se complementar o material com vídeos específicos das operações de cadastro, edição, exclusão e persistência dos dados após fechar e reabrir o aplicativo.

## Funcionalidades

- **Rotinas:** cadastro e visualização de rotinas e atividades.
- **Metas:** criação, acompanhamento, conclusão e exclusão de metas.
- **Progresso:** visualização do nível, da experiência e das métricas de evolução.
- **Histórico:** acompanhamento de informações relacionadas às atividades e metas concluídas.
- **Perfil:** área destinada às informações do usuário.
- **Navegação:** acesso às principais funcionalidades por meio de uma barra de navegação.
- **Persistência local:** armazenamento dos dados utilizando Room, permitindo sua recuperação após o encerramento do aplicativo, conforme as operações implementadas.

## Tecnologias utilizadas

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- ViewModel
- Room
- SQLite
- KSP
- Corrotinas Kotlin
- Android Studio
- Git e GitHub

## Arquitetura e gerenciamento de dados

O aplicativo utiliza `NavHost` e `NavController` para gerenciar a navegação entre as telas. As rotas são organizadas em uma estrutura específica, facilitando a manutenção e a organização do código.

A interface é desenvolvida com Jetpack Compose, enquanto os ViewModels são responsáveis por intermediar as operações e gerenciar os estados utilizados pelas telas.

Para a persistência dos dados, o projeto utiliza Room como camada de acesso ao banco de dados SQLite. As entidades representam os dados armazenados, os DAOs definem as operações de acesso e a classe `AppDataBase` centraliza a configuração do banco de dados.

As operações de persistência incluem inserção, consulta, atualização e exclusão de registros. As chamadas ao banco são realizadas de forma assíncrona, evitando o bloqueio da interface durante as operações.

## Documentação do processo

A documentação do projeto apresenta a evolução do aplicativo desde o Trabalho 2, descrevendo as mudanças realizadas para implementar a persistência local com Room.

O material deve registrar as decisões de implementação, as classes criadas ou modificadas, as dificuldades encontradas e as soluções adotadas durante o desenvolvimento.

Os principais tópicos documentados são:

- Mudanças realizadas nas classes de apresentação e nos ViewModels.
- Modelagem das entidades (`@Entity`) e definição de suas chaves primárias.
- Criação dos DAOs (`@Dao`) e implementação das consultas.
- Configuração do banco de dados (`@Database`).
- Implementação das operações de inserção, consulta, atualização e exclusão.
- Migração do armazenamento em memória para a persistência local.
- Tratamento de erros e execução assíncrona das operações.
- Testes de persistência e evidências do funcionamento do aplicativo.
- Dificuldades encontradas e soluções desenvolvidas pelo grupo.

[Visualizar a documentação do projeto](https://docs.google.com/presentation/d/1iY6JRYjjSx3Rd6ML8kH7899-WqSeXBDa_Q5x_Lc1g-s/edit?hl=pt-BR&slide=id.h3a2c9153f717c9d4_0_3#slide=id.h3a2c9153f717c9d4_0_3)

## Como executar o projeto

### Pré-requisitos

- Android Studio.
- JDK compatível com a configuração do projeto.
- Android SDK.
- Emulador Android ou dispositivo físico.
- Conexão com a internet para baixar as dependências na primeira sincronização.

### Instalação

1. Clone o repositório:

   ```bash
   git clone URL_DO_REPOSITORIO
   ```

2. Abra o projeto no Android Studio.
3. Aguarde a sincronização das dependências do Gradle.
4. Verifique se a configuração do Room e do KSP foi sincronizada corretamente.
5. Inicie um emulador ou conecte um dispositivo Android.
6. Execute o projeto utilizando a opção **Run** do Android Studio.

Substitua `URL_DO_REPOSITORIO` pelo endereço HTTPS do novo repositório no GitHub.

## Contexto acadêmico

**Trabalho 3 — Persistência de Dados com Room**

Disciplina: Desenvolvimento de Aplicativos Android.

Instituição: Universidade Positivo.

Projeto desenvolvido para fins acadêmicos, com foco na implementação de persistência de dados locais, na utilização do Room e na evolução de um aplicativo Android funcional.

---

**RotinApp** — Organize suas rotinas, acompanhe suas metas e visualize seu progresso.
