# AetherWorld LitRPG Core — Fabric 26.1.2

Core do sistema LitRPG do AetherWorld para Minecraft 26.1.2.

## Conteúdo
- Perfil persistente do jogador via Fabric Data Attachments.
- Level, EXP, pontos de atributo, PS e Mana.
- Progressão automática de atributos e regeneração de Mana.
- GUI dourada aberta pela tecla O.
- Loja de sistema por PS.
- Núcleo de Mana e Cartão Black Dourado.
- Selo da Cripta e gerador de cripta subterrânea.
- Build automático com GitHub Actions.

## Compilação
O workflow em `.github/workflows/build.yml` usa Java 25 e Gradle 9.8 e publica o JAR como artefato da execução.

Este repositório contém o core LitRPG; conteúdo de armas/combate não faz parte deste pacote.
