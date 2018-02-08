<template>
  <span>
    <span v-if="article.stocks && article.stocks.length > 0">
      <i class="fa fa-star-o"></i>
      <span v-for="stock in article.stocks" :key="stock.stockId">
        <a :href="'open.knowledge/stocks?stockid=' + stock.stockId">
            <span class="tag label label-default"><i class="fa fa-star"></i>{{stock.stockName}}</span>
        </a>
      </span>
    </span>
  </span>
</template>

<script>
export default {
  name: 'ArticlePartsStocks',
  props: ['article'],
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>

