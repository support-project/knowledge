<template>
  <span>
    <i :class="'fa ' + (article.type ? article.type.icon : 'fa-book' )" aria-hidden="true"></i>
    {{article.type ? article.type.name : 'knowledge'}}
  </span>
</template>

<script>
export default {
  name: 'ArticlePartsTypeLabel',
  props: ['article'],
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>
