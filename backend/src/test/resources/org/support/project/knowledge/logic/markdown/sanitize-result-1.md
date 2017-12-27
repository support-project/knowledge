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

- Javascriptの変数の定義は &#96;var a &#61; &#34;aaa&#34;;&#96; です
- HTMLのタグは&#96;&lt;script&gt;alaert(&#39;hoge&#39;)&lt;/script&gt;&#96;のように書けます

&#96;&#96;&#96;ruby:qiita.rb
puts &#39;The best way to log and share programmers knowledge.&#39;
&#96;&#96;&#96;

&#96;&#96;&#96;html
&lt;button&gt;hogehoge&lt;/button&gt;
&#96;&#96;&#96;

&#96;&#96;&#96;java
private List&lt;Object&gt; params &#61; new ArrayList&lt;&gt;();
&#96;&#96;&#96;

&#96;&#96;&#96;
&lt;script&gt;alert(&#39;hoge&#39;);&lt;/script&gt;
&#96;&#96;&#96;