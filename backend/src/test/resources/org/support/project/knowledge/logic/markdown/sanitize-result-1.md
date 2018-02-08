# テスト
- 簡単なmarkdown
- <font color="red">HTMLのタグを使える&#xff1f;</font>
   - インデント
   - 少しだけ複雑
- どうなるか&#xff1f;

## 見出し&#xff12;
- Javascriptで簡単にパースしていたけど、Java側で実行した方が制御しやすいね
- 危険な

### Script 

これ通る&#xff1f;
<p>通らない</p>

- サニタイジングする

- Javascriptの変数の定義は `var a = "aaa";` です
- HTMLのタグは`&lt;script&gt;alaert('hoge')&lt;/script&gt;`のように書けます

```ruby:qiita.rb
puts 'The best way to log and share programmers knowledge.'
```

```html
&lt;button&gt;hogehoge&lt;/button&gt;
```

```java
private List&lt;Object&gt; params = new ArrayList&lt;&gt;();
```

```
&lt;script&gt;alert('hoge');&lt;/script&gt;
```