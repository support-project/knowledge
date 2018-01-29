<template>
  <div>
    <div v-for="flag in publicFlags" :key="flag.id">
      <label>
        <input type="radio" name="type" :value="flag.id" v-model="article.PublicFlag">
        <i :class="'fa ' + flag.icon" aria-hidden="true"></i>
        {{ $t(flag.name) }}
        {{article.PublicFlag}}
      </label>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import logger from 'logger'
const LABEL = 'ArticlePartsTarget.vue'

export default {
  name: 'ArticlePartsTarget',
  computed: {
    ...mapState([
      'resources',
      'article'
    ])
  },
  data () {
    logger.info(LABEL, JSON.stringify(this.article))
    return {
      publicFlags: [
        {id: 0, name: 'Article.PublicFlag.0', icon: 'fa-globe'},
        {id: 1, name: 'Article.PublicFlag.1', icon: 'fa-lock'},
        {id: 2, name: 'Article.PublicFlag.2', icon: 'fa-address-book-o'}
      ]
    }
  },
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>

<style>
</style>
