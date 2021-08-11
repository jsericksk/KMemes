## Sobre

Projeto simples que utiliza o Firebase Cloud Storage e o Realtime Database, com foco no novo kit de ferramentas moderno para criar IUs declarativas do Android, [Jetpack Compose](https://developer.android.com/jetpack/compose). O projeto também segue a arquitetura [MVVM](https://developer.android.com/jetpack/guide) com **ViewModel** e **LiveData**.

## Clone

Se você desejar clonar e testar este projeto, primeiro deve ter uma conta Firebase e se conectar ao seu projeto. Siga os seguintes passos:
1. Conecte-se ao Firebase:
- [Faça login no Firebase](https://console.firebase.google.com) e depois crie um projeto com qualquer nome, seguindo todas as etapas descritas no site durante a criação. Siga o [guia oficial](https://firebase.google.com/docs/android/setup) para a configuração do projeto ou mais informações.
2. Configure o **Storage**:
- Com o projeto Firebase conectado corretamente ao seu projeto Android Studio, abra a opção **Storage** no console do Firebase e crie duas pastas para armazenar suas imagens (memes), uma para os memes populares e outra para memes de animes. Isto é apenas para fins de organização.
- Envie as imagens para as pastas correspondentes.
- Para cada imagem que você enviar, terá que copiar o link direto delas. O link deve se parecer mais ou menos com isto: https://firebasestorage.googleapis.com/v0/b/YOUR-PROJECT.appspot.com/o/FOLDER%2FIMAGE-NAME.jpg?alt=media&token=TOKEN. Esses links serão usados no JSON que funcionará como uma API no nosso Realtime Database.
- Tendo o link de todas as imagens, siga para a última etapa.
3. Configure o **Realtime Database**:
- Você terá que ter um JSON com a seguinte estrutura: 

```
{
  "popular": [
    {
      "imageUrl": "IMAGE-URL",
      "imageName": "Image Name"
    }
    // More popular memes...
  ],
  "anime": [
    {
      "imageUrl": "IMAGE-URL",
      "imageName": "Image Name"
    }
    // More anime memes...
  ]
}
```

- Você tem duas opções:
1. Criar um arquivo JSON com os links que copiou e, após isso, importar o JSON no Realtime Database;
2. Criar manualmente no painel do Realtime Database toda essa estrutura, adicionando os valores **imageUrl** e **imageName** um de cada vez.

Não importa qual escolha, o JSON deve ter os valores mencionados anteriormente.
Se após importar o JSON no painel do Realtime Database a estrutura estiver diferente do seu arquivo JSON, como o array *anime* estando no início, ao invés do array *popular*, não precisa se preocupar. Isso não afetará a busca pelos valores no aplicativo, o importante é que o JSON tenha esses valores.

Tudo isso foi feito apenas para fins de estudo.

## Bibliotecas utilizadas

[Coil](https://coil-kt.github.io/coil/compose/): para carregar imagens.  
[Accompanist-Pager](https://google.github.io/accompanist/pager/): para uso do "ViewPager" com Tabs.  
[Navigation com o Compose](https://developer.android.com/jetpack/compose/navigation): para navegar entre as telas do Compose usando o Navigation Component do Compose.  
[Coroutines](https://developer.android.com/kotlin/coroutines): para acesso ao Firebase em uma thread IO.  

**Jetpack Compose supremacy!** 🧎
