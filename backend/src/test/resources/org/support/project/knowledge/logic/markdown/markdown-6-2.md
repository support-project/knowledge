# テスト
- 簡単なmarkdown
- <font color="red">HTMLのタグを使える？</font>
   - インデント
   - 少しだけ複雑
- どうなるか？

## 見出し２
- Javascriptで簡単にパースしていたけど、Java側で実行した方が制御しやすいね
- 危険な<script>タグは使える？</script>

1. テスト
2. ほげ
    1. テスト
    2. 階層
3. 数値


### Script 
<script>alert('hoge');</script>
<span onblur="alert('hoge');">これ通る？</span>
<p onblur="alert('hoge');">通らない</p>

- PegDownProcessorだけだと、そのまま出力する（XSSでやばそう）
- サニタイジングする

```ruby:qiita.rb
puts 'The best way to log and share programmers knowledge.'
```

```html
<button>hogehoge</button>
```

```java
private List<Object> params = new ArrayList<>();
```



