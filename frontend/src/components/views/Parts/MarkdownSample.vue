<template>
  <span>
    <button type="button" data-toggle="modal" data-target="#helpMarkdownModal" class="btn btn-link">
      <i class="fa fa-info-circle" aria-hidden="true"></i>
      Markdown information
    </button>
    <div class="modal fade in" id="helpMarkdownModal">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">×</span></button>
            <h4 class="modal-title">
              <i class="fa fa-info-circle" aria-hidden="true"></i>
              Markdown information
            </h4>
          </div>
          <div class="modal-body">
            <div class="row">
              <div class="col-sm-12">
                <h3>Markdown sample</h3>
                <textarea rows="8" class="form-control" v-model="article.content">
                </textarea>
              </div>
            </div>
            <div class="row ">
              <div class="col-sm-12">
                <h3>Display sample</h3>
                <div class="markdown-body viewarea">
                  <span v-html="article.displaySafeHtml"></span>
                </div>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" v-on:click="parse()">
              <i class="fa fa-bolt" aria-hidden="true"></i>Parse
            </button>
            <button type="button" class="btn btn-default" data-dismiss="modal">
              <i class="fa fa-close" aria-hidden="true"></i>Close
            </button>
          </div>
        </div>
        <!-- /.modal-content -->
      </div>
      <!-- /.modal-dialog -->
    </div>
    <input type="hidden" id="sampleMarkdown" value="# Markdown sample 
- This Markdown text is dispay on 'Display sample' area,
  You can check the display of Markdown

## Heading -> '#'
- '#' is heading
   - After the '#', by opening a space, write the title of the character
   - Change is the number the magnitude of the '#'

## Itemization -> '-'
- '-' is itemization
   - After the '#', by opening a space, write the contents of the bullet
   - If the space put three, and displayed by being indented as before
      - This will be displayed in a hierarchical structure as

## Itemization(with numbers) -> '1.'
1. '1.' is itemization(with numbers)
   1. After the '1.', by opening a space, write the contents of the bullet
   1.Except that the number is attached '-' and this is the same

## Strong -> 'Two * (asterisk)'
- Emphasize that it surrounds the emphasis you want part in the statement in the 'two * (asterisk)'
   - ** It is like this ** emphasized for example

## Border -> 'Three or more of the * (asterisk)'
- If you want to put a border, it is described only 'three or more of the * (asterisk)'
***
- Border is displayed above

## Link
- The part you want to put a link, to describe the [name of the link](link destination URL)
   - [Knowledge](https://information-knowledge.support-project.org)

## Code
- Where you want to display the code is enclosed in '` (back quote) '

```javascript
var msg = 'hoge'
alert(msg)
```

## Extended markdown syntax
- The part you want to put a web site introduction, to describe the [oembed WebSiteURL]
   - GitHub
      - [oembed https://github.com/support-project/knowledge]
   - SlideShare
      - [oembed http://www.slideshare.net/koda3/knowledge-information]
   - GoogleMap
      - [oembed https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d12966.919088372344!2d139.72177695!3d35.6590289!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x5bfe0248594cc802!2z5YWt5pys5pyo44OS44Or44K6!5e0!3m2!1sja!2sjp!4v1482300583922]


- Emoji
   - Pictographs are displayed by inserting a key between `:` and `:`
   - The list of pictographs is displayed on the link of `people nature objects places symbols`, so it is also possible to select from them
   - :+1: :smile:

- Link to other articles
   - If you enter the article ID after `#`, it will be a link to another article
   - If you enter `#`, the choices of linked articles are displayed
   - #1 ← Link to article '1'

- Formula in LaTeX format (block display)
   - If you enclose it with `$$`, it will display the formula as a block
   - $$ e^{i\theta} = \cos\theta + i\sin\theta $$
   
- Formula in LaTeX format (inline display)
   - Inline display when enclosed with `$`
   - The famous Euler formula is $e^{i\theta}=\cos\theta+i\sin\theta$ ．

- Formula in LaTeX format (code block)
   - In the code block \ `\` \ `math, write the mathematical expression directly (The above '$' is unnecessary)
   - `\\` is a newline

```math
Formula1: 

E = mc^2

\\

Formula2: 
\sum_{n=1}^\infty \frac{1}{n^2} = \frac{\pi^2}{6}

\\


The  famous  Euler  formula  is  $e^{i\theta}=\cos\theta+i\sin\theta$ ．


```


## Insert the image into the article
- Uploading an image allows you to press the 'Show image' button, and pressing it will display the image
- Describe the display of images in the following format
    - `![Image name](image URL)`
- ![Sample](https://test-knowledge.support-project.org/open.account/icon/1)


## Insert the image into the article (upload the image copied to the clipboard directly)
- It is possible to paste directly into the text editing area


## Insert slide into article
- Uploading a slide (PDF) will allow you to press the 'Slideshow' button and press it to display a slideshow


## More info
- [GitHub Flavored Markdown](https://help.github.com/articles/github-flavored-markdown/) 
    " />

    <input type="hidden" id="sampleMarkdown-ja" value="# Markdown sample 
- ここに書かれているMarkdownのテキストが、下のDispley sampleに表示されるので、
  Markdownがどのように表示されるかのイメージが湧くと思います

## 見出し -> 「#」
- 「#」はタイトル
   - 「#」の後に、スペースを1つあけて、タイトルの文字を書く
   - 「#」の個数で大きさが変わる（個数が多くなるほど小さくなる）

# タイトル１
## タイトル２
### タイトル３
#### タイトル４

## 箇条書き -> 「-」
- 「-」は箇条書き
   - 「-」の後に、スペースを1つあけて、箇条書きの内容を書く
   - 「-」の前に、スペースを3つ置くと、インデントされて表示される
      - このように階層構造で表示される

## 箇条書き(番号付き) -> 「1.」
1. 「1.」は箇条書き(番号付き)
   1. 「1.」の後に、スペースを1つあけて、箇条書きの内容を書く
   1. 番号が付くこと以外「-」と同じ

## 強調 -> 「2つの*(アスタリスク)」
- 文の中の強調したい部分を「2つの*(アスタリスク)」で囲むと強調する
   - 例えば **こんなふうに** 強調される

## 罫線 -> 「3つ以上の*(アスタリスク)」
- 罫線を入れたい場合、「3つ以上の*(アスタリスク)」のみを記載する
***
- 上に罫線が表示される

## リンク
- リンクを入れたい部分に、[リンクの名前](リンク先のURL) を記載する
   - [Knowledge](https://information-knowledge.support-project.org)

## コードを表示
- コードを表示したい部分は「`（バッククオート）」で囲みます

```javascript
var msg = 'hoge'
alert(msg)
```

## 拡張Markdown syntax
- WebSiteの紹介を入れたい部分に[oembed WebSiteURL]を記載する
   - GitHub
      - [oembed https://github.com/support-project/knowledge]
   - SlideShare
      - [oembed http://www.slideshare.net/koda3/knowledge-information]
   - GoogleMap
      - [oembed https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d12966.919088372344!2d139.72177695!3d35.6590289!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x5bfe0248594cc802!2z5YWt5pys5pyo44OS44Or44K6!5e0!3m2!1sja!2sjp!4v1482300583922]

- 絵文字
   - `:` と `:` の間にキーを入れることで絵文字を表示します
   - `people nature objects places symbols` のリンクで絵文字の一覧を表示するので、そこから選択することも可能です
   - :+1: :smile:

- 他の記事へのリンク
   - `#` の後に記事のIDを入力すると、他の記事へのリンクになる
   - `#` を入力すると、リンク先の記事の選択肢が表示される
   - #1 ← 記事「１」へのリンク

- LaTeX形式で数式（ブロック表示）
   - `$$`で囲むと数式をブロック表示します
   - $$ e^{i\theta} = \cos\theta + i\sin\theta $$
   
- LaTeX形式で数式（インライン表示）
   - `$` で囲むとインライン表示
   - 有名なオイラーの公式は，$e^{i\theta}=\cos\theta+i\sin\theta$ です．

- LaTeX形式で数式（コードブロック）
   - コードブロック \`\`\`math の中は、直接数式を記載します（上記の'$'が必要無し）
   - `\\` が改行

```math
数式1: 

E = mc^2

\\

数式2: 
\sum_{n=1}^\infty \frac{1}{n^2} = \frac{\pi^2}{6}

\\


有名なオイラーの公式は，e^{i\theta}=\cos\theta+i\sin\theta です．


```


## 画像を記事内に挿入
- 画像をアップロードすると、「画像を表示」ボタンを押せるようになり、それを押すと画像が表示される
- 画像の表示は以下のような形式で記載する
   - `![画像名](画像のURL)`
- ![サンプル](https://test-knowledge.support-project.org/open.account/icon/1)


## 画像を記事内に挿入（クリップボードにコピーした画像を直接アップロード）
- テキストの編集エリアに、直接貼り付けることが可能です


## スライドを記事内に挿入
- スライド(PDF)をアップロードすると、「スライドショー」ボタンを押せるようになり、それを押すとスライドショー表示ができる


## さらに詳しく
- [GitHub Flavored Markdown](https://help.github.com/articles/github-flavored-markdown/) が参考になります
" />


  </span>
</template>

<script>
/* global $ */
import processFootnotesPotision from '../../../lib/displayParts/processFootnotesPotision'

export default {
  name: 'MarkdownSample',
  data () {
    return {
      article: {
        displaySafeHtml: '',
        content: ''
      }
    }
  },
  methods: {
    parse: function () {
      this.$store.dispatch('article/previewArticle', this.article)
      .then((result) => {
        this.article.displaySafeHtml = result
        setTimeout(() => {
          processFootnotesPotision($('.markdown-body'))
        }, 500)
      })
    }
  },
  mounted () {
    this.$nextTick(() => {
      if (this.$i18n.locale === 'ja') {
        this.article.content = $('#sampleMarkdown-ja').val()
      } else {
        this.article.content = $('#sampleMarkdown').val()
      }
      this.parse()
    })
  }
}
</script>

<style>
.viewarea {
  height: 300px;
  overflow-y: scroll;
  border: 1px solid gray;
  padding: 10px;
}
</style>

