<template>
  <span>
    <span v-if="article.tags && article.tags.length > 0">
      <i class="fa fa-tags"></i>
      <span v-for="tag in article.tags" :key="tag">
        <a :href="'open.knowledge/list?tagNames=' + tag" v-if="tag">
            <span class="tag label label-default"><i class="fa fa-tag"></i>{{tag}}</span>
        </a>
      </span>
    </span>
  </span>
</template>

<script>
export default {
  name: 'ArticlePartsTags',
  props: ['article'],
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>

