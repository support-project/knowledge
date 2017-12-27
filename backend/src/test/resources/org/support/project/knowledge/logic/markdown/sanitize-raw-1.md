# テスト
- 簡単なmarkdown
- <font color="red">HTMLのタグを使える？</font>
   - インデント
   - 少しだけ複雑
- どうなるか？

## 見出し２
- Javascriptで簡単にパースしていたけど、Java側で実行した方が制御しやすいね
- 危険な<script>タグは使える？</script>

### Script 
<script>alert('hoge');</script>
<span onblur="alert('hoge');">これ通る？</span>
<p onblur="alert('hoge');">通らない</p>

- サニタイジングする

- Javascriptの変数の定義は `var a = "aaa";` です
- HTMLのタグは`<script>alaert('hoge')</script>`のように書けます

```ruby:qiita.rb
puts 'The best way to log and share programmers knowledge.'
```

```html
<button>hogehoge</button>
```

```java
private List<Object> params = new ArrayList<>();
```

```
<script>alert('hoge');</script>
```


