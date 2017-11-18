# Markdown sample 
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

```ruby
require 'redcarpet'
markdown = Redcarpet.new("Hello World!")
puts markdown.to_html
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

